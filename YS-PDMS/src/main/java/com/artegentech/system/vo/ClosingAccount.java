package com.artegentech.system.vo;

import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
public class ClosingAccount implements Serializable {

	private Integer closing_id;
	private Date closing_date;
	private String member_id;

	public Integer getClosing_id() {
		return closing_id;
	}

	public void setClosing_id(Integer closing_id) {
		this.closing_id = closing_id;
	}

	public Date getClosing_date() {
		return closing_date;
	}

	public void setClosing_date(Date closing_date) {
		this.closing_date = closing_date;
	}

	public String getMember_id() {
		return member_id;
	}

	public void setMember_id(String member_id) {
		this.member_id = member_id;
	}

	@Override
	public String toString() {
		return "ClosingAccount [closing_id=" + closing_id + ", closing_date=" + closing_date + ", member_id="
				+ member_id + "]";
	}

}
