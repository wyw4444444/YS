package com.artegentech.system.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.artegentech.util.action.AbstractAction;
import com.artegentech.util.register.SystemRegister;

@Controller
public class IndexAction extends AbstractAction {
	
	private Map<String, Object> map = new HashMap<String, Object>();
	
	@RequestMapping("/index")
	public ModelAndView index(HttpServletResponse response) throws Exception {
		//System.out.println("************************* index.action *******************************");
		//map = SystemRegister.isRegisterLinux();
		map = SystemRegister.isRegister();
		//注释可修改不需注册使用
//		if((Boolean) map.get("flag")==false) {
//			return new ModelAndView(super.getValue("noregister.page"));
//		}
		return new ModelAndView(super.getValue("index.page"));
	}
	
	@RequestMapping("/registerpage")
	public ModelAndView registerpage(HttpServletResponse response) throws Exception {
		return new ModelAndView(super.getValue("registerpage.page"));
	}

	@Override
	public String getType() {
		return null;
	}
}
