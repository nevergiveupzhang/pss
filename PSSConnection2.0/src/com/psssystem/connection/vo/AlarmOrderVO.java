package com.psssystem.connection.vo;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AlarmOrderVO implements Serializable{
	private int id;
	private int commID;
	private int amount;
	private int warningline;
	
	private String commName;
	private String status;
	private String createdDate;
	
	public AlarmOrderVO(int commID, String commName, int amount, int warningline) {
		this(0,commID,commName,amount,warningline,"草稿",new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
	}
	
	public AlarmOrderVO(int id, int commID, String commName, int amount,
			int warningline, String status, String createdDate) {
		super();
		this.id = id;
		this.commID = commID;
		this.commName = commName;
		this.amount = amount;
		this.warningline = warningline;
		this.status = status;
		this.createdDate = createdDate;
	}
	public int getId() {
		return id;
	}
	public int getCommID() {
		return commID;
	}
	public String getStatus() {
		return status;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setCommID(int commID) {
		this.commID = commID;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getCreatedDate() {
		return createdDate;
	}
	public String getCommName() {
		return commName;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	public void setCommName(String commName) {
		this.commName = commName;
	}
	public int getAmount() {
		return amount;
	}
	public int getWarningline() {
		return warningline;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public void setWarningline(int warningline) {
		this.warningline = warningline;
	}
	
	@Override
	public boolean equals(Object obj){
		if(obj==null) return false;
		if(obj instanceof AlarmOrderVO){
			AlarmOrderVO vo=(AlarmOrderVO) obj;
			return this.amount==vo.amount&&this.warningline==vo.warningline&&this.commID==vo.commID&&this.commName.equals(vo.commName)&&this.createdDate.equals(vo.createdDate)&&this.id==vo.id&&this.status.equals(vo.status);
		}
		return false;
	}
	@Override
	public int hashCode(){
		int result=17;
		result=31*result+id;
		result=31*result+amount;
		result=31*result+commID;
		result=31*result+warningline;
		return result;
	}
	@Override
	public String toString() {
		return "商品名称："+this.commName+",警戒线"+this.warningline+",数量："+this.amount+",状态："+this.status;
	}

}
