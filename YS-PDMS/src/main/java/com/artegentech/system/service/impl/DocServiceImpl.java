package com.artegentech.system.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.artegentech.system.dao.IDocDAO;
import com.artegentech.system.service.IDocService;
import com.artegentech.system.service.abs.AbstractService;
import com.artegentech.system.vo.Doc;

@Service
public class DocServiceImpl extends AbstractService implements IDocService {

	@Resource
	private IDocDAO docdao;

	@Override
	public boolean add(Doc doc) throws Exception {
		System.out.println(doc.getPart_code());
//		if (this.partdao.checkPartCode(part.getPart_code()) == null) {
//			return false;
//		}
		return this.docdao.add(doc);
	}

	@Override
	public Integer getMaxVersion(String code) throws Exception {
		// TODO Auto-generated method stub
		Integer count = 0;
		List<Doc> result = this.docdao.getDocByCode(code);
		for(int i=0;i<result.size();i++) {
			System.out.println(i+"::"+result.get(i).getVersion());
			if(count<result.get(i).getVersion()) {
				count = result.get(i).getVersion();
			}
		}
		return count+1;
	}
	@Override
	public Long getAllCount(String column, String keyword) throws Exception {
		Map<String, Object> map = super.handleParams(column, keyword);
		return this.docdao.getAllCount(map);
	}
	@Override
	public List<Doc> getAllSplit(String column, String keyword, Integer currentPage, Integer lineSize)
			throws Exception {
		Map<String, Object> map = super.handleParams(column, keyword, currentPage, lineSize);
		List<Doc> doc = this.docdao.findAllSplit(map);
		return doc;
	}

	@Override
	public Doc findOnedoc(String part_code, Integer version) {
		// TODO Auto-generated method stub
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("part_code", part_code);
		map.put("version", version);
		return this.docdao.findOnedoc(map);
	}

	@Override
	public List<Doc> findByPartCode(String part_code,Integer currentPage, Integer lineSize) {
		// TODO Auto-generated method stub
		if ("".equals(part_code) || part_code == null || "null".equalsIgnoreCase(part_code)) {
		}else {
			part_code = "%"+part_code+"%";
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("part_code", part_code);
		map.put("start", (currentPage - 1) * lineSize);
		map.put("lineSize", lineSize);		
		return this.docdao.findByPartCode(map);
	}
	@Override
	public List<Doc> findNewByPartCode(String part_code) {
		// TODO Auto-generated method stub
		if ("".equals(part_code) || part_code == null || "null".equalsIgnoreCase(part_code)) {
		}else {
			part_code = "%"+part_code+"%";
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("part_code", part_code);	
		return this.docdao.findNewByPartCode(map);
	}

	@Override
	public List<Doc> findAllNewDoc() {
		// TODO Auto-generated method stub
		return this.docdao.findAllNewDoc();
	}

}
