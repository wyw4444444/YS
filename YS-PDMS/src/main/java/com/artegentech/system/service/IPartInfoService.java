package com.artegentech.system.service;

import java.util.List;
import java.util.Map;

import com.artegentech.system.vo.PartInfo;

public interface IPartInfoService {

	public PartInfo getByCode(String part_code) throws Exception;

	public boolean add(PartInfo partinfo) throws Exception;
	
	public PartInfo getPartInfoById(Integer id) throws Exception;
	
	public boolean edit(PartInfo partinfo) throws Exception;
	
	public boolean editStatus(PartInfo partinfo) throws Exception;
	
	
	public Long getAllUnlockedCountByConditions(String part_code,String tradename,String spec,String prop) throws Exception;
	
	public List<PartInfo> getAllUnlockedSplitByConditions(String part_code,String tradename,String spec,String prop,Integer currentPage, Integer lineSize)
			throws Exception;

	
	public Long getAllCountByConditions(String part_code,String tradename,String spec,String prop) throws Exception;
	
	
	public List<PartInfo> getAllSplitByConditions(String part_code,String tradename,String spec,String prop,Integer currentPage, Integer lineSize)
			throws Exception;

	public boolean editLock(PartInfo partinfo) throws Exception;
	
	public List<PartInfo> getAllUnlocked() throws Exception;

}
