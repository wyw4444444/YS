package com.artegentech.system.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.artegentech.system.vo.PDR;
import com.artegentech.system.vo.Role;

public interface IPDRService {
	
	
	public boolean add(PDR pdr) throws Exception;

	public boolean edit(PDR pdr) throws Exception;
	
	
	public PDR getLast() throws Exception;

	public PDR getByPDR_id(String PDR_id) throws Exception;
	
	
	public List<PDR> getAllSplit(Map map)throws Exception;

	public Long getAllCount(Map map) throws Exception;
	
}
