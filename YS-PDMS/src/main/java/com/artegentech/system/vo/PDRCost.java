package com.artegentech.system.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@SuppressWarnings("serial")
public class PDRCost implements Serializable {
	private Integer no;
	private String PDR_id;
	private String PAR;
	private String name;
	private String type;
	private String description;
	private float estimate_cost;
	private float actual_cost;
	private String remark;
	
	private PDR pdr;

	
	
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getPAR() {
		return PAR;
	}

	public void setPAR(String pAR) {
		PAR = pAR;
	}

	public Integer getNo() {
		return no;
	}

	public void setNo(Integer no) {
		this.no = no;
	}

	public String getPDR_id() {
		return PDR_id;
	}

	public void setPDR_id(String pDR_id) {
		PDR_id = pDR_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	



	public float getEstimate_cost() {
		return estimate_cost;
	}

	public void setEstimate_cost(float estimate_cost) {
		this.estimate_cost = estimate_cost;
	}

	public float getActual_cost() {
		return actual_cost;
	}

	public void setActual_cost(float actual_cost) {
		this.actual_cost = actual_cost;
	}

	public PDR getPdr() {
		return pdr;
	}

	public void setPdr(PDR pdr) {
		this.pdr = pdr;
	}
	
	

}
