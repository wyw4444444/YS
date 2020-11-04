package com.artegentech.system.vo;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
public class Role implements Serializable {
	// 角色id
	private Integer role_id;
	// 角色代碼
	private String flag;
	// 角色名稱
	private String title;
	// 是否鎖定
	private Integer locked;
	// 對應權限的列表
	private List<Action> roleAction;

	public Integer getRole_id() {
		return role_id;
	}

	public void setRole_id(Integer role_id) {
		this.role_id = role_id;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getLocked() {
		return locked;
	}

	public void setLocked(Integer locked) {
		this.locked = locked;
	}

	public List<Action> getRoleAction() {
		return roleAction;
	}

	public void setRoleAction(List<Action> roleAction) {
		this.roleAction = roleAction;
	}

	@Override
	public String toString() {
		return "Role [role_id=" + role_id + ", flag=" + flag + ", title=" + title + ", locked=" + locked
				+ ", roleAction=" + roleAction + "]";
	}

}
