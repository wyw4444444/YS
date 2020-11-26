package com.artegentech.system.vo;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
public class Knowledge implements Serializable {
	// 部門id
	private Integer id;
	// 部門代號
	private String part_code;
	// 部門名稱
	private String part_name;
	// 部門名稱
	private String descr;
	// 部門名稱
	private String status;
	private String version;
	private String part_type;
	private Date date;
	private String fileUrl;
	private String reg_man;
	public String getFileUrl() {
		return fileUrl;
	}
	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getPart_type() {
		return part_type;
	}
	public void setPart_type(String part_type) {
		this.part_type = part_type;
	}
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
	public String getPart_name() {
		return part_name;
	}
	public void setPart_name(String part_name) {
		this.part_name = part_name;
	}
	public String getDescr() {
		return descr;
	}
	public void setDescr(String descr) {
		this.descr = descr;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getReg_man() {
		return reg_man;
	}
	public void setReg_man(String reg_man) {
		this.reg_man = reg_man;
	}


}
