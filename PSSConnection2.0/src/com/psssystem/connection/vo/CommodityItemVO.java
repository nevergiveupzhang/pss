package com.psssystem.connection.vo;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CommodityItemVO implements Serializable {
	private String operationID="";
	private int commID=0;
	
	private String commName="";
	private String commType="";
	private int amount=0;
	private int price=0;
	private int sum=0;
	private String remarks="";
	private String createdDate=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
	public static class Builder{
		private String operationID="";
		private int commID=0;
		private String createdDate=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		private String commName="";
		private String commType="";
		private String remarks="";
		private int amount=0;
		private int price=0;
		private int sum=0;
		
		public Builder(){}
		
		public Builder(String operationID,int commID,String createdDate,String commName,String commType,String remarks,int amount,int price,int sum){
			this.amount=amount;
			this.commID=commID;
			this.createdDate=createdDate;
			this.commName=commName;
			this.commType=commType;
			this.operationID=operationID;
			this.price=price;
			this.remarks=remarks;
			this.sum=sum;
		}
		
		public CommodityItemVO build(){
			return new CommodityItemVO(this);
		}
		
		public Builder copyProperties(CommodityItemVO vo){
			this.amount=vo.amount;
			this.commID=vo.commID;
			this.commName=vo.commName;
			this.commType=vo.commType;
			this.operationID=vo.operationID;
			this.price=vo.price;
			this.remarks=vo.remarks;
			this.sum=vo.sum;
			return this;
		}
		
		public Builder operationID(String operationID){
			this.operationID=operationID;
			return this;
		}
		
		public Builder commID(int commID){
			this.commID=commID;
			return this;
		}
		public Builder createdDate(String createdDate){
			this.createdDate=createdDate;
			return this;
		}
		public Builder commName(String commName){
			this.commName=commName;
			return this;
		}
		
		public Builder commType(String commType){
			this.commType=commType;
			return this;
		}
		public Builder remarks(String remarks){
			this.remarks=remarks;
			return this;
		}
		
		public Builder price(int price){
			this.price=price;
			return this;
		}
		
		public Builder amount(int amount){
			this.amount=amount;
			return this;
		}
		
		public Builder sum(int sum){
			this.sum=sum;
			return this;
		}
		
	}
	
	public CommodityItemVO(Builder builder) {
		this.amount=builder.amount;
		this.commID=builder.commID;
		this.createdDate=builder.createdDate;
		this.commName=builder.commName;
		this.commType=builder.commType;
		this.operationID=builder.operationID;
		this.price=builder.price;
		this.remarks=builder.remarks;
		this.sum=builder.sum;
	}

	public String getOperationID() {
		return operationID;
	}
	public int getCommID() {
		return commID;
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
	public String getRemarks() {
		return remarks;
	}
	public String getCreatedDate(){
		return createdDate;
	}
	
	
	
	public static void main(String []args){
		CommodityItemVO vo=new CommodityItemVO.Builder().operationID("111").commName("name").build();
		vo=new CommodityItemVO.Builder().copyProperties(vo).commName("changedname").build();
		System.out.println(vo.getCommName()+" "+vo.getOperationID());
	}
}
