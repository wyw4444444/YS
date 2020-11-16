package com.artegentech.system.dao;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.artegentech.system.vo.Action;

public interface IActionDAO extends IDAO<Integer, Action> {

	public Action findByFlag(String flag) throws Exception;

	public Action findByTitle(String title) throws Exception;

	public Set<String> findAllActionFlagByMid(String member_id) throws Exception;

	public List<Action> findByMid(String member_id) throws Exception;

	public List<Action> findAllSplitByRole(Map<String, Object> map) throws Exception;

}