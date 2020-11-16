package com.artegentech.system.service;

import java.util.List;

import com.artegentech.system.vo.Doc;
import com.artegentech.system.vo.Knowledge_part;
import com.artegentech.system.vo.Knowledge_part_knowledge;
import com.artegentech.system.vo.Knowledge_total;
import com.artegentech.system.vo.Knowledge;

public interface IKnowledgeService {
	/**
	 * 增加物料文檔
	 * 
	 * @param Doc
	 * @return
	 * @throws Exception
	 */
	public boolean addPart(Knowledge_part Knowledge_part) throws Exception;
	public String add(Knowledge Knowledge) throws Exception;
	
	
	public List<Knowledge_part> findProcessed(String part_code,Integer currentPage, Integer lineSize);
	
	public Knowledge_part findOnePart(String part_code, String version);
	
	public Knowledge_part findPartById(Integer id);
	
	public Knowledge findKnowledgeById(Integer id);
	

	public boolean updatePartPass(Knowledge_part Knowledge_part) throws Exception;
	public boolean updatePartReject(Knowledge_part Knowledge_part) throws Exception;


	public List<Knowledge_part> findAllPart(String part_code, Integer currentPage, Integer lineSize);

	public List<Knowledge> findAllKnowledge(String part_code, Integer currentPage, Integer lineSize);
	
	public List<Knowledge_part> findPartByCode(String part_code,String status,Integer currentPage, Integer lineSize);
	
	public List<Knowledge> findKnowledgeByCode(String part_code,String status,Integer currentPage, Integer lineSize);
	
	public Knowledge findOneKnowledge(String part_code, String version);

	public boolean addKnowledge_part_knowledge(Knowledge_part_knowledge Knowledge_part_knowledge) throws Exception;

	public List<Knowledge_part> findPartList(String knowledge_id) throws Exception;

	public boolean updateKnowledgePass(Knowledge Knowledge) throws Exception;

	public boolean updateKnowledgeReject(Knowledge Knowledge) throws Exception;
	
	public boolean updateKnowledge_part(Knowledge_part Knowledge_part) throws Exception;
	
	public boolean updateKnowledge(Knowledge Knowledge) throws Exception;
	
	public boolean deleteKnowledgeRelation(Integer id) throws Exception;
	
	public List<Knowledge_total> findKnowledgeByPart(String part_code,String status,Integer currentPage,Integer lineSize) throws Exception;
	
	public boolean updateKnowledgeStatus(Integer id,String status) throws Exception;
	
	public boolean updateKnowledgePartRelation(Integer id,String kid,String kpid) throws Exception;
	
	public String findNewPartByCode(String part_code) throws Exception;
	
	public List<Knowledge_total> findOneKnowledgePartList(Integer id) throws Exception;
	
	public Long getAllPartCount(String part_code, String status) throws Exception;
	
}
