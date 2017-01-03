package com.psssystem.connection.vo;

import java.io.Serializable;

public class AccountTransferVO implements Serializable{
	private String receiptsID;
	private String accountName;
	private int sum;
	private String remarks;
	
	public AccountTransferVO(String receiptsID, String accountName, int sum,
			String remarks) {
		super();
		this.receiptsID = receiptsID;
		this.accountName = accountName;
		this.sum = sum;
		this.remarks = remarks;
	}


	
	public int getSum() {
		return sum;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setSum(int sum) {
		this.sum = sum;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getReceiptsID() {
		return receiptsID;
	}
	public void setReceiptsID(String receiptsID) {
		this.receiptsID = receiptsID;
	}
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	@Override
	public String toString() {
		return "收款单编号："+this.receiptsID+",账户名："+this.accountName+"，总额："+this.sum;
	}
}
