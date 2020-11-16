package com.artegentech.system.vo;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

@SuppressWarnings("serial")
public class Type implements Serializable {
	// 類型id
	private Integer id;
	// 類型代碼
	private String parent_type;
	// 類型名稱
	private String sub_type;

	private Integer upper_id;

	private Date reg_time;
	
	private String member_id;
	// 是否鎖定
	private Integer locked;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getParent_type() {
		return parent_type;
	}
	public void setParent_type(String parent_type) {
		this.parent_type = parent_type;
	}

	public String getMember_id() {
		return member_id;
	}
	public void setMember_id(String member_id) {
		this.member_id = member_id;
	}
	public Integer getLocked() {
		return locked;
	}
	public void setLocked(Integer locked) {
		this.locked = locked;
	}
	public Date getReg_time() {
		return reg_time;
	}
	public void setReg_time(Date reg_time) {
		this.reg_time = reg_time;
	}
	public Integer getUpper_id() {
		return upper_id;
	}
	public void setUpper_id(Integer upper_id) {
		this.upper_id = upper_id;
	}
	public String getSub_type() {
		return sub_type;
	}
	public void setSub_type(String sub_type) {
		this.sub_type = sub_type;
	}
	@Override
	public String toString() {
		return "Type [id=" + id + ", parent_type=" + parent_type + ", sub_type=" + sub_type + ", upper_id=" + upper_id
				+ ", reg_time=" + reg_time + ", member_id=" + member_id + ", locked=" + locked + "]";
	}
	


}
