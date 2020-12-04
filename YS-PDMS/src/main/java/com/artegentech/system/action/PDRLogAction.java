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

import com.artegentech.system.service.IPDRLogService;
import com.artegentech.system.service.IPDRService;
import com.artegentech.system.service.IRoleService;
import com.artegentech.system.vo.PDR;
import com.artegentech.system.vo.PDRDetail;
import com.artegentech.system.vo.PDRLog;
import com.artegentech.system.vo.Role;
import com.artegentech.util.JsonNullConvert;
import com.artegentech.util.UploadFileUtil;
import com.artegentech.util.action.AbstractAction;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

@Controller
@ResponseBody
@RequestMapping("PDRLog/*")
public class PDRLogAction extends AbstractAction {
	@Resource
	private IPDRLogService PDRLogService;
	
	@Resource
	private IPDRService PDRService;

	public String path="/project-images/";
	
	private Logger log = Logger.getLogger(PDRLogAction.class) ;
	
	@RequiresUser
	@RequestMapping("add")
	public boolean add(HttpServletRequest request) throws Exception {
		String PDR_id = request.getParameter("PDR_id");
		String log = request.getParameter("log");
		String member_id = request.getParameter("member_id");
		
		String reg_time1 = request.getParameter("reg_time");
		DateFormat format_start_date = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Date reg_time=null;
		if(reg_time1!=null && !"".equals(reg_time1)) {
			reg_time = format_start_date.parse(reg_time1);
		}
		
		PDRLog pdrlog = new PDRLog();
		pdrlog.setPDR_id(PDR_id);
		pdrlog.setLog(log);
		pdrlog.setReg_time(reg_time);
		pdrlog.setMember_id(member_id);
		
		return this.PDRLogService.add(pdrlog);
	}
	
	//加视频
	@RequestMapping("addpicture")
	public ModelAndView addpicture(MultipartFile file, HttpServletRequest request) throws Exception {
		String PDR_id = request.getParameter("PDR_id");
		String reg_time1 = request.getParameter("reg_time");
		String no = request.getParameter("no");
		System.out.println(reg_time1);
		String reg_time=reg_time1.substring(0,10);
			
		if (!file.isEmpty()) {
			String filePath = request.getServletContext().getRealPath(path) 
					 + PDR_id + File.separator + reg_time + no + ".jpg";
			log.info("path:"+filePath);
			UploadFileUtil.save(file.getInputStream(), new File(filePath));
		}
		return null;
	}


	@RequestMapping("deletePicture")
	public boolean deleteESOP(HttpServletRequest request) throws Exception {
		String PDR_id = request.getParameter("PDR_id");
		String reg_time1 = request.getParameter("reg_time");
		System.out.println(PDR_id);
		String reg_time = reg_time1.substring(0,10);
		
		String filePath = request.getServletContext().getRealPath(path) + PDR_id;
		File file=new File(filePath);
        File[] tempList = file.listFiles();
        for (int i = 0; i < tempList.length; i++) {
        	String name = tempList[i].getName();
        	System.out.println(name);
        	if(reg_time.equals(name.substring(0,10))){
        		UploadFileUtil.delete(filePath + File.separator + name);
        	}
        }
		
		return true;
	}
	
	@RequestMapping("update")
	public boolean update(HttpServletRequest request) throws Exception {
		String log = request.getParameter("log");
		Integer no = Integer.parseInt(request.getParameter("no"));
		String reg_time1 = request.getParameter("reg_time");
		DateFormat format_start_date = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Date reg_time=null;
		if(reg_time1!=null && !"".equals(reg_time1)) {
			reg_time = format_start_date.parse(reg_time1);
		}
		PDRLog pdrlog = new PDRLog();
		pdrlog.setNo(no);
		pdrlog.setLog(log);
		pdrlog.setReg_time(reg_time);
		return this.PDRLogService.edit(pdrlog);
	}
	
	@RequestMapping("findByPDR_id")
	public JSONObject findByPDR_id(HttpServletRequest request) throws Exception {
		String PDR_id =request.getParameter("PDR_id");
		
		List<PDRLog> result = this.PDRLogService.findByPDR_id(PDR_id);
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
	
	@RequestMapping("removeByno")
	public boolean removeByno(HttpServletRequest request) throws Exception {
		Integer no =Integer.parseInt(request.getParameter("no"));
		return this.PDRLogService.removeByno(no);
	}
	
	@Override
	public String getType() {
		return null;
	}

}
