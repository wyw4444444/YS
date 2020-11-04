package com.artegentech.system.service;

import java.util.List;

import com.artegentech.system.vo.Dept;

public interface IDeptService {
	/**
	 * 增加部門
	 * 
	 * @param dept
	 * @return
	 * @throws Exception
	 */
	public boolean add(Dept dept) throws Exception;

	/**
	 * 修改部門資料
	 * 
	 * @param dept
	 * @return
	 * @throws Exception
	 */
	public boolean edit(Dept dept) throws Exception;

	/**
	 * 查詢部門全部數據資料
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<Dept> getAll() throws Exception;

	/**
	 * 模糊分寫查詢全部數據資料
	 * 
	 * @param column      要進行模糊查詢的數據列
	 * @param keyword     要查詢的關鍵字，如果關鍵字為空字符串則表示查詢全部
	 * @param currentPage 當前所在頁
	 * @param lineSize    每頁顯示的數據行數
	 * @return 返回多個Dept對象，將以list的集合形式返回。 如果表中沒有數據則返回的list集合長度為0(size()==0)
	 * @throws Exception 數據庫未連接，或者數據庫操作錯誤
	 */
	public List<Dept> getAllSplit(String column, String keyword, Integer currentPage, Integer lineSize)
			throws Exception;

	/**
	 * 查詢部門全部未鎖定的數據資料
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<Dept> getAllUnlocked() throws Exception;

	/**
	 * 模糊分頁查詢全部未鎖定的數據資料
	 * 
	 * @param column      要進行模糊查詢的數據列
	 * @param keyword     要查詢的關鍵字，如果關鍵字為空字符串則表示查詢全部
	 * @param currentPage 當前所在頁
	 * @param lineSize    每頁顯示的數據行數
	 * @return 返回多個Dept對象，將以list的集合形式返回。 如果表中沒有數據則返回的list集合長度為0(size()==0)
	 * @throws Exception 數據庫未連接，或者數據庫操作錯誤
	 */
	public List<Dept> getAllUnlockedSplit(String column, String keyword, Integer currentPage, Integer lineSize)
			throws Exception;

	/**
	 * 根據dept_code查詢部門資料
	 * 
	 * @param dept_code
	 * @return
	 * @throws Exception
	 */
	public Dept getByDeptCode(String dept_code) throws Exception;

	/**
	 * 根據dept_name查詢部門資料
	 * 
	 * @param dept_name
	 * @return
	 * @throws Exception
	 */
	public Dept getByDeptName(String dept_name) throws Exception;

	/**
	 * 抓取所有部門資料的總行數
	 * 
	 * @return
	 * @throws Exception
	 */
	public Long getAllCount() throws Exception;

	/**
	 * 抓取所有部門資料的總行數
	 * 
	 * @return
	 * @throws Exception
	 */
	public Long getAllCount(String column, String keyword) throws Exception;

	/**
	 * 抓取所有未鎖定的部門資料的總行數
	 * 
	 * @return
	 * @throws Exception
	 */
	public Long getAllUnlockedCount() throws Exception;

	/**
	 * 根據member_id查詢所有Dept資料
	 * 
	 * @param member_id
	 * @return
	 * @throws Exception
	 */
	public List<Dept> getAllUnlockedByMid(String member_id) throws Exception;
}
