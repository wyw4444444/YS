package com.artegentech.system.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

@SuppressWarnings("serial")
public class Member implements Serializable {
	// 用戶ID（工號）
	private String member_id;
	// 登錄密碼
	private String password;
	// 用戶姓名
	private String member_name;
	// 註冊日期
	private Date reg_date;
	// 是否鎖定
	private Integer locked;

	// 人員對應角色
	private Set<Role> roles;
	// 人員對應部門
	private List<Dept> depts;

	public String getMember_id() {
		return member_id;
	}

	public void setMember_id(String member_id) {
		this.member_id = member_id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMember_name() {
		return member_name;
	}

	public void setMember_name(String member_name) {
		this.member_name = member_name;
	}

	public Date getReg_date() {
		return reg_date;
	}

	public void setReg_date(Date reg_date) {
		this.reg_date = reg_date;
	}

	public Integer getLocked() {
		return locked;
	}

	public void setLocked(Integer locked) {
		this.locked = locked;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public List<Dept> getDepts() {
		return depts;
	}

	public void setDepts(List<Dept> depts) {
		this.depts = depts;
	}

	@Override
	public String toString() {
		return "Member [member_id=" + member_id + ", password=" + password + ", member_name=" + member_name
				+ ", reg_date=" + reg_date + ", locked=" + locked + ", roles=" + roles + ", depts=" + depts + "]";
	}

}
