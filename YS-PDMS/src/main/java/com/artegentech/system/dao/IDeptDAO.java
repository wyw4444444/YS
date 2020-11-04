package com.artegentech.system.dao;

import java.util.List;

import com.artegentech.system.vo.Dept;

public interface IDeptDAO extends IDAO<Integer, Dept> {
	/**
	 * 根據dept_code查詢部門資料
	 * 
	 * @param dept_code 要查詢的dept_code
	 * @return 如果數據找到則以Dept類對象的形式返回，否則返回null
	 * @throws Exception 數據庫未連接，或者數據庫操作錯誤
	 */
	public Dept findByCode(String dept_code) throws Exception;

	/**
	 * 根據dept_name查詢部門資料
	 * 
	 * @param dept_name 要查詢的dept_name
	 * @return 如果數據找到則以Dept類對象的形式返回，否則返回null
	 * @throws Exception 數據庫未連接，或者數據庫操作錯誤
	 */
	public Dept findByName(String dept_name) throws Exception;

	public List<Dept> findAllDeptFlagByMid(String member_id) throws Exception;
}
