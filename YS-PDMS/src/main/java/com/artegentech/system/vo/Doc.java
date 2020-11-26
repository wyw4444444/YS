package com.artegentech.system.vo;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
public class Doc implements Serializable {
	// id
	private Integer id;
	// 料號
	private String part_code;
	// 版本号
	private String version;
	// 設變原因
	private String change_reason;
	// 設變前
	private String changeimg_before;
	// 設變後
	private String changeimg_after;
	// pdf文檔
	private String doc_pdf;
	// dwg文檔
	private String doc_dwg;
	// ppt文檔
	private String doc_ppt;
	
//	日期
	private Date reg_time;
//	屬性
	private String spec;
	private Integer status;
	private String member_id;
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
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
	public String getChange_reason() {
		return change_reason;
	}
	public void setChange_reason(String change_reason) {
		this.change_reason = change_reason;
	}
	public String getChangeimg_before() {
		return changeimg_before;
	}
	public void setChangeimg_before(String changeimg_before) {
		this.changeimg_before = changeimg_before;
	}
	public String getChangeimg_after() {
		return changeimg_after;
	}
	public void setChangeimg_after(String changeimg_after) {
		this.changeimg_after = changeimg_after;
	}
	public String getDoc_pdf() {
		return doc_pdf;
	}
	public void setDoc_pdf(String doc_pdf) {
		this.doc_pdf = doc_pdf;
	}
	public String getDoc_dwg() {
		return doc_dwg;
	}
	public void setDoc_dwg(String doc_dwg) {
		this.doc_dwg = doc_dwg;
	}
	public String getDoc_ppt() {
		return doc_ppt;
	}
	public void setDoc_ppt(String doc_ppt) {
		this.doc_ppt = doc_ppt;
	}
	public Date getReg_time() {
		return reg_time;
	}
	public void setReg_time(Date date) {
		this.reg_time = date;
	}
	public String getSpec() {
		return spec;
	}
	public void setSpec(String spec) {
		this.spec = spec;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getMember_id() {
		return member_id;
	}
	public void setMember_id(String member_id) {
		this.member_id = member_id;
	}
	

}
