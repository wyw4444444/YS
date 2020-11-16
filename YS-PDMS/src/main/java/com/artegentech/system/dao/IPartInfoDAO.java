package com.artegentech.system.dao;

import java.util.List;
import java.util.Map;

import com.artegentech.system.vo.PartInfo;

public interface IPartInfoDAO extends IDAO<Integer, PartInfo> {

	public PartInfo findByCode(String part_code) throws Exception;
	
	
	public List<PartInfo> findAwaitCheckSplit() throws Exception;
	
	public List<PartInfo> findAwaitRehandleSplit() throws Exception;
	
	
	public boolean doUpdateStatus(PartInfo partinfo) throws Exception;
	
	public boolean doUpdateLock(PartInfo partinfo) throws Exception;
	

}
