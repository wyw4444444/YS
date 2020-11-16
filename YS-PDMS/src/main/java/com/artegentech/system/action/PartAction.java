package com.artegentech.system.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.artegentech.system.service.IPartService;
import com.artegentech.system.vo.Part;
import com.artegentech.util.JsonNullConvert;
import com.artegentech.util.action.AbstractAction;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

@Controller
@ResponseBody
@RequestMapping("part/*")
public class PartAction extends AbstractAction {
	@Resource
	private IPartService partService;

	@RequiresUser
	@RequestMapping("checkPartCode")
	public Part checkPartCode(HttpServletRequest request) throws Exception {
		Part part = new Part();
		String partcode = request.getParameter("partcode");
		System.out.println(partcode);
		part.setPart_code(partcode);
		return this.partService.checkPartCode(part);
	}


	@Override
	public String getType() {
		return null;
	}
}
