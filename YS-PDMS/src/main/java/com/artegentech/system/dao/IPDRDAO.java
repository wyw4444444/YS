package com.artegentech.system.dao;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.artegentech.system.vo.Action;
import com.artegentech.system.vo.PDR;
import com.artegentech.system.vo.Role;

public interface IPDRDAO extends IDAO<Integer, PDR> {

	public Set<String> findAllRoleFlagByMid(String member_id) throws Exception;



	

	public boolean doCreateRoleAndAction(Map<String, Object> map) throws Exception;

	public boolean removeRoleAndAction(Integer role_id) throws Exception;

	
	
	
	public List<PDR> findAllSplit(Map<String, Object> map) throws Exception;
	
	public Long getAllCount(Map<String, Object> map) throws Exception;
	
	public PDR getByPDR_id(String PDR_id) throws Exception;
	
	public PDR getLast() throws Exception;
}
