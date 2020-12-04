package com.artegentech.system.action;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

import com.artegentech.system.service.IPDRDetailService;
import com.artegentech.system.service.IPDRLogService;
import com.artegentech.system.service.IPDRService;
import com.artegentech.system.service.IRoleService;
import com.artegentech.system.vo.PDR;
import com.artegentech.system.vo.PDRDetail;
import com.artegentech.system.vo.PDRLog;
import com.artegentech.system.vo.Role;
import com.artegentech.util.JsonNullConvert;
import com.artegentech.util.action.AbstractAction;


import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

@Controller
@ResponseBody
@RequestMapping("PDRDetail/*")
public class PDRDetailAction extends AbstractAction {
	@Resource
	private IPDRDetailService PDRDetailService;
	
	@Resource
	private IPDRService PDRService;

	@RequiresUser
	@RequestMapping("add")
	public boolean add(HttpServletRequest request) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		String receivedString = request.getParameter("data");
		String PDR_id = request.getParameter("PDR_id");
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class, new com.artegentech.util.JsonDateValueProcessor());
		JSONArray jsonArray = JSONArray.fromObject(receivedString, jsonConfig);
		
		List<PDRDetail> list = new ArrayList<PDRDetail>();
		
		this.PDRDetailService.removeByPDR_id(PDR_id);
		
		for (int i = 0; i < jsonArray.size(); i++) {
			JSONArray jsonArray2 = JSONArray.fromObject(jsonArray.get(i), jsonConfig);
			
			String task = (String) jsonArray2.get(0);
			String type = (String) jsonArray2.get(1);
			String start_date1 = (String) jsonArray2.get(2);
			Date start_date = null;
			System.out.println(start_date1);
			start_date = "NaN/aN/aN".equals(start_date1) ? null : sdf.parse(start_date1);
			
			String end_date1 = (String) jsonArray2.get(3);
			Date end_date = null;
			end_date = "NaN/aN/aN".equals(end_date1) ? null : sdf.parse(end_date1);
			
			String actual_end_date1 = (String) jsonArray2.get(4);
			Date actual_end_date = null;
			actual_end_date = "NaN/aN/aN".equals(actual_end_date1) ? null : sdf.parse(actual_end_date1);
			
			String dept = (String) jsonArray2.get(5);
			String person = (String) jsonArray2.get(6);
			String remark = (String) jsonArray2.get(7);
			
			PDRDetail p = new PDRDetail();
			p.setPDR_id(PDR_id);
			p.setTask(task);
			p.setType(type);
			p.setStart_date(start_date);
			p.setEnd_date(end_date);
			p.setActual_end_date(actual_end_date);
			p.setDept(dept);
			p.setPerson(person);
			p.setRemark(remark);
			
			list.add(p);
		}
		return this.PDRDetailService.add(list);
	}

	
	
	@RequestMapping("findByPDR_id")
	public JSONObject findByPDR_id(HttpServletRequest request) throws Exception {
		String PDR_id =request.getParameter("PDR_id");
		
		List<PDRDetail> result = this.PDRDetailService.findByPDR_id(PDR_id);
		JSONArray json = JSONArray.fromObject(result);
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class, new com.artegentech.util.JsonDateValueProcessor());
		JSONArray jsonArray = JSONArray.fromObject(json, jsonConfig);
		for (int i = 0; i < jsonArray.size(); i++) {
			JsonNullConvert.filterNull(jsonArray.getJSONObject(i));
		}
		
		PDR pdr= this.PDRService.getByPDR_id(PDR_id);
		JSONArray json2 = JSONArray.fromObject(pdr);
		JsonConfig jsonConfig2 = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class, new com.artegentech.util.JsonDateValueProcessor());
		JSONArray jsonArray2 = JSONArray.fromObject(json2, jsonConfig2);
		for (int i = 0; i < jsonArray2.size(); i++) {
			JsonNullConvert.filterNull(jsonArray2.getJSONObject(i));
		}
		
		Map<String, Object> map2 = new HashMap<String, Object>();
		map2.put("list", jsonArray);
		map2.put("PDR", jsonArray2);
		JSONObject json3 = JSONObject.fromObject(map2);
		return json3;
	}
	
	
	@RequestMapping("findByLate")
	public JSONObject findByLate(HttpServletRequest request) throws Exception {
		List<PDRDetail> result = this.PDRDetailService.findByLate();
		
		System.out.println(result);
		JSONArray json = JSONArray.fromObject(result);
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class, new com.artegentech.util.JsonDateValueProcessor());
		JSONArray jsonArray = JSONArray.fromObject(json, jsonConfig);
		for (int i = 0; i < jsonArray.size(); i++) {
			JsonNullConvert.filterNull(jsonArray.getJSONObject(i));
		}
		
		Map<String, Object> map2 = new HashMap<String, Object>();
		map2.put("list", jsonArray);
		JSONObject json2 = JSONObject.fromObject(map2);
		return json2;
	}
	
	@RequestMapping("findBy5Day")
	public JSONObject findBy5Day(HttpServletRequest request) throws Exception {
		List<PDRDetail> result = this.PDRDetailService.findByDays();
		
		System.out.println(result);
		JSONArray json = JSONArray.fromObject(result);
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class, new com.artegentech.util.JsonDateValueProcessor());
		JSONArray jsonArray = JSONArray.fromObject(json, jsonConfig);
		for (int i = 0; i < jsonArray.size(); i++) {
			JsonNullConvert.filterNull(jsonArray.getJSONObject(i));
		}
		
		Map<String, Object> map2 = new HashMap<String, Object>();
		map2.put("list", jsonArray);
		JSONObject json2 = JSONObject.fromObject(map2);
		return json2;
	}


	@Override
	public String getType() {
		return null;
	}

}
