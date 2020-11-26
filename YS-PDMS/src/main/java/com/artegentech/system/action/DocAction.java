package com.artegentech.system.action;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.artegentech.system.service.IBomService;
import com.artegentech.system.service.ICheckLogService;
import com.artegentech.system.service.IDocService;
import com.artegentech.system.vo.Bom;
import com.artegentech.system.vo.CheckLog;
import com.artegentech.system.vo.Dept;
import com.artegentech.system.vo.Doc;
import com.artegentech.system.vo.Part;
import com.artegentech.util.JsonDateValueProcessor;
import com.artegentech.util.JsonNullConvert;
import com.artegentech.util.UploadFileUtil;
import com.artegentech.util.action.AbstractAction;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

@Controller
@ResponseBody
@RequestMapping("doc/*")
public class DocAction extends AbstractAction {
	@Resource
	private IDocService docService;
	@Resource
	private ICheckLogService  checkLogService;
	@Resource
	private IBomService  bomService;

	@RequiresUser
	@RequestMapping("add")
	public boolean add(HttpServletRequest request) throws Exception {
		Doc doc = new Doc();
		String part_code = request.getParameter("part_code");
		String version = request.getParameter("version");
		String change_reason = request.getParameter("change_reason");
		String changeimg_before = request.getParameter("changeimg_before");
		String changeimg_after = request.getParameter("changeimg_after");
		String doc_pdf = request.getParameter("doc_pdf");
		String doc_dwg = request.getParameter("doc_dwg");
		String doc_ppt = request.getParameter("doc_ppt");
		String member_id = request.getParameter("member_id");
		doc.setPart_code(part_code);
		doc.setVersion(version);
		doc.setChange_reason(change_reason);
		doc.setChangeimg_before(changeimg_before);
		doc.setChangeimg_after(changeimg_after);
		doc.setDoc_pdf(doc_pdf);
		doc.setDoc_dwg(doc_dwg);
		doc.setDoc_ppt(doc_ppt);
		doc.setReg_time(new Date());
		doc.setMember_id(member_id);
		if(this.docService.add(doc)) {
			
			int id_check=doc.getId();
			CheckLog checklog=new CheckLog();
			checklog.setType_check("圖檔");
			checklog.setId_check(id_check);
			checklog.setReg_time_apply(new Date());

			checklog.setMember_id(member_id);
			checklog.setTips(part_code+"圖檔建立申请");
			checklog.setCheck_status(1);
			
			return this.checkLogService.add(checklog);
		}
		return false;
	}
	@RequiresUser
	@RequestMapping("updateDoc")
	public boolean updateDoc(HttpServletRequest request) throws Exception {
		Doc doc = new Doc();
		Integer id = Integer.parseInt(request.getParameter("id"));
		String part_code = request.getParameter("part_code");
		String version = request.getParameter("version");
		String change_reason = request.getParameter("change_reason");
		String changeimg_before = request.getParameter("changeimg_before");
		String changeimg_after = request.getParameter("changeimg_after");
		String doc_pdf = request.getParameter("doc_pdf");
		String doc_dwg = request.getParameter("doc_dwg");
		String doc_ppt = request.getParameter("doc_ppt");
		String member_id = request.getParameter("member_id");
		doc.setId(id);
		doc.setPart_code(part_code);
		doc.setVersion(version);
		doc.setChange_reason(change_reason);
		doc.setChangeimg_before(changeimg_before);
		doc.setChangeimg_after(changeimg_after);
		doc.setDoc_pdf(doc_pdf);
		doc.setDoc_dwg(doc_dwg);
		doc.setDoc_ppt(doc_ppt);
		doc.setReg_time(new Date());
		doc.setStatus(1);
		if(this.docService.updateDoc(doc)) {
			
			int id_check=doc.getId();
			CheckLog checklog=new CheckLog();
			checklog.setType_check("圖檔");
			checklog.setId_check(id_check);
			checklog.setReg_time_apply(new Date());

			checklog.setMember_id(member_id);
			checklog.setTips(part_code+"圖檔修改申请");
			checklog.setCheck_status(1);
			
			return this.checkLogService.add(checklog);
		}
		return false;
	}
	@RequiresUser
	@RequestMapping("getMaxVersion")
	public void getMaxVersion(HttpServletRequest request, HttpServletResponse response) throws Exception {
//		Doc doc = new Doc();
		String part_code = request.getParameter("part_code");
//		doc.setPart_code(part_code);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("success", true);
		map.put("result", this.docService.getMaxVersion(part_code));
		JSONObject json = JSONObject.fromObject(map);
		super.print(response, json); 
	}
	
	@RequestMapping("addpicture")
	public ModelAndView addpicture(MultipartFile file, HttpServletRequest request) throws Exception {
		String path = request.getParameter("path");
		
		if (!file.isEmpty()) {
//			String filePath = request.getServletContext().getRealPath("/PDR-images/") + PDR_id + File.separator  + no + ".jpg";
			String filePath = path;
//			PDR.info("path:"+filePath);
			UploadFileUtil.save(file.getInputStream(), new File(filePath));
		}
		return null;
	}
	@RequiresUser
	@RequestMapping("findAlldoc")
	public JSONObject findAlldoc(HttpServletRequest request) throws Exception {
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
		System.out.println("findAlldoccolumn : " + column);
		System.out.println("findAlldockeyword : " + keyword);
		Map<String, Object> map = new HashMap<String, Object>();
		Long count = (long) 0;
		count = this.docService.getAllCount(column, keyword);
		List<Doc> result = new ArrayList<Doc>();
		result = this.docService.getAllSplit(column, keyword, currentPage, lineSize);
		
		JSONArray json1 = JSONArray.fromObject(result);
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());
		JSONArray jsonArray = JSONArray.fromObject(json1, jsonConfig);
		for (int i = 0; i < jsonArray.size(); i++) {
			JsonNullConvert.filterNull(jsonArray.getJSONObject(i));
		}
		
		map.put("count", count);
		map.put("list", jsonArray);
		JSONObject json = JSONObject.fromObject(map);
		return json;
	}
	@RequiresUser
	@RequestMapping("findOnedoc")
	public Doc findOnedoc(HttpServletRequest request) throws Exception {
		String part_code = request.getParameter("part_code");
		Integer version = Integer.parseInt(request.getParameter("version"));
		Doc doc = this.docService.findOnedoc(part_code, version);
		return doc;
	}
	@RequiresUser
	@RequestMapping("findByPartCode")
	public JSONObject findByPartCode(HttpServletRequest request) throws Exception {
		String part_code = request.getParameter("part_code");
		Integer currentPage = Integer.parseInt(request.getParameter("currentPage"));
		Integer lineSize = Integer.parseInt(request.getParameter("lineSize"));
		List<Doc> result = this.docService.findByPartCode(part_code,currentPage,lineSize);
		Integer count = 0;
		count = result.size();
		Map<String, Object> map = new HashMap<String, Object>();
		JSONArray json1 = JSONArray.fromObject(result);
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());
		JSONArray jsonArray = JSONArray.fromObject(json1, jsonConfig);
		for (int i = 0; i < jsonArray.size(); i++) {
			JsonNullConvert.filterNull(jsonArray.getJSONObject(i));
		}
		
		map.put("count", count);
		map.put("list", jsonArray);
		JSONObject json = JSONObject.fromObject(map);
		return json;
	}
	@RequiresUser
	@RequestMapping("findNewByPartCode")
	public JSONObject findNewByPartCode(HttpServletRequest request) throws Exception {
		String part_code = request.getParameter("part_code");
		List<Doc> result = this.docService.findNewByPartCode(part_code);
		Integer count = 0;
		count = result.size();
		Map<String, Object> map = new HashMap<String, Object>();
		JSONArray json1 = JSONArray.fromObject(result);
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());
		JSONArray jsonArray = JSONArray.fromObject(json1, jsonConfig);
		for (int i = 0; i < jsonArray.size(); i++) {
			JsonNullConvert.filterNull(jsonArray.getJSONObject(i));
		}
		
		map.put("count", count);
		map.put("list", jsonArray);
		JSONObject json = JSONObject.fromObject(map);
		return json;
	}
	@RequiresUser
	@RequestMapping("findAllNewDoc")
	public JSONObject findAllNewDoc(HttpServletRequest request) throws Exception {
		List<Doc> result = this.docService.findAllNewDoc();
		Integer count = 0;
		count = result.size();
		Map<String, Object> map = new HashMap<String, Object>();
		JSONArray json1 = JSONArray.fromObject(result);
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());
		JSONArray jsonArray = JSONArray.fromObject(json1, jsonConfig);
		for (int i = 0; i < jsonArray.size(); i++) {
			JsonNullConvert.filterNull(jsonArray.getJSONObject(i));
		}
		
		map.put("count", count);
		map.put("list", jsonArray);
		JSONObject json = JSONObject.fromObject(map);
		return json;
	}
	@RequiresUser
	@RequestMapping("listDocProcessed")
	public JSONObject listDocProcessed(HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		Long count = (long) 0;
		
		List<Doc> result = this.docService.getDocByStatus(1);
		for (int i = 0; i < result.size(); i++) {
			CheckLog cl = this.docService.getCheckLogByDoc(result.get(i).getId());
			if(cl.getNo() != null) {
				result.get(i).setReg_time(cl.getReg_time_apply());
				result.get(i).setSpec(cl.getTips());
				result.get(i).setChange_reason(cl.getMember_id());
				
			}
		}
		
		JSONArray json1 = JSONArray.fromObject(result);
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());
		JSONArray jsonArray = JSONArray.fromObject(json1, jsonConfig);
		for (int i = 0; i < jsonArray.size(); i++) {
			JsonNullConvert.filterNull(jsonArray.getJSONObject(i));
		}
		map.put("count", count);
		map.put("list", jsonArray);
		JSONObject json = JSONObject.fromObject(map);
		return json;
	}
	
	@RequiresUser
	@RequestMapping("checkPartCode")
	public Part checkPartCode(HttpServletRequest request) throws Exception {
		Part part = new Part();
		String partcode = request.getParameter("partcode");
		System.out.println(partcode);
		part.setPart_code(partcode);
		return this.docService.checkPartCode(part);
	}
	@RequiresUser
	@RequestMapping("updateArchivesStatus")
	public boolean updateArchivesStatus(HttpServletRequest request) throws Exception {
//		Integer id_check = Integer.parseInt(request.getParameter("id_check_selects"));
//		String member_id = request.getParameter("member_id");
//		String status = request.getParameter("check_status");
////		添加checklog記錄
//		CheckLog checklog=new CheckLog();
//		checklog.setType_check("圖檔");
//		checklog.setId_check(id_check);
//		checklog.setReg_time_apply(new Date());
//
//		checklog.setMember_id(member_id);
//		checklog.setTips(part_code+"圖檔建立申请");
//		checklog.setCheck_status(1);
//		
//		this.checkLogService.add(checklog);
//		return this.docService.updateArchivesStatus(part);
		return false;
		
	}
	@RequiresUser
	@RequestMapping("doUpdateStatus")
	public boolean doUpdateStatus(HttpServletRequest request) throws Exception {
		String member_id = request.getParameter("member_id");
		Integer id = Integer.parseInt(request.getParameter("id"));
		Integer status = Integer.parseInt(request.getParameter("status"));
		Doc doc = new Doc();
		doc.setId(id);
		doc.setStatus(status);
		doc.setMember_id(member_id);
		return this.docService.updateStatus(doc);
	}
	@RequiresUser
	@RequestMapping("findById")
	public Doc findById(HttpServletRequest request) throws Exception {
		Integer id = Integer.parseInt(request.getParameter("id"));
		return this.docService.findById(id);
	}
	@RequiresUser
	@RequestMapping("findDocByBom")
	public JSONObject findDocByBom(HttpServletRequest request) throws Exception {
		String part_code = request.getParameter("part_code");
		List<Bom> result = new ArrayList<Bom>();
		result = this.bomService.getAllDownSplit(part_code);

		
		
		JSONArray json1 = JSONArray.fromObject(result);

		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());
		JSONArray jsonArray = JSONArray.fromObject(json1, jsonConfig);
		for (int i = 0; i < jsonArray.size(); i++) {
			JsonNullConvert.filterNull(jsonArray.getJSONObject(i));
		}

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", jsonArray);
		JSONObject json = JSONObject.fromObject(map);
		return json;
	}
	@Override
	public String getType() {
		return null;
	}
}
