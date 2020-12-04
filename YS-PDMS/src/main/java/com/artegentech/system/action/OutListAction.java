package com.artegentech.system.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.artegentech.system.service.IOutListService;
import com.artegentech.system.vo.OutList;
import com.artegentech.util.JsonDateValueProcessor;
import com.artegentech.util.JsonNullConvert;
import com.artegentech.util.action.AbstractAction;

import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;

@Controller
@ResponseBody
@RequestMapping("outlist/*")
public class OutListAction extends AbstractAction {
	
	@Resource
	private IOutListService outListService;

	@RequiresUser
	@SuppressWarnings("unchecked")
	@RequestMapping("list")
	public List<OutList> list(HttpServletRequest request) throws Exception {
		List<OutList> result = new ArrayList<OutList>();
		result = this.outListService.getAll();

		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());
		JSONArray jsonArray = JSONArray.fromObject(result, jsonConfig);
		JsonNullConvert.filterNull(jsonArray);
		return jsonArray;
	}

	@Override
	public String getType() {
		return null;
	}

}
