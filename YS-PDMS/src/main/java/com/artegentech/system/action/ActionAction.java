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

import com.artegentech.system.service.IActionService;
import com.artegentech.system.vo.Action;
import com.artegentech.util.JsonNullConvert;
import com.artegentech.util.action.AbstractAction;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

@Controller
@ResponseBody
@RequestMapping("action/*")
public class ActionAction extends AbstractAction {

	@Resource
	private IActionService actionService;

	@RequiresUser
	@RequestMapping("add")
	public boolean add(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Action action = new Action();
		String flag = request.getParameter("flag");
		String title = request.getParameter("title");
		System.out.println(flag + "," + title);
		action.setFlag(flag);
		action.setTitle(title);
		action.setLocked(0);
		return this.actionService.add(action);
	}

	@RequiresUser
	@RequestMapping("update")
	public boolean update(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Action action = new Action();
		Integer action_id = Integer.parseInt(request.getParameter("action_id"));
		String flag = request.getParameter("flag");
		String title = request.getParameter("title");
		Integer locked = Integer.parseInt(request.getParameter("locked"));
		action.setAction_id(action_id);
		action.setFlag(flag);
		action.setTitle(title);
		action.setLocked(locked);
		return this.actionService.edit(action);
	}

	@RequiresUser
	@RequestMapping("checkActionFlag")
	public void checkActionFlag(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String flag = request.getParameter("flag");
		if (flag == null || "".equals(flag)) {
			super.print(response, false);
		} else {
			super.print(response, this.actionService.getByActionFlag(flag) == null);
		}
	}

	@RequiresUser
	@RequestMapping("checkActionTitle")
	public void checkActionTitle(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String title = request.getParameter("title");
		if (title == null || "".equals(title)) {
			super.print(response, false);
		} else {
			super.print(response, this.actionService.getByActionTitle(title) == null);
		}
	}

	@RequiresUser
	@SuppressWarnings("unchecked")
	@RequestMapping("listUnlocked")
	public List<Action> listUnlocked() throws Exception {
		List<Action> result = new ArrayList<Action>();
		result = this.actionService.getAllUnlocked();

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
		count = this.actionService.getAllUnlockedCount();
		List<Action> result = new ArrayList<Action>();
		result = this.actionService.getAllUnlockedSplit(column, keyword, currentPage, lineSize);
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
		count = this.actionService.getAllCount(column, keyword);
		List<Action> result = new ArrayList<Action>();
		result = this.actionService.getAllSplit(column, keyword, currentPage, lineSize);
		map.put("count", count);
		map.put("list", result);
		JSONObject json = JSONObject.fromObject(map);
		return json;
	}

	@RequiresUser
	@SuppressWarnings("unchecked")
	@RequestMapping("list")
	public List<Action> list() throws Exception {
		List<Action> result = new ArrayList<Action>();
		result = this.actionService.getAll();

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
	public List<Action> listByMid(HttpServletRequest request) throws Exception {
		String member_id = request.getParameter("member_id");
		List<Action> result = new ArrayList<Action>();
		result = this.actionService.getAllByMid(member_id);

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
