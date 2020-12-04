package com.artegentech.system.action;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.artegentech.system.service.IPDRService;
import com.artegentech.system.service.IRecordService;
import com.artegentech.system.service.IRoleService;
import com.artegentech.system.vo.PDR;
import com.artegentech.system.vo.PDRLog;
import com.artegentech.system.vo.Record;
import com.artegentech.system.vo.Role;
import com.artegentech.util.JsonNullConvert;
import com.artegentech.util.action.AbstractAction;
import com.artegentech.util.JsonDateValueProcessor;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

@Controller
@ResponseBody
@RequestMapping("record/*")
public class RecordAction extends AbstractAction {

	@Resource
	private IRecordService recordService;
	

	
	@RequestMapping("listSplit")
	public JSONObject findByPDR_id(HttpServletRequest request) throws Exception {
		String get1=request.getParameter("reg_time");
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
		Timestamp beginDate = new Timestamp(sdf1.parse(get1).getTime());
		long time =TimeUnit.MILLISECONDS.convert(1,TimeUnit.DAYS);
		
		long end=sdf1.parse(get1).getTime() + time;
		Timestamp endDate = new Timestamp(end);
		System.out.println("一天秒數為：" + time);
		
		Integer currentPage = Integer.parseInt(request.getParameter("currentPage"));
		Integer lineSize = Integer.parseInt(request.getParameter("lineSize"));
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		
		map.put("beginDate", beginDate);
		map.put("endDate", endDate);
		Long count = (long) 0;
		count = this.recordService.getAllCount(map);
		
		
		if ((currentPage - 1) * lineSize < 0) {
			map.put("start", 0);
		} else {
			map.put("start", (currentPage - 1) * lineSize);
		}
		map.put("lineSize", lineSize > 0 ? lineSize : 10);
		List<Record> result = this.recordService.getAllSplit(map);
		
		JSONArray json = JSONArray.fromObject(result);
		JsonConfig jsonConfig2 = new JsonConfig();
		jsonConfig2.registerJsonValueProcessor(Date.class, new com.artegentech.util.JsonDateValueProcessor());
		JSONArray jsonArray2 = JSONArray.fromObject(json, jsonConfig2);
		for (int i = 0; i < jsonArray2.size(); i++) {
			JsonNullConvert.filterNull(jsonArray2.getJSONObject(i));
		}
		
		Map<String, Object> map2 = new HashMap<String, Object>();
		map2.put("count", count);
		map2.put("list", jsonArray2);
		JSONObject json2 = JSONObject.fromObject(map2);
		return json2;
	}
	

	@Override
	public String getType() {
		return null;
	}

}
