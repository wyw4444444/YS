package com.artegentech.system.action;

import java.util.ArrayList;
import java.util.Date;
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

import com.artegentech.system.service.ITypeService;
import com.artegentech.system.vo.Type;
import com.artegentech.util.JsonNullConvert;
import com.artegentech.util.action.AbstractAction;
import com.artegentech.util.JsonDateValueProcessor;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

@Controller
@ResponseBody
@RequestMapping("type/*")
public class TypeAction extends AbstractAction {
	@Resource
	private ITypeService typeService;

	@RequiresUser
	@RequestMapping("add")
	public boolean add(HttpServletRequest request) throws Exception {
		Type type = new Type();
		String parent_type = request.getParameter("parent_type");
		String sub_type = request.getParameter("sub_type");
		Integer upper_id=Integer.parseInt((String)request.getParameter("upper_id"));
		String member_id = request.getParameter("member_id");
		System.out.println(parent_type + "," + sub_type);
		type.setParent_type(parent_type);
		type.setSub_type(sub_type);
		type.setUpper_id(upper_id);
		type.setMember_id(member_id);
		type.setReg_time(new Date());
		type.setLocked(0);
		return this.typeService.add(type);
	}
	
	@RequiresUser
	@RequestMapping("checkFirstParent")
	public void checkFirstParent(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String parent_type = request.getParameter("parent_type");
		if (parent_type == null || "".equals(parent_type)) {
			super.print(response, false);
		} else {
			super.print(response, this.typeService.getByParentAndUpper(parent_type,0) == null);
		}
	}
	
	@RequiresUser
	@RequestMapping("checkParentAndSubAndUpper")
	public void checkParentAndSubAndUpper(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String parent_type = request.getParameter("parent_type");
		String sub_type = request.getParameter("sub_type");
		Integer upper_id=Integer.parseInt((String)request.getParameter("upper_id"));
		if (upper_id == null || parent_type == null || "".equals(parent_type)|| sub_type == null || "".equals(sub_type)) {
			super.print(response, false);
		} else {
			super.print(response, this.typeService.getByParentAndSubAndUpper(parent_type,sub_type,upper_id) == null);
		}
	}

	
	@RequiresUser
	@SuppressWarnings("unchecked")
	@RequestMapping("listAllParentType")
	public JSONObject listAllParentType(HttpServletRequest request) throws Exception {
		List<Type> result = new ArrayList<Type>();
		result = this.typeService.getAllParentType();
		

		JSONArray json1 = JSONArray.fromObject(result);
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());
		JSONArray jsonArray = JSONArray.fromObject(json1, jsonConfig);
		for (int i = 0; i < jsonArray.size(); i++) {
			JsonNullConvert.filterNull(jsonArray.getJSONObject(i));
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", jsonArray);
		JSONObject json = JSONObject.fromObject(map);
		return json;
	}
	
	/**
	 * 需要傳回查找到的首頁結果，並且傳回總數據的行數
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequiresUser
	@RequestMapping("listUnlockedSplit")
	public JSONObject listUnlockedSplit(HttpServletRequest request) throws Exception {
		Integer currentPage = Integer.parseInt(request.getParameter("currentPage"));
		Integer lineSize = Integer.parseInt(request.getParameter("lineSize"));
		String column = request.getParameter("column");
		String keyword = request.getParameter("keyword");
		if (column == null || "".equals(column) || "undefined".equals(column)) {
			column = null;
		}
		if (keyword == null || "".equals(keyword) || "undefined".equals(keyword)) {
			keyword = null;
		}
	
		Map<String, Object> map = new HashMap<String, Object>();
		Long count = (long) 0;
		count = this.typeService.getAllUnlockedCount();
		List<Type> result = new ArrayList<Type>();
		result = this.typeService.getAllUnlockedSplit(column, keyword, currentPage, lineSize);
		map.put("count", count);
		map.put("list", result);
		JSONObject json = JSONObject.fromObject(map);
		return json;
	}

	/**
	 * 需要傳回查找到的首頁結果，並且傳回總數據的行數
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequiresUser
	@RequestMapping("listSplit")
	public JSONObject listSplit(HttpServletRequest request) throws Exception {
		Integer currentPage = Integer.parseInt(request.getParameter("currentPage"));
		Integer lineSize = Integer.parseInt(request.getParameter("lineSize"));
		String column = request.getParameter("column");
		String keyword = request.getParameter("keyword");
		if (column == null || "".equals(column) || "undefined".equals(column)) {
			column = null;
		}
		if (keyword == null || "".equals(keyword) || "undefined".equals(keyword)) {
			keyword = null;
		}
		System.out.println("column : " + column);
		System.out.println("keyword : " + keyword);
		Map<String, Object> map = new HashMap<String, Object>();
		Long count = (long) 0;
		count = this.typeService.getAllCount(column, keyword);
		List<Type> result = new ArrayList<Type>();
		result = this.typeService.getAllSplit(column, keyword, currentPage, lineSize);
		map.put("count", count);
		map.put("list", result);
		JSONObject json = JSONObject.fromObject(map);
		return json;
	}


	
	@RequiresUser
	@RequestMapping("update")
	public boolean update(HttpServletRequest request) throws Exception {
		Type type = new Type();
		Integer id = Integer.parseInt(request.getParameter("id"));
		Integer locked = Integer.parseInt(request.getParameter("locked"));
		type.setId(id);
		type.setLocked(locked);
		return this.typeService.edit(type);
	}

	/**
	 * 根据初代类型名查询器其子类型
	 * @param request
	 * @return  
	 * @throws Exception
	 */
	@RequiresUser
	@SuppressWarnings("unchecked")
	@RequestMapping("listSubTypeByFirstType")
	public JSONObject listSubTypeByFirstType(HttpServletRequest request) throws Exception {
		String parent_type=request.getParameter("parent_type");
		List<Type> result = new ArrayList<Type>();
		result = this.typeService.getSubTypeByFirstType(parent_type);
		

		JSONArray json1 = JSONArray.fromObject(result);
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());
		JSONArray jsonArray = JSONArray.fromObject(json1, jsonConfig);
		for (int i = 0; i < jsonArray.size(); i++) {
			JsonNullConvert.filterNull(jsonArray.getJSONObject(i));
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", jsonArray);
		JSONObject json = JSONObject.fromObject(map);
		return json;
	}
	/**
	 * 根据上阶类型id查询器其子类型
	 * @param request
	 * @return
	 * @throws Exception
	 */
	
	@RequiresUser
	@SuppressWarnings("unchecked")
	@RequestMapping("listSubTypeByUpperID")
	public JSONObject listSubTypeByUpperID(HttpServletRequest request) throws Exception {
	
		Integer upper_id = Integer.parseInt(request.getParameter("upper_id"));
		
		List<Type> result = new ArrayList<Type>();
		result = this.typeService.getSubTypeByUpperID(upper_id);
		

		JSONArray json1 = JSONArray.fromObject(result);
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());
		JSONArray jsonArray = JSONArray.fromObject(json1, jsonConfig);
		for (int i = 0; i < jsonArray.size(); i++) {
			JsonNullConvert.filterNull(jsonArray.getJSONObject(i));
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", jsonArray);
		JSONObject json = JSONObject.fromObject(map);
		return json;
	}
	
	@Override
	public String getType() {
		return null;
	}
}
