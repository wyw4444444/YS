package com.artegentech.system.dao;

import java.util.List;
import java.util.Map;

import com.artegentech.system.vo.CheckLog;
import com.artegentech.system.vo.Type;

public interface ICheckLogDAO extends IDAO<Integer, CheckLog> {


	public CheckLog findById_CheckAndType(Map<String,Object> params) throws Exception;
	
	public List<CheckLog> findById_CheckAndTypeAll(Map<String,Object> params) throws Exception;
	
	
	public Long getCountByTypeAndPartCodeInPartInfo(Map<String,Object> params) throws Exception;
	
	public List<CheckLog> findByPartCodeInPartInfo(Map<String,Object> params) throws Exception;
	
	public Long getCountByTypeAndPartCodeInBom(Map<String,Object> params) throws Exception;
	
	public List<CheckLog> findByPartCodeInBom(Map<String,Object> params) throws Exception;
	

}
