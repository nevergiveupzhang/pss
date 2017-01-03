package com.psssystem.connection.vo;

import java.io.Serializable;

public class PaymentItemVO implements Serializable{
	private String paymentOrderID;
	private String name;
	private int sum;
	private String  remarks;

	public PaymentItemVO(String name, int sum, String remarks) {
		super();
		this.name = name;
		this.sum = sum;
		this.remarks = remarks;
	}
	public PaymentItemVO(String paymentOrderID, String name, int sum,
			String remarks) {
		super();
		this.paymentOrderID = paymentOrderID;
		this.name = name;
		this.sum = sum;
		this.remarks = remarks;
	}
	public String getPaymentOrderID() {
		return paymentOrderID;
	}
	public String getName() {
		return name;
	}
	public int getSum() {
		return sum;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setPaymentOrderID(String paymentOrderID) {
		this.paymentOrderID = paymentOrderID;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setSum(int sum) {
		this.sum = sum;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
}
