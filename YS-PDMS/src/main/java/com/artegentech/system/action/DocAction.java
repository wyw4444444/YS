package com.artegentech.system.action;

import java.io.File;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.artegentech.system.service.IDocService;
import com.artegentech.system.vo.Dept;
import com.artegentech.system.vo.Doc;
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

	@RequiresUser
	@RequestMapping("add")
	public boolean add(HttpServletRequest request) throws Exception {
		Doc doc = new Doc();
		String part_code = request.getParameter("part_code");
		Integer version = Integer.parseInt(request.getParameter("version"));
		String change_reason = request.getParameter("change_reason");
		String changeimg_before = request.getParameter("changeimg_before");
		String changeimg_after = request.getParameter("changeimg_after");
		String doc_pdf = request.getParameter("doc_pdf");
		String doc_dwg = request.getParameter("doc_dwg");
		String doc_ppt = request.getParameter("doc_ppt");
		doc.setPart_code(part_code);
		doc.setVersion(version);
		doc.setChange_reason(change_reason);
		doc.setChangeimg_before(changeimg_before);
		doc.setChangeimg_after(changeimg_after);
		doc.setDoc_pdf(doc_pdf);
		doc.setDoc_dwg(doc_dwg);
		doc.setDoc_ppt(doc_ppt);
		return this.docService.add(doc);
	}
	@RequiresUser
	@RequestMapping("getMaxVersion")
	public Integer getMaxVersion(HttpServletRequest request) throws Exception {
//		Doc doc = new Doc();
		String part_code = request.getParameter("part_code");
//		doc.setPart_code(part_code);
		return this.docService.getMaxVersion(part_code);
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
		System.out.println("column : " + column);
		System.out.println("keyword : " + keyword);
		Map<String, Object> map = new HashMap<String, Object>();
		Long count = (long) 0;
		count = this.docService.getAllCount(column, keyword);
		List<Doc> result = new ArrayList<Doc>();
		result = this.docService.getAllSplit(column, keyword, currentPage, lineSize);
		map.put("count", count);
		map.put("list", result);
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
		map.put("count", count);
		map.put("list", result);
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
		map.put("count", count);
		map.put("list", result);
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
		map.put("count", count);
		map.put("list", result);
		JSONObject json = JSONObject.fromObject(map);
		return json;
	}

	@Override
	public String getType() {
		return null;
	}
}
