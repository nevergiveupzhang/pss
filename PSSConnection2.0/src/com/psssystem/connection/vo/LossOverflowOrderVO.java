package com.psssystem.connection.vo;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LossOverflowOrderVO implements Serializable {
	private int id;
	private int commID;
	private int amount;
	private int sum=0;
	
	private String commName;
	private String type;
	private String createdDate;
	private String status;
	
	public LossOverflowOrderVO(int commID, String commName, int amount,String type) {
		this(commID,commName,amount,type,"草稿");
	}
	public LossOverflowOrderVO(int commID, String commName, int amount,
			String type, String status) {
		this(0,commID,commName,amount,type,new SimpleDateFormat("yyyy-MM-dd HH:mm:dd").format(new Date()),status);
	}
	public LossOverflowOrderVO(int id,int commID, String commName, int amount,
			String type, String createdDate, String status) {
		this(id, commID, commName, amount, type, createdDate, status, 0);
	}
	public LossOverflowOrderVO(int id,int commID, String commName, int amount,
			String type, String createdDate, String status,int sum) {
		super();
		this.id=id;
		this.commID = commID;
		this.commName = commName;
		this.amount = amount;
		this.type = type;
		this.createdDate = createdDate;
		this.status = status;
		this.sum=sum;
	}
	public int getCommID() {
		return commID;
	}
	public String getCommName() {
		return commName;
	}
	public int getAmount() {
		return amount;
	}
	public String getType() {
		return type;
	}
	public String getStatus() {
		return status;
	}
	public void setCommID(int commID) {
		this.commID = commID;
	}
	public void setCommName(String commName) {
		this.commName = commName;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public void setType(String type) {
		this.type = type;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public int getSum() {
		return sum;
	}
	public void setSum(int sum) {
		this.sum = sum;
	}
	@Override
	public boolean equals(Object obj){
		if(obj==null) return false;
		if(obj instanceof GiftOrderVO){
			LossOverflowOrderVO vo=(LossOverflowOrderVO) obj;
			return this.amount==vo.amount&&this.commID==vo.commID&&this.commName.equals(vo.commName)&&this.createdDate.equals(vo.createdDate)&&this.id==vo.id&&this.status.equals(vo.status)&&this.type.equals(vo.type)&&this.sum==vo.sum;
		}
		return false;
	}
	@Override
	public int hashCode(){
		int result=17;
		result=31*result+id;
		result=31*result+amount;
		result=31*result+commID;
		result=31*result+sum;
		return result;
	}
}
