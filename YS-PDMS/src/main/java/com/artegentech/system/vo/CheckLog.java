package com.artegentech.system.vo;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
public class CheckLog implements Serializable {
	private Integer no;
	private String 	type_check;
	private Integer  id_check;
	private Date  reg_time_apply; 

	private String 	member_id;
	private String 	tips;
	
	private Integer check_status;
	
	private PartInfo partInfo;
	private Member member;
	private Bom   bom;
	
	public Integer getNo() {
		return no;
	}
	public void setNo(Integer no) {
		this.no = no;
	}
	public String getType_check() {
		return type_check;
	}
	public void setType_check(String type_check) {
		this.type_check = type_check;
	}
	public Integer getId_check() {
		return id_check;
	}
	public void setId_check(Integer id_check) {
		this.id_check = id_check;
	}
	public Date getReg_time_apply() {
		return reg_time_apply;
	}
	public void setReg_time_apply(Date reg_time_apply) {
		this.reg_time_apply = reg_time_apply;
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
	public Integer getCheck_status() {
		return check_status;
	}
	public void setCheck_status(Integer check_status) {
		this.check_status = check_status;
	}
	public PartInfo getPartInfo() {
		return partInfo;
	}
	public void setPartInfo(PartInfo partInfo) {
		this.partInfo = partInfo;
	}
	public Member getMember() {
		return member;
	}
	public void setMember(Member member) {
		this.member = member;
	}

	public Bom getBom() {
		return bom;
	}
	public void setBom(Bom bom) {
		this.bom = bom;
	}
	@Override
	public String toString() {
		return "CheckLog [no=" + no + ", type_check=" + type_check + ", id_check=" + id_check + ", reg_time_apply="
				+ reg_time_apply + ", member_id=" + member_id + ", tips=" + tips + ", check_status="
				+ check_status + ", partInfo=" + partInfo + ", member=" + member + ", bom=" + bom + "]";
	}

	
	
	
}
