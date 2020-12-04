package com.artegentech.system.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@SuppressWarnings("serial")
public class PDRDetail implements Serializable {
	
	private String PDR_id;
	private String task;
	
	private String type;
	
	private Date start_date;
	
	private Date end_date;
	
	private Date actual_end_date;
	
	private String remark;

	private String person;
	
	private String dept;
	
	
	private PDR pdr;
	
	
	
	

	public PDR getPdr() {
		return pdr;
	}

	public void setPdr(PDR pdr) {
		this.pdr = pdr;
	}

	public String getPerson() {
		return person;
	}

	public void setPerson(String person) {
		this.person = person;
	}

	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	public String getPDR_id() {
		return PDR_id;
	}

	public void setPDR_id(String pDR_id) {
		PDR_id = pDR_id;
	}

	public String getTask() {
		return task;
	}

	public void setTask(String task) {
		this.task = task;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Date getStart_date() {
		return start_date;
	}

	public void setStart_date(Date start_date) {
		this.start_date = start_date;
	}

	public Date getEnd_date() {
		return end_date;
	}

	public void setEnd_date(Date end_date) {
		this.end_date = end_date;
	}

	public Date getActual_end_date() {
		return actual_end_date;
	}

	public void setActual_end_date(Date actual_end_date) {
		this.actual_end_date = actual_end_date;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Override
	public String toString() {
		return "PDRDetail [PDR_id=" + PDR_id + ", task=" + task + ", type=" + type + ", start_date=" + start_date
				+ ", end_date=" + end_date + ", actual_end_date=" + actual_end_date + ", remark=" + remark + "]";
	}
	
	
	

}
