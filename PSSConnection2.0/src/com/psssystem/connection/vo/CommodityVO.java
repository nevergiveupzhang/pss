package com.psssystem.connection.vo;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CommodityVO implements Serializable {
	private int id;
	private int categoryID;
	private String name;
	private String type; 
	private int stockAmount;
	private int purchasingPrice;
	private int sellingPrice;
	private int recentPurchasingPrice;
	private int recentSellingPrice;
	private int warningLine;
	private String createdDate;
	
	
	public CommodityVO(int id, String name) {
		this(id, 0, name, null, 0, 0, 0, 0, 0, 0, new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()));
	}

	public CommodityVO(String name, String type, int stockAmount,
			int purchasingPrice, int sellingPrice, int recentPurchasingPrice,
			int recentSellingPrice) {
		this(0,0,name,type,stockAmount,purchasingPrice,sellingPrice,recentPurchasingPrice,recentSellingPrice,0,new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()));
	}
	
	public CommodityVO(int id, int categoryID, String name, String type,
			int stockAmount, int purchasingPrice, int sellingPrice,
			int recentPurchasingPrice, int recentSellingPrice, int warningLine,String createDate) {
		super();
		this.id = id;
		this.categoryID = categoryID;
		this.name = name;
		this.type = type;
		this.stockAmount = stockAmount;
		this.purchasingPrice = purchasingPrice;
		this.sellingPrice = sellingPrice;
		this.recentPurchasingPrice = recentPurchasingPrice;
		this.recentSellingPrice = recentSellingPrice;
		this.warningLine = warningLine;
		this.createdDate=createDate;
	}
	
	public int getCategoryID() {
		return categoryID;
	}
	public void setCategoryID(int categoryID) {
		this.categoryID = categoryID;
	}
	public int getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public String getType() {
		return type;
	}
	public int getStockAmount() {
		return stockAmount;
	}
	public int getPurchasingPrice() {
		return purchasingPrice;
	}
	public int getSellingPrice() {
		return sellingPrice;
	}
	public int getRecentPurchasingPrice() {
		return recentPurchasingPrice;
	}
	public int getRecentSellingPrice() {
		return recentSellingPrice;
	}
	public int getWarningLine() {
		return warningLine;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setType(String type) {
		this.type = type;
	}
	public void setStockAmount(int stockAmount) {
		this.stockAmount = stockAmount;
	}
	public void setPurchasingPrice(int purchasingPrice) {
		this.purchasingPrice = purchasingPrice;
	}
	public void setSellingPrice(int sellingPrice) {
		this.sellingPrice = sellingPrice;
	}
	public void setRecentPurchasingPrice(int recentPurchasingPrice) {
		this.recentPurchasingPrice = recentPurchasingPrice;
	}
	public void setRecentSellingPrice(int recentSellingPrice) {
		this.recentSellingPrice = recentSellingPrice;
	}
	public void setWarningLine(int warningLine) {
		this.warningLine = warningLine;
	}
	public String getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	
	@Override
	public String toString() {
		return "商品编号："+this.id+",商品名称："+this.name+",商品型号："+this.type+",分类编号："+this.categoryID;
	}
}
