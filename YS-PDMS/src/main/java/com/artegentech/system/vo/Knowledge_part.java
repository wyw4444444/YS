package com.artegentech.system.vo;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
public class Knowledge_part implements Serializable {
	// id
	private Integer id;
	// 料號
	private String part_code;
	// 版本号
	private String version;
	// 描述
	private String descr;
	// 名稱
	private String part_name;
	// 創建日期
	private Date save_date;
	// 創建人
	private String reg_man;
	// 附件地址
	private String fileUrl;
	// 分階狀態
	private String status;
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
	public String getDescr() {
		return descr;
	}
	public void setDescr(String descr) {
		this.descr = descr;
	}
	public String getPart_name() {
		return part_name;
	}
	public void setPart_name(String part_name) {
		this.part_name = part_name;
	}
	public Date getSave_date() {
		return save_date;
	}
	public void setSave_date(Date save_date) {
		this.save_date = save_date;
	}
	public String getReg_man() {
		return reg_man;
	}
	public void setReg_man(String reg_man) {
		this.reg_man = reg_man;
	}
	public String getFileUrl() {
		return fileUrl;
	}
	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	

}
