package com.artegentech.system.service;

import java.util.List;

import com.artegentech.system.vo.Type;

public interface ITypeService {
	/**
	 * 增加類型
	 * 
	 * @param type
	 * @return
	 * @throws Exception
	 */
	public boolean add(Type type) throws Exception;

	/**
	 * 修改類型資料
	 * 
	 * @param type
	 * @return
	 * @throws Exception
	 */
	
	public Type getByParentAndUpper(String parent_type,Integer upper_id) throws Exception;

	public Type getByParentAndSubAndUpper(String parent_type,String sub_type,Integer upper_id) throws Exception;

	
	public List<Type> getAllParentType() throws Exception;


	/**
	 * 抓取所有類型資料的總行數
	 * 
	 * @return
	 * @throws Exception
	 */
	public Long getAllCount() throws Exception;
	/**
	 * 抓取所有類型資料的總行數
	 * 
	 * @return
	 * @throws Exception
	 */
	public Long getAllCount(String column, String keyword) throws Exception;

	/**
	 * 抓取所有未鎖定的類型資料的總行數
	 * 
	 * @return
	 * @throws Exception
	 */
	public Long getAllUnlockedCount() throws Exception;
	
	/**
	 * 模糊分寫查詢全部數據資料
	 * 
	 * @param column      要進行模糊查詢的數據列
	 * @param keyword     要查詢的關鍵字，如果關鍵字為空字符串則表示查詢全部
	 * @param currentPage 當前所在頁
	 * @param lineSize    每頁顯示的數據行數
	 * @return 返回多個Type對象，將以list的集合形式返回。 如果表中沒有數據則返回的list集合長度為0(size()==0)
	 * @throws Exception 數據庫未連接，或者數據庫操作錯誤
	 */
	public List<Type> getAllSplit(String column, String keyword, Integer currentPage, Integer lineSize)
			throws Exception;


	/**
	 * 模糊分頁查詢全部未鎖定的數據資料
	 * 
	 * @param column      要進行模糊查詢的數據列
	 * @param keyword     要查詢的關鍵字，如果關鍵字為空字符串則表示查詢全部
	 * @param currentPage 當前所在頁
	 * @param lineSize    每頁顯示的數據行數
	 * @return 返回多個Type對象，將以list的集合形式返回。 如果表中沒有數據則返回的list集合長度為0(size()==0)
	 * @throws Exception 數據庫未連接，或者數據庫操作錯誤
	 */
	public List<Type> getAllUnlockedSplit(String column, String keyword, Integer currentPage, Integer lineSize)
			throws Exception;
	
	
	public boolean edit(Type type) throws Exception;
	
	
	
	
	public List<Type> getSubTypeByFirstType(String parent_type) throws Exception;

	public List<Type> getSubTypeByUpperID(Integer upper_id) throws Exception;


}
