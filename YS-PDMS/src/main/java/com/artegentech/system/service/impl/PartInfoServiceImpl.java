package com.artegentech.system.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.artegentech.system.dao.IPartInfoDAO;
import com.artegentech.system.service.IPartInfoService;
import com.artegentech.system.service.abs.AbstractService;
import com.artegentech.system.vo.PartInfo;

@Service
public class PartInfoServiceImpl extends AbstractService implements IPartInfoService {

	@Resource
	private IPartInfoDAO partInfoDao;
	
	

	@Override
	public boolean add(PartInfo partinfo) throws Exception {

		if (this.partInfoDao.findByCode(partinfo.getPart_code()) == null) {
			return this.partInfoDao.doCreate(partinfo);
		}
		return false;
	}
	
	@Override
	public PartInfo getByCode(String part_code) throws Exception {
		return this.partInfoDao.findByCode(part_code);
	}

	@Override
	public PartInfo getPartInfoById(Integer id) throws Exception {
		return this.partInfoDao.findById(id);
	}

	
	public boolean edit(PartInfo partinfo) throws Exception {

		if (this.partInfoDao.findByCode(partinfo.getPart_code()) != null) {
			return this.partInfoDao.doUpdate(partinfo);
		}
		return false;
	}
	
	public boolean editStatus(PartInfo partinfo) throws Exception {

		PartInfo old=this.partInfoDao.findById(partinfo.getId());
		if (old!= null) {
			//取回、退回、发行申请时,只能是待审核状态
			if((partinfo.getStatus().equals(2)||partinfo.getStatus().equals(3)||partinfo.getStatus().equals(5))&& !old.getStatus().equals(1)) {
				return false;
			}
			
			return this.partInfoDao.doUpdateStatus(partinfo);
		}
		return false;
	}

	@Override
	public Long getAllUnlockedCountByConditions(String part_code,String tradename,String spec,String prop) throws Exception {
		
		Map<String, Object> map = new HashMap<String, Object>();
		if ("".equals(part_code) || part_code == null || "null".equalsIgnoreCase(part_code)) {
			map.put("part_code", null);
		} else {
			map.put("part_code", "%" + part_code + "%");
		}
		
		if ("".equals(tradename) || tradename == null || "null".equalsIgnoreCase(tradename)) {
			map.put("tradename", null);
		} else {
			map.put("tradename", "%" + tradename + "%");
		}
		if ("".equals(spec) || spec == null || "null".equalsIgnoreCase(spec)) {
			map.put("spec", null);
		} else {
			map.put("spec", "%" + spec + "%");
		}
		if ("".equals(prop) || prop == null || "null".equalsIgnoreCase(prop)) {
			map.put("prop", null);
		} else {
			map.put("prop", "%" + prop + "%");
		}
		
		return this.partInfoDao.getAllUnlockedCount(map);
	}

	
	@Override
	public List<PartInfo> getAllUnlockedSplitByConditions(String part_code,String tradename,String spec,String prop,Integer currentPage, Integer lineSize)
			throws Exception {
		Map<String, Object> map = super.handleParams1(currentPage, lineSize);
		if ("".equals(part_code) || part_code == null || "null".equalsIgnoreCase(part_code)) {
			map.put("part_code", null);
		} else {
			map.put("part_code", "%" + part_code + "%");
		}
		
		if ("".equals(tradename) || tradename == null || "null".equalsIgnoreCase(tradename)) {
			map.put("tradename", null);
		} else {
			map.put("tradename", "%" + tradename + "%");
		}
		if ("".equals(spec) || spec == null || "null".equalsIgnoreCase(spec)) {
			map.put("spec", null);
		} else {
			map.put("spec", "%" + spec + "%");
		}
		if ("".equals(prop) || prop == null || "null".equalsIgnoreCase(prop)) {
			map.put("prop", null);
		} else {
			map.put("prop", "%" + prop + "%");
		}
		
		return this.partInfoDao.findAllUnlockedSplit(map);
	}
	
	
	
	@Override
	public Long getAllCountByConditions(String part_code,String tradename,String spec,String prop) throws Exception {
		
		Map<String, Object> map = new HashMap<String, Object>();
		if ("".equals(part_code) || part_code == null || "null".equalsIgnoreCase(part_code)) {
			map.put("part_code", null);
		} else {
			map.put("part_code", "%" + part_code + "%");
		}
		
		if ("".equals(tradename) || tradename == null || "null".equalsIgnoreCase(tradename)) {
			map.put("tradename", null);
		} else {
			map.put("tradename", "%" + tradename + "%");
		}
		if ("".equals(spec) || spec == null || "null".equalsIgnoreCase(spec)) {
			map.put("spec", null);
		} else {
			map.put("spec", "%" + spec + "%");
		}
		if ("".equals(prop) || prop == null || "null".equalsIgnoreCase(prop)) {
			map.put("prop", null);
		} else {
			map.put("prop", "%" + prop + "%");
		}
		
		
		return this.partInfoDao.getAllCount(map);
	}

	@Override
	public List<PartInfo> getAllSplitByConditions(String part_code,String tradename,String spec,String prop,Integer currentPage, Integer lineSize)
			throws Exception {
		Map<String, Object> map = super.handleParams1(currentPage, lineSize);
		
		if ("".equals(part_code) || part_code == null || "null".equalsIgnoreCase(part_code)) {
			map.put("part_code", null);
		} else {
			map.put("part_code", "%" + part_code + "%");
		}
		
		if ("".equals(tradename) || tradename == null || "null".equalsIgnoreCase(tradename)) {
			map.put("tradename", null);
		} else {
			map.put("tradename", "%" + tradename + "%");
		}
		if ("".equals(spec) || spec == null || "null".equalsIgnoreCase(spec)) {
			map.put("spec", null);
		} else {
			map.put("spec", "%" + spec + "%");
		}
		if ("".equals(prop) || prop == null || "null".equalsIgnoreCase(prop)) {
			map.put("prop", null);
		} else {
			map.put("prop", "%" + prop + "%");
		}
		return this.partInfoDao.findAllSplit(map);
	}

	
	public boolean editLock(PartInfo partinfo) throws Exception {

		if (this.partInfoDao.findByCode(partinfo.getPart_code()) != null) {
			return this.partInfoDao.doUpdateLock(partinfo);
		}
		return false;
	}
	
}
