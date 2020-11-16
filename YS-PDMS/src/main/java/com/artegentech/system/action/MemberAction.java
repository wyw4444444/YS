package com.artegentech.system.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresUser;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.artegentech.system.service.IDeptService;
import com.artegentech.system.service.IMemberService;
import com.artegentech.system.service.IRoleService;
import com.artegentech.system.vo.Member;
import com.artegentech.util.JsonDateValueProcessor;
import com.artegentech.util.action.AbstractAction;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

@Controller
@ResponseBody
@RequestMapping("member/*")
public class MemberAction extends AbstractAction {
	@Resource
	private IMemberService memberService;
	@Resource
	private IRoleService roleService;
	@Resource
	private IDeptService deptService;

	// ATGSYSTEM
	private static final String SALT = "QVRHU1lTVEVN";

	@RequiresUser
	@RequestMapping("add")
	public boolean Add(HttpServletRequest request) throws Exception {
		Member member = new Member();
		member.setMember_id(request.getParameter("member_id"));
		member.setMember_name(request.getParameter("member_name"));
		member.setReg_date(new Date());
		member.setLocked(0);
		Set<Integer> deptsSet = super.getSetByParam(request, "deptsSet");
		Set<Integer> rolesSet = super.getSetByParam(request, "rolesSet");
		return this.memberService.add(member, deptsSet, rolesSet);
	}

	@RequiresUser
	@RequestMapping("update")
	public boolean Update(HttpServletRequest request) throws Exception {
		Member member = new Member();
		member.setMember_id(request.getParameter("member_id"));
		member.setMember_name(request.getParameter("member_name"));
		member.setLocked(Integer.parseInt(request.getParameter("locked")));
		Set<Integer> deptsSet = super.getSetByParam(request, "deptsSet");
		Set<Integer> rolesSet = super.getSetByParam(request, "rolesSet");
		return this.memberService.edit(member, deptsSet, rolesSet);
	}

	@RequiresUser
	@RequestMapping("updatePassword")
	public boolean updatePassword(HttpServletRequest request) throws Exception {
		Member member = new Member();
		String member_id = request.getParameter("member_id");
		String new_password = request.getParameter("new_password");
		Integer setType = Integer.parseInt(request.getParameter("setType"));
		member = this.memberService.getById(member_id);
		if (setType == 1) {
			member.setPassword(new Md5Hash(new String(new_password.getBytes()), SALT, 5).toString());
		} else if (setType == 2) {
			String old_password = request.getParameter("old_password");
			if (new Md5Hash(new String(old_password.getBytes()), SALT, 5).toString().equals(member.getPassword())) {
				member.setPassword(new Md5Hash(new String(new_password.getBytes()), SALT, 5).toString());
			} else {
				return false;
			}
		}
		return this.memberService.editPassword(member);
	}

	@RequestMapping("checkMemberId")
	@RequiresUser
	public void checkMemberId(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String member_id = request.getParameter("member_id");
		if (member_id == null || "".equals(member_id)) {
			super.print(response, false);
		} else {
			super.print(response, this.memberService.getById(member_id) == null);
		}
	}

	@RequiresUser
	@SuppressWarnings("unchecked")
	@RequestMapping("listUnlocked")
	public List<Member> listUnlocked() throws Exception {
		List<Member> result = new ArrayList<Member>();
		result = this.memberService.getAllUnlocked();

		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());
		JSONArray jsonArray = JSONArray.fromObject(result, jsonConfig);
		return jsonArray;
	}

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
		count = this.memberService.getAllUnlockedCount(column, keyword);
		List<Member> result = new ArrayList<Member>();
		result = this.memberService.getAllUnlockedSplit(column, keyword, currentPage, lineSize);
		map.put("count", count);
		map.put("list", result);

		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());
		JSONObject json = JSONObject.fromObject(map, jsonConfig);
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
		System.out.println("currentPage : " + currentPage);
		Map<String, Object> map = new HashMap<String, Object>();
		Long count = (long) 0;
		count = this.memberService.getAllCount(column, keyword);
		List<Member> result = new ArrayList<Member>();
		result = this.memberService.getAllSplit(column, keyword, currentPage, lineSize);
		map.put("count", count);
		map.put("list", result);

		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());
		JSONObject json = JSONObject.fromObject(map, jsonConfig);
		return json;
	}

	@RequiresUser
	@SuppressWarnings("unchecked")
	@RequestMapping("list")
	public List<Member> list() throws Exception {
		List<Member> result = new ArrayList<Member>();
		result = this.memberService.getAll();

		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());
		JSONArray jsonArray = JSONArray.fromObject(result, jsonConfig);
		return jsonArray;
	}

	@RequiresUser
	@RequestMapping("getByMid")
	public Member getByMid(HttpServletRequest request) throws Exception {
		String member_id = request.getParameter("param");
		return this.memberService.getById(member_id);
	}

	@RequiresUser
	@RequestMapping("getByMidAll")
	public Member getByMidAll(HttpServletRequest request) throws Exception {
		String member_id = request.getParameter("param");
		return this.memberService.getByIdAll(member_id);
	}

	@RequiresUser
	@SuppressWarnings("unchecked")
	@RequestMapping("listUnlockedByRoleFlag")
	public List<Member> listUnlockedByRoleFlag(HttpServletRequest request) throws Exception {
		String flag = request.getParameter("flag");
		List<Member> result = new ArrayList<Member>();
		result = this.memberService.getAllUnlockedByRoleFlag(flag);

		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());
		JSONArray jsonArray = JSONArray.fromObject(result, jsonConfig);
		return jsonArray;
	}

	@RequiresUser
	@RequestMapping("getActiveUserRolesActions")
	public Map<String, Object> getActiveUserRolesActions(HttpServletRequest request) throws Exception {
		String member_id = request.getParameter("member_id");
		Map<String, Object> map = new HashMap<String, Object>();
		map = this.memberService.listAuthByMember(member_id);
		return map;
	}

	@Override
	public String getType() {
		return null;
	}
}
