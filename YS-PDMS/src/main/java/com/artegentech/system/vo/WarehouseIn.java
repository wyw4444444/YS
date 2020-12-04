package com.artegentech.system.vo;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
public class WarehouseIn implements Serializable {

	private String in_id;
	private Date in_date;
	private String part_code;
	private Double quantity;
	private String pur_sheet_id;
	private Double price;
	// 入库金额，自动计算，quantity*price
	private Double amount;
	private String member_id;
	private String note;
	private Integer in_reason;
	private String pdr_no;
	// 剩余库存数量
	private Double surplus_quantity;
	// 库存金额，自动计算，surplus_quantity*price
	private Double storage_amount;
	private Integer locked;
	private Date lock_date;
	private String history_in_id;

	 private PartInfo partInfo;
	private Member member;
	private Type type_in_reason;
	private SafetyStorage safetyStorage;

	public String getIn_id() {
		return in_id;
	}

	public void setIn_id(String in_id) {
		this.in_id = in_id;
	}

	public Date getIn_date() {
		return in_date;
	}

	public void setIn_date(Date in_date) {
		this.in_date = in_date;
	}

	public String getPart_code() {
		return part_code;
	}

	public void setPart_code(String part_code) {
		this.part_code = part_code;
	}

	public Double getQuantity() {
		return quantity;
	}

	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}

	public String getPur_sheet_id() {
		return pur_sheet_id;
	}

	public void setPur_sheet_id(String pur_sheet_id) {
		this.pur_sheet_id = pur_sheet_id;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
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

	public Integer getIn_reason() {
		return in_reason;
	}

	public void setIn_reason(Integer in_reason) {
		this.in_reason = in_reason;
	}

	public String getPdr_no() {
		return pdr_no;
	}

	public void setPdr_no(String pdr_no) {
		this.pdr_no = pdr_no;
	}

	public Double getSurplus_quantity() {
		return surplus_quantity;
	}

	public void setSurplus_quantity(Double surplus_quantity) {
		this.surplus_quantity = surplus_quantity;
	}

	public Double getStorage_amount() {
		return storage_amount;
	}

	public void setStorage_amount(Double storage_amount) {
		this.storage_amount = storage_amount;
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

	public String getHistory_in_id() {
		return history_in_id;
	}

	public void setHistory_in_id(String history_in_id) {
		this.history_in_id = history_in_id;
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public Type getType_in_reason() {
		return type_in_reason;
	}

	public void setType_in_reason(Type type_in_reason) {
		this.type_in_reason = type_in_reason;
	}

	public SafetyStorage getSafetyStorage() {
		return safetyStorage;
	}

	public void setSafetyStorage(SafetyStorage safetyStorage) {
		this.safetyStorage = safetyStorage;
	}
	

	public PartInfo getPartInfo() {
		return partInfo;
	}

	public void setPartInfo(PartInfo partInfo) {
		this.partInfo = partInfo;
	}

	@Override
	public String toString() {
		return "WarehouseIn [in_id=" + in_id + ", in_date=" + in_date + ", part_code=" + part_code + ", quantity="
				+ quantity + ", pur_sheet_id=" + pur_sheet_id + ", price=" + price + ", amount=" + amount
				+ ", member_id=" + member_id + ", note=" + note + ", in_reason=" + in_reason + ", pdr_no=" + pdr_no
				+ ", surplus_quantity=" + surplus_quantity + ", storage_amount=" + storage_amount + ", locked=" + locked
				+ ", lock_date=" + lock_date + ", history_in_id=" + history_in_id + ", member=" + member
				+ ", type_in_reason=" + type_in_reason + ", safetyStorage=" + safetyStorage + "]";
	}

}
