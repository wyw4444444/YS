package com.artegentech.system.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@SuppressWarnings("serial")
public class WarehouseOut implements Serializable {

	private String out_id;
	private Date out_date;
	private String part_code;
	private Double total_quantity;
	private Integer out_reason;
	private String pdr_no;
	private String member_id;
	private String note;
	private Integer locked;
	private Date lock_date;

	private PartInfo partInfo;
	private Member member;
	private Type type_out_reason;
	private List<OutList> outLists;

	public String getOut_id() {
		return out_id;
	}

	public void setOut_id(String out_id) {
		this.out_id = out_id;
	}

	public Date getOut_date() {
		return out_date;
	}

	public void setOut_date(Date out_date) {
		this.out_date = out_date;
	}

	public String getPart_code() {
		return part_code;
	}

	public void setPart_code(String part_code) {
		this.part_code = part_code;
	}

	public Double getTotal_quantity() {
		return total_quantity;
	}

	public void setTotal_quantity(Double total_quantity) {
		this.total_quantity = total_quantity;
	}

	public Integer getOut_reason() {
		return out_reason;
	}

	public void setOut_reason(Integer out_reason) {
		this.out_reason = out_reason;
	}

	public String getPdr_no() {
		return pdr_no;
	}

	public void setPdr_no(String pdr_no) {
		this.pdr_no = pdr_no;
	}

	public String getMember_id() {
		return member_id;
	}

	public void setMember_id(String member_id) {
		this.member_id = member_id;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Integer getLocked() {
		return locked;
	}

	public void setLocked(Integer locked) {
		this.locked = locked;
	}

	public Date getLock_date() {
		return lock_date;
	}

	public void setLock_date(Date lock_date) {
		this.lock_date = lock_date;
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public Type getType_out_reason() {
		return type_out_reason;
	}

	public void setType_out_reason(Type type_out_reason) {
		this.type_out_reason = type_out_reason;
	}

	public List<OutList> getOutLists() {
		return outLists;
	}

	public void setOutLists(List<OutList> outLists) {
		this.outLists = outLists;
	}
	

	public PartInfo getPartInfo() {
		return partInfo;
	}

	public void setPartInfo(PartInfo partInfo) {
		this.partInfo = partInfo;
	}

	@Override
	public String toString() {
		return "WarehouseOut [out_id=" + out_id + ", out_date=" + out_date + ", part_code=" + part_code
				+ ", total_quantity=" + total_quantity + ", out_reason=" + out_reason + ", pdr_no=" + pdr_no
				+ ", member_id=" + member_id + ", note=" + note + ", locked=" + locked + ", lock_date=" + lock_date
				+ ", member=" + member + ", type_out_reason=" + type_out_reason + ", outLists=" + outLists + "]";
	}

}
