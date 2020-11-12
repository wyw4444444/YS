package com.artegentech.system.vo;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Knowledge_total implements Serializable {
	// id
	private Integer id;
	// 成品id
	private Integer knowledge_id;
	// 分階id
	private Integer knowledge_part_id;
	// 分階料號
	private String part_code;
	// 成品料號
	private String code;
	// 分階版本号
	private String part_version;
	//	成品版本號
	private String version;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getKnowledge_id() {
		return knowledge_id;
	}
	public void setKnowledge_id(Integer knowledge_id) {
		this.knowledge_id = knowledge_id;
	}
	public Integer getKnowledge_part_id() {
		return knowledge_part_id;
	}
	public void setKnowledge_part_id(Integer knowledge_part_id) {
		this.knowledge_part_id = knowledge_part_id;
	}
	public String getPart_code() {
		return part_code;
	}
	public void setPart_code(String part_code) {
		this.part_code = part_code;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getPart_version() {
		return part_version;
	}
	public void setPart_version(String part_version) {
		this.part_version = part_version;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	
	
}