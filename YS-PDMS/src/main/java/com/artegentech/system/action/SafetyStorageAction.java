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

import com.artegentech.system.service.ISafetyStorageService;
import com.artegentech.system.vo.SafetyStorage;
import com.artegentech.util.JsonDateValueProcessor;
import com.artegentech.util.JsonNullConvert;
import com.artegentech.util.action.AbstractAction;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

@Controller
@ResponseBody
@RequestMapping("safetyStorage/*")
public class SafetyStorageAction extends AbstractAction {

	@Resource
	private ISafetyStorageService safetyStorageService;

	@RequiresUser
	@RequestMapping("add")
	public boolean add(HttpServletRequest request) throws Exception {
		SafetyStorage safetyStorage = new SafetyStorage();
		String part_code = request.getParameter("part_code");
		Double safety_stock = Double.parseDouble(request.getParameter("safety_stock"));
		String member_id = request.getParameter("member_id");
		
		// 此处需增加判断part_code是否存在的语句，如果part_code存在，再向下执行
		
		Date data_date = new Date();
		safetyStorage.setPart_code(part_code);
		safetyStorage.setSafety_stock(safety_stock);
		safetyStorage.setMember_id(member_id);
		safetyStorage.setData_date(data_date);
		return this.safetyStorageService.add(safetyStorage);
	}

	@RequiresUser
	@SuppressWarnings("unchecked")
	@RequestMapping("list")
	public List<SafetyStorage> list() throws Exception {
		List<SafetyStorage> result = new ArrayList<SafetyStorage>();
		result = this.safetyStorageService.getAll();

		JsonConfig jsonConfig = new JsonConfig();
		// 有時間字段的話需要用到下面這句將DATE類型轉換為Json格式
		jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());
		JSONArray jsonArray = JSONArray.fromObject(result, jsonConfig);
		JsonNullConvert.filterNull(jsonArray);
		return jsonArray;
	}

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
		Map<String, Object> map = new HashMap<String, Object>();
		Long count = (long) 0;
		count = this.safetyStorageService.getAllCount(column, keyword);
		List<SafetyStorage> result = new ArrayList<SafetyStorage>();
		result = this.safetyStorageService.getAllSplit(column, keyword, currentPage, lineSize);
		map.put("count", count);
		map.put("list", result);
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());
		JSONObject json = JSONObject.fromObject(map, jsonConfig);
		return json;
	}

	@RequiresUser
	@SuppressWarnings("unchecked")
	@RequestMapping("listLatest")
	public List<SafetyStorage> listLatest() throws Exception {
		List<SafetyStorage> result = new ArrayList<SafetyStorage>();
		result = this.safetyStorageService.getAllLatest();

		JsonConfig jsonConfig = new JsonConfig();
		// 有時間字段的話需要用到下面這句將DATE類型轉換為Json格式
		jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());
		JSONArray jsonArray = JSONArray.fromObject(result, jsonConfig);
		JsonNullConvert.filterNull(jsonArray);
		return jsonArray;
	}

	@RequiresUser
	@RequestMapping("listLatestSplit")
	public JSONObject listLatestSplit(HttpServletRequest request) throws Exception {
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
		count = this.safetyStorageService.getAllLatestCount();
		List<SafetyStorage> result = new ArrayList<SafetyStorage>();
		result = this.safetyStorageService.getAllLatestSplit(currentPage, lineSize);
		map.put("count", count);
		map.put("list", result);
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());
		JSONObject json = JSONObject.fromObject(map, jsonConfig);
		return json;
	}

	@Override
	public String getType() {
		return null;
	}
}
