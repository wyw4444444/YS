package com.artegentech.system.dao;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.artegentech.system.vo.Action;
import com.artegentech.system.vo.Role;

public interface IRoleDAO extends IDAO<Integer, Role> {

	public Set<String> findAllRoleFlagByMid(String member_id) throws Exception;

	public Role findByFlag(String flag) throws Exception;

	public Role findByTitle(String title) throws Exception;

	public Set<Role> findByMid(String member_id) throws Exception;

	public boolean doCreateRoleAndAction(Map<String, Object> map) throws Exception;

	public boolean removeRoleAndAction(Integer role_id) throws Exception;

	public List<Action> findAllActionByRid(Integer role_id) throws Exception;

	public List<Role> findAllByActionId(Map<String, Object> map) throws Exception;

	public Long getAllCountByActionId(Integer action_id) throws Exception;
}
