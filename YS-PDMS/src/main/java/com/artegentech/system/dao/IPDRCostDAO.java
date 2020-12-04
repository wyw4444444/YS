package com.artegentech.system.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.artegentech.system.vo.PDRCost;

public interface IPDRCostDAO extends IDAO<Integer, PDRCost> {

	public List<PDRCost> findByPDR_id(String PDR_id) throws Exception;
	
	public boolean removeByPDR_id (String PDR_id) throws Exception;

}