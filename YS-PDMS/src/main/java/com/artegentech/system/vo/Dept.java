package com.artegentech.system.vo;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Dept implements Serializable {
	// 部門id
	private Integer dept_id;
	// 部門代號
	private String dept_code;
	// 部門名稱
	private String dept_name;
	// 是否鎖定
	private Integer locked;

	public Integer getDept_id() {
		return dept_id;
	}

	public void setDept_id(Integer dept_id) {
		this.dept_id = dept_id;
	}

	public String getDept_code() {
		return dept_code;
	}

	public void setDept_code(String dept_code) {
		this.dept_code = dept_code;
	}

	public String getDept_name() {
		return dept_name;
	}

	public void setDept_name(String dept_name) {
		this.dept_name = dept_name;
	}

	public Integer getLocked() {
		return locked;
	}

	public void setLocked(Integer locked) {
		this.locked = locked;
	}

	@Override
	public String toString() {
		return "Dept [dept_id=" + dept_id + ", dept_code=" + dept_code + ", dept_name=" + dept_name + ", locked="
				+ locked + "]";
	}

}
