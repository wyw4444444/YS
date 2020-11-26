package com.artegentech.system.service;

import java.util.List;

import com.artegentech.system.vo.CheckLog;
import com.artegentech.system.vo.Dept;
import com.artegentech.system.vo.Doc;
import com.artegentech.system.vo.Part;

public interface IDocService {
	/**
	 * 增加物料文檔
	 * 
	 * @param Doc
	 * @return
	 * @throws Exception
	 */
	public boolean add(Doc Doc) throws Exception;
	/**
	 * 根據料號查詢對應文檔數據
	 * 
	 * @param code
	 * @return
	 * @throws Exception
	 */
	public boolean updateDoc(Doc Doc) throws Exception;
	/**
	 * 根據料號查詢對應文檔數據
	 * 
	 * @param code
	 * @return
	 * @throws Exception
	 */
	public String getMaxVersion(String code) throws Exception;
	/**
	 * 查詢所有文檔信息
	 * 
	 * @param column
	 * @param keyword
	 * @param currentPage
	 * @param lineSize
	 * @return
	 * @throws Exception
	 */
	public List<Doc> getAllSplit(String column, String keyword, Integer currentPage, Integer lineSize) throws Exception;
	Long getAllCount(String column, String keyword) throws Exception;
	/**
	 * 查詢一個文檔信息，用在查看頁面
	 * 
	 * @param part_code
	 * @param version
	 * @return
	 * @throws Exception
	 */
	public Doc findOnedoc(String part_code, Integer version);
	/**
	 * 查詢文檔信息，用在查詢頁面
	 * 
	 * @param part_code
	 * @return
	 * @throws Exception
	 */
	public List<Doc> findByPartCode(String part_code,Integer currentPage, Integer lineSize);
	public List<Doc> getDocByStatus(Integer status) throws Exception;
	public CheckLog getCheckLogByDoc(Integer id) throws Exception;

	public List<Doc> findNewByPartCode(String part_code);
	
	public List<Doc> findAllNewDoc();
	

	public Part checkPartCode(Part Part) throws Exception;

	public Part getPartinfo(Part part) throws Exception;

	public boolean updateStatus(Doc doc) throws Exception;

	public Doc findById(Integer id) throws Exception;
}
