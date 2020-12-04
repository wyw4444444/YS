package com.artegentech.system.dao;

import java.util.List;
import java.util.Map;

import com.artegentech.system.vo.Type;

public interface ITypeDAO extends IDAO<Integer, Type> {
	/**
	 * 根據child_type和parent_type查詢類型資料
	 * 
	 * @param params 傳入參數如下：<br>
	 *               1、key=child_type,value=child_type<br>
	 *               2、key=parent_type,value=parent_type<br>
	 * @return Type
	 * @throws Exception
	 */

	
	public Type findByParentAndUpper(Map<String, Object> params) throws Exception;
	
	
	public Type findByParentAndSubAndUpper(Map<String, Object> params) throws Exception;
	
	public List<Type> findFirstParentType() throws Exception;
	
	public List<Type> findParentTypeEcptFirst() throws Exception;
	
	public List<Type> findSubTypeByFirstType(String parent_type) throws Exception;
	
	public List<Type> findSubTypeByUpperID(Integer upper_id) throws Exception;
	
	public Type findJointType(String joint_type) throws Exception;
	
	public List<Type> findByParentType(String parent_type) throws Exception;
	
}
