package com.psssystem.connection.vo;

import java.io.Serializable;

public class ResultMessage implements Serializable{
	/*结果违反唯一性约束*/
	public final static int DUPLICATE=1062;
	/*返回正确的结果*/
	public final static int SUCCESS=200;
	
	public final static int FAIL=404;
	
	public final static int NOT_INIT=0;
	private boolean status=true;
	private boolean failRows[];
	public ResultMessage(int size) {
		super();
		initRows(size);
	}
	private void initRows(int size) {
		failRows=new boolean[size];
		for(int i=0;i<size;i++){
			failRows[i]=false;
		}
	}
	public boolean getStatus() {
		return status;
	}
	public boolean[] getFailRows() {
		return failRows;
	}
	public ResultMessage setStatus(boolean status) {
		this.status = status;
		return this;
	}
	public ResultMessage setFailRows(int row) {
		this.failRows[row] =true;
		return this;
	}
	
	
}
