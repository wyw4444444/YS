package com.artegentech.system.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.google.protobuf.Timestamp;

@SuppressWarnings("serial")
public class PDRLog implements Serializable {
	private Integer no;
	private String PDR_id;
	private Date reg_time;
	private String log;
	private String member_id;
	
	private Member member;
	
	
	
	
	public Integer getNo() {
		return no;
	}
	public void setNo(Integer no) {
		this.no = no;
	}
	public Member getMember() {
		return member;
	}
	public void setMember(Member member) {
		this.member = member;
	}
	public String getPDR_id() {
		return PDR_id;
	}
	public void setPDR_id(String pDR_id) {
		PDR_id = pDR_id;
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
	public String getLog() {
		return log;
	}
	public void setLog(String log) {
		this.log = log;
	}
	
	
	
	@Override
	public String toString() {
		return "PDRLog [PDR_id=" + PDR_id + ", reg_time=" + reg_time + ", log=" + log + ", member_id=" + member_id
				+ "]";
	}
	
	
	

}
