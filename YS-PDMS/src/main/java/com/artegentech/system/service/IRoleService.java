package com.artegentech.system.service;

import java.util.List;
import java.util.Set;

import com.artegentech.system.vo.Role;

public interface IRoleService {
	/**
	 * 增加角色
	 * 
	 * @param role
	 * @return
	 * @throws Exception
	 */
	public boolean add(Role role, Set<Integer> actionsSet) throws Exception;

	/**
	 * 修改角色資料
	 * 
	 * @param role
	 * @return
	 * @throws Exception
	 */
	public boolean edit(Role role, Set<Integer> actionsSet) throws Exception;

	/**
	 * 查詢角色全部數據資料
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<Role> getAll() throws Exception;

	/**
	 * 模糊分頁查詢全部數據資料
	 * 
	 * @param column      要進行模糊查詢的數據列
	 * @param keyword     要查詢的關鍵字，如果關鍵字為空字符串則表示查詢全部
	 * @param currentPage 當前所在頁
	 * @param lineSize    每頁顯示的數據行數
	 * @return 返回多個Action對象，將以list的集合形式返回。 如果表中沒有數據則返回的list集合長度為0(size()==0)
	 * @throws Exception 數據庫未連接，或者數據庫操作錯誤
	 */
	public List<Role> getAllSplit(String column, String keyword, Integer currentPage, Integer lineSize)
			throws Exception;

	/**
	 * 模糊分頁查詢全部數據資料
	 * 
	 * @param column      要進行模糊查詢的數據列
	 * @param keyword     要查詢的關鍵字，如果關鍵字為空字符串則表示查詢全部
	 * @param currentPage 當前所在頁
	 * @param lineSize    每頁顯示的數據行數
	 * @return 返回多個Action對象，將以list的集合形式返回。 如果表中沒有數據則返回的list集合長度為0(size()==0)
	 * @throws Exception 數據庫未連接，或者數據庫操作錯誤
	 */
	public List<Role> getAllSplitByActionId(String column, String keyword, Integer currentPage, Integer lineSize)
			throws Exception;

	/**
	 * 查詢角色全部未鎖定的數據資料
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<Role> getAllUnlocked() throws Exception;

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
	public List<Role> getAllUnlockedSplit(String column, String keyword, Integer currentPage, Integer lineSize)
			throws Exception;

	/**
	 * 根據flag查詢角色資料
	 * 
	 * @param flag
	 * @return
	 * @throws Exception
	 */
	public Role getByRoleFlag(String flag) throws Exception;

	/**
	 * 根據title查詢角色資料
	 * 
	 * @param title
	 * @return
	 * @throws Exception
	 */
	public Role getByRoleTitle(String title) throws Exception;

	/**
	 * 抓取所有角色資料的總行數
	 * 
	 * @return
	 * @throws Exception
	 */
	public Long getAllCount() throws Exception;

	/**
	 * 抓取所有角色資料的總行數
	 * 
	 * @return
	 * @throws Exception
	 */
	public Long getAllCountByActionId(Integer action_id) throws Exception;

	/**
	 * 抓取所有角色資料的總行數
	 * 
	 * @return
	 * @throws Exception
	 */
	public Long getAllCount(String column, String keyword) throws Exception;

	/**
	 * 抓取所有未鎖定的角色資料的總行數
	 * 
	 * @return
	 * @throws Exception
	 */
	public Long getAllUnlockedCount() throws Exception;
	
	/**
	 * 抓取所有未鎖定的角色資料的總行數
	 * 
	 * @return
	 * @throws Exception
	 */
	public Long getAllUnlockedCount(String column, String keyword) throws Exception;

	/**
	 * 根據member_id查詢所有角色資料
	 * 
	 * @param member_id
	 * @return
	 * @throws Exception
	 */
	public Set<Role> getByMid(String member_id) throws Exception;

	/**
	 * 根據role_id查詢角色資料
	 * 
	 * @param role_id
	 * @return
	 * @throws Exception
	 */
	public Role getById(Integer role_id) throws Exception;
}
