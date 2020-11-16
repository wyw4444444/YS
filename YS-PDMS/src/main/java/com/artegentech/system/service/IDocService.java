package com.artegentech.system.service;

import java.util.List;

import com.artegentech.system.vo.Dept;
import com.artegentech.system.vo.Doc;

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
	public Integer getMaxVersion(String code) throws Exception;
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

	public List<Doc> findNewByPartCode(String part_code);
	
	public List<Doc> findAllNewDoc();
}
