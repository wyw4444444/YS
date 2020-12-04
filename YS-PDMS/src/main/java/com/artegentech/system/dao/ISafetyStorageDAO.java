package com.artegentech.system.dao;

import java.util.List;
import java.util.Map;

import com.artegentech.system.vo.SafetyStorage;

public interface ISafetyStorageDAO extends IDAO<Integer, SafetyStorage> {

	/**
	 * 查询所有料号最新安全库存资料
	 * 
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public List<SafetyStorage> findAllLatest() throws Exception;

	/**
	 * 查询所有料号最新安全库存资料（分页）
	 * 
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public List<SafetyStorage> findAllLatestSplit(Map<String, Object> params) throws Exception;

	/**
	 * 查询最新安全库存资料的笔数
	 * 
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public Long getAllLatestCount() throws Exception;
	
	/**
	 * 查询所有料号最新安全库存资料
	 * 
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public SafetyStorage findByPartCode(String part_code) throws Exception;

}
