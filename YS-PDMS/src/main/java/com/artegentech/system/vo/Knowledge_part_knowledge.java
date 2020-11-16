package com.artegentech.system.vo;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Knowledge_part_knowledge implements Serializable {
	// 部門id
	private Integer id;
	// 部門代號
	private String knowledge_id;
	private String knowledge_part_id;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getKnowledge_id() {
		return knowledge_id;
	}
	public void setKnowledge_id(String knowledge_id) {
		this.knowledge_id = knowledge_id;
	}
	public String getKnowledge_part_id() {
		return knowledge_part_id;
	}
	public void setKnowledge_part_id(String knowledge_part_id) {
		this.knowledge_part_id = knowledge_part_id;
	}



}
