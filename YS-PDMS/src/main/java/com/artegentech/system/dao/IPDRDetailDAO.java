package com.artegentech.system.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.artegentech.system.vo.PDRDetail;

public interface IPDRDetailDAO extends IDAO<Integer, PDRDetail> {

	public List<PDRDetail> findByPDR_id(String PDR_id) throws Exception;
	
	public List<PDRDetail> findByLate() throws Exception;
	
	public List<PDRDetail> findByDays() throws Exception;
	
	public boolean removeByPDR_id (String PDR_id) throws Exception;

}