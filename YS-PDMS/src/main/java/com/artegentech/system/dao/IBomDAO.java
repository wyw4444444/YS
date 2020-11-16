package com.artegentech.system.dao;

import java.util.List;
import java.util.Map;

import com.artegentech.system.vo.Bom;


public interface IBomDAO extends IDAO<Integer, Bom> {

	
	public Bom getMaxVersionByPart_code_up(String part_code_up) throws Exception;	
	
	public List<Bom> findAwaitCheckSplit() throws Exception;
	
	public List<Bom> findAwaitRehandleSplit() throws Exception;
	
	public boolean doUpdateStatus(Bom bom) throws Exception;
	
	public List<Bom> getByPart_code_upAndVer(Bom bom) throws Exception;
	
	public boolean doDelete(Bom bom) throws Exception;
	
	public List<Bom> findAllPart_code_up() throws Exception;
	
	public List<Bom> findByPart_code_up(String part_code_up) throws Exception;
	
	public List<Bom> findAllPart_code_upAll() throws Exception;
	
	public List<Bom> findByPart_code_upAll(String part_code_up) throws Exception;
	
	public List<Bom> findAllVerByPart_code_up(String part_code_up) throws Exception;
	
	public boolean doUpdateLock(Bom bom) throws Exception;
/*	
  	public Bom findByCode(String part_code) throws Exception;
	
	*/
	
}
