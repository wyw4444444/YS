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

import com.artegentech.system.service.IPDRCostService;
import com.artegentech.system.service.IPDRDetailService;
import com.artegentech.system.service.IPDRLogService;
import com.artegentech.system.service.IPDRService;
import com.artegentech.system.service.IRoleService;
import com.artegentech.system.vo.PDR;
import com.artegentech.system.vo.PDRCost;
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
@RequestMapping("PDRCost/*")
public class PDRCostAction extends AbstractAction {
	@Resource
	private IPDRDetailService PDRDetailService;
	
	@Resource
	private IPDRCostService PDRCostService;
	
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
		
		List<PDRCost> list = new ArrayList<PDRCost>();
		
		this.PDRCostService.removeByPDR_id(PDR_id);
		
		for (int i = 0; i < jsonArray.size(); i++) {
			JSONArray jsonArray2 = JSONArray.fromObject(jsonArray.get(i), jsonConfig);
			
			String name = (String) jsonArray2.get(0);
			String PAR = (String) jsonArray2.get(1);
			String type = (String) jsonArray2.get(2);
			String description = (String) jsonArray2.get(3);
//			判斷了預計費用和實際費用為空時的操作
			float estimate_cost =!((String)jsonArray2.get(4)).equals("")?Float.parseFloat((String) jsonArray2.get(4)):0;
			float actual_cost = !((String)jsonArray2.get(5)).equals("")?Float.parseFloat((String) jsonArray2.get(5)):0;
			String remark = (String) jsonArray2.get(6);
			
			PDRCost p = new PDRCost();
			p.setPDR_id(PDR_id);
			p.setName(name);
			p.setPAR(PAR);
			p.setType(type);
			p.setDescription(description);
			p.setEstimate_cost(estimate_cost);
			p.setActual_cost(actual_cost);
			p.setRemark(remark);
			list.add(p);
		}
		return this.PDRCostService.add(list);
	}

	
	
	@RequestMapping("findByPDR_id")
	public JSONObject findByPDR_id(HttpServletRequest request) throws Exception {
		String PDR_id =request.getParameter("PDR_id");
		
		List<PDRCost> result = this.PDRCostService.findByPDR_id(PDR_id);
		if(result.size()!=0) {
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
		}else {
			return null;
		}
	}
	

	@Override
	public String getType() {
		return null;
	}

}
