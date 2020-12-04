package com.artegentech.system.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.artegentech.system.service.ICustomerService;
import com.artegentech.system.vo.Customer;
import com.artegentech.util.JsonDateValueProcessor;
import com.artegentech.util.JsonNullConvert;
import com.artegentech.util.action.AbstractAction;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

@Controller
@ResponseBody
@RequestMapping("customer/*")
public class CustomerAction extends AbstractAction {
	@Resource
	private ICustomerService customerService;

	@RequestMapping("listSplit")
	public JSONObject listSplit(HttpServletRequest request) throws Exception {
		Integer currentPage = Integer.parseInt(request.getParameter("currentPage"));
		Integer lineSize = Integer.parseInt(request.getParameter("lineSize"));
		String column = request.getParameter("column");
		String keyword = request.getParameter("keyword");
		if(column==null || "".equals(column) || "undefined".equals(column)) {
			column=null;
		}
		if(keyword==null || "".equals(keyword) || "undefined".equals(keyword)) {
			keyword=null;
		}
		System.out.println("column : " + column);
		System.out.println("keyword : " + keyword);
		Map<String, Object> map = new HashMap<String, Object>();
		Long count = (long) 0;
		count = this.customerService.getAllCount(column, keyword);
		
		
		List<Customer> result = new ArrayList<Customer>();
		result = this.customerService.getAllSplit(column, keyword, currentPage, lineSize);
		System.out.println(result);
		map.put("count", count);
		map.put("list1", result);
		JSONObject json = JSONObject.fromObject(map);
		return json;
	}
	
	
	@RequestMapping("add")
	public boolean add(HttpServletRequest request) throws Exception {
		Customer customer = new Customer();
		String short_name = request.getParameter("short_name");
		String full_name = request.getParameter("full_name");
		String member_id = request.getParameter("member_id");
		
		customer.setShort_name(short_name);
		customer.setFull_name(full_name);
		customer.setReg_time(new Date());
		customer.setMember_id(member_id);
		customer.setLocked(0);
		return this.customerService.add(customer);
	}
	
	@RequestMapping("update")
	public boolean update(HttpServletRequest request) throws Exception {
		Customer customer = new Customer();
		Integer no = Integer.parseInt(request.getParameter("no"));
		String short_name = request.getParameter("short_name");
		String full_name = request.getParameter("full_name");
		String member_id = request.getParameter("member_id");
		Integer locked = Integer.parseInt(request.getParameter("locked"));
		
		customer.setNo(no);
		customer.setShort_name(short_name);
		customer.setFull_name(full_name);
		customer.setReg_time(new Date());
		customer.setMember_id(member_id);
		customer.setLocked(locked);
		return this.customerService.edit(customer);
	}
	
	
	@RequestMapping("findByShort_name")
	public JSONObject findByShort_name(HttpServletRequest request) throws Exception {
		String short_name = request.getParameter("short_name");
		Customer L= this.customerService.findByShort_name(short_name);
		boolean result=true;
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		if (L==null) {
			result=false;
			map.put("list", "undefined");
		}else {
			result=true;
			
			JSONArray json1 = JSONArray.fromObject(L);
			JsonConfig jsonConfig1 = new JsonConfig();
			jsonConfig1.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());
			JSONArray jsonArray1 = JSONArray.fromObject(json1, jsonConfig1);
			for (int i = 0; i < jsonArray1.size(); i++) {
				JsonNullConvert.filterNull(jsonArray1.getJSONObject(i));
			}
			map.put("list", jsonArray1);
		}
		
		map.put("result", result);
		
		JSONObject json = JSONObject.fromObject(map);
		return json;
	}
	
	@Override
	public String getType() {
		// TODO Auto-generated method stub
		return null;
	}
}
