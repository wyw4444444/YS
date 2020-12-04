package com.artegentech.system.service;

import java.util.Date;
import java.util.List;

import com.artegentech.system.vo.PDRCost;
import com.artegentech.system.vo.PDRDetail;


public interface IPDRCostService {

	public boolean  add(List<PDRCost> list) throws Exception;
	
	public boolean  removeByPDR_id(String PDR_id) throws Exception;
	
	public List<PDRCost>  findByPDR_id(String PDR_id)throws Exception;
	
}
