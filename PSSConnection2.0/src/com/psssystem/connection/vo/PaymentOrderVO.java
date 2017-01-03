package com.psssystem.connection.vo;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class PaymentOrderVO implements Serializable{
	private int customerID;
	private int userID;
	private int sum;
	
	private String customerName;
	private String id;
	private String userName;
	private String accountName;
	private String createdDate;
	private String status;
	
	private List<PaymentItemVO> items;
	
	public static class Builder{
		private String id="";
		private int customerID=0;
		private String customerName="";
		private int userID=0;
		private String userName="";
		private String accountName="";
		private int sum=0;
		private List<PaymentItemVO> items=new ArrayList<PaymentItemVO>();
		
		private String createdDate=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		private String status="草稿";
		
		public Builder(){}
		
		public Builder(String id, int customerID, String customerName,
				int userID, String userName, String accountName, int sum,
				List<PaymentItemVO> items, String createdDate,
				String status) {
			super();
			this.id = id;
			this.customerID = customerID;
			this.customerName = customerName;
			this.userID = userID;
			this.userName = userName;
			this.accountName = accountName;
			this.sum = sum;
			this.items = items;
			this.createdDate = createdDate;
			this.status = status;
		}
		
		
		public PaymentOrderVO build(){
			return new PaymentOrderVO(this);
		}
		
		public Builder copyProperties(PaymentOrderVO vo){
			this.id = vo.id;
			this.customerID = vo.customerID;
			this.customerName = vo.customerName;
			this.userID = vo.userID;
			this.userName = vo.userName;
			this.accountName = vo.accountName;
			this.sum = vo.sum;
			this.items = vo.items;
			this.createdDate = vo.createdDate;
			this.status = vo.status;
			return this;
		}
		public Builder Id(String id) {
			this.id = id;
			return this;
		}
		public Builder CustomerID(int customerID) {
			this.customerID = customerID;
			return this;
		}
		public Builder CustomerName(String customerName) {
			this.customerName = customerName;
			return this;
		}
		public Builder UserID(int userID) {
			this.userID = userID;
			return this;
		}
		public Builder UserName(String userName) {
			this.userName = userName;
			return this;
		}
		public Builder AccountName(String accountName) {
			this.accountName = accountName;
			return this;
		}
		public Builder Sum(int sum) {
			this.sum = sum;
			return this;
		}
		public Builder Items(List<PaymentItemVO> items) {
			this.items = items;
			return this;
		}
		public Builder CreatedDate(String createdDate) {
			this.createdDate = createdDate;
			return this;
		}
		public Builder Status(String status) {
			this.status = status;
			return this;
		}
		
	}
	
	
	public PaymentOrderVO(Builder builder) {
		this.id = builder.id;
		this.customerID = builder.customerID;
		this.customerName = builder.customerName;
		this.userID = builder.userID;
		this.userName = builder.userName;
		this.accountName = builder.accountName;
		this.sum = builder.sum;
		this.items = builder.items;
		this.createdDate = builder.createdDate;
		this.status = builder.status;
	}
	public String getId() {
		return id;
	}
	public int getCustomerID() {
		return customerID;
	}
	public String getCustomerName() {
		return customerName;
	}
	public int getUserID() {
		return userID;
	}
	public String getUserName() {
		return userName;
	}
	public String getAccountName() {
		return accountName;
	}
	public int getSum() {
		return sum;
	}
	public List<PaymentItemVO> getItems() {
		return items;
	}
	public String getCreatedDate() {
		return createdDate;
	}
	public String getStatus() {
		return status;
	}
	
	@Override
	public boolean equals(Object obj){
		if(obj==null) return false;
		if(obj instanceof PaymentOrderVO){
			PaymentOrderVO vo=(PaymentOrderVO) obj;
			return this.accountName.equals(vo.accountName)&&this.createdDate.equals(vo.createdDate)&&this.customerID==vo.customerID&&this.customerName.equals(vo.customerName)&&this.id.equals(vo.id)&&this.status.equals(vo.status)&&this.sum==vo.sum&&this.userID==vo.userID&&this.userName.equals(vo.userName);
		}
		return false;
	}
	@Override
	public int hashCode(){
		int result=17;
		result=31*result+userID;
		result=31*result+customerID;
		result=31*result+sum;
		return result;
	}
}
