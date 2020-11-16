package com.artegentech.system.service;

import java.util.List;

import com.artegentech.system.vo.CheckLog;

public interface ICheckLogService {


	
	public boolean add(CheckLog checklog) throws Exception;
	
	public List<CheckLog> getAwaitCheckSplit(String member_id) throws Exception;
	
	public List<CheckLog> getAwaitRehandleSplit(String member_id) throws Exception;
	
	public List<CheckLog> getAwaitHandledSplit(String member_id) throws Exception;
	
	public boolean updateBatchAwait(String id_check_selects, String type_check_selects,String member_id,Integer check_status) throws Exception;
	
	
	public List<CheckLog> getById_checkAndType(String type_check,Integer id_check) throws Exception;
	
	public Long getCountByTypeAndPartCode(String type_check, String part_code) throws Exception;

	public List<CheckLog> getSplitByTypeAndPartCode(String type_check, String part_code, Integer currentPage, Integer lineSize) throws Exception;
	
	
	
	
	/*
	 * 
	 * public PartInfo getByCode(String part_code) throws Exception;

	 * 
	public boolean edit(PartInfo dept) throws Exception;


	public List<PartInfo> getAll() throws Exception;


	public List<PartInfo> getAllSplit(String column, String keyword, Integer currentPage, Integer lineSize)
			throws Exception;


	public List<PartInfo> getAllUnlocked() throws Exception;


	public List<PartInfo> getAllUnlockedSplit(String column, String keyword, Integer currentPage, Integer lineSize)
			throws Exception;


	public PartInfo getByDeptCode(String dept_code) throws Exception;


	public PartInfo getByDeptName(String dept_name) throws Exception;


	public Long getAllCount() throws Exception;


	public Long getAllCount(String column, String keyword) throws Exception;


	public Long getAllUnlockedCount() throws Exception;


	public List<PartInfo> getAllUnlockedByMid(String member_id) throws Exception;*/
}
