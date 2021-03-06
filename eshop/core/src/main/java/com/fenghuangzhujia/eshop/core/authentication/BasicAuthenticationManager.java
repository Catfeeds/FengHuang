package com.fenghuangzhujia.eshop.core.authentication;

import static com.fenghuangzhujia.eshop.core.base.SystemErrorCodes.*;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fenghuangzhujia.eshop.core.authentication.token.TokenRepository;
import com.fenghuangzhujia.eshop.core.authentication.token.UserToken;
import com.fenghuangzhujia.eshop.core.base.Dics;
import com.fenghuangzhujia.eshop.core.base.SystemErrorCodes;
import com.fenghuangzhujia.eshop.core.couponsDef.CouponsAllocater;
import com.fenghuangzhujia.eshop.core.user.User;
import com.fenghuangzhujia.eshop.core.user.UserRepository;
import com.fenghuangzhujia.eshop.core.validate.BasicValidater;
import com.fenghuangzhujia.eshop.core.validate.core.Validater;
import com.fenghuangzhujia.eshop.core.validate.dao.DaoValidaterService;
import com.fenghuangzhujia.eshop.core.validate.message.MessageManager;
import com.fenghuangzhujia.foundation.core.rest.ErrorCodeException;
import com.fenghuangzhujia.foundation.dics.CategoryItem;
import com.fenghuangzhujia.foundation.dics.CategoryItemRepository;
import com.fenghuangzhujia.foundation.media.MediaContent;
import com.fenghuangzhujia.foundation.media.MediaContentRepository;
import com.fenghuangzhujia.foundation.utils.Identities;
import com.fenghuangzhujia.foundation.utils.validater.UsernameValidater;
import com.qq.connect.QQConnectException;
import com.qq.connect.api.OpenID;
import com.qq.connect.api.qzone.UserInfo;
import com.qq.connect.javabeans.AccessToken;
import com.qq.connect.javabeans.qzone.UserInfoBean;
import com.qq.connect.oauth.Oauth;

@Service(value="authenticateService")
@Transactional
public class BasicAuthenticationManager implements AuthenticationManager {
	
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private TokenRepository tokenRepository;
	@Autowired
	private CouponsAllocater couponsEventBus;
	@Autowired
	private MessageManager messageManager;
	@Autowired
	private DaoValidaterService daoValidaterService;
	@Autowired
	private CategoryItemRepository categoryItemRepository;
	@Autowired
	private MediaContentRepository mediaContentRepository;
	
	/**
	 * 确保加载全部权限信息
	 */
	@Override
	public SimpleUserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		User user=userRepository.getByUsername(username);
		if(user==null) {
			throw new UsernameNotFoundException("not found");
		}
		return new SimpleUserDetails(user);
	}
	
	/**
	 * 确保加载全部权限信息
	 */
	@Override
	public SimpleUserDetails authenticate(String token) throws ErrorCodeException {
		UserToken tk=tokenRepository.getByToken(token);
		if(tk==null) {
			throw new ErrorCodeException(TOKEN_ERROR);
		}
		User user=tk.getUser();
		//保证用户通过认证
		if(!user.isVerified())throw new ErrorCodeException(TOKEN_ERROR,"该用户尚未通过认证");
		return new SimpleUserDetails(user);
	}

	@Override
	public UserToken login(String username, String password, String ip) throws ErrorCodeException {
		User user=findUser(username);
		if(user==null) {
			throw new ErrorCodeException(LOGIN_ERROR,"用户名不存在");
		}
		//确保用户经过认证
		if(!user.isVerified())throw new ErrorCodeException(LOGIN_ERROR, "用户尚未通过认证，请先注册");
		String pwd=user.getPassword();
		if(!ENCODER.matches(password, pwd)) {
			throw new ErrorCodeException(LOGIN_ERROR, "密码错误，请重新输入");
		}
		user.setLoginTime(new Date());//保存用户登录时间
		if(StringUtils.isNotBlank(ip)) {
			user.setLoginip(ip);//保存用户登录ip
		}		
	    userRepository.save(user);
		UserToken token=tokenRepository.getByUser(user);
		if(token==null) {
			token=new UserToken();		
			token.setUser(user);
			token=refreshToken(token);
			return token;
		}
		token=refreshToken(token);
		return token;
	}
	
	@Override
	public void logout(String token) {
		UserToken tk=tokenRepository.getByToken(token);
		if(tk!=null) {
			tokenRepository.delete(tk);
		}
	}

	@Override
	public UserToken regist(String username, String password, String mobile, String ip)
			throws ErrorCodeException {
		try {
			checkAccount(username);
			checkPassword(password);
		} catch (Exception e) {
			throw new ErrorCodeException(REGIST_ERROR, e);
		}
		User user=userRepository.getByUsername(username);
		if(user!=null) {
			throw new ErrorCodeException(SystemErrorCodes.REGIST_ERROR, "该用户名已被注册！");
		}
		user=userRepository.getByMobile(mobile);
		if(user!=null) {
			throw new ErrorCodeException(SystemErrorCodes.REGIST_ERROR, "改手机号已被使用，一个手机号限一个用户使用！");
		}
		user=new User();
		user.setUsername(username);
		user.setPassword(password);
		user.setVerified(true);
		user.setRegIp(ip);//保存用户注册id
		user.setRegTime(new Date());//保存用户注册时间
		user.setLoginip(ip);
		user.setLoginTime(new Date());
		user.setMobile(mobile);
		entryptPassword(user);
		user=userRepository.save(user);
		UserToken token=tokenRepository.getByUser(user);
		if(token==null){
			token=new UserToken();
			token.setUser(user);
		}
		token=refreshToken(token);
		//触发注册的优惠券分发事件
		couponsEventBus.allocate(CouponsAllocater.REGIST, user.getId());
		
		return token;
	}
	
	/**
	 * 设定安全的密码，生成随机的salt并经过1024次 sha-1 hash
	 */
	private void entryptPassword(User user) {
		String password=AuthenticationManager.encryptPassword(user.getPassword());
		user.setPassword(password);
	}

	@Override
	public UserToken changePassword(String username, String newPassword,
			String oldPassword) throws ErrorCodeException {
		try {
			UserToken t=login(username, oldPassword, null);
			User user=t.getUser();
			checkPassword(newPassword);
			user.setPassword(newPassword);
			entryptPassword(user);
			userRepository.save(user);
			return t;
		} catch (Exception e) {
			throw new ErrorCodeException(CHANGE_PASSWORD_ERROR, e);
		}
	}
	
	@Override
	public String forgetPassword(String username, String telephone) throws ErrorCodeException {
		User user=userRepository.getByUsername(username);
		if(user==null) throw new ErrorCodeException(SystemErrorCodes.ILLEGAL_ARGUMENT, "用户名不存在");
		if(!telephone.equals(user.getMobile()))
			throw new ErrorCodeException(SystemErrorCodes.ILLEGAL_ARGUMENT, "请使用绑定手机号");
		String code=Identities.uuid2();
		BasicValidater validater2=new BasicValidater(code,username,10);//以生成的uuid为id
		daoValidaterService.add(validater2);
		return code;
	}
	
	@Override
	public void changeForgotPassword(String username, String validater,
			String password) throws ErrorCodeException {
		Validater validater2=daoValidaterService.get(validater);
		validater2.validate(validater, username);
		User user=userRepository.getByUsername(username);
		if(user==null) throw new ErrorCodeException(SystemErrorCodes.ILLEGAL_ARGUMENT, "用户名不存在");
		try {
			checkPassword(password);
		} catch (Exception e) {
			throw new ErrorCodeException(CHANGE_PASSWORD_ERROR, e);
		}
		
		user.setPassword(password);
		entryptPassword(user);
		userRepository.save(user);
	}
	
	/**
	 * qq登录
	 * @param token
	 * @return
	 */
	public UserToken qqLogin(HttpServletRequest request) {
		try {
			
			AccessToken token = (new Oauth()).getAccessTokenByRequest(request);
			if (token.getAccessToken().equals("")) {
               throw new ErrorCodeException(SystemErrorCodes.OTHER, "没有获取到响应参数");
			}
			//从qq端获取openId
			String accessToken = token.getAccessToken();
			//Long tokenExpireIn = token.getExpireIn();
			OpenID openIDObj =  new OpenID(accessToken);
	        String openID = openIDObj.getUserOpenID();
	        
	        //如果用户已存在，返回token
	        User user=userRepository.getByQqid(openID);
	        if(user!=null) {
	        	UserToken userToken=tokenRepository.getByUser(user);
	        	if(userToken==null) {
	        		userToken=new UserToken();
	        		userToken.setUser(user);	        		
	        	}
	        	userToken=refreshToken(userToken);
	        	return userToken;
	        }
	        
	        //如果用户不存在，根据qq账户内容，生成新用户
	        UserInfo qzoneUserInfo = new UserInfo(accessToken, openID);
	        UserInfoBean userInfoBean = qzoneUserInfo.getUserInfo();
	        if (userInfoBean.getRet() == 0) {
	        	//String sex=userInfoBean.getGender();
	        	String cname=userInfoBean.getNickname();	        	   	
	        	user=new User();
	        	
	        	String gender=userInfoBean.getGender();	     
	        	List<CategoryItem> genders=categoryItemRepository.findByCategoryType(Dics.SEX);
	        	for (CategoryItem categoryItem : genders) {
					if(categoryItem.getName().equals(gender)) {
						user.setSex(categoryItem);
					}
				}
	        	
	        	String avatar=userInfoBean.getAvatar().getAvatarURL100();
	        	MediaContent media=new MediaContent();
	        	media.setLink(avatar);
	        	media=mediaContentRepository.save(media);	        	
	        	user.setAvatar(media);
	        	
	        	user.setQqid(openID);
	        	user.setCnname(cname);
	        	user.setVerified(true);
	        	user=userRepository.save(user);
	        	UserToken userToken=new UserToken();
	        	userToken.setUser(user);
	        	userToken=refreshToken(userToken);
	        	return userToken;
	        } else {
				throw new ErrorCodeException(SystemErrorCodes.OTHER, "很抱歉，我们没能正确获取到您的信息，原因是： " + userInfoBean.getMsg());
			}
	        
		} catch (QQConnectException e) {
			throw new ErrorCodeException(SystemErrorCodes.QQ_CONNECTION_ERROR, e);
		}		
	}
	
	/**
	 * 根据用户名或者手机号码找到系统用户
	 * @param principal
	 * @return
	 */
	protected User findUser(String principal) {
		User user=userRepository.getByUsername(principal);
		if(user!=null) return user;
		user=userRepository.getByMobile(principal);
		return user;
	}
	
	/**
	 * 生成新token，并将更新持久化。
	 * @param token
	 * @return
	 */
	protected UserToken refreshToken(UserToken token) {
		String tokenString=Identities.uuid2();
		token.setToken(tokenString);
		token=tokenRepository.save(token);
		return token;
	}
	
	
	/**
	 * 检查账号，如果符合规则，返回true
	 * @param account
	 * @return
	 */
	protected void checkAccount(String account) throws Exception {
		if(!UsernameValidater.isUsername(account)) {
			throw new Exception("请输入2~10位数字、英文字母、下划线或者汉字组成的用户名");
		}
	}
	/**
	 * 检查密码，如果符合规则，返回true
	 * @param account
	 * @return
	 */
	protected void checkPassword(String password) throws Exception {
		
	}
}
