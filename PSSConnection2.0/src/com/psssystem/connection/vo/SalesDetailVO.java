package com.psssystem.connection.vo;

import java.io.Serializable;
import java.util.Date;

public class SalesDetailVO implements Serializable {
	/*时间（精确到天），商品名，型号，数量，单价，总额*/
	private String date;
	private String commName;
	private String commType;
	private int amount;
	private int price;
	private int sum;
	public SalesDetailVO(String date, String commName, String commType,
			int amount, int price, int sum) {
		super();
		this.date = date;
		this.commName = commName;
		this.commType = commType;
		this.amount = amount;
		this.price = price;
		this.sum = sum;
	}
	public String getDate() {
		return date;
	}
	public String getCommName() {
		return commName;
	}
	public String getCommType() {
		return commType;
	}
	public int getAmount() {
		return amount;
	}
	public int getPrice() {
		return price;
	}
	public int getSum() {
		return sum;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public void setCommName(String commName) {
		this.commName = commName;
	}
	public void setCommType(String commType) {
		this.commType = commType;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public void setSum(int sum) {
		this.sum = sum;
	}
	
	@Override
	public boolean equals(Object obj){
		if(obj==null) return false;
		if(obj instanceof SalesDetailVO){
			SalesDetailVO s=(SalesDetailVO) obj;
			return this.amount==s.amount&&this.commName.equals(s.commName)&&this.commType.equals(s.commType)&&this.date.equals(s.date)&&this.price==s.price&&this.sum==s.sum;
		}
		return false;
	}
	@Override
	public int hashCode(){
		int result=17;
		result=31*result+amount;
		result=31*result+price;
		result=31*result+sum;
		return result;
	}
}
