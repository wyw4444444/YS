package com.artegentech.system.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.artegentech.util.action.AbstractAction;
import com.artegentech.util.register.SystemRegister;

import net.sf.json.JSONObject;

@Controller
@ResponseBody
@RequestMapping("SystemRegisterAction/*")
public class SystemRegisterAction extends AbstractAction {
	
	@RequestMapping("registerSystem")
	public JSONObject registerSystem(HttpServletRequest request) throws Exception{
		String registerCode = request.getParameter("registerCode");
		String publicKey = request.getParameter("publicKey");
		Map<String, Object> map = new HashMap<String, Object>();
		map = SystemRegister.registerSystem(registerCode, publicKey);
		JSONObject json = JSONObject.fromObject(map);
		return json;
	}
	
	@RequestMapping("checkRegister")
	public JSONObject checkRegister(HttpServletRequest request) throws Exception{
		//String publicKey = request.getParameter("publicKey");
		System.out.println(request.getParameter("publicKey"));
		Map<String, Object> map = new HashMap<String, Object>();
		map = SystemRegister.isRegister();
		//System.out.println(map.toString());
		JSONObject json = JSONObject.fromObject(map);
		return json;
	}
	
	@RequestMapping("checkRegisterLinux")
	public JSONObject checkRegisterLinux(HttpServletRequest request) throws Exception{
		System.out.println(request.getParameter("publicKey"));
		Map<String, Object> map = new HashMap<String, Object>();
		map = SystemRegister.isRegisterLinux();
		JSONObject json = JSONObject.fromObject(map);
		return json;
	}
	
	@Override
	public String getType() {
		return null;
	}

}
