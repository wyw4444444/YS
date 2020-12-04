package com.artegentech.system.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.artegentech.system.service.IClosingAccountService;
import com.artegentech.system.vo.ClosingAccount;
import com.artegentech.util.JsonDateValueProcessor;
import com.artegentech.util.JsonNullConvert;
import com.artegentech.util.action.AbstractAction;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

@Controller
@ResponseBody
@RequestMapping("closingAccount/*")
public class ClosingAccountAction extends AbstractAction {

	@Resource
	private IClosingAccountService closingAccountService;
	
	@RequiresUser
	@RequestMapping("closing")
	public JSONObject closing(HttpServletRequest request) throws Exception {
		Date closing_date = new Date();
		String member_id = request.getParameter("member_id");
		ClosingAccount closingAccount = new ClosingAccount();
		closingAccount.setClosing_date(closing_date);
		closingAccount.setMember_id(member_id);
		Map<String, Object> result = new HashMap<String, Object>();
		if(this.closingAccountService.add(closingAccount)==true) {
			result.put("msg", "结账作业完毕！");
			result.put("result", true);
		}else {
			result.put("msg", "结账作业出错！");
			result.put("result", false);
		}
		JSONObject json = JSONObject.fromObject(result);
		return json;
	}

	/**
	 * 需要传回查找到的分页结果，并且传回总数据的行数
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequiresUser
	@RequestMapping("listSplit")
	public JSONObject listSplit(HttpServletRequest request) throws Exception {
		Integer currentPage = Integer.parseInt(request.getParameter("currentPage"));
		Integer lineSize = Integer.parseInt(request.getParameter("lineSize"));
		Map<String, Object> map = new HashMap<String, Object>();
		Long count = (long) 0;
		List<ClosingAccount> result = new ArrayList<ClosingAccount>();
		count = this.closingAccountService.getAllCount();
		result = this.closingAccountService.getAllSplit(currentPage, lineSize);
		map.put("count", count);
		map.put("list", result);
		JSONObject json = JSONObject.fromObject(map);
		JsonNullConvert.filterNull(json);
		return json;
	}

	@RequiresUser
	@SuppressWarnings("unchecked")
	@RequestMapping("list")
	public List<ClosingAccount> list(HttpServletRequest request) throws Exception {
		List<ClosingAccount> result = new ArrayList<ClosingAccount>();
		result = this.closingAccountService.getAll();

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
