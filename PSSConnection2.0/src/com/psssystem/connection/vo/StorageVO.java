package com.psssystem.connection.vo;

import java.io.Serializable;
import java.util.Date;

public class StorageVO implements Serializable{
	private int id;
	private int commID;
	private String commName;
	private int amount;
	private int sum;
	private String date;
	private String type;
	
	public StorageVO(int commID, String commName, int amount, int sum,
			String date, String type) {
		super();
		this.commID = commID;
		this.commName = commName;
		this.amount = amount;
		this.sum = sum;
		this.date = date;
		this.type = type;
	}
	
	public StorageVO(int id, int commID, String commName, int amount, int sum,
			String date, String type) {
		super();
		this.id = id;
		this.commID = commID;
		this.commName = commName;
		this.amount = amount;
		this.sum = sum;
		this.date = date;
		this.type = type;
	}

	public int getCommID() {
		return commID;
	}
	public String getCommName() {
		return commName;
	}
	public void setCommName(String commName) {
		this.commName = commName;
	}
	public int getAmount() {
		return amount;
	}
	public int getSum() {
		return sum;
	}
	public String getDate() {
		return date;
	}
	public String getType() {
		return type;
	}
	public void setCommID(int commID) {
		this.commID = commID;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public void setSum(int sum) {
		this.sum = sum;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public void setType(String type) {
		this.type = type;
	}
}
