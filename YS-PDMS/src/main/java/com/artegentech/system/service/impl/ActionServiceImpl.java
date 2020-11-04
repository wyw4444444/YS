package com.artegentech.system.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.artegentech.system.dao.IActionDAO;
import com.artegentech.system.service.IActionService;
import com.artegentech.system.service.abs.AbstractService;
import com.artegentech.system.vo.Action;

@Service
public class ActionServiceImpl extends AbstractService implements IActionService {

	@Resource
	private IActionDAO actiondao;

	@Override
	public boolean add(Action action) throws Exception {
		if (this.actiondao.findByFlag(action.getFlag()) == null
				&& this.actiondao.findByTitle(action.getTitle()) == null) {
			return this.actiondao.doCreate(action);
		}
		return false;
	}

	@Override
	public boolean edit(Action action) throws Exception {
		if (this.actiondao.findById(action.getAction_id()) == null) {
			return false;
		} else {
			return this.actiondao.doUpdate(action);
		}
	}

	@Override
	public List<Action> getAll() throws Exception {
		return this.actiondao.findAll();
	}

	@Override
	public List<Action> getAllSplit(String column, String keyword, Integer currentPage, Integer lineSize)
			throws Exception {
		Map<String, Object> map = super.handleParams(column, keyword, currentPage, lineSize);
		return this.actiondao.findAllSplit(map);
	}

	@Override
	public List<Action> getAllUnlocked() throws Exception {
		return this.actiondao.findAllUnlocked();
	}

	@Override
	public List<Action> getAllUnlockedSplit(String column, String keyword, Integer currentPage, Integer lineSize)
			throws Exception {
		Map<String, Object> map = super.handleParams(column, keyword, currentPage, lineSize);
		return this.actiondao.findAllUnlockedSplit(map);
	}

	@Override
	public Action getByActionFlag(String flag) throws Exception {
		return this.actiondao.findByFlag(flag);
	}

	@Override
	public Action getByActionTitle(String title) throws Exception {
		return this.actiondao.findByTitle(title);
	}

	@Override
	public Long getAllCount() throws Exception {
		return this.actiondao.getAllCount();
	}

	@Override
	public Long getAllCount(String column, String keyword) throws Exception {
		Map<String, Object> map = super.handleParams(column, keyword);
		return this.actiondao.getAllCount(map);
	}

	@Override
	public Long getAllUnlockedCount() throws Exception {
		return this.actiondao.getAllUnlockedCount();
	}

	@Override
	public List<Action> getAllByMid(String member_id) throws Exception {
		return this.actiondao.findByMid(member_id);
	}

}
