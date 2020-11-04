package com.artegentech.system.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.artegentech.system.dao.ITypeDAO;
import com.artegentech.system.service.ITypeService;
import com.artegentech.system.service.abs.AbstractService;
import com.artegentech.system.vo.Type;

@Service
public class TypeServiceImpl extends AbstractService implements ITypeService {

	@Resource
	private ITypeDAO typedao;

	@Override
	public boolean add(Type type) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("parent_type", type.getParent_type());
		map.put("sub_type", type.getSub_type());
		map.put("upper_id", type.getUpper_id());
		if (this.typedao.findByChildAndParentAndUpper(map) == null) {
			return this.typedao.doCreate(type);
		}
		return false;
	}
	
	@Override
	public Type getByParentAndUpper(String parent_type,Integer upper_id) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("parent_type", parent_type);
		map.put("upper_id", upper_id);
		return this.typedao.findByParentAndUpper(map);
	}

	
	@Override
	public Type getByParentAndSubAndUpper(String parent_type,String sub_type,Integer upper_id) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("parent_type", parent_type);
		map.put("sub_type", sub_type);
		map.put("upper_id", upper_id);
		return this.typedao.findByParentAndSubAndUpper(map);
	}

	
	@Override
	public List<Type> getAllParentType() throws Exception {

		List<Type> result=new ArrayList<>();
		
		Iterator<Type> iter1=this.typedao.findParentTypeFirst().iterator();
		while(iter1.hasNext()) {
			Type type1=iter1.next();
			result.add(type1);
		}
	
		Iterator<Type> iter2=this.typedao.findParentTypeEcptFirst().iterator();
		while(iter2.hasNext()) {
			Type type2=iter2.next();
			
			Type type3=new Type();
			String parent_type=type2.getParent_type()+"-"+type2.getSub_type();
			Integer upper_id=type2.getId();
			
			type3.setParent_type(parent_type);
			type3.setUpper_id(upper_id);
			
			result.add(type3);
		}

		return result;
	}

	
	@Override
	public List<Type> getAllSplit(String column, String keyword, Integer currentPage, Integer lineSize)
			throws Exception {
		Map<String, Object> map = super.handleParams(column, keyword, currentPage, lineSize);
		return this.typedao.findAllSplit(map);
	}

	@Override
	public List<Type> getAllUnlockedSplit(String column, String keyword, Integer currentPage, Integer lineSize)
			throws Exception {
		Map<String, Object> map = super.handleParams(column, keyword, currentPage, lineSize);
		return this.typedao.findAllUnlockedSplit(map);
	}
	
	
	@Override
	public Long getAllCount() throws Exception {
		return this.typedao.getAllCount();
	}

	@Override
	public Long getAllCount(String column, String keyword) throws Exception {
		Map<String, Object> map = super.handleParams(column, keyword);
		return this.typedao.getAllCount(map);
	}

	@Override
	public Long getAllUnlockedCount() throws Exception {
		return this.typedao.getAllUnlockedCount();
	}
	
	@Override
	public boolean edit(Type type) throws Exception {
		if (this.typedao.findById(type.getId()) == null) {
			return false;
		} else {
			return this.typedao.doUpdate(type);
		}
	}

}
