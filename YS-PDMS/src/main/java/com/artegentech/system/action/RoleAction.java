package com.artegentech.system.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.artegentech.system.service.IRoleService;
import com.artegentech.system.vo.Role;
import com.artegentech.util.JsonNullConvert;
import com.artegentech.util.action.AbstractAction;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

@Controller
@ResponseBody
@RequestMapping("role/*")
public class RoleAction extends AbstractAction {

	@Resource
	private IRoleService roleService;

	@RequiresUser
	@RequestMapping("add")
	public boolean add(HttpServletRequest request) throws Exception {
		Role role = new Role();
		String flag = request.getParameter("flag");
		String title = request.getParameter("title");
		System.out.println(flag + "," + title);
		role.setFlag(flag);
		role.setTitle(title);
		role.setLocked(0);
		Set<Integer> actionsSet = super.getSetByParam(request, "actionsSet");
		// System.out.println(actionsSet);
		return this.roleService.add(role, actionsSet);
	}

	@RequiresUser
	@RequestMapping("update")
	public boolean update(HttpServletRequest request) throws Exception {
		Role role = new Role();
		Integer role_id = Integer.parseInt(request.getParameter("role_id"));
		String flag = request.getParameter("flag");
		String title = request.getParameter("title");
		Integer locked = Integer.parseInt(request.getParameter("locked"));
		Set<Integer> actionsSet = super.getSetByParam(request, "actionsSet");
		if (actionsSet == null) {
			System.out.println("没有传递role对应的action数据");
		}
		role.setRole_id(role_id);
		role.setFlag(flag);
		role.setTitle(title);
		role.setLocked(locked);
		return this.roleService.edit(role, actionsSet);
	}

	@RequestMapping("checkRoleFlag")
	@RequiresUser
	public void checkRoleFlag(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String flag = request.getParameter("flag");
		if (flag == null || "".equals(flag)) {
			super.print(response, false);
		} else {
			super.print(response, this.roleService.getByRoleFlag(flag) == null);
		}
	}

	@RequestMapping("checkRoleTitle")
	@RequiresUser
	public void checkRoleTitle(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String title = request.getParameter("title");
		if (title == null || "".equals(title)) {
			super.print(response, false);
		} else {
			super.print(response, this.roleService.getByRoleTitle(title) == null);
		}
	}

	@RequiresUser
	@SuppressWarnings("unchecked")
	@RequestMapping("listUnlocked")
	public List<Role> listUnlocked() throws Exception {
		List<Role> result = new ArrayList<Role>();
		result = this.roleService.getAllUnlocked();

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
		count = this.roleService.getAllUnlockedCount(column, keyword);
		List<Role> result = new ArrayList<Role>();
		result = this.roleService.getAllUnlockedSplit(column, keyword, currentPage, lineSize);
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
		if ("action_id".equals(column)) {
			// 前台選擇的是以權限flag或權限title查找，傳到後台是action_id
			count = this.roleService.getAllCountByActionId(Integer.parseInt(keyword));
			List<Role> result = new ArrayList<Role>();
			result = this.roleService.getAllSplitByActionId(column, keyword, currentPage, lineSize);
			map.put("count", count);
			map.put("list", result);
		} else {
			// 前台選擇的是以角色flag或角色title查找
			count = this.roleService.getAllCount(column, keyword);
			List<Role> result = new ArrayList<Role>();
			result = this.roleService.getAllSplit(column, keyword, currentPage, lineSize);
			map.put("count", count);
			map.put("list", result);
		}
		JSONObject json = JSONObject.fromObject(map);
		return json;
	}

	@RequiresUser
	@SuppressWarnings("unchecked")
	@RequestMapping("list")
	public List<Role> list() throws Exception {
		List<Role> result = new ArrayList<Role>();
		result = this.roleService.getAll();

		JsonConfig jsonConfig = new JsonConfig();
		// 有時間字段的話需要用到下面這句將DATE類型轉換為Json格式
		// jsonConfig.registerJsonValueProcessor(Date.class, new
		// JsonDateValueProcessor());
		JSONArray jsonArray = JSONArray.fromObject(result, jsonConfig);
		JsonNullConvert.filterNull(jsonArray);
		return jsonArray;
	}

	@RequiresUser
	@RequestMapping("listByMid")
	public JSONArray listByMid(HttpServletRequest request) throws Exception {
		String member_id = request.getParameter("member_id");
		Set<Role> result = new HashSet<Role>();
		result = this.roleService.getByMid(member_id);

		JSONArray jsonArray = JSONArray.fromObject(result.toString());

		return jsonArray;
	}

	@RequiresUser
	@RequestMapping("getByRid")
	public Role getByRid(HttpServletRequest request) throws Exception {
		Integer role_id = Integer.parseInt(request.getParameter("param"));
		return this.roleService.getById(role_id);
	}

	@Override
	public String getType() {
		return null;
	}

}
