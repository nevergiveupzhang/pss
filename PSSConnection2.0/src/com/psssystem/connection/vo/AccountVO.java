package com.psssystem.connection.vo;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AccountVO implements Serializable{
	private String name;
	
	private int sum;
	
	public AccountVO(String name) {
		this(name,0);
	}
	
	public AccountVO(String name, int sum) {
		super();
		this.name = name;
		this.sum = sum;
	}
	public String getName() {
		return name;
	}
	public int getSum() {
		return sum;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setSum(int sum) {
		this.sum = sum;
	}
	@Override
	public String toString() {
		return "账户名："+this.name+"，总额："+this.sum;
	}
}
