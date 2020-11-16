package com.artegentech.action.shiro;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.artegentech.system.service.IMemberService;
import com.artegentech.util.action.AbstractAction;
import com.artegentech.util.register.SystemRegister;

@Controller
public class ShiroMemberAction extends AbstractAction {

	@Resource
	private IMemberService memberService;

	@Autowired
	HttpServletRequest request;
	
	private Map<String, Object> map = new HashMap<String, Object>();

	@RequestMapping("/successUrl")
	public ModelAndView successUrl() throws Exception {
		//System.out.println("************************* successUrl.action *******************************");
		//map = SystemRegister.isRegisterLinux();
		map = SystemRegister.isRegister();
		if((Boolean) map.get("flag")==false) {
			return new ModelAndView(super.getValue("noregister.page"));
		}
		return new ModelAndView(super.getValue("shiro.successUrl.page"));
	}

	@RequestMapping("/loginUrl")
	public ModelAndView loginUrl() throws Exception {
		//System.out.println("************************* loginUrl.action *******************************");
		//map = SystemRegister.isRegisterLinux();
		map = SystemRegister.isRegister();
		if((Boolean) map.get("flag")==false) {
			return new ModelAndView(super.getValue("noregister.page"));
		}
		return new ModelAndView(super.getValue("shiro.loginUrl.page"));
	}

	@RequestMapping("/unauthUrl")
	public ModelAndView unauthUrl() throws Exception {
		//System.out.println("************************* unauthUrl.action *******************************");
		//map = SystemRegister.isRegisterLinux();
		map = SystemRegister.isRegister();
		if((Boolean) map.get("flag")==false) {
			return new ModelAndView(super.getValue("noregister.page"));
		}
		return new ModelAndView(super.getValue("shiro.unauthUrl.page"));
	}
	
	@RequestMapping("/logoutUrl")
	public ModelAndView logoutUrl() throws Exception {
		//map = SystemRegister.isRegisterLinux();
		map = SystemRegister.isRegister();
		if((Boolean) map.get("flag")==false) {
			return new ModelAndView(super.getValue("noregister.page"));
		}
		ModelAndView mav=new ModelAndView(super.getValue("shiro.logoutUrl.page"));
		super.setMsgAndUrl(mav, "shiro.logout.msg", "index.action");
		SecurityUtils.getSubject().logout();
		return mav;
	}

	@Override
	public String getType() {
		return null;
	}

}
