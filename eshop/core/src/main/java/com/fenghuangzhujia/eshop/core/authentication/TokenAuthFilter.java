package com.fenghuangzhujia.eshop.core.authentication;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

@Component(value="tokenAuthFilter")
public class TokenAuthFilter extends GenericFilterBean {
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	private static final String TOKEN_NAME="token";

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {		
		SecurityContext context=SecurityContextHolder.getContext();
		Authentication oldAuthentication=context.getAuthentication();
		//如果用户已经通过session方式登录，不会再次验证
		if(oldAuthentication!=null && oldAuthentication.isAuthenticated()) {
			chain.doFilter(request, response);
			return;
		}
		
		String token=getToken((HttpServletRequest)request);		
		if(token==null) {
			chain.doFilter(request, response);
			return;
		}
		//标识是否需要清除Authentication，防止重复请求接口产生大量Authentication
		boolean shouldClean=false;
		try {
			SimpleUserDetails user=authenticationManager.authenticate(token);
			if(user!=null) {
				UsernamePasswordAuthenticationToken authentication=
						new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(), user.getAuthorities());	
				authentication.setDetails(user);
				context.setAuthentication(authentication);
				SecurityContextHolder.setContext(context);
				shouldClean=true;			
			}
		} catch (Exception e) {
			logger.debug(e.getMessage(), e);
		}
		chain.doFilter(request, response);
		if(shouldClean) {
			SecurityContextHolder.clearContext();
		}
	}
	
	/**
	 * 负责从ServletRequest中解析token，如果没有符合规则的token，返回<code>null</code>
	 * @param request
	 * @return
	 */
	protected String getToken(HttpServletRequest request) {
		String token=(String)request.getParameter(TOKEN_NAME);
		return token;
	}
}
