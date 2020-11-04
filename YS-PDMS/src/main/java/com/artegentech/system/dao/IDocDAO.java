package com.artegentech.system.dao;

import java.util.List;
import java.util.Map;

import com.artegentech.system.vo.Doc;

public interface IDocDAO extends IDAO<Integer, Doc> {
	/**
	 * 檢查part_code是否存在
	 * 

	 */
	public boolean add(Doc doc) throws Exception;
	/**
	 * 根據料號查詢文檔信息
	 * 

	 */
	public List<Doc> getDocByCode(String code) throws Exception;
	/**
	 * 查詢所有文檔信息
	 * 

	 */
	public List<Doc> findAll() throws Exception;
	
	public Doc findOnedoc(Map<String,Object> params);

	public List<Doc> findByPartCode(Map<String,Object> params);

	public List<Doc> findNewByPartCode(Map<String,Object> params);

	public List<Doc> findAllNewDoc();

}
