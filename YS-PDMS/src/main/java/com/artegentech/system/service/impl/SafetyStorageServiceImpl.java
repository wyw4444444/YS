package com.artegentech.system.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.artegentech.system.dao.IMemberDAO;
import com.artegentech.system.dao.IPartInfoDAO;
import com.artegentech.system.dao.ISafetyStorageDAO;
import com.artegentech.system.service.ISafetyStorageService;
import com.artegentech.system.service.abs.AbstractService;
import com.artegentech.system.vo.PartInfo;
import com.artegentech.system.vo.SafetyStorage;

@Service
public class SafetyStorageServiceImpl extends AbstractService implements ISafetyStorageService {

	@Resource
	private ISafetyStorageDAO safetyStorageDAO;
	
	@Resource
	private IMemberDAO memberDAO;
	
	@Resource
	private IPartInfoDAO partInfoDAO;
	
/*	@Resource
	private IPartInfoDAO partInfoDAO;*/

	@Override
	public boolean add(SafetyStorage safetyStorage) throws Exception {
		return this.safetyStorageDAO.doCreate(safetyStorage);
	}

	@Override
	public List<SafetyStorage> getAll() throws Exception {
		Iterator<SafetyStorage> safetyStorages = this.safetyStorageDAO.findAll().iterator();
		if (safetyStorages != null) {
			List<SafetyStorage> result = new ArrayList<SafetyStorage>();
			while (safetyStorages.hasNext()) {
				SafetyStorage safetyStorage = safetyStorages.next();
				String member_id = safetyStorage.getMember_id();
				String part_code = safetyStorage.getPart_code();
				safetyStorage.setMember(this.memberDAO.findById(member_id));
				safetyStorage.setPartInfo(this.partInfoDAO.findByCode(part_code));
				result.add(safetyStorage);
			}
			return result;
		}
		return null;
	}

	@Override
	public List<SafetyStorage> getAllSplit(String column, String keyword, Integer currentPage, Integer lineSize)
			throws Exception {
		Map<String, Object> map = super.handleParams(column, keyword, currentPage, lineSize);
		Iterator<SafetyStorage> safetyStorages = this.safetyStorageDAO.findAllSplit(map).iterator();
		if (safetyStorages != null) {
			List<SafetyStorage> result = new ArrayList<SafetyStorage>();
			while (safetyStorages.hasNext()) {
				SafetyStorage safetyStorage = safetyStorages.next();
				String member_id = safetyStorage.getMember_id();
				String part_code = safetyStorage.getPart_code();
				safetyStorage.setMember(this.memberDAO.findById(member_id));
				safetyStorage.setPartInfo(this.partInfoDAO.findByCode(part_code));
				result.add(safetyStorage);
			}
			return result;
		}
		return null;
	}

	@Override
	public List<SafetyStorage> getAllLatest() throws Exception {
		Iterator<SafetyStorage> safetyStorages = this.safetyStorageDAO.findAllLatest().iterator();
		if (safetyStorages != null) {
			List<SafetyStorage> result = new ArrayList<SafetyStorage>();
			while (safetyStorages.hasNext()) {
				SafetyStorage safetyStorage = safetyStorages.next();
				String member_id = safetyStorage.getMember_id();
				//String part_code = safetyStorage.getPart_code();
				safetyStorage.setMember(this.memberDAO.findById(member_id));
				//safetyStorage.setPartInfo(this.partInfoDAO.findById(part_code));
				result.add(safetyStorage);
			}
			return result;
		}
		return null;
	}

	@Override
	public List<SafetyStorage> getAllLatestSplit(Integer currentPage, Integer lineSize) throws Exception {
		Map<String, Object> map = super.handleParams(null, null, currentPage, lineSize);
		Iterator<SafetyStorage> safetyStorages = this.safetyStorageDAO.findAllLatestSplit(map).iterator();
		if (safetyStorages != null) {
			List<SafetyStorage> result = new ArrayList<SafetyStorage>();
			while (safetyStorages.hasNext()) {
				SafetyStorage safetyStorage = safetyStorages.next();
				String member_id = safetyStorage.getMember_id();
				String part_code = safetyStorage.getPart_code();
				safetyStorage.setMember(this.memberDAO.findById(member_id));
				PartInfo partinfo = this.partInfoDAO.findByCode(part_code);
				safetyStorage.setPartInfo(partinfo);
				result.add(safetyStorage);
			}
			return result;
		}
		return null;
	}

	@Override
	public Long getAllCount() throws Exception {
		return this.safetyStorageDAO.getAllCount();
	}

	@Override
	public Long getAllCount(String column, String keyword) throws Exception {
		Map<String, Object> map = super.handleParams(column, keyword);
		return this.safetyStorageDAO.getAllCount(map);
	}

	@Override
	public Long getAllLatestCount() throws Exception {
		return this.safetyStorageDAO.getAllLatestCount();
	}

}
