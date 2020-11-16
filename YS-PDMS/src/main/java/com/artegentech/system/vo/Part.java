package com.artegentech.system.vo;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Part implements Serializable {
	// 部件id
	private Integer id;
	// 料號
	private String part_code;
	// 名稱
	private String tradename;
	// spec
	private String spec;
	// unitid
	private String unit;
	
	public Integer getId() {
		return id;
	}
	public String getTradename() {
		return tradename;
	}
	public void setTradename(String tradename) {
		this.tradename = tradename;
	}
	public String getSpec() {
		return spec;
	}
	public void setSpec(String spec) {
		this.spec = spec;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
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



}
