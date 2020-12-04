package com.artegentech.system.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@SuppressWarnings("serial")
public class PDR implements Serializable {
	private Integer no;
	private String PDR_id;
	private String PER_id;
	private String PM;
	private String man;
	private String description;
	private Integer customer_no;
	private Date start_date;
	private Date end_date;
	private Integer type_id_status;
	
	private List<PDRLog> pdrlog;
	private List<PDRDetail> pdrdetail;
	private List<PDRCost> pdrcost;
	private Customer customer;
	private Member member;
	private Member manDetail;
	private Type type;
	
	
	//-------------------------------------

	
	

	public Integer getCustomer_no() {
		return customer_no;
	}


	public List<PDRCost> getPdrcost() {
		return pdrcost;
	}


	public void setPdrcost(List<PDRCost> pdrcost) {
		this.pdrcost = pdrcost;
	}


	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public Integer getType_id_status() {
		return type_id_status;
	}

	public void setType_id_status(Integer type_id_status) {
		this.type_id_status = type_id_status;
	}

	public void setPdrdetail(List<PDRDetail> pdrdetail) {
		this.pdrdetail = pdrdetail;
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public Integer getNo() {
		return no;
	}

	public void setNo(Integer no) {
		this.no = no;
	}

	public void setCustomer_no(Integer customer_no) {
		this.customer_no = customer_no;
	}

	

	
	public String getPDR_id() {
		return PDR_id;
	}

	public void setPDR_id(String pDR_id) {
		PDR_id = pDR_id;
	}

	public String getPER_id() {
		return PER_id;
	}


	public void setPER_id(String pER_id) {
		PER_id = pER_id;
	}


	public String getPM() {
		return PM;
	}

	public void setPM(String pM) {
		PM = pM;
	}

	public String getMan() {
		return man;
	}


	public void setMan(String man) {
		this.man = man;
	}


	public Member getManDetail() {
		return manDetail;
	}


	public void setManDetail(Member manDetail) {
		this.manDetail = manDetail;
	}


	public Date getEnd_date() {
		return end_date;
	}

	public void setEnd_date(Date end_date) {
		this.end_date = end_date;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	

	public Date getStart_date() {
		return start_date;
	}

	public void setStart_date(Date start_date) {
		this.start_date = start_date;
	}

	public List<PDRLog> getPdrlog() {
		return pdrlog;
	}

	public void setPdrlog(List<PDRLog> pdrlog) {
		this.pdrlog = pdrlog;
	}

	public List<PDRDetail> getPdrdetail() {
		return pdrdetail;
	}

	public void setPdr_detail(List<PDRDetail> pdrdetail) {
		this.pdrdetail = pdrdetail;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	@Override
	public String toString() {
		return "PDR [PDR_id=" + PDR_id + ", PM=" + PM + ", description=" + description + ", customer_no=" + customer_no
				+ ", start_date=" + start_date + ", pdrlog=" + pdrlog + ", pdrdetail=" + pdrdetail + ", customer="
				+ customer + "]";
	}

	
	
}
