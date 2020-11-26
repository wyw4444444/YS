package com.artegentech.system.vo;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
public class Knowledge_levelupLog implements Serializable {
	// id
	private Integer id;
	// 升版類型
	private String part_type;
	// 分階ID
	private Integer part_id;
	// 申請人
	private String member_id;
	// 升級原因
	private String tips;
	// 申請時間
	private Date reg_time;
	// 發行時間
	private Date approve_time;
	// 分階料號
	private String part_code;
	// 版本號
	private String version;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getPart_type() {
		return part_type;
	}
	public void setPart_type(String part_type) {
		this.part_type = part_type;
	}
	public Integer getPart_id() {
		return part_id;
	}
	public void setPart_id(Integer part_id) {
		this.part_id = part_id;
	}
	public String getMember_id() {
		return member_id;
	}
	public void setMember_id(String member_id) {
		this.member_id = member_id;
	}
	public String getTips() {
		return tips;
	}
	public void setTips(String tips) {
		this.tips = tips;
	}
	public Date getReg_time() {
		return reg_time;
	}
	public void setReg_time(Date reg_time) {
		this.reg_time = reg_time;
	}
	public Date getApprove_time() {
		return approve_time;
	}
	public void setApprove_time(Date approve_time) {
		this.approve_time = approve_time;
	}
	public String getPart_code() {
		return part_code;
	}
	public void setPart_code(String part_code) {
		this.part_code = part_code;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	
	
}
