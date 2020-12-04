package com.artegentech.system.dao;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.artegentech.system.vo.Dept;
import com.artegentech.system.vo.Member;
import com.artegentech.system.vo.Role;

public interface IMemberDAO extends IDAO<String, Member> {

	public boolean doCreateMemberAndRole(Map<String, Object> params) throws Exception;

	public boolean doCreateMemberAndDept(Map<String, Object> params) throws Exception;

	public boolean RemoveMemberAndRole(String member_id) throws Exception;

	public boolean RemoveMemberAndDept(String member_id) throws Exception;

	public List<Member> findAllUnlockedByRoleFlag(String flag) throws Exception;

	public List<Member> findAllSplitByRole(Map<String, Object> map) throws Exception;
	
	public List<Member> findAllByRole(Map<String, Object> map) throws Exception;

	public List<Member> findAllSplitByDept(Map<String, Object> map) throws Exception;

	public Long getAllCountByRole(Map<String, Object> map) throws Exception;

	public Long getAllCountByDept(Map<String, Object> map) throws Exception;

	public Long getAllUnlockedCountByRole(Map<String, Object> map) throws Exception;

	public Long getAllUnlockedCountByDept(Map<String, Object> map) throws Exception;

	public List<Dept> findAllDeptByMid(String member_id) throws Exception;

	public Set<Role> findAllRoleByMid(String member_id) throws Exception;

	public boolean doUpdatePassword(Member member) throws Exception;

	public Member findByIdAll(String member_id) throws Exception;
}
