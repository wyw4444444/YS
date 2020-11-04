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

import com.artegentech.system.service.IDeptService;
import com.artegentech.system.vo.Dept;
import com.artegentech.util.JsonNullConvert;
import com.artegentech.util.action.AbstractAction;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

@Controller
@ResponseBody
@RequestMapping("dept/*")
public class DeptAction extends AbstractAction {
	@Resource
	private IDeptService deptService;

	@RequiresUser
	@RequestMapping("add")
	public boolean add(HttpServletRequest request) throws Exception {
		Dept dept = new Dept();
		String dept_code = request.getParameter("dept_code");
		String dept_name = request.getParameter("dept_name");
		System.out.println(dept_code + "," + dept_name);
		dept.setDept_code(dept_code);
		dept.setDept_name(dept_name);
		dept.setLocked(0);
		return this.deptService.add(dept);
	}

	@RequiresUser
	@RequestMapping("update")
	public boolean update(HttpServletRequest request) throws Exception {
		Dept dept = new Dept();
		Integer dept_id = Integer.parseInt(request.getParameter("dept_id"));
		String dept_code = request.getParameter("dept_code");
		String dept_name = request.getParameter("dept_name");
		Integer locked = Integer.parseInt(request.getParameter("locked"));
		dept.setDept_id(dept_id);
		dept.setDept_code(dept_code);
		dept.setDept_name(dept_name);
		dept.setLocked(locked);
		return this.deptService.edit(dept);
	}

	@RequiresUser
	@RequestMapping("checkDeptCode")
	public void checkDeptCode(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String dept_code = request.getParameter("dept_code");
		if (dept_code == null || "".equals(dept_code)) {
			super.print(response, false);
		} else {
			super.print(response, this.deptService.getByDeptCode(dept_code) == null);
		}
	}

	@RequestMapping("checkDeptName")
	@RequiresUser
	public void checkDeptName(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String dept_name = request.getParameter("dept_name");
		if (dept_name == null || "".equals(dept_name)) {
			super.print(response, false);
		} else {
			super.print(response, this.deptService.getByDeptName(dept_name) == null);
		}
	}

	@RequiresUser
	@SuppressWarnings("unchecked")
	@RequestMapping("listUnlocked")
	public List<Dept> listUnlocked() throws Exception {
		List<Dept> result = new ArrayList<Dept>();
		result = this.deptService.getAllUnlocked();

		JsonConfig jsonConfig = new JsonConfig();
		// jsonConfig.registerJsonValueProcessor(Date.class, new
		// JsonDateValueProcessor());
		JSONArray jsonArray = JSONArray.fromObject(result, jsonConfig);

		return jsonArray;
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
		// System.out.println("column : " + (column==null));
		// System.out.println("keyword : " + (keyword==null));
		Map<String, Object> map = new HashMap<String, Object>();
		Long count = (long) 0;
		count = this.deptService.getAllUnlockedCount();
		List<Dept> result = new ArrayList<Dept>();
		result = this.deptService.getAllUnlockedSplit(column, keyword, currentPage, lineSize);
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
		count = this.deptService.getAllCount(column, keyword);
		List<Dept> result = new ArrayList<Dept>();
		result = this.deptService.getAllSplit(column, keyword, currentPage, lineSize);
		map.put("count", count);
		map.put("list", result);
		JSONObject json = JSONObject.fromObject(map);
		return json;
	}

	@RequiresUser
	@SuppressWarnings("unchecked")
	@RequestMapping("list")
	public List<Dept> list() throws Exception {
		List<Dept> result = new ArrayList<Dept>();
		result = this.deptService.getAll();

		JsonConfig jsonConfig = new JsonConfig();
		// 有時間字段的話需要用到下面這句將DATE類型轉換為Json格式
		// jsonConfig.registerJsonValueProcessor(Date.class, new
		// JsonDateValueProcessor());
		JSONArray jsonArray = JSONArray.fromObject(result, jsonConfig);
		JsonNullConvert.filterNull(jsonArray);
		return jsonArray;
	}

	@RequiresUser
	@SuppressWarnings("unchecked")
	@RequestMapping("listByMid")
	public List<Dept> listByMid(HttpServletRequest request) throws Exception {
		String member_id = request.getParameter("member_id");
		List<Dept> result = new ArrayList<Dept>();
		result = this.deptService.getAllUnlockedByMid(member_id);

		JsonConfig jsonConfig = new JsonConfig();
		// 有時間字段的話需要用到下面這句將DATE類型轉換為Json格式
		// jsonConfig.registerJsonValueProcessor(Date.class, new
		// JsonDateValueProcessor());
		JSONArray jsonArray = JSONArray.fromObject(result, jsonConfig);

		return jsonArray;
	}

	@Override
	public String getType() {
		return null;
	}
}
