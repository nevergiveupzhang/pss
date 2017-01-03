package com.psssystem.connection.vo;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class SalesOrderVO implements Serializable {
	// 单据编号（XSD-yyyyMMdd-xxxxx），客户（仅显示销售商），
	// 业务员（和这个客户打交道的公司员工，可以设置一个客户的默认业务员），
	// 操作员（当前登录系统的用户），仓库，出货商品清单，
	// 折让前总额，折让，使用代金卷金额，折让后总额，备注。
	// 出货商品清单中要显示商品的编号，名称（从商品选择界面选择），型号，数量（手工输入），单价（默认为商品信息里的销售价，可修改），金额（自动生成），商品备注
	private int customerID;
	private int userID;
	private int sumBeforeDiscount = 0;
	private int discount = 0;
	private int voucher = 0;
	private int sumAfterDiscount = 0;
	private String id = "";
	private String customerName = "";
	private String username="";
	private String salesman = "";
	private List<CommodityItemVO> storageList = null;
	private String remarks = "";
	private String status = "草稿";
	private String createdDate=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

	public static class Builder{
		private int customerID;
		private int userID;
		
		private String id = "";
		private String customerName = "";
		private String username="";
		private String salesman = "";
		private List<CommodityItemVO> storageList = null;
		private int sumBeforeDiscount = 0;
		private int discount = 0;
		private int voucher = 0;
		private int sumAfterDiscount = 0;
		private String remarks = "";
		private String status = "草稿";
		private String createdDate=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		public Builder(int customerID,int userID){
			this.customerID=customerID;
			this.userID=userID;
		}
		
		public SalesOrderVO build(){
			return new SalesOrderVO(this);
		}
		public Builder id(String id){
			this.id=id;
			return this;
		}
		public Builder username(String username){
			this.username=username;
			return this;
		}
		public Builder customerName(String customerName) {
			this.customerName = customerName;
			return this;
		}

		public Builder salesman(String salesman) {
			this.salesman = salesman;
			return this;
		}

		public Builder storageList(List<CommodityItemVO> storageList) {
			this.storageList = storageList;
			return this;
		}

		public Builder sumBeforeDiscount(int sumBeforeDiscount) {
			this.sumBeforeDiscount = sumBeforeDiscount;
			return this;
		}

		public Builder discount(int discount) {
			this.discount = discount;
			return this;
		}

		public Builder voucher(int voucher) {
			this.voucher = voucher;
			return this;
		}

		public Builder sumAfterDiscount(int sumAfterDiscount) {
			this.sumAfterDiscount = sumAfterDiscount;
			return this;
		}

		public Builder remarks(String remarks) {
			this.remarks = remarks;
			return this;
		}

		public Builder status(String status) {
			this.status = status;
			return this;
		}

		public Builder createdDate(String createdDate) {
			this.createdDate = createdDate;
			return this;
		}
		
	}

	public SalesOrderVO(Builder builder) {
		this.createdDate=builder.createdDate;
		this.customerID=builder.customerID;
		this.customerName=builder.customerName;
		this.username=builder.username;
		this.discount=builder.discount;
		this.id=builder.id;
		this.remarks=builder.remarks;
		this.salesman=builder.salesman;
		this.status=builder.status;
		this.storageList=builder.storageList;
		this.sumAfterDiscount=builder.sumAfterDiscount;
		this.sumBeforeDiscount=builder.sumBeforeDiscount;
		this.userID=builder.userID;
		this.voucher=builder.voucher;
	}

	public String getId() {
		return id;
	}

	public int getCustomerID() {
		return customerID;
	}

	public String getUsername(){
		return username;
	}
	public String getCustomerName() {
		return customerName;
	}

	public String getSalesman() {
		return salesman;
	}

	public List<CommodityItemVO> getStorageList() {
		return storageList;
	}

	public int getSumBeforeDiscount() {
		return sumBeforeDiscount;
	}

	public int getDiscount() {
		return discount;
	}

	public int getVoucher() {
		return voucher;
	}

	public int getSumAfterDiscount() {
		return sumAfterDiscount;
	}

	public String getRemarks() {
		return remarks;
	}

	public String getStatus() {
		return status;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public int getUserID() {
		return userID;
	}
	
	@Override
	public boolean equals(Object obj){
		if(obj==null) return false;
		if(obj instanceof SalesOrderVO){
			SalesOrderVO vo=(SalesOrderVO) obj;
			return this.createdDate.equals(vo.createdDate)&&this.customerID==vo.customerID&&this.customerName.equals(vo.customerName)&&this.discount==vo.discount&&this.id.equals(vo.id)&&this.remarks.equals(vo.remarks)&&this.salesman.equals(vo.salesman)&&this.status.equals(vo.status)&&this.sumAfterDiscount==vo.sumAfterDiscount&&this.sumBeforeDiscount==vo.sumBeforeDiscount&&this.userID==vo.userID&&this.username.equals(vo.username)&&this.voucher==vo.voucher;
		}
		return false;
	}
	@Override
	public int hashCode(){
		int result=17;
		result=31*result+userID;
		result=31*result+customerID;
		result=31*result+sumBeforeDiscount;
		result=31*result+sumAfterDiscount;
		result=31*result+discount;
		result=31*result+voucher;
		return result;
	}
}
