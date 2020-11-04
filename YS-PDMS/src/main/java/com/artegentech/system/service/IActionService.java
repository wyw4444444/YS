package com.artegentech.system.service;

import java.util.List;

import com.artegentech.system.vo.Action;

public interface IActionService {
	/**
	 * 增加權限
	 * 
	 * @param action
	 * @return
	 * @throws Exception
	 */
	public boolean add(Action action) throws Exception;

	/**
	 * 修改權限資料
	 * 
	 * @param action
	 * @return
	 * @throws Exception
	 */
	public boolean edit(Action action) throws Exception;

	/**
	 * 查詢權限全部數據資料
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<Action> getAll() throws Exception;

	/**
	 * 模糊分寫查詢全部數據資料
	 * 
	 * @param column      要進行模糊查詢的數據列
	 * @param keyword     要查詢的關鍵字，如果關鍵字為空字符串則表示查詢全部
	 * @param currentPage 當前所在頁
	 * @param lineSize    每頁顯示的數據行數
	 * @return 返回多個Action對象，將以list的集合形式返回。 如果表中沒有數據則返回的list集合長度為0(size()==0)
	 * @throws Exception 數據庫未連接，或者數據庫操作錯誤
	 */
	public List<Action> getAllSplit(String column, String keyword, Integer currentPage, Integer lineSize)
			throws Exception;

	/**
	 * 查詢權限全部未鎖定的數據資料
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<Action> getAllUnlocked() throws Exception;

	/**
	 * 模糊分頁查詢全部未鎖定的數據資料
	 * 
	 * @param column      要進行模糊查詢的數據列
	 * @param keyword     要查詢的關鍵字，如果關鍵字為空字符串則表示查詢全部
	 * @param currentPage 當前所在頁
	 * @param lineSize    每頁顯示的數據行數
	 * @return 返回多個Action對象，將以list的集合形式返回。 如果表中沒有數據則返回的list集合長度為0(size()==0)
	 * @throws Exception 數據庫未連接，或者數據庫操作錯誤
	 */
	public List<Action> getAllUnlockedSplit(String column, String keyword, Integer currentPage, Integer lineSize)
			throws Exception;

	/**
	 * 根據flag查詢權限資料
	 * 
	 * @param flag
	 * @return
	 * @throws Exception
	 */
	public Action getByActionFlag(String flag) throws Exception;

	/**
	 * 根據title查詢權限資料
	 * 
	 * @param title
	 * @return
	 * @throws Exception
	 */
	public Action getByActionTitle(String title) throws Exception;

	/**
	 * 抓取所有權限資料的總行數
	 * 
	 * @return
	 * @throws Exception
	 */
	public Long getAllCount() throws Exception;

	/**
	 * 抓取所有權限資料的總行數
	 * 
	 * @return
	 * @throws Exception
	 */
	public Long getAllCount(String column, String keyword) throws Exception;

	/**
	 * 抓取所有未鎖定的權限資料的總行數
	 * 
	 * @return
	 * @throws Exception
	 */
	public Long getAllUnlockedCount() throws Exception;

	/**
	 * 查詢人員對應的所有權限資料
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<Action> getAllByMid(String member_id) throws Exception;
}
