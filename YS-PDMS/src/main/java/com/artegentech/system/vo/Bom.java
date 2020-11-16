package com.artegentech.system.vo;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Bom implements Serializable {
	private Integer id;
	private String part_code_up;
	private String  part_code_down;
	private Float  dosage; 
	private Integer  base;
	private String  unit;
	private Integer ver;
	private Integer status;
	private Integer locked;
	
	private Integer level;
	
	private PartInfo partInfo;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getPart_code_up() {
		return part_code_up;
	}
	public void setPart_code_up(String part_code_up) {
		this.part_code_up = part_code_up;
	}
	public String getPart_code_down() {
		return part_code_down;
	}
	public void setPart_code_down(String part_code_down) {
		this.part_code_down = part_code_down;
	}
	public Float getDosage() {
		return dosage;
	}
	public void setDosage(Float dosage) {
		this.dosage = dosage;
	}
	public Integer getBase() {
		return base;
	}
	public void setBase(Integer base) {
		this.base = base;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public Integer getVer() {
		return ver;
	}
	public void setVer(Integer ver) {
		this.ver = ver;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getLocked() {
		return locked;
	}
	public void setLocked(Integer locked) {
		this.locked = locked;
	}
	public PartInfo getPartInfo() {
		return partInfo;
	}
	public void setPartInfo(PartInfo partInfo) {
		this.partInfo = partInfo;
	}
	public Integer getLevel() {
		return level;
	}
	public void setLevel(Integer level) {
		this.level = level;
	}
	@Override
	public String toString() {
		return "Bom [id=" + id + ", part_code_up=" + part_code_up + ", part_code_down=" + part_code_down + ", dosage="
				+ dosage + ", base=" + base + ", unit=" + unit + ", ver=" + ver + ", status=" + status + ", locked="
				+ locked + ", level=" + level + ", partInfo=" + partInfo + "]";
	}
	
	
}
