package com.psssystem.connection.vo;

import java.io.*;
import java.util.*;

public class InventoryVO implements Serializable{
	//商品的名称，型号，库存数量，库存均价，批次，批号，出厂日期，并且显示行号
	private int id;
	private Date batch;
	private byte[] commInfo;
	
	public InventoryVO() {
		this(0,new Date(),null);
	}


	public InventoryVO(int id, Date batch, byte[] commInfo) {
		super();
		this.id = id;
		this.batch = batch;
		this.commInfo = commInfo;
	}
	
	
	public int getId() {
		return id;
	}
	public Date getBatch() {
		return batch;
	}
	public byte[] getCommInfo() {
		return commInfo;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setBatch(Date batch) {
		this.batch = batch;
	}
	public void setCommInfo(byte[] commInfo) {
		this.commInfo = commInfo;
	}
}
