package com.artegentech.system.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.artegentech.system.vo.Member;

public interface IMemberService {
	
	/**
	 * 增加人員
	 * 
	 * @param member
	 * @return
	 * @throws Exception
	 */
	public boolean add(Member member, Set<Integer> deptsSet, Set<Integer> rolesSet) throws Exception;
	
	/**
	 * 修改人員資料
	 * 
	 * @param member
	 * @return
	 * @throws Exception
	 */
	public boolean edit(Member member, Set<Integer> deptsSet, Set<Integer> rolesSet) throws Exception;
	
	public boolean editPassword(Member member) throws Exception;
	
	/**
	 * 查詢人員全部數據資料
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<Member> getAll() throws Exception;
	
	/**
	 * 模糊分頁查詢全部數據資料
	 * @param column 要進行模糊查詢的數據列
	 * @param keyword 要查詢的關鍵字，如果關鍵字為空字符串則表示查詢全部
	 * @param currentPage 當前所在頁
	 * @param lineSize 每頁顯示的數據行數
	 * @return 返回多個Action對象，將以list的集合形式返回。 如果表中沒有數據則返回的list集合長度為0(size()==0)
	 * @throws Exception  數據庫未連接，或者數據庫操作錯誤
	 */
	public List<Member> getAllSplit(String column, String keyword, Integer currentPage, Integer lineSize) throws Exception;
	
	/**
	 * 查詢人員全部未鎖定的數據資料
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<Member> getAllUnlocked() throws Exception;
	
	/**
	 * 模糊分頁查詢全部未鎖定的數據資料
	 * @param column 要進行模糊查詢的數據列
	 * @param keyword 要查詢的關鍵字，如果關鍵字為空字符串則表示查詢全部
	 * @param currentPage 當前所在頁
	 * @param lineSize 每頁顯示的數據行數
	 * @return 返回多個Action對象，將以list的集合形式返回。 如果表中沒有數據則返回的list集合長度為0(size()==0)
	 * @throws Exception  數據庫未連接，或者數據庫操作錯誤
	 */
	public List<Member> getAllUnlockedSplit(String column, String keyword, Integer currentPage, Integer lineSize) throws Exception;
	
	/**
	 * 根據member_id查詢獲取用戶資料，調用IMemberDAO.findById()方法
	 * 
	 * @param mid
	 * @return
	 */
	public Member getById(String member_id) throws Exception;
	
	public Member getByIdAll(String member_id) throws Exception;
	
	/**
	 * 抓取所有人員資料的總行數
	 * @return
	 * @throws Exception
	 */
	public Long getAllCount() throws Exception;
	
	/**
	 * 抓取所有人員資料的總行數
	 * @return
	 * @throws Exception
	 */
	public Long getAllCount(String column, String keyword) throws Exception;
	
	/**
	 * 抓取所有未鎖定的人員資料的總行數
	 * @return
	 * @throws Exception
	 */
	public Long getAllUnlockedCount() throws Exception;
	
	/**
	 * 根據用戶mid查詢改用戶對應的所有角色以及所有權限數據，要調用如下的接口方法：<br>
	 * 1、查詢所有的角色：IRoleDAO.findAllRoleFlagByMid();<br>
	 * 2、查詢所有的權限：IActionDAO.findAllActionflagByMid();<br>
	 * 
	 * @param mid
	 * @return 返回的結構包含有如下的幾個內容：<br>
	 *         1、key = allRoles ； value = IRoleDAO.findAllRoleFlagByMid()，Set集合；<br>
	 *         2、key = allAction ； value =
	 *         IActionDAO.findAllActionflagByMid()，Set集合；<br>
	 */
	public Map<String, Object> listAuthByMember(String member_id) throws Exception;

	public Long getAllUnlockedCount(String column, String keyword) throws Exception;
	
	public List<Member> getAllUnlockedByRoleFlag(String flag) throws Exception;
}
