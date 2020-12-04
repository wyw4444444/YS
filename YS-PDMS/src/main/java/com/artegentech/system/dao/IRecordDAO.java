package com.artegentech.system.dao;

import java.util.List;
import java.util.Map;

import com.artegentech.system.vo.PDR;
import com.artegentech.system.vo.Record;

public interface IRecordDAO extends IDAO<String, Record> {
	public boolean doCreateRecord(Map<String, Object> params) throws Exception;

	public List<Record> findAllSplit(Map<String, Object> map) throws Exception;
	
	public Long getAllCount(Map<String, Object> map) throws Exception;
		
}
