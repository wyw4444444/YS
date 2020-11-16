package com.artegentech.system.dao;

import java.util.List;
import java.util.Map;

import com.artegentech.system.vo.Doc;
import com.artegentech.system.vo.Knowledge;
import com.artegentech.system.vo.Knowledge_part;
import com.artegentech.system.vo.Knowledge_part_knowledge;
import com.artegentech.system.vo.Knowledge_total;

public interface IKnowledgeDAO extends IDAO<Integer, Knowledge_part> {
	/**
	 * 檢查part_code是否存在
	 * 

	 */
	public boolean doCreate(Knowledge_part Knowledge_part) throws Exception;

	public List<Knowledge_part> findProcessed(Map<String,Object> params);
	
	public List<Knowledge_part> findAllPart(Map<String,Object> params);

	public Knowledge_part findOnePart(Map<String,Object> params);

	public Knowledge_part findPartById(Integer id);
	
	public Knowledge findKnowledgeById(Integer id);
	
	public boolean updatePartPass(Knowledge_part Knowledge_part) throws Exception;
	
	public boolean updatePartReject(Knowledge_part Knowledge_part) throws Exception;

	public boolean add(Knowledge Knowledge) throws Exception;
	
	public List<Knowledge> findAllKnowledge(Map<String,Object> params);
	
	public List<Knowledge_part> findPartByCode(Map<String,Object> params);
	
	public List<Knowledge> findKnowledgeByCode(Map<String,Object> params);
	
	public Knowledge findOneKnowledge(Map<String,Object> params);
	
	public boolean addKnowledge_part_knowledge(Knowledge_part_knowledge Knowledge_part_knowledge) throws Exception;
	
	public List<Knowledge_part> findPartList(String knowledge_id);
	
	public boolean updateKnowledgePass(Knowledge Knowledge) throws Exception;
	
	public boolean updateKnowledgeReject(Knowledge Knowledge) throws Exception;
	
	public boolean updateKnowledge_part(Knowledge_part Knowledge_part) throws Exception;
	
	public boolean updateKnowledge(Knowledge Knowledge) throws Exception;
	
	public boolean deleteKnowledgeRelation(Integer id) throws Exception;
	
	public List<Knowledge_total> findKnowledgeByPart(Map<String,Object> params) throws Exception;
	
	public boolean updateKnowledgeStatus(Map<String,Object> params) throws Exception;
	
	public boolean updateKnowledgePartRelation(Map<String,Object> params) throws Exception;
	
	public String findNewPartByCode(String part_code) throws Exception;
	
	public List<Knowledge_total> findOneKnowledgePartList(Integer id) throws Exception;
	
	public Long getAllPartCount(Map<String,Object> params) throws Exception;
	
	public boolean updatePartStatus(Knowledge_part Knowledge_part) throws Exception;
}
