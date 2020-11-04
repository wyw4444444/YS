package com.artegentech.system.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.artegentech.system.dao.IDeptDAO;
import com.artegentech.system.service.IDeptService;
import com.artegentech.system.service.abs.AbstractService;
import com.artegentech.system.vo.Dept;

@Service
public class DeptServiceImpl extends AbstractService implements IDeptService {

	@Resource
	private IDeptDAO deptdao;

	@Override
	public List<Dept> getAll() throws Exception {
		return this.deptdao.findAll();
	}

	@Override
	public List<Dept> getAllUnlocked() throws Exception {
		return this.deptdao.findAllUnlocked();
	}

	@Override
	public boolean add(Dept dept) throws Exception {
		if (this.deptdao.findByCode(dept.getDept_code()) == null
				&& this.deptdao.findByName(dept.getDept_name()) == null) {
			return this.deptdao.doCreate(dept);
		}
		return false;
	}

	@Override
	public boolean edit(Dept dept) throws Exception {
		if (this.deptdao.findById(dept.getDept_id()) == null) {
			return false;
		} else {
			return this.deptdao.doUpdate(dept);
		}
	}

	@Override
	public Dept getByDeptCode(String dept_code) throws Exception {
		return this.deptdao.findByCode(dept_code);
	}

	@Override
	public Dept getByDeptName(String dept_name) throws Exception {
		return this.deptdao.findByName(dept_name);
	}

	@Override
	public Long getAllCount() throws Exception {
		return this.deptdao.getAllCount();
	}

	@Override
	public Long getAllCount(String column, String keyword) throws Exception {
		Map<String, Object> map = super.handleParams(column, keyword);
		return this.deptdao.getAllCount(map);
	}

	@Override
	public Long getAllUnlockedCount() throws Exception {
		return this.deptdao.getAllUnlockedCount();
	}

	@Override
	public List<Dept> getAllSplit(String column, String keyword, Integer currentPage, Integer lineSize)
			throws Exception {
		Map<String, Object> map = super.handleParams(column, keyword, currentPage, lineSize);
		return this.deptdao.findAllSplit(map);
	}

	@Override
	public List<Dept> getAllUnlockedSplit(String column, String keyword, Integer currentPage, Integer lineSize)
			throws Exception {
		Map<String, Object> map = super.handleParams(column, keyword, currentPage, lineSize);
		return this.deptdao.findAllUnlockedSplit(map);
	}

	@Override
	public List<Dept> getAllUnlockedByMid(String member_id) throws Exception {
		return this.deptdao.findAllDeptFlagByMid(member_id);
	}

}
