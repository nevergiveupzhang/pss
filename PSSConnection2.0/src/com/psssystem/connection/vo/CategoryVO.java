package com.psssystem.connection.vo;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CategoryVO implements Serializable{
	private int id;
	private String name;
	private int parentID;
	private int hasCommodityAmount;
	private int hasCategoryAmount;
	private String createdDate;
	
	
	public CategoryVO(int id, String name) {
		this(id,name,0);
	}

	public CategoryVO(String name, int parentID) {
		this(0,name,parentID);
	}

	public CategoryVO(int id, String name, int parentID) {
		this(id,name,parentID,0,0,new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()));
	}
	
	public CategoryVO(int id, String name, int parentID,
			int hasCommodityAmount, int hasCategoryAmount, String createdDate) {
		super();
		this.id = id;
		this.name = name;
		this.parentID = parentID;
		this.hasCommodityAmount = hasCommodityAmount;
		this.hasCategoryAmount = hasCategoryAmount;
		this.createdDate = createdDate;
	}

	public int getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public int getParentID() {
		return parentID;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setParentID(int parentID) {
		this.parentID = parentID;
	}
	public int getHasCommodityAmount() {
		return hasCommodityAmount;
	}
	public int getHasCategoryAmount() {
		return hasCategoryAmount;
	}
	public void setHasCommodityAmount(int hasCommodityAmount) {
		this.hasCommodityAmount = hasCommodityAmount;
	}
	public void setHasCategoryAmount(int hasCategoryAmount) {
		this.hasCategoryAmount = hasCategoryAmount;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	
	@Override
	public String toString() {
		return "分类编号："+this.id+",分类名称："+this.name+",父节点："+this.parentID;
	}
}
