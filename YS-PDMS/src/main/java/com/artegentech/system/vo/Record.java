package com.artegentech.system.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

@SuppressWarnings("serial")
public class Record implements Serializable {
	// 用戶ID（工號）
	private String member_id;
	
	// 註冊日期
	private Date reg_time;
	
	private Member member;
	
	

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public String getMember_id() {
		return member_id;
	}

	public void setMember_id(String member_id) {
		this.member_id = member_id;
	}

	public Date getReg_time() {
		return reg_time;
	}

	public void setReg_time(Date reg_time) {
		this.reg_time = reg_time;
	}
	
	
	

}
