package com.psssystem.connection.vo;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class GiftOrderVO implements Serializable{
	private int id;
	private int commID;
	private int amount;//商品的库存数量stockAmount=stockAmount-amount
	private int sum=0;
	private String commName;
	private String createdDate;
	private String status;
	
	public GiftOrderVO(int commID, int amount) {
		this(commID,null,amount);
	}
	public GiftOrderVO(int commID, String commName, int amount) {
		this(0,commID,commName,amount,new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()),"草稿");
	}
	
	public GiftOrderVO(int commID, String createdDate) {
		this(0,commID,null,0,createdDate,"草稿");
	}
	public GiftOrderVO(int id,int commID, String commName, int amount,
			String createdDate, String status) {
		this(id,commID,commName,amount,createdDate,status,0);
	}
	


	public GiftOrderVO(int id, int commID, String commName, int amount,
			String createdDate, String status, int sum) {
			super();
			this.id=id;
			this.commID = commID;
			this.commName = commName;
			this.amount = amount;
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
	public String getCreatedDate() {
		return createdDate;
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
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	public void setStatus(String status) {
		this.status = status;
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
			GiftOrderVO vo=(GiftOrderVO) obj;
			return this.amount==vo.amount&&this.commID==vo.commID&&this.commName.equals(vo.commName)&&this.createdDate.equals(vo.createdDate)&&this.id==vo.id&&this.status.equals(vo.status)&&this.sum==vo.sum;
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
