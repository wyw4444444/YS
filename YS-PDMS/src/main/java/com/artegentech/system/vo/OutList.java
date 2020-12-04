package com.artegentech.system.vo;

import java.io.Serializable;

@SuppressWarnings("serial")
public class OutList implements Serializable {

	private Integer outlist_id;
	private String out_id;
	private String in_id;
	private Double quantity;
	private Double pur_price;

	public Integer getOutlist_id() {
		return outlist_id;
	}

	public void setOutlist_id(Integer outlist_id) {
		this.outlist_id = outlist_id;
	}

	public String getOut_id() {
		return out_id;
	}

	public void setOut_id(String out_id) {
		this.out_id = out_id;
	}

	public String getIn_id() {
		return in_id;
	}

	public void setIn_id(String in_id) {
		this.in_id = in_id;
	}

	public Double getQuantity() {
		return quantity;
	}

	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}

	public Double getPur_price() {
		return pur_price;
	}

	public void setPur_price(Double pur_price) {
		this.pur_price = pur_price;
	}

	@Override
	public String toString() {
		return "OutList [outlist_id=" + outlist_id + ", out_id=" + out_id + ", in_id=" + in_id + ", quantity="
				+ quantity + ", pur_price=" + pur_price + "]";
	}

}
