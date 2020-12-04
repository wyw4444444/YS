package com.artegentech.system.action;

import java.io.File;
import java.text.SimpleDateFormat;
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

import com.artegentech.system.service.IKnowledgeService;
import com.artegentech.system.vo.Doc;
import com.artegentech.system.vo.Knowledge_part;
import com.artegentech.system.vo.Knowledge;
import com.artegentech.system.vo.Knowledge_levelupLog;
import com.artegentech.system.vo.Knowledge_part_knowledge;
import com.artegentech.system.vo.Knowledge_total;
import com.artegentech.util.JsonDateValueProcessor;
import com.artegentech.util.JsonNullConvert;
import com.artegentech.util.UploadFileUtil;
import com.artegentech.util.PrintToPdfUtil;
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
	/**
	 * 添加分階
	 * 分兩種情況
	 * 1、有id時，是修改
	 * 2、無id時，是新建
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequiresUser
	@RequestMapping("addPart")
	public Integer addPart(HttpServletRequest request) throws Exception {
		Knowledge_part Knowledge_part = new Knowledge_part();
		String idStr = (request.getParameter("id"));
		Integer id = 0;
		if(idStr!=null&&!"".equals(idStr)) { 
			id = Integer.parseInt(idStr);
			Knowledge_part.setId(id);
		}
		String part_code = request.getParameter("part_code");
		String version = request.getParameter("version");
		String name = request.getParameter("name");
		String descr = request.getParameter("desc");
		String file = request.getParameter("file");
		String reg_man = request.getParameter("member_id");
		Date date = new Date(System.currentTimeMillis());
		Knowledge_part.setPart_code(part_code);
		Knowledge_part.setDescr(descr);
		Knowledge_part.setSave_date(date);
		Knowledge_part.setPart_name(name);
		Knowledge_part.setFileUrl(file);
		Knowledge_part.setVersion(version);
		Knowledge_part.setReg_man(reg_man);
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
				Integer rsid = this.knowledgeService.findOnePart(part_code, version).getId();
				System.out.println(version+"__"+String.valueOf('A')+"--"+String.valueOf(version.equals("A"))+"--"+String.valueOf(version==String.valueOf('A')));
				if(version.equals("A")) {//如果是升版
				}else {
					String tips = request.getParameter("tips");
					Knowledge_levelupLog Knowledge_levelupLog = new Knowledge_levelupLog();
					Knowledge_levelupLog.setPart_id(rsid);
					Knowledge_levelupLog.setMember_id(reg_man);
					Knowledge_levelupLog.setReg_time(date);
					Knowledge_levelupLog.setPart_type("part");
					Knowledge_levelupLog.setTips(tips);
					this.knowledgeService.addKnowledgeLevelupLog(Knowledge_levelupLog);
				}
				return rsid;
			}else {
				return 0;
			}
		}
		
			
	}
	/**
	 * 上傳圖片
	 * 
	 * 
	 * @return
	 * @throws Exception
	 */
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
	/**
	 * 查詢待審核的分階
	 * 
	 * @return
	 * @throws Exception
	 */
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
	/**
	 * 查詢全部審核通過的分階
	 * 在sql語句中固定查詢status=2的記錄
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequiresUser
	@RequestMapping("findAllPart")
	public JSONObject findAllPart(HttpServletRequest request) throws Exception {
		String part_code = request.getParameter("part_code");
		Integer currentPage = Integer.parseInt(request.getParameter("currentPage"));
		Integer lineSize = Integer.parseInt(request.getParameter("lineSize"));
		List<Knowledge_part> result = this.knowledgeService.findAllPart(part_code,currentPage,lineSize);
		Long count = (long)0;
		count = this.knowledgeService.getAllPartCount("", "5");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("count", count);
		map.put("list", result);
		JSONObject json = JSONObject.fromObject(map);
		return json;
	}
	/**
	 * 查詢一條分階記錄
	 * 通過料號和版本號查詢，原則上是唯一的
	 * 通過id查詢分階記錄更加穩定，此方法是適用於不知道id的情況下查詢
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequiresUser
	@RequestMapping("findOnePart")
	public Knowledge_part findOnePart(HttpServletRequest request) throws Exception {
		String part_code = request.getParameter("part_code");
		String version = request.getParameter("version");
		Knowledge_part Knowledge_part = this.knowledgeService.findOnePart(part_code, version);
		return Knowledge_part;
	}

	/**
	 * 查詢一條最新分階記錄
	 * 
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequiresUser
	@RequestMapping("findOneNewPart")
	public Knowledge_part findOneNewPart(HttpServletRequest request) throws Exception {
		String part_code = request.getParameter("part_code");
		Knowledge_part Knowledge_part = this.knowledgeService.findOneNewPart(part_code);
		return Knowledge_part;
	}
	/**
	 * 查詢一條分階記錄
	 * 通過id
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequiresUser
	@RequestMapping("findPartById")
	public Knowledge_part findPartById(HttpServletRequest request) throws Exception {
		Integer id = Integer.parseInt(request.getParameter("id"));
		Knowledge_part result = this.knowledgeService.findPartById(id);
		return result;
	}
	/**
	 * 審核分階申請
	 * 將分階記錄的status改成2
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequiresUser
	@RequestMapping("updatePartStatus")
	public boolean updatePartStatus(HttpServletRequest request) throws Exception {
		Integer id = Integer.parseInt(request.getParameter("id"));
		String status = request.getParameter("status");
		Knowledge_part Knowledge_part = new Knowledge_part();
		Knowledge_part.setId(id);
		Knowledge_part.setStatus(status);
		boolean rs = this.knowledgeService.updatePartStatus(Knowledge_part);
		return rs;
	}
	/**
	 * 審核分階申請
	 * 將分階記錄的status改成2
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequiresUser
	@RequestMapping("updatePartPass")
	public boolean updatePartPass(HttpServletRequest request) throws Exception {
		Integer id = Integer.parseInt(request.getParameter("id"));
		String part_code = request.getParameter("part_code");
		String version = request.getParameter("version");
		Knowledge_part Knowledge_part = new Knowledge_part();
		Knowledge_part.setPart_code(part_code);
		Knowledge_part.setVersion(version);
		boolean rs = this.knowledgeService.updatePartPass(Knowledge_part);
		this.knowledgeService.updateKnowledgeLog(id);
		return rs;
	}
	/**
	 * 駁回分階申請
	 * 將分階記錄的status改成0
	 * 
	 * @return
	 * @throws Exception
	 */
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
	/**
	 * 查詢升版記錄
	 * 將分階記錄的status改成0
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequiresUser
	@RequestMapping("findKnowledgeLevelupLog")
	public JSONObject findKnowledgeLevelupLog(HttpServletRequest request) throws Exception {
		String part_code = request.getParameter("part_code");
		String part_type = request.getParameter("part_type");
		Integer currentPage = Integer.parseInt(request.getParameter("currentPage"));
		Integer lineSize = Integer.parseInt(request.getParameter("lineSize"));
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("part_code", part_code);
		map.put("part_type", part_type);
		map.put("start", (currentPage - 1) * lineSize);
		map.put("lineSize", lineSize);	
		
		Long count = (long)0;
		count = this.knowledgeService.getKnowledgeLevelupLogCount(part_code, part_type);
		List<Knowledge_levelupLog> result = this.knowledgeService.findKnowledgeLevelupLog(map);
		JSONArray json1 = JSONArray.fromObject(result);
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());
		JSONArray jsonArray = JSONArray.fromObject(json1, jsonConfig);
		for (int i = 0; i < jsonArray.size(); i++) {
			JsonNullConvert.filterNull(jsonArray.getJSONObject(i));
		}
		Map<String, Object> maprs = new HashMap<String, Object>();
		maprs.put("count", count);
		maprs.put("list", jsonArray);
		JSONObject json = JSONObject.fromObject(maprs);
		return json;
	}
	/**
	 * 添加成品記錄
	 * 分兩種情況
	 * 1、如果有id傳入，修改
	 * 2、如果沒有id傳入，新建
	 * 
	 * @return
	 * @throws Exception
	 */
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
		String reg_man = request.getParameter("reg_man");
		Date date = new Date(System.currentTimeMillis());
		String liststr = request.getParameter("partArr");
		System.out.println(liststr);
		JSONArray list=JSONArray.fromObject(liststr);
		Knowledge.setPart_code(part_code);
		Knowledge.setPart_name(name);
		Knowledge.setPart_type(part_type);
		Knowledge.setDate(date);
		Knowledge.setDescr(desc);
		Knowledge.setVersion(version);
		Knowledge.setReg_man(reg_man);
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
				if(version.equals("A")) {//如果是升版
				}else {
					String tips = request.getParameter("tips");
					Knowledge_levelupLog Knowledge_levelupLog = new Knowledge_levelupLog();
					Knowledge_levelupLog.setPart_id(knowledge_id);
					Knowledge_levelupLog.setMember_id(reg_man);
					Knowledge_levelupLog.setReg_time(date);
					Knowledge_levelupLog.setPart_type("knowledge");
					Knowledge_levelupLog.setTips(tips);
					this.knowledgeService.addKnowledgeLevelupLog(Knowledge_levelupLog);
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
	/**
	 * 查詢所有成品
	 * 
	 * @return
	 * @throws Exception
	 */
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
	/**
	 * 查詢分階記錄
	 * 模糊查詢，適用所有狀態，
	 * status：0代表駁回；1代表待審核；2代表通過審核
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequiresUser
	@RequestMapping("findPartByCode")
	public JSONObject findPartByCode(HttpServletRequest request) throws Exception {
		String part_code = request.getParameter("part_code");
		Integer currentPage = Integer.parseInt(request.getParameter("currentPage"));
		Integer lineSize = Integer.parseInt(request.getParameter("lineSize"));
		String status = request.getParameter("status");
		List<Knowledge_part> result = this.knowledgeService.findPartByCode(part_code,status,currentPage,lineSize);
		Long count = (long)0;
		count = this.knowledgeService.getAllPartCount(part_code,status);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("count", count);
		map.put("list", result);
		JSONObject json = JSONObject.fromObject(map);
		return json;
	}
	/**
	 * 查詢成品
	 * 通過id
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequiresUser
	@RequestMapping("findKnowledgeById")
	public Knowledge findKnowledgeById(HttpServletRequest request) throws Exception {
		Integer id = Integer.parseInt(request.getParameter("id"));
		Knowledge result = this.knowledgeService.findKnowledgeById(id);
		return result;
	}
	/**
	 * 查詢成品
	 * 模糊查詢，適用所有狀態，
	 * status：0代表駁回；1代表待審核；2代表通過審核
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequiresUser
	@RequestMapping("findKnowledgeByCode")
	public JSONObject findKnowledgeByCode(HttpServletRequest request) throws Exception {
		String part_code = request.getParameter("part_code");
		Integer currentPage = Integer.parseInt(request.getParameter("currentPage"));
		String member_id = request.getParameter("member_id");
		Integer lineSize = Integer.parseInt(request.getParameter("lineSize"));
		String status = request.getParameter("status");
		List<Knowledge> result = this.knowledgeService.findKnowledgeByCode(part_code,status,currentPage,lineSize,member_id);
		Integer count = 0;
		count = result.size();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("count", count);
		map.put("list", result);
		JSONObject json = JSONObject.fromObject(map);
		return json;
	}
	/**
	 * 查詢一個成品
	 * 通過料號和版本號
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequiresUser
	@RequestMapping("findOneKnowledge")
	public Knowledge findOneKnowledge(HttpServletRequest request) throws Exception {
		String part_code = request.getParameter("part_code");
		String version = request.getParameter("version");
		Knowledge Knowledge = this.knowledgeService.findOneKnowledge(part_code, version);
		return Knowledge;
	}
	/**
	 * 查詢分階列表
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequiresUser
	@RequestMapping("findPartList")
	public List<Knowledge_part> findPartList(HttpServletRequest request) throws Exception {
		String knowledge_id = request.getParameter("knowledge_id");
		List<Knowledge_part> Knowledge = this.knowledgeService.findPartList(knowledge_id);
		return Knowledge;
	}
	/**
	 * 審核成品
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequiresUser
	@RequestMapping("updateKnowledgePass")
	public boolean updateKnowledgePass(HttpServletRequest request) throws Exception {
		Integer id = Integer.parseInt(request.getParameter("id"));
		String part_code = request.getParameter("part_code");
		String version = request.getParameter("version");
//		查詢所有分階數據
		List<Knowledge_total> list = this.knowledgeService.findOneKnowledgePartList(id);

		List<String> partList = new ArrayList<String>();
		for(Knowledge_total v:list){
			partList.add(v.getFileUrl());
		}
//		定義pdf地址，
		String fileUrl = "E://part-images/knowledge/"+part_code+"_"+version+".pdf";
//		將所有分階附件匯總成pdf
		PrintToPdfUtil.toPdf(partList, fileUrl);
//		最後把成品狀態改成2，並把pdf地址存入
		Knowledge Knowledge = new Knowledge();
		Knowledge.setPart_code(part_code);
		Knowledge.setVersion(version);
		Knowledge.setFileUrl(fileUrl);
		boolean rs = this.knowledgeService.updateKnowledgePass(Knowledge);
		this.knowledgeService.updateKnowledgeLog(id);
		return rs;
	}
	/**
	 * 駁回成品
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequiresUser
	@RequestMapping("updateKnowledgeReject")
	public boolean updateKnowledgeReject(HttpServletRequest request) throws Exception {
		Integer id = Integer.parseInt(request.getParameter("id"));
		Knowledge Knowledge = new Knowledge();
		Knowledge.setId(id);
		boolean rs = this.knowledgeService.updateKnowledgeReject(Knowledge);
		return rs;
	}
	/**
	 * 上階查詢
	 * 
	 * @return
	 * @throws Exception
	 */
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
	/**
	 * 修改成品狀態
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequiresUser
	@RequestMapping("updateKnowledgeStatus")
	public boolean updateKnowledgeStatus(HttpServletRequest request) throws Exception {
		Integer id = Integer.parseInt(request.getParameter("id"));
		String status = request.getParameter("status");
//		修改成品的狀態
		return this.knowledgeService.updateKnowledgeStatus(id,status);
	}
	/**
	 * 分階升版時成品同步更新
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequiresUser
	@RequestMapping("updateKnowledgeByPart")
	public boolean updateKnowledgeByPart(HttpServletRequest request) throws Exception {
		String knowledge_id = request.getParameter("knowledge_id");
		Integer id = Integer.parseInt(request.getParameter("id"));
		String knowledge_part_id = request.getParameter("knowledge_part_id");
		System.out.println(knowledge_id+","+id+","+knowledge_part_id);
//		修改成品的狀態
		this.knowledgeService.updateKnowledgeStatus(Integer.parseInt(knowledge_id),"1");
//		修改分階的id
		this.knowledgeService.updateKnowledgePartRelation(id,knowledge_id,knowledge_part_id);
		return true;
	}
	/**
	 * 查詢分階的最大版本號
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequiresUser
	@RequestMapping("findNewPartByCode")
	public String findNewPartByCode(HttpServletRequest request) throws Exception {
		String part_code = request.getParameter("part_code");
		return this.knowledgeService.findNewPartByCode(part_code);
	}
	/**
	 * 查詢一條最新分階記錄
	 * 
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequiresUser
	@RequestMapping("findOneNewKnowledge")
	public Knowledge findOneNewKnowledge(HttpServletRequest request) throws Exception {
		String part_code = request.getParameter("part_code");
		Knowledge Knowledge = this.knowledgeService.findOneNewKnowledge(part_code);
		return Knowledge;
	}
	@RequiresUser
	@RequestMapping("test")
	public boolean test(HttpServletRequest request) throws Exception {
		List<String> list = new ArrayList<String>();
		list.add("E://part-images/knowledge/RB2-P1100A0G_G/RB2-P1100A0G_G.jpg");
		list.add("E://part-images/knowledge/RB2-P1100A0G_I/RB2-P1100A0G_I.jpg");
		PrintToPdfUtil.toPdf(list, "E://part-images/test.pdf");
		return false;
	}

	@Override
	public String getType() {
		return null;
	}
}
