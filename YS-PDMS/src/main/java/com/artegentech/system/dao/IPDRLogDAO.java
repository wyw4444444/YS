package com.artegentech.system.dao;

import java.util.List;
import java.util.Map;

import com.artegentech.system.vo.PDRLog;

public interface IPDRLogDAO extends IDAO<Integer, PDRLog> {

	public List<PDRLog> findById(String PDR_id) throws Exception;
	
	public List<PDRLog> findLastById(String PDR_id) throws Exception;
	
	public boolean removeByno(Integer no) throws Exception;

}