package com.artegentech.system.vo;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Action implements Serializable {
	// 權限id
	private Integer action_id;
	// 權限代碼
	private String flag;
	// 權限名稱
	private String title;
	// 是否鎖定
	private Integer locked;

	public Integer getAction_id() {
		return action_id;
	}

	public void setAction_id(Integer action_id) {
		this.action_id = action_id;
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

	@Override
	public String toString() {
		return "Action [action_id=" + action_id + ", flag=" + flag + ", title=" + title + ", locked=" + locked + "]";
	}

}
