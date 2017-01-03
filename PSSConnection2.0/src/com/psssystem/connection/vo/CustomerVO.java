package com.psssystem.connection.vo;

import java.io.Serializable;

public class CustomerVO implements Serializable{
	// 编号、分类（进货商、销售商）、级别（五级，一级普通用户，五级VIP客户）
	// 姓名、电话、地址、邮编、电子邮箱、应收额度、应收、应付、默认业务员
	private int id;
	private String type;
	private String defaultSalesman;
	private String name;
	private String phoneNumber;
	private int postcode;
	private String email;
	private String addr;

	private int level = 0;
	private int amuntReceivable = 0;
	private int receivable = 0;
	private int payable = 0;

	
	public static class Builder{
		private String name;
		private String type;
		
		private int id=0;
		private int postcode=000000;
		private int level = 1;
		private int amuntReceivable = 0;
		private int receivable = 0;
		private int payable = 0;
		private String phoneNumber="";
		private String email="";
		private String addr="";
		private String defaultSalesman="";
		

		public Builder(String name,String type){
			this.name=name;
			this.type=type;
		}
		public CustomerVO build(){
			return new CustomerVO(this);
		}
		public Builder id(int id){
			this.id=id;
			return this;
		}
		public Builder postcode(int postcode){
			this.postcode=postcode;
			return this;
		}
		public Builder level(int level){
			this.level=level;
			return this;
		}
		public Builder amuntReceivable(int amuntReceivable){
			this.amuntReceivable=amuntReceivable;
			return this;
		}
		public Builder receivable(int receivable){
			this.receivable=receivable;
			return this;
		}
		public Builder payable(int payable){
			this.payable=payable;
			return this;
		}
		
		public Builder phoneNumber(String phoneNumber){
			this.phoneNumber=phoneNumber;
			return this;
		}
		public Builder email(String email){
			this.email=email;
			return this;
		}
		public Builder addr(String addr){
			this.addr=addr;
			return this;
		}
		public Builder defaultSalesman(String defaultSalesman){
			this.defaultSalesman=defaultSalesman;
			return this;
		}
	}

	public CustomerVO(Builder builder) {
		this.id=builder.id;
		this.name=builder.name;
		this.type=builder.type;
		this.addr=builder.addr;
		this.amuntReceivable=builder.amuntReceivable;
		this.defaultSalesman=builder.defaultSalesman;
		this.email=builder.email;
		this.level=builder.level;
		this.payable=builder.payable;
		this.phoneNumber=builder.phoneNumber;
		this.receivable=builder.receivable;
		this.postcode=builder.postcode;
	}

	public int getId() {
		return id;
	}

	public String getType() {
		return type;
	}

	public String getDefaultSalesman() {
		return defaultSalesman;
	}

	public String getName() {
		return name;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public int getPostcode() {
		return postcode;
	}

	public String getEmail() {
		return email;
	}

	public String getAddr() {
		return addr;
	}

	public int getLevel() {
		return level;
	}

	public int getAmuntReceivable() {
		return amuntReceivable;
	}

	public int getReceivable() {
		return receivable;
	}

	public int getPayable() {
		return payable;
	}
	
	@Override
	public String toString() {
		return "客户编号："+this.id+",客户姓名："+this.name+"，客户地址："+this.addr;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj==null){
			return false;
		}
		if(!(obj instanceof CustomerVO)){
			return false;
		}
		
		CustomerVO vo=(CustomerVO) obj;
		return vo.name.equals(name)&&vo.type.equals(type);
	}
	
	@Override
	public int hashCode() {
		return name.hashCode()+type.hashCode();
	}
}
