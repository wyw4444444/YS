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

import com.artegentech.system.service.IKnowledgeService;
import com.artegentech.system.vo.Doc;
import com.artegentech.system.vo.Knowledge_part;
import com.artegentech.system.vo.Knowledge;
import com.artegentech.system.vo.Knowledge_part_knowledge;
import com.artegentech.system.vo.Knowledge_total;
import com.artegentech.util.JsonNullConvert;
import com.artegentech.util.UploadFileUtil;
import com.artegentech.util.action.AbstractAction;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

@Controller
@ResponseBody
@RequestMapping("knowledge/*")
public class KnowledgeAction extends AbstractAction {
	@Resource
	private IKnowledgeService knowledgeService;

	@RequiresUser
	@RequestMapping("addPart")
	public Integer addPart(HttpServletRequest request) throws Exception {
		Knowledge_part Knowledge_part = new Knowledge_part();
		String idStr = (request.getParameter("id"));
		Integer id = 0;
		System.out.println(idStr+"1112"+id);
		if(idStr!=null&&!"".equals(idStr)) { 
			id = Integer.parseInt(idStr);
			Knowledge_part.setId(id);
		}
		System.out.println(idStr+"1112"+id);
		String part_code = request.getParameter("part_code");
		String version = request.getParameter("version");
		String name = request.getParameter("name");
		String desc = request.getParameter("desc");
		String date = request.getParameter("date");
		String file = request.getParameter("file");
		Knowledge_part.setPart_code(part_code);
		Knowledge_part.setDesc(desc);
		Knowledge_part.setDate(date);
		Knowledge_part.setName(name);
		Knowledge_part.setFile(file);
		Knowledge_part.setVersion(version);
//		兩種情況：1、存在id時，代表是修改行為，做修改操作；2、不存在id時，代表添加或者升版，做新建操作
		if(id!=0) {
//			成為返回id
			if(this.knowledgeService.updateKnowledge_part(Knowledge_part)) {
				return id;
			}else {
				return 0;				
			}
		}else {
//			成為返回id
			if(this.knowledgeService.addPart(Knowledge_part)) {
				return this.knowledgeService.findOnePart(part_code, version).getId();
			}else {
				return 0;
			}
		}
		
			
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
	@RequestMapping("findProcessed")
	public JSONObject findProcessed(HttpServletRequest request) throws Exception {
		String part_code = request.getParameter("part_code");
		Integer currentPage = Integer.parseInt(request.getParameter("currentPage"));
		Integer lineSize = Integer.parseInt(request.getParameter("lineSize"));
		List<Knowledge_part> result = this.knowledgeService.findProcessed(part_code,currentPage,lineSize);
		Integer count = 0;
		count = result.size();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("count", count);
		map.put("list", result);
		JSONObject json = JSONObject.fromObject(map);
		return json;
	}
	@RequiresUser
	@RequestMapping("findAllPart")
	public JSONObject findAllPart(HttpServletRequest request) throws Exception {
		String part_code = request.getParameter("part_code");
		Integer currentPage = Integer.parseInt(request.getParameter("currentPage"));
		Integer lineSize = Integer.parseInt(request.getParameter("lineSize"));
		List<Knowledge_part> result = this.knowledgeService.findAllPart(part_code,currentPage,lineSize);
		Integer count = 0;
		count = result.size();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("count", count);
		map.put("list", result);
		JSONObject json = JSONObject.fromObject(map);
		return json;
	}
	@RequiresUser
	@RequestMapping("findOnePart")
	public Knowledge_part findOnePart(HttpServletRequest request) throws Exception {
		String part_code = request.getParameter("part_code");
		String version = request.getParameter("version");
		Knowledge_part Knowledge_part = this.knowledgeService.findOnePart(part_code, version);
		return Knowledge_part;
	}
	@RequiresUser
	@RequestMapping("updatePartPass")
	public boolean updatePartPass(HttpServletRequest request) throws Exception {
		String part_code = request.getParameter("part_code");
		String version = request.getParameter("version");
		Knowledge_part Knowledge_part = new Knowledge_part();
		Knowledge_part.setPart_code(part_code);
		Knowledge_part.setVersion(version);
		boolean rs = this.knowledgeService.updatePartPass(Knowledge_part);
		return rs;
	}
	@RequiresUser
	@RequestMapping("updatePartReject")
	public boolean updatePartReject(HttpServletRequest request) throws Exception {
		String part_code = request.getParameter("part_code");
		String version = request.getParameter("version");
		Knowledge_part Knowledge_part = new Knowledge_part();
		Knowledge_part.setPart_code(part_code);
		Knowledge_part.setVersion(version);
		boolean rs = this.knowledgeService.updatePartReject(Knowledge_part);
		return rs;
	}
	@RequiresUser
	@RequestMapping("add")
	public boolean add(HttpServletRequest request) throws Exception {
		Knowledge Knowledge = new Knowledge();
		String idStr = (request.getParameter("id"));
		Integer id = 0;
		System.out.println(idStr+"2222"+id);
		if(idStr!=null&&!"".equals(idStr)) { 
			id = Integer.parseInt(idStr);
			Knowledge.setId(id);
		}
		String part_code = request.getParameter("part_code");
		String part_type = request.getParameter("part_type");
		String version = request.getParameter("version");
		String name = request.getParameter("part_name");
		String desc = request.getParameter("desc");
		String date = request.getParameter("date");
		String liststr = request.getParameter("partArr");
		System.out.println(liststr);
		JSONArray list=JSONArray.fromObject(liststr);
		Knowledge.setPart_code(part_code);
		Knowledge.setPart_name(name);
		Knowledge.setPart_type(part_type);
		Knowledge.setDate(date);
		Knowledge.setDescr(desc);
		Knowledge.setVersion(version);
		boolean rs=false;
		if(id==0) {
			if(this.knowledgeService.add(Knowledge)=="success") {
				Integer knowledge_id = this.knowledgeService.findOneKnowledge(part_code, version).getId();
				//获得jsonArray的第一个元素
				for(int i=0;i<list.size();i++) {
					Object o=list.get(i);
					JSONObject jsonObject2=JSONObject.fromObject(o);
					Knowledge_part_knowledge Knowledge2=(Knowledge_part_knowledge)JSONObject.toBean(jsonObject2, Knowledge_part_knowledge.class);
					Knowledge2.setKnowledge_id(knowledge_id.toString());
//				存入分階關係表
					this.knowledgeService.addKnowledge_part_knowledge(Knowledge2);
				}
				rs=true;
			}
		}else {
			if(this.knowledgeService.updateKnowledge(Knowledge)) {
//				先刪除舊的關係
				this.knowledgeService.deleteKnowledgeRelation(id);
				Integer knowledge_id = id;
				//获得jsonArray的第一个元素
				for(int i=0;i<list.size();i++) {
					Object o=list.get(i);
					JSONObject jsonObject2=JSONObject.fromObject(o);
					Knowledge_part_knowledge Knowledge2=(Knowledge_part_knowledge)JSONObject.toBean(jsonObject2, Knowledge_part_knowledge.class);
					Knowledge2.setKnowledge_id(knowledge_id.toString());
//				存入分階關係表
					this.knowledgeService.addKnowledge_part_knowledge(Knowledge2);
				}
				rs=true;
			}
		}
		return rs;
	}
	@RequiresUser
	@RequestMapping("findAllKnowledge")
	public JSONObject findAllKnowledge(HttpServletRequest request) throws Exception {
		String part_code = request.getParameter("part_code");
		Integer currentPage = Integer.parseInt(request.getParameter("currentPage"));
		Integer lineSize = Integer.parseInt(request.getParameter("lineSize"));
		List<Knowledge> result = this.knowledgeService.findAllKnowledge(part_code,currentPage,lineSize);
		Integer count = 0;
		count = result.size();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("count", count);
		map.put("list", result);
		JSONObject json = JSONObject.fromObject(map);
		return json;
	}
	@RequiresUser
	@RequestMapping("findPartByCode")
	public JSONObject findPartByCode(HttpServletRequest request) throws Exception {
		String part_code = request.getParameter("part_code");
		Integer currentPage = Integer.parseInt(request.getParameter("currentPage"));
		Integer lineSize = Integer.parseInt(request.getParameter("lineSize"));
		String status = request.getParameter("status");
		List<Knowledge_part> result = this.knowledgeService.findPartByCode(part_code,status,currentPage,lineSize);
		Integer count = 0;
		count = result.size();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("count", count);
		map.put("list", result);
		JSONObject json = JSONObject.fromObject(map);
		return json;
	}
	@RequiresUser
	@RequestMapping("findPartById")
	public Knowledge_part findPartById(HttpServletRequest request) throws Exception {
		Integer id = Integer.parseInt(request.getParameter("id"));
		Knowledge_part result = this.knowledgeService.findPartById(id);
		return result;
	}
	@RequiresUser
	@RequestMapping("findKnowledgeById")
	public Knowledge findKnowledgeById(HttpServletRequest request) throws Exception {
		Integer id = Integer.parseInt(request.getParameter("id"));
		Knowledge result = this.knowledgeService.findKnowledgeById(id);
		return result;
	}
	@RequiresUser
	@RequestMapping("findKnowledgeByCode")
	public JSONObject findKnowledgeByCode(HttpServletRequest request) throws Exception {
		String part_code = request.getParameter("part_code");
		Integer currentPage = Integer.parseInt(request.getParameter("currentPage"));
		Integer lineSize = Integer.parseInt(request.getParameter("lineSize"));
		String status = request.getParameter("status");
		List<Knowledge> result = this.knowledgeService.findKnowledgeByCode(part_code,status,currentPage,lineSize);
		Integer count = 0;
		count = result.size();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("count", count);
		map.put("list", result);
		JSONObject json = JSONObject.fromObject(map);
		return json;
	}
	@RequiresUser
	@RequestMapping("findOneKnowledge")
	public Knowledge findOneKnowledge(HttpServletRequest request) throws Exception {
		String part_code = request.getParameter("part_code");
		String version = request.getParameter("version");
		Knowledge Knowledge = this.knowledgeService.findOneKnowledge(part_code, version);
		return Knowledge;
	}
	@RequiresUser
	@RequestMapping("findPartList")
	public List<Knowledge_part> findPartList(HttpServletRequest request) throws Exception {
		String knowledge_id = request.getParameter("knowledge_id");
		List<Knowledge_part> Knowledge = this.knowledgeService.findPartList(knowledge_id);
		return Knowledge;
	}
	@RequiresUser
	@RequestMapping("updateKnowledgePass")
	public boolean updateKnowledgePass(HttpServletRequest request) throws Exception {
		String part_code = request.getParameter("part_code");
		String version = request.getParameter("version");
		Knowledge Knowledge = new Knowledge();
		Knowledge.setPart_code(part_code);
		Knowledge.setVersion(version);
		boolean rs = this.knowledgeService.updateKnowledgePass(Knowledge);
		return rs;
	}
	@RequiresUser
	@RequestMapping("updateKnowledgeReject")
	public boolean updateKnowledgeReject(HttpServletRequest request) throws Exception {
		Integer id = Integer.parseInt(request.getParameter("id"));
		Knowledge Knowledge = new Knowledge();
		Knowledge.setId(id);
		boolean rs = this.knowledgeService.updateKnowledgeReject(Knowledge);
		return rs;
	}
	@RequiresUser
	@RequestMapping("findKnowledgeByPart")
	public JSONObject findKnowledgeByPart(HttpServletRequest request) throws Exception {
		String part_code = request.getParameter("part_code");
		Integer currentPage = Integer.parseInt(request.getParameter("currentPage"));
		Integer lineSize = Integer.parseInt(request.getParameter("lineSize"));
		String status = request.getParameter("status");
		System.out.println(part_code+status);
		List<Knowledge_total> result = this.knowledgeService.findKnowledgeByPart(part_code,status,currentPage,lineSize);
		Integer count = 0;
		count = result.size();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("count", count);
		map.put("list", result);
		JSONObject json = JSONObject.fromObject(map);
		return json;
	}
	@RequiresUser
	@RequestMapping("updateKnowledgeByPart")
	public boolean updateKnowledgeByPart(HttpServletRequest request) throws Exception {
		String knowledge_id = request.getParameter("knowledge_id");
		Integer id = Integer.parseInt(request.getParameter("id"));
		String knowledge_part_id = request.getParameter("knowledge_part_id");
		String oldid = request.getParameter("oldid");
		System.out.println(knowledge_id+","+id+","+knowledge_part_id);
//		修改成品的狀態
		this.knowledgeService.updateKnowledgeStatus(Integer.parseInt(knowledge_id),"1");
//		修改分階的id
		this.knowledgeService.updateKnowledgePartRelation(id,knowledge_id,knowledge_part_id,oldid);
		return true;
	}
	@RequiresUser
	@RequestMapping("findNewPartByCode")
	public String findNewPartByCode(HttpServletRequest request) throws Exception {
		String part_code = request.getParameter("part_code");
		return this.knowledgeService.findNewPartByCode(part_code);
	}

	@Override
	public String getType() {
		return null;
	}
}
