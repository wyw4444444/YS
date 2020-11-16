package com.artegentech.system.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.artegentech.system.dao.IKnowledgeDAO;
import com.artegentech.system.service.IKnowledgeService;
import com.artegentech.system.service.abs.AbstractService;
import com.artegentech.system.vo.Knowledge;
import com.artegentech.system.vo.Knowledge_part;
import com.artegentech.system.vo.Knowledge_part_knowledge;
import com.artegentech.system.vo.Knowledge_total;

@Service
public class KnowledgeServiceImpl extends AbstractService implements IKnowledgeService {

	@Resource
	private IKnowledgeDAO knowledgedao;

	@Override
	public boolean addPart(Knowledge_part Knowledge_part) throws Exception {
		System.out.println(Knowledge_part.getPart_code());
//		if (this.partdao.checkPartCode(part.getPart_code()) == null) {
//			return false;
//		}
		return this.knowledgedao.doCreate(Knowledge_part);
	}

	@Override
	public List<Knowledge_part> findProcessed(String part_code, Integer currentPage, Integer lineSize) {
		// TODO Auto-generated method stub
		if ("".equals(part_code) || part_code == null || "null".equalsIgnoreCase(part_code)) {
		}else {
			part_code = "%"+part_code+"%";
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("part_code", part_code);
		map.put("start", (currentPage - 1) * lineSize);
		map.put("lineSize", lineSize);
		return this.knowledgedao.findProcessed(map);
	}
	@Override
	public List<Knowledge_part> findAllPart(String part_code, Integer currentPage, Integer lineSize) {
		// TODO Auto-generated method stub
		if ("".equals(part_code) || part_code == null || "null".equalsIgnoreCase(part_code)) {
		}else {
			part_code = "%"+part_code+"%";
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("part_code", part_code);
		map.put("start", (currentPage - 1) * lineSize);
		map.put("lineSize", lineSize);		
		return this.knowledgedao.findAllPart(map);
	}

	@Override
	public Knowledge_part findOnePart(String part_code, String version) {
		// TODO Auto-generated method stub
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("part_code", part_code);
		map.put("version", version);
		return this.knowledgedao.findOnePart(map);
	}
	
	@Override
	public Knowledge_part findPartById(Integer id) {
		// TODO Auto-generated method stub
		return this.knowledgedao.findPartById(id);
	}

	public boolean updatePartPass(Knowledge_part Knowledge_part) throws Exception {
		System.out.println(Knowledge_part.getPart_code());
//		if (this.partdao.checkPartCode(part.getPart_code()) == null) {
//			return false;
//		}
		return this.knowledgedao.updatePartPass(Knowledge_part);
	}

	@Override
	public String add(Knowledge Knowledge) throws Exception {
		// TODO Auto-generated method stub
		String rs = "asdf";
		if(this.knowledgedao.add(Knowledge)) {
			rs = "success";
		}
		return rs;
	}

	@Override
	public List<Knowledge> findAllKnowledge(String part_code, Integer currentPage, Integer lineSize) {
		// TODO Auto-generated method stub
		if ("".equals(part_code) || part_code == null || "null".equalsIgnoreCase(part_code)) {
		}else {
			part_code = "%"+part_code+"%";
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("part_code", part_code);
		map.put("start", (currentPage - 1) * lineSize);
		map.put("lineSize", lineSize);		
		return this.knowledgedao.findAllKnowledge(map);
	}

	@Override
	public List<Knowledge_part> findPartByCode(String part_code,String status, Integer currentPage, Integer lineSize) {
		// TODO Auto-generated method stub
		if ("".equals(part_code) || part_code == null || "null".equalsIgnoreCase(part_code)) {
		}else {
			part_code = "%"+part_code+"%";
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("part_code", part_code);
		map.put("status", status);
		map.put("start", (currentPage - 1) * lineSize);
		map.put("lineSize", lineSize);		
		return this.knowledgedao.findPartByCode(map);
	}

	@Override
	public List<Knowledge> findKnowledgeByCode(String part_code, String status, Integer currentPage, Integer lineSize) {
		// TODO Auto-generated method stub
		if ("".equals(part_code) || part_code == null || "null".equalsIgnoreCase(part_code)) {
		}else {
			part_code = "%"+part_code+"%";
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("part_code", part_code);
		map.put("status", status);
		map.put("start", (currentPage - 1) * lineSize);
		map.put("lineSize", lineSize);		
		return this.knowledgedao.findKnowledgeByCode(map);
	}
	@Override
	public Knowledge findOneKnowledge(String part_code, String version) {
		// TODO Auto-generated method stub
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("part_code", part_code);
		map.put("version", version);
		return this.knowledgedao.findOneKnowledge(map);
	}

	@Override
	public boolean addKnowledge_part_knowledge(Knowledge_part_knowledge Knowledge_part_knowledge) throws Exception {
		// TODO Auto-generated method stub
		return this.knowledgedao.addKnowledge_part_knowledge(Knowledge_part_knowledge);
	}

	@Override
	public List<Knowledge_part> findPartList(String knowledge_id) throws Exception {
		// TODO Auto-generated method stub
		return this.knowledgedao.findPartList(knowledge_id);
	}

	@Override
	public boolean updateKnowledgePass(Knowledge Knowledge) throws Exception {
		// TODO Auto-generated method stub
		return this.knowledgedao.updateKnowledgePass(Knowledge);
	}
	@Override
	public boolean updateKnowledgeReject(Knowledge Knowledge) throws Exception {
		// TODO Auto-generated method stub
		return this.knowledgedao.updateKnowledgeReject(Knowledge);
	}

	@Override
	public boolean updatePartReject(Knowledge_part Knowledge_part) throws Exception {
		// TODO Auto-generated method stub
		return this.knowledgedao.updatePartReject(Knowledge_part);
	}

	@Override
	public boolean updateKnowledge_part(Knowledge_part Knowledge_part) throws Exception {
		// TODO Auto-generated method stub
		return this.knowledgedao.updateKnowledge_part(Knowledge_part);
	}

	@Override
	public Knowledge findKnowledgeById(Integer id) {
		// TODO Auto-generated method stub
		return this.knowledgedao.findKnowledgeById(id);
	}

	@Override
	public boolean updateKnowledge(Knowledge Knowledge) throws Exception {
		// TODO Auto-generated method stub
		return this.knowledgedao.updateKnowledge(Knowledge);
	}

	@Override
	public boolean deleteKnowledgeRelation(Integer id) throws Exception {
		// TODO Auto-generated method stub
		return this.knowledgedao.deleteKnowledgeRelation(id);
	}

	@Override
	public List<Knowledge_total> findKnowledgeByPart(String part_code,String status,Integer currentPage,Integer lineSize) throws Exception {
		// TODO Auto-generated method stub
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("part_code", part_code);
		map.put("status", status);
		map.put("start", (currentPage - 1) * lineSize);
		map.put("lineSize", lineSize);		
		return this.knowledgedao.findKnowledgeByPart(map);
	}

	@Override
	public boolean updateKnowledgeStatus(Integer id, String status) throws Exception {
		// TODO Auto-generated method stub
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("status", status);
		return this.knowledgedao.updateKnowledgeStatus(map);
	}

	@Override
	public boolean updateKnowledgePartRelation(Integer id, String kid, String kpid) throws Exception {
		// TODO Auto-generated method stub
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("knowledge_part_id", kpid);
		return this.knowledgedao.updateKnowledgePartRelation(map);
	}

	@Override
	public String findNewPartByCode(String part_code) throws Exception {
		// TODO Auto-generated method stub
		return this.knowledgedao.findNewPartByCode(part_code);
	}

	@Override
	public List<Knowledge_total> findOneKnowledgePartList(Integer id) throws Exception {
		// TODO Auto-generated method stub
		return this.knowledgedao.findOneKnowledgePartList(id);
	}

	@Override
	public Long getAllPartCount(String part_code, String status) throws Exception {
		// TODO Auto-generated method stub
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("part_code", part_code);
		map.put("status", status);
		return this.knowledgedao.getAllPartCount(map);
	}
}
