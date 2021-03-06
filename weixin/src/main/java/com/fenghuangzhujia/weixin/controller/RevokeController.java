package com.fenghuangzhujia.weixin.controller;

import javax.servlet.http.HttpServletRequest;

import me.chanjar.weixin.mp.api.WxMpService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fenghuangzhujia.weixin.service.TokenValidateService;

/**
 * 处理微信请求
 * @author pc
 *
 */
@RestController
public class RevokeController {
	
	@Autowired
	private WxMpService wxService;
	@Autowired
	private TokenValidateService tokenValidateService;

	@RequestMapping(value="/callback",method=RequestMethod.GET)
	public String revoke(HttpServletRequest request) {
		String signature=request.getParameter("signature");
		String timestamp=request.getParameter("timestamp");
		String nonce=request.getParameter("nonce");
		String echostr=request.getParameter("echostr");
		if(tokenValidateService.validateToken(signature, timestamp, nonce)) {
			return echostr;
		}
		return "failed";
	}
}
