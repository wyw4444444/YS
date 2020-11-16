package com.artegentech.system.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.artegentech.system.dao.IRoleDAO;
import com.artegentech.system.service.IRoleService;
import com.artegentech.system.service.abs.AbstractService;
import com.artegentech.system.vo.Role;

@Service
public class RoleServiceImpl extends AbstractService implements IRoleService {

	@Resource
	private IRoleDAO roledao;

	@Transactional(rollbackFor=Exception.class)
	@Override
	public boolean add(Role role, Set<Integer> actionsSet) throws Exception {
		if (this.roledao.findByFlag(role.getFlag()) == null && this.roledao.findByTitle(role.getTitle()) == null) {
			if (this.roledao.doCreate(role)) {
				Integer role_id = this.roledao.findByTitle(role.getTitle()).getRole_id();

				boolean flagAction = false;
				if (actionsSet!=null && actionsSet.size()!=0) {
					Iterator<Integer> iter = actionsSet.iterator();
					while (iter.hasNext()) {
						Map<String, Object> map = new HashMap<String, Object>();
						map.put("role_id", role_id);
						map.put("action_id", iter.next());
						flagAction = this.roledao.doCreateRoleAndAction(map);
						if (flagAction) {
							System.out.println("IRoleDAO doCreateRoleAndAction新增成功。");
						} else {
							System.out.println("IRoleDAO doCreateRoleAndAction新增失败。");
						}
					}
				}
				// 確認部門、組別和角色是否都已增加
				if ((actionsSet.size()!=0 && flagAction == true) || actionsSet.size()!=0) {
					return true;
				} else {
					return false;
				}
			} else {
				System.out.println("IRoleDAO Add新增失败，此角色在系统中已有。");
				return false;
			}
		}
		return false;
	}

	@Transactional(rollbackFor=Exception.class)
	@Override
	public boolean edit(Role role, Set<Integer> actionsSet) throws Exception {
		if (this.roledao.findById(role.getRole_id()) == null) {
			return false;
		} else {
			if (actionsSet==null || actionsSet.size()==0) {
				this.roledao.removeRoleAndAction(role.getRole_id());
				return this.roledao.doUpdate(role);
			} else {
				this.roledao.doUpdate(role);
				Integer role_id = role.getRole_id();
				// 先刪除既有的Role與Action的關係表數據，再新增關係表數據
				this.roledao.removeRoleAndAction(role_id);
				Iterator<Integer> iter = actionsSet.iterator();
				boolean a = false;
				while (iter.hasNext()) {
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("role_id", role_id);
					map.put("action_id", iter.next());
					a = this.roledao.doCreateRoleAndAction(map);
					if (a) {
						System.out.println("IRoleDAO doCreateRoleAndAction新增成功。");
					} else {
						System.out.println("IRoleDAO doCreateRoleAndAction新增失败。");
						return false;
					}
				}
				return true;
			}
		}
	}

	@Override
	public List<Role> getAll() throws Exception {
		List<Role> roles = this.roledao.findAll();
		List<Role> result = new ArrayList<Role>();
		Iterator<Role> iter = roles.iterator();
		while (iter.hasNext()) {
			Role role = iter.next();
			role.setRoleAction(this.roledao.findAllActionByRid(role.getRole_id()));
			result.add(role);
		}
		return result;
	}

	@Override
	public List<Role> getAllSplit(String column, String keyword, Integer currentPage, Integer lineSize)
			throws Exception {
		Map<String, Object> map = super.handleParams(column, keyword, currentPage, lineSize);
		List<Role> roles = this.roledao.findAllSplit(map);
		List<Role> result = new ArrayList<Role>();
		Iterator<Role> iter = roles.iterator();
		while (iter.hasNext()) {
			Role role = iter.next();
			role.setRoleAction(this.roledao.findAllActionByRid(role.getRole_id()));
			result.add(role);
		}
		return result;
	}

	@Override
	public List<Role> getAllSplitByActionId(String column, String keyword, Integer currentPage, Integer lineSize)
			throws Exception {
		Map<String, Object> map = super.handleParams2(column, keyword, currentPage, lineSize);
		List<Role> roles = this.roledao.findAllByActionId(map);
		List<Role> result = new ArrayList<Role>();
		Iterator<Role> iter = roles.iterator();
		while (iter.hasNext()) {
			Role role = iter.next();
			role.setRoleAction(this.roledao.findAllActionByRid(role.getRole_id()));
			result.add(role);
		}
		return result;
	}

	@Override
	public List<Role> getAllUnlocked() throws Exception {
		List<Role> roles = this.roledao.findAllUnlocked();
		List<Role> result = new ArrayList<Role>();
		Iterator<Role> iter = roles.iterator();
		while (iter.hasNext()) {
			Role role = iter.next();
			role.setRoleAction(this.roledao.findAllActionByRid(role.getRole_id()));
			result.add(role);
		}
		return result;
	}

	@Override
	public List<Role> getAllUnlockedSplit(String column, String keyword, Integer currentPage, Integer lineSize)
			throws Exception {
		Map<String, Object> map = super.handleParams(column, keyword, currentPage, lineSize);
		List<Role> roles = this.roledao.findAllUnlockedSplit(map);
		List<Role> result = new ArrayList<Role>();
		Iterator<Role> iter = roles.iterator();
		while (iter.hasNext()) {
			Role role = iter.next();
			role.setRoleAction(this.roledao.findAllActionByRid(role.getRole_id()));
			result.add(role);
		}
		return result;
	}

	@Override
	public Role getByRoleFlag(String flag) throws Exception {
		return this.roledao.findByFlag(flag);
	}

	@Override
	public Role getByRoleTitle(String title) throws Exception {
		return this.roledao.findByTitle(title);
	}

	@Override
	public Long getAllCount() throws Exception {
		return this.roledao.getAllCount();
	}

	@Override
	public Long getAllCountByActionId(Integer action_id) throws Exception {
		return this.roledao.getAllCountByActionId(action_id);
	}

	@Override
	public Long getAllCount(String column, String keyword) throws Exception {
		Map<String, Object> map = super.handleParams(column, keyword);
		return this.roledao.getAllCount(map);
	}

	@Override
	public Long getAllUnlockedCount() throws Exception {
		return this.roledao.getAllUnlockedCount();
	}
	
	@Override
	public Long getAllUnlockedCount(String column, String keyword) throws Exception {
		Map<String, Object> map = super.handleParams(column, keyword);
		return this.roledao.getAllUnlockedCount(map);
	}

	@Override
	public Set<Role> getByMid(String member_id) throws Exception {
		return this.roledao.findByMid(member_id);
	}

	@Override
	public Role getById(Integer role_id) throws Exception {
		Role role = this.roledao.findById(role_id);
		role.setRoleAction(this.roledao.findAllActionByRid(role_id));
		return role;
	}

}
