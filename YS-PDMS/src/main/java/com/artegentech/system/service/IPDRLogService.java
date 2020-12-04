package com.artegentech.system.service;

import java.util.List;

import com.artegentech.system.vo.PDR;
import com.artegentech.system.vo.PDRDetail;
import com.artegentech.system.vo.PDRLog;


public interface IPDRLogService {
	public List<PDRLog> findLastById(String PDR_id)throws Exception;
	
	public List<PDRLog>  findByPDR_id(String PDR_id)throws Exception;

	public boolean  add(PDRLog vo)throws Exception;
	
	public boolean removeByno(Integer no)throws Exception;
	
	public boolean edit(PDRLog pdr) throws Exception;
}
