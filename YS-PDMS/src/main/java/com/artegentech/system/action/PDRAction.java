package com.artegentech.system.action;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

import org.apache.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.artegentech.system.service.IPDRService;
import com.artegentech.system.service.IRoleService;
import com.artegentech.system.vo.PDR;
import com.artegentech.system.vo.Role;
import com.artegentech.util.JsonNullConvert;
import com.artegentech.util.UploadFileUtil;
import com.artegentech.util.action.AbstractAction;
import com.artegentech.util.JsonDateValueProcessor;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

@Controller
@ResponseBody
@RequestMapping("PDR/*")
public class PDRAction extends AbstractAction {

	@Resource
	private IRoleService roleService;
	
	@Resource
	private IPDRService PDRService;

	private Logger PDR = Logger.getLogger(PDRAction.class) ;
	
	public String path_PDR="/PDR-images/";
	
	@RequiresUser
	@RequestMapping("add")
	public boolean add(HttpServletRequest request) throws Exception {
		String PDR_id = request.getParameter("PDR_id");
		String PER_id = request.getParameter("PER_id");
		String description = request.getParameter("description");
		String PM = request.getParameter("pm");
		String man = request.getParameter("man");
		Integer customer_no = Integer.parseInt(request.getParameter("customer_no"));
		Integer type_id_status = Integer.parseInt(request.getParameter("type_id_status"));
		
		String start_date1 = request.getParameter("start_date");
		DateFormat format_start_date = new SimpleDateFormat("yyyy-MM-dd");
		Date start_date=null;
		if(start_date1!=null && !"".equals(start_date1)) {
			start_date = format_start_date.parse(start_date1);
		}
		
		String end_date1 = request.getParameter("end_date");
		DateFormat format_end_date = new SimpleDateFormat("yyyy-MM-dd");
		System.out.println("--------------------------------");
		System.out.println(PDR_id);
		Date end_date=null;
		if(end_date1!=null && !"".equals(end_date1)) {
			end_date = format_end_date.parse(end_date1);
		}
		
		PDR pdr = new PDR();
		pdr.setPDR_id(PDR_id);
		pdr.setPER_id(PER_id);
		pdr.setDescription(description);
		pdr.setCustomer_no(customer_no);
		pdr.setStart_date(start_date);
		pdr.setEnd_date(end_date);
		pdr.setPM(PM);
		pdr.setMan(man);
		pdr.setType_id_status(type_id_status);
		
		return this.PDRService.add(pdr);
	}

	@RequiresUser
	@RequestMapping("update")
	public boolean update(HttpServletRequest request) throws Exception {
		String PDR_id = request.getParameter("PDR_id");
		String description = request.getParameter("description");
		String PM = request.getParameter("pm");
		String man = request.getParameter("man");
		Integer customer_no = Integer.parseInt(request.getParameter("customer_no"));
		Integer type_id_status = Integer.parseInt(request.getParameter("type_id_status"));
		
		String start_date1 = request.getParameter("start_date");
		DateFormat format_start_date = new SimpleDateFormat("yyyy-MM-dd");
		Date start_date=null;
		if(start_date1!=null && !"".equals(start_date1)) {
			start_date = format_start_date.parse(start_date1);
		}
		
		String end_date1 = request.getParameter("end_date");
		DateFormat format_end_date = new SimpleDateFormat("yyyy-MM-dd");
		Date end_date=null;
		if(end_date1!=null && !"".equals(end_date1)) {
			end_date = format_end_date.parse(end_date1);
		}
		
		PDR pdr = new PDR();
		pdr.setPDR_id(PDR_id);
		pdr.setDescription(description);
		pdr.setCustomer_no(customer_no);
		pdr.setStart_date(start_date);
		pdr.setEnd_date(end_date);
		pdr.setPM(PM);
		pdr.setMan(man);
		pdr.setType_id_status(type_id_status);
		
		return this.PDRService.edit(pdr);
	}

	@RequestMapping("checkPDR_id")
	@RequiresUser
	public void checkPDR_id(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String PDR_id = request.getParameter("PDR_id");
		if (PDR_id == null || "".equals(PDR_id)) {
			super.print(response, false);
		} else {
			super.print(response, this.PDRService.getByPDR_id(PDR_id) == null);
		}
	}
	@RequestMapping("checkPER_id")
	@RequiresUser
	public JSONObject checkPER_id(HttpServletRequest request, HttpServletResponse response) throws Exception {

		Map<String, Object> map2 = new HashMap<String, Object>();
		String PDR_id = request.getParameter("PER_id");
		PDR pdr = this.PDRService.getByPDR_id(PDR_id);
		if(pdr==null) {
			map2.put("result", "0");
		}else {
			JSONArray json = JSONArray.fromObject(pdr);
			JsonConfig jsonConfig = new JsonConfig();
			jsonConfig.registerJsonValueProcessor(Date.class, new com.artegentech.util.JsonDateValueProcessor());
			JSONArray jsonArray = JSONArray.fromObject(json, jsonConfig);
			for (int i = 0; i < jsonArray.size(); i++) {
				JsonNullConvert.filterNull(jsonArray.getJSONObject(i));
			}
			map2.put("result", jsonArray);
		}
		JSONObject json2 = JSONObject.fromObject(map2);
		return json2;
	}

	@RequestMapping("findLast")
	@RequiresUser
	public JSONObject findLast(HttpServletRequest request) throws Exception {
		PDR result = this.PDRService.getLast();
		Map<String, Object> map2 = new HashMap<String, Object>();
		
		if(result !=null) {
			JSONArray json = JSONArray.fromObject(result);
			JsonConfig jsonConfig = new JsonConfig();
			jsonConfig.registerJsonValueProcessor(Date.class, new com.artegentech.util.JsonDateValueProcessor());
			JSONArray jsonArray = JSONArray.fromObject(json, jsonConfig);
			for (int i = 0; i < jsonArray.size(); i++) {
				JsonNullConvert.filterNull(jsonArray.getJSONObject(i));
			}
			map2.put("list", jsonArray);
		}else {
			map2.put("list", null);
		}
		
		JSONObject json2 = JSONObject.fromObject(map2);
		return json2;
	}

	
	@RequiresUser
	@RequestMapping("listSplit")
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
		if ("".equals(column) || column == null || "null".equalsIgnoreCase(column)) {
			map.put("column", null);
		} else {
			map.put("column", column);
		}
		
		if ("".equals(keyword) || "0".equals(keyword) || "null".equalsIgnoreCase(keyword)) {
			map.put("keyword", null);
		} else {
			map.put("keyword", "%" + keyword + "%");
		}
		
		
		String receivedString = request.getParameter("status");
		if(receivedString==null || "".equals(receivedString) || "undefined".equals(receivedString)) {
			receivedString=null;
		}
		System.out.println(receivedString);
		if(receivedString !=null) {
			JsonConfig jsonConfig = new JsonConfig();
			jsonConfig.registerJsonValueProcessor(Date.class, new com.artegentech.util.JsonDateValueProcessor());
			JSONArray jsonArray = JSONArray.fromObject(receivedString, jsonConfig);
			System.out.println(jsonArray);
			if(jsonArray==null || "".equals(jsonArray) || "undefined".equals(jsonArray) || jsonArray.size()==0) {
				jsonArray=null;
			}
			if (jsonArray == null) {
				map.put("orderstatusArray", null);
			} else {
				map.put("orderstatusArray", jsonArray);
			}
		}else {
			map.put("orderstatusArray", null);
		}
		
		
		long time =TimeUnit.SECONDS.convert(1,TimeUnit.DAYS);
		System.out.println("一天秒數為：" + time);
		
		
		Long count = (long) 0;
		count = this.PDRService.getAllCount(map);
		
		
		if ((currentPage - 1) * lineSize < 0) {
			map.put("start", 0);
		} else {
			map.put("start", (currentPage - 1) * lineSize);
		}
		map.put("lineSize", lineSize > 0 ? lineSize : 10);
		List<PDR> result = this.PDRService.getAllSplit(map);
		
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

	
	//加视频
	@RequestMapping("addpicture")
	public ModelAndView addpicture(MultipartFile file, HttpServletRequest request) throws Exception {
		String PDR_id = request.getParameter("PDR_id");
		String no = request.getParameter("no");
		
		System.out.println(PDR_id + no);
		if (!file.isEmpty()) {
			String filePath = request.getServletContext().getRealPath(path_PDR) 
					 + PDR_id + File.separator  + no + ".jpg";
			PDR.info("path:"+filePath);
			UploadFileUtil.save(file.getInputStream(), new File(filePath));
		}
		return null;
	}
}
