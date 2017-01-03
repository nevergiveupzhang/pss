package com.psssystem.connection.vo;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ReceiptsOrderVO implements Serializable{
	private int customerID=0;
	private int userID=0;
	private int sum=0;
	
	private String id="";
	private String username="";
	private String customerName="";
	private String createdDate=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
	private String status="草稿";
	private List<AccountTransferVO> transfers=null;
	
	public static class Builder{
		private String id="";
		private int customerID=0;
		private int userID=0;
		private int sum=0;
		private String username="";
		private String customerName="";
		private String createdDate=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		private String status="草稿";
		private List<AccountTransferVO> transfers=null;
		public Builder(){}
		
		public ReceiptsOrderVO build(){
			return new ReceiptsOrderVO(this);
		}
		public Builder copyProperties(ReceiptsOrderVO vo){
			this.customerID = vo.customerID;
			this.customerName=vo.customerName;
			this.userID = vo.userID;
			this.username=vo.username;
			this.transfers = vo.transfers;
			this.sum = vo.sum;
			this.createdDate=vo.createdDate;
			this.id=vo.id;
			this.status=vo.status;
			this.transfers=vo.transfers;
			return this;
		}
		public Builder Id(String id) {
			this.id = id;
			return this;
		}
		public Builder Username(String username){
			this.username=username;
			return this;
		}
		public Builder CustomerName(String customerName){
			this.customerName=customerName;
			return this;
		}
		public Builder CustomerID(int customerID) {
			this.customerID = customerID;
			return this;
		}
		public Builder UserID(int userID) {
			this.userID = userID;
			return this;
		}
		public Builder Transfers(List<AccountTransferVO> transfers) {
			this.transfers = transfers;
			return this;
		}
		public Builder Sum(int sum){
			this.sum=sum;
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
	
	public ReceiptsOrderVO(Builder builder) {
		this.customerID = builder.customerID;
		this.userID = builder.userID;
		this.customerName=builder.customerName;
		this.username=builder.username;
		this.transfers = builder.transfers;
		this.sum = builder.sum;
		this.createdDate=builder.createdDate;
		this.id=builder.id;
		this.status=builder.status;
		this.transfers=builder.transfers;
	}
	public String getId() {
		return id;
	}
	public int getCustomerID() {
		return customerID;
	}
	public int getUserID() {
		return userID;
	}
	public int getSum(){
		return sum;
	}
	public List<AccountTransferVO> getTransfers() {
		return transfers;
	}
	
	public String getCreatedDate() {
		return createdDate;
	}
	public String getStatus() {
		return status;
	}
	public String getUsername(){
		return username;
	}
	public String getCustomerName(){
		return customerName;
	}
	@Override
	public boolean equals(Object obj){
		if(obj==null) return false;
		if(obj instanceof ReceiptsOrderVO){
			ReceiptsOrderVO vo=(ReceiptsOrderVO) obj;
			return this.username.equals(vo.username)&&this.customerName.equals(vo.customerName)&&this.createdDate.equals(vo.createdDate)&&this.customerID==vo.customerID&&this.id.equals(vo.id)&&this.status.equals(vo.status)&&this.sum==vo.sum&&this.userID==vo.userID;
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
