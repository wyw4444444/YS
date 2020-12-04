package com.artegentech.system.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.artegentech.system.vo.Member;
import com.artegentech.system.vo.PDR;
import com.artegentech.system.vo.Record;

public interface IRecordService {
	
	
	public boolean recordmember(Map map) throws Exception;
	
	public List<Record> getAllSplit(Map map)throws Exception;

	public Long getAllCount(Map map) throws Exception;
	
}
