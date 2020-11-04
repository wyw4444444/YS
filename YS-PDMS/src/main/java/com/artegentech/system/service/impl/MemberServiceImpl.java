package com.artegentech.system.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.artegentech.system.dao.IActionDAO;
import com.artegentech.system.dao.IDeptDAO;
import com.artegentech.system.dao.IMemberDAO;
import com.artegentech.system.dao.IRoleDAO;
import com.artegentech.system.service.IMemberService;
import com.artegentech.system.service.abs.AbstractService;
import com.artegentech.system.vo.Dept;
import com.artegentech.system.vo.Member;
import com.artegentech.system.vo.Role;

@Service
public class MemberServiceImpl extends AbstractService implements IMemberService {

	@Resource
	private IMemberDAO memberDAO;

	@Resource
	private IRoleDAO roleDAO;

	@Resource
	private IActionDAO actionDAO;

	@Resource
	private IDeptDAO deptDAO;

	// ATGSYSTEM
	private static final String SALT = "QVRHU1lTVEVN";
	
	@Transactional(rollbackFor=Exception.class)
	@Override
	public boolean add(Member member, Set<Integer> deptsSet, Set<Integer> rolesSet) throws Exception {
		if (this.memberDAO.findById(member.getMember_id()) == null) {
			member.setPassword(new Md5Hash("admin", SALT, 5).toString());
			if (this.memberDAO.doCreate(member)) {
				
				Boolean flagDept = false;
				Boolean flagRole = false;
				
				if(deptsSet!=null && deptsSet.size()!=0) {
					Iterator<Integer> iterDept = deptsSet.iterator();
					while (iterDept.hasNext()) {
						Map<String, Object> mapDept = new HashMap<String, Object>();
						mapDept.put("member_id", member.getMember_id());
						mapDept.put("dept_id", iterDept.next());
						flagDept=this.memberDAO.doCreateMemberAndDept(mapDept);
						if (flagDept == true) {
							System.out.println("ImemberDAO doCreateMemberAndDept新增成功。");
						} else {
							System.out.println("ImemberDAO doCreateMemberAndDept新增失败。");
						}
					}
				}else {
					flagDept = true;
				}
				if(rolesSet!=null && rolesSet.size()!=0) {
					Iterator<Integer> iterRole = rolesSet.iterator();
					while (iterRole.hasNext()) {
						Map<String, Object> mapRole = new HashMap<String, Object>();
						mapRole.put("member_id", member.getMember_id());
						mapRole.put("role_id", iterRole.next());
						flagRole=this.memberDAO.doCreateMemberAndRole(mapRole);
						if (flagRole == true) {
							System.out.println("ImemberDAO doCreateMemberAndRole新增成功。");
						} else {
							System.out.println("ImemberDAO doCreateMemberAndRole新增失败。");
						}
					}
				}else {
					flagRole = true;
				}
				
				// 確認部門、組別和角色是否都已增加
				if (flagDept == true && flagRole == true) {
					return true;
				} else {
					return false;
				}
			} else {
				System.out.println("ImemberDAO AddMember新增失败，此人已存在。");
				return false;
			}
		}
		return false;
	}
	
	@Transactional(rollbackFor=Exception.class)
	@Override
	public boolean edit(Member member, Set<Integer> deptsSet, Set<Integer> rolesSet) throws Exception {
		if(this.memberDAO.doUpdate(member)) {
			String member_id = member.getMember_id();
			
			// 刪除member與dept對應表中的關係，然後再建立新關係
			this.memberDAO.RemoveMemberAndDept(member_id);
			if(deptsSet!=null && deptsSet.size()!=0) {
				Iterator<Integer> iterDept = deptsSet.iterator();
				while (iterDept.hasNext()) {
					Map<String, Object> mapDept = new HashMap<String, Object>();
					mapDept.put("member_id", member_id);
					mapDept.put("dept_id", iterDept.next());
					this.memberDAO.doCreateMemberAndDept(mapDept);
				}
			}
			
			// 刪除member與role對應表中的關係，然後再建立新關係
			this.memberDAO.RemoveMemberAndRole(member_id);
			if(rolesSet!=null && rolesSet.size()!=0) {
				Iterator<Integer> iterRole = rolesSet.iterator();
				while (iterRole.hasNext()) {
					Map<String, Object> mapRole = new HashMap<String, Object>();
					mapRole.put("member_id", member_id);
					mapRole.put("role_id", iterRole.next());
					this.memberDAO.doCreateMemberAndRole(mapRole);
				}
			}
			
			return true;
		}
		return false;
	}
	
	@Override
	public boolean editPassword(Member member) throws Exception {
		return this.memberDAO.doUpdatePassword(member);
	}
	
	@Override
	public List<Member> getAll() throws Exception {
		Iterator<Member> members = this.memberDAO.findAll().iterator();
		if (members != null) {
			List<Member> result = new ArrayList<Member>();
			while (members.hasNext()) {
				Member vo = members.next();
				String member_id = vo.getMember_id();
				List<Dept> depts = this.memberDAO.findAllDeptByMid(member_id);
				Set<Role> roles = this.memberDAO.findAllRoleByMid(member_id);
				if (depts != null && depts.size()!=0) {
					vo.setDepts(depts);
				}
				if (roles != null && roles.size()!=0) {
					vo.setRoles(roles);
				}
				result.add(vo);
			}
			return result;
		}
		return null;
	}
	
	@Override
	public List<Member> getAllSplit(String column, String keyword, Integer currentPage, Integer lineSize) throws Exception {
		Map<String, Object> map = super.handleParams(column, keyword, currentPage, lineSize);
		Map<String, Object> map2 = super.handleParams2(column, keyword, currentPage, lineSize);
		Iterator<Member> members = null;
		if (column != null) {
			switch (column) {
			case ("dept_id"):
				members = this.memberDAO.findAllSplitByDept(map2).iterator();
				break;
			case ("role_id"):
				members = this.memberDAO.findAllSplitByRole(map2).iterator();
				break;
			default:
				members = this.memberDAO.findAllSplit(map).iterator();
				break;
			}
		} else {
			members = this.memberDAO.findAllSplit(map).iterator();
		}
		if (members != null) {
			List<Member> result = new ArrayList<Member>();
			while (members.hasNext()) {
				Member vo = members.next();
				String member_id = vo.getMember_id();
				List<Dept> depts = this.memberDAO.findAllDeptByMid(member_id);
				Set<Role> roles = this.memberDAO.findAllRoleByMid(member_id);
				if (depts != null && depts.size()!=0) {
					vo.setDepts(depts);
				}
				if (roles != null && roles.size()!=0) {
					vo.setRoles(roles);
				}
				result.add(vo);
			}
			return result;
		}
		return null;
	}
	
	@Override
	public List<Member> getAllUnlocked() throws Exception{
		Iterator<Member> members = this.memberDAO.findAllUnlocked().iterator();
		if (members != null) {
			List<Member> result = new ArrayList<Member>();
			while (members.hasNext()) {
				Member vo = members.next();
				String member_id = vo.getMember_id();
				List<Dept> depts = this.memberDAO.findAllDeptByMid(member_id);
				Set<Role> roles = this.memberDAO.findAllRoleByMid(member_id);
				if (depts != null && depts.size()!=0) {
					vo.setDepts(depts);
				}
				if (roles != null && roles.size()!=0) {
					vo.setRoles(roles);
				}
				result.add(vo);
			}
			return result;
		}
		return null;
	}
	
	@Override
	public List<Member> getAllUnlockedSplit(String column, String keyword, Integer currentPage, Integer lineSize)
			throws Exception {
		Map<String, Object> map = super.handleParams(column, keyword, currentPage, lineSize);
		Iterator<Member> members = this.memberDAO.findAllUnlockedSplit(map).iterator();
		if (members != null) {
			List<Member> result = new ArrayList<Member>();
			while (members.hasNext()) {
				Member vo = members.next();
				String member_id = vo.getMember_id();
				List<Dept> depts = this.memberDAO.findAllDeptByMid(member_id);
				Set<Role> roles = this.memberDAO.findAllRoleByMid(member_id);
				if (depts != null && depts.size()!=0) {
					vo.setDepts(depts);
				}
				if (roles != null && roles.size()!=0) {
					vo.setRoles(roles);
				}
				result.add(vo);
			}
			return result;
		}
		return null;
	}
	
	@Override
	public Member getById(String member_id) throws Exception {
		Member member = this.memberDAO.findById(member_id);
		List<Dept> depts = this.memberDAO.findAllDeptByMid(member_id);
		Set<Role> roles = this.memberDAO.findAllRoleByMid(member_id);
		if (depts != null && depts.size()!=0) {
			member.setDepts(depts);
		}
		if (roles != null && roles.size()!=0) {
			member.setRoles(roles);
		}
		return member;
	}
	
	@Override
	public Member getByIdAll(String member_id) throws Exception {
		Member member = this.memberDAO.findByIdAll(member_id);
		List<Dept> depts = this.memberDAO.findAllDeptByMid(member_id);
		Set<Role> roles = this.memberDAO.findAllRoleByMid(member_id);
		if (depts != null && depts.size()!=0) {
			member.setDepts(depts);
		}
		if (roles != null && roles.size()!=0) {
			member.setRoles(roles);
		}
		return member;
	}
	
	@Override
	public Long getAllCount() throws Exception {
		return this.memberDAO.getAllCount();
	}
	
	@Override
	public Long getAllCount(String column, String keyword) throws Exception {
		Map<String, Object> map = super.handleParams(column, keyword);
		Map<String, Object> map2 = super.handleParams2(column, keyword);
		if (column != null) {
			switch (column) {
			case ("dept_id"):
				return this.memberDAO.getAllCountByDept(map2);
			case ("role_id"):
				return this.memberDAO.getAllCountByRole(map2);
			default:
				return this.memberDAO.getAllCount(map);
			}
		} else {
			return this.memberDAO.getAllCount(map);
		}
	}
	
	@Override
	public Long getAllUnlockedCount() throws Exception {
		return this.memberDAO.getAllUnlockedCount();
	}
	
	@Override
	public Map<String, Object> listAuthByMember(String member_id) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("allRoles", this.roleDAO.findAllRoleFlagByMid(member_id));
		map.put("allActions", this.actionDAO.findAllActionFlagByMid(member_id));
		return map;
	}

	@Override
	public Long getAllUnlockedCount(String column, String keyword) throws Exception {
		Map<String, Object> map = super.handleParams(column, keyword);
		Map<String, Object> map2 = super.handleParams2(column, keyword);
		if (column != null) {
			switch (column) {
			case ("dept_id"):
				return this.memberDAO.getAllUnlockedCountByDept(map2);
			case ("role_id"):
				return this.memberDAO.getAllUnlockedCountByRole(map2);
			default:
				return this.memberDAO.getAllUnlockedCount(map);
			}
		} else {
			return this.memberDAO.getAllUnlockedCount(map);
		}
	}

	@Override
	public List<Member> getAllUnlockedByRoleFlag(String flag) throws Exception {
		return this.memberDAO.findAllUnlockedByRoleFlag(flag);
	}
	
	
}
