package com.psssystem.connection.vo;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class PurchasingOrderVO implements Serializable {
	// 单据编号（格式为：JHD-yyyyMMdd-xxxxx），供应商，仓库，操作员，
	// 入库商品列表，备注，总额合计。
	// 其中入库商品列表包含的信息有：商品编号，名称（从商品选择界面进行选择），型号，数量（手动输入），单价（默认为商品信息中的进价），金额，备注（手动输入）
	private int customerID;
	private int userID;
	private int sum=0;
	
	private String id="";
	private String customerName="";
	private String username="";
	private String salesman="";
	private String createdDate=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
	private String remarks="";
	private String status="";
	private List<CommodityItemVO> storageList=null;
	
	public static class Builder{
		private int customerID;
		private int userID;
		private int sum=0;	
		
		private String id="";
		private String customerName="";
		private String username="";
		private String salesman="";
		private String remarks="";
		private String status="";
		private String createdDate=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		private List<CommodityItemVO> storageList=null;
			
	
		
		public Builder(int customerID,int userID){
			this.customerID=customerID;
			this.userID=userID;
		}
		
		public PurchasingOrderVO build(){
			return new PurchasingOrderVO(this);
		}
		public Builder id(String id){
			this.id=id;
			return this;
		}
		public Builder username(String username){
			this.username=username;
			return this;
		}
		public Builder customerName(String customerName){
			this.customerName=customerName;
			return this;
		}
		public Builder salesman(String salesman){
			this.salesman=salesman;
			return this;
		}
		public Builder remarks(String remarks){
			this.remarks=remarks;
			return this;
		}
		public Builder status(String status){
			this.status=status;
			return this;
		}
		public Builder createdDate(String createdDate){
			this.createdDate=createdDate;
			return this;
		}
		
		public Builder storageList(List<CommodityItemVO> storageList){
			this.storageList=storageList;
			return this;
		}
		
		public Builder sum(int sum){
			this.sum=sum;
			return this;
		}
	}
	
	public PurchasingOrderVO(Builder builder) {
		this.id=builder.id;
		this.customerID=builder.customerID;
		this.username=builder.username;
		this.customerName=builder.customerName;
		this.remarks=builder.remarks;
		this.salesman=builder.salesman;
		this.status=builder.status;
		this.storageList=builder.storageList;
		this.sum=builder.sum;
		this.createdDate=builder.createdDate;
		this.userID=builder.userID;
	}

	public String getId() {
		return id;
	}

	public int getCustomerID() {
		return customerID;
	}

	public String getUsername() {
		return username;
	}
	public String getCustomerName() {
		return customerName;
	}

	public int getUserID() {
		return userID;
	}

	public List<CommodityItemVO> getStorageList() {
		return storageList;
	}

	public String getRemarks() {
		return remarks;
	}

	public int getSum() {
		return sum;
	}

	public String getStatus() {
		return status;
	}

	public String getSalesman() {
		return salesman;
	}


	public String getCreatedDate() {
		return createdDate;
	}
	
	@Override
	public boolean equals(Object obj){
		if(obj==null) return false;
		if(obj instanceof PurchasingOrderVO){
			PurchasingOrderVO vo=(PurchasingOrderVO) obj;
			return this.username.equals(vo.username)&&this.customerName.equals(vo.customerName)&&this.createdDate.equals(vo.createdDate)&&this.customerID==vo.customerID&&this.customerName.equals(vo.customerName)&&this.id.equals(vo.id)&&this.remarks.equals(vo.remarks)&&this.salesman.equals(vo.salesman)&&this.status.equals(vo.status)&&this.sum==vo.sum&&this.userID==vo.userID;
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
