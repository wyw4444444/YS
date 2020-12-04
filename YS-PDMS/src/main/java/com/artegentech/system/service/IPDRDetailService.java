package com.artegentech.system.service;

import java.util.Date;
import java.util.List;

import com.artegentech.system.vo.PDRDetail;


public interface IPDRDetailService {

	public boolean  add(List<PDRDetail> list) throws Exception;
	
	public boolean  removeByPDR_id(String PDR_id) throws Exception;
	
	public List<PDRDetail>  findByPDR_id(String PDR_id)throws Exception;
	
	public List<PDRDetail>  findByLate()throws Exception;
	
	public List<PDRDetail>  findByDays()throws Exception;
}
