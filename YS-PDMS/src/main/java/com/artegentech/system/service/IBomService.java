package com.artegentech.system.service;

import java.util.List;
import java.util.Map;

import com.artegentech.system.vo.Bom;

public interface IBomService {
	
	public Integer getNewVersion(String part_code_up)throws Exception;
	
	public boolean addStocks(List<Bom> ltBom,String member_id) throws Exception;
	
	public boolean add(List<Bom> ltBom,String member_id) throws Exception;
	
	public List<Bom> getBomById(Integer id) throws Exception;

	public boolean updateUpdate(List<Bom> ltBom,String member_id,String tips) throws Exception;
	
	public boolean updateAdd(List<Bom> ltBom) throws Exception;
	
	public boolean updateDel(List<Bom> ltBom) throws Exception;
	
	public boolean editStatus(Bom bom) throws Exception;
	
	public List<Bom> getDownSplit(String part_code_up,boolean All) throws Exception;
	
	public List<Bom> getUpSplit(String part_code_down) throws Exception;
	
	public List<Bom> getAllDownSplit(String part_code_up) throws Exception;
	
	public List<Bom> getStepDownSplit(List<Bom> validBom,String part_code_up,Integer level) throws Exception;
	
	public List<Bom> getDownHistorySplit(String part_code_up) throws Exception;
	
	
	public List<Bom> getUpTailSplit(String part_code_down) throws Exception;
	
	public List<Bom> getStepUpSplit(List<Bom> validBom,Bom bom,Integer level) throws Exception;
	
	public boolean editLock(Bom bom) throws Exception;
	

	

}
