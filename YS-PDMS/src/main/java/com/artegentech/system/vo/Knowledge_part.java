package com.artegentech.system.vo;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Knowledge_part implements Serializable {
	// id
	private Integer id;
	// 料號
	private String part_code;
	// 版本号
	private String version;
	// 版本号
	private String descr;
	// 版本号
	private String part_name;
	// 版本号
	private String date;
	// 版本号
	private String fileUrl;
	// 版本号
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
	public String getDesc() {
		return descr;
	}
	public void setDesc(String desc) {
		this.descr = desc;
	}
	public String getName() {
		return part_name;
	}
	public void setName(String name) {
		this.part_name = name;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getFile() {
		return fileUrl;
	}
	public void setFile(String file) {
		this.fileUrl = file;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

}
