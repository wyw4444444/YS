package com.artegentech.system.vo;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
public class SafetyStorage implements Serializable {
	// 记录ID
	private Integer data_id;
	// 料号
	private String part_code;
	// 安全库存量
	private Double safety_stock;
	// 建立人员
	private String member_id;
	// 建立时间
	private Date data_date;

	private Member member;
	
	private PartInfo partInfo;

	public Integer getData_id() {
		return data_id;
	}

	public void setData_id(Integer data_id) {
		this.data_id = data_id;
	}

	public String getPart_code() {
		return part_code;
	}

	public void setPart_code(String part_code) {
		this.part_code = part_code;
	}

	public Double getSafety_stock() {
		return safety_stock;
	}

	public void setSafety_stock(Double safety_stock) {
		this.safety_stock = safety_stock;
	}

	public String getMember_id() {
		return member_id;
	}

	public void setMember_id(String member_id) {
		this.member_id = member_id;
	}

	public Date getData_date() {
		return data_date;
	}

	public void setData_date(Date data_date) {
		this.data_date = data_date;
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public PartInfo getPartInfo() {
		return partInfo;
	}

	public void setPartInfo(PartInfo partInfo) {
		this.partInfo = partInfo;
	}

	@Override
	public String toString() {
		return "SatetyStorage [data_id=" + data_id + ", part_code=" + part_code + ", safety_stock=" + safety_stock
				+ ", member_id=" + member_id + ", data_date=" + data_date + ", member=" + member + "]";
	}

}
