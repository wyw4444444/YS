package com.artegentech.action.filter;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;

import com.artegentech.system.service.IMemberService;



/**
 * 在已有的Form認證授權器基礎上擴展一個新的子類
 * 
 * @author ATG
 */
public class CustomerFormAuthenticationFilter extends FormAuthenticationFilter {
	
	@Resource
	private IMemberService memberService;
	
	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
		//System.out.println("************************* ShiroFilter验证失败 *******************************");
		return super.onAccessDenied(request, response); // 操作繼續向後執行
	}

	@Override
	protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request,
			ServletResponse response) throws Exception {
		//System.out.println("************************* ShiroFilter登录成功 *******************************");
		return super.onLoginSuccess(token, subject, request, response);
	}
	
}
