package com.artegentech.action.shiro;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.artegentech.system.service.IMemberService;

import net.sf.json.JSONObject;

@Controller
@ResponseBody
public class LoginAction {
	
	@Resource
	private IMemberService memberService;
	
	@SuppressWarnings("unchecked")
	@RequestMapping("loginApp")
	public JSONObject loginApp(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 1、如果要想取得在Session中出現的驗證碼，則必須取得HttpSession數據
		Integer type = Integer.parseInt(request.getParameter("type"));
		String username = request.getParameter("mid");
		String password = null;
		if(type==1) {
			password = request.getParameter("password");
		}else if(type==2) {
			password = new String(Base64.decodeBase64(request.getParameter("password")), "UTF-8");
		}
		// 2、取得用戶提交表單過來的驗證碼資料
		System.out.println("username: " + username);
		//System.out.println("password: " + password);
		Subject currentUser = SecurityUtils.getSubject();
		System.out.println(currentUser);
		// 給username增加一個後綴，用來到realm時判斷是不是手機端訪問
		// username = username + "|app";
		UsernamePasswordToken token = new UsernamePasswordToken(username, password);
		Map<String, Object> map = new HashMap<String, Object>();
		// 开始进入shiro的认证流程
		try {
			token.setRememberMe(true);
			currentUser.login(token);
			System.out.println("currentUser: " + currentUser);
			// System.out.println("action vo : " + vo);
			Map<String, Object> map2 = this.memberService.listAuthByMember(username);
			Set<String> roles = (Set<String>) map2.get("allRoles");
			System.out.println("tokenId back : " + currentUser.getSession().getId());
			map.put("tokenId", currentUser.getSession().getId());
			//map.put("username", username.substring(0,username.length()-4));
			map.put("username", username);
			map.put("password", Base64.encodeBase64String(password.getBytes("UTF-8")));
			map.put("roles", roles);
			map.put("isLogin", true);
		}catch(Exception e) {
			e.printStackTrace();
			map.put("isLogin", false);
			map.put("token", token);
		}
		JSONObject json = JSONObject.fromObject(map);
		return json;
	}
	
	@RequestMapping("loginWeb")
	public JSONObject loginWeb(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//System.out.println("********************* loginWeb *************************");
		String username = request.getParameter("mid");
		String password = request.getParameter("password");
		Boolean rememberMe = Boolean.parseBoolean(request.getParameter("rememberMe"));
		// 2、取得用戶提交表單過來的驗證碼資料
		Subject currentUser = SecurityUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken(username, password);
		Map<String, Object> map = new HashMap<String, Object>();
		// 开始进入shiro的认证流程
		try {
			token.setRememberMe(rememberMe);
			currentUser.login(token);
			map.put("isLogin", true);
		}catch(Exception e) {
			e.printStackTrace();
			map.put("isLogin", false);
		}
		JSONObject json = JSONObject.fromObject(map);
		return json;
	}

}
