package com.psssystem.server.dao.inf.others;

import java.sql.Connection;
import java.sql.PreparedStatement;

import com.psssystem.connection.vo.BalanceVO;
import com.psssystem.server.util.DBUtils;

public abstract class BalanceDaoTemplate {
	protected abstract int getResult(String sql,String startDate,String endDate);
	
	public int getSumOfPurchasing(String startDate,String endDate){
		if("".equals(startDate)||"".equals(endDate)||startDate==null||endDate==null)return 0;
		String sql="select sum(sum) from purchasingorder where unix_timestamp(createddate)>=unix_timestamp(?) and unix_timestamp(createddate)<=unix_timestamp(?)";
		return getResult(sql,startDate,endDate);
	}
	public int getSumOfPurchasingReturn(String startDate,String endDate){
		if("".equals(startDate)||"".equals(endDate)||startDate==null||endDate==null)return 0;
		String sql="select sum(sum) from purchasingreturnorder where unix_timestamp(createddate)>=unix_timestamp(?) and unix_timestamp(createddate)<=unix_timestamp(?)";
		return getResult(sql,startDate,endDate);
	}
	public int getSumOfSales(String startDate,String endDate){
		if("".equals(startDate)||"".equals(endDate)||startDate==null||endDate==null)return 0;
		String sql="select sum(sumafterdiscount),count(discount) from salesorder where unix_timestamp(createddate)>=unix_timestamp(?) and unix_timestamp(createddate)<=unix_timestamp(?)";
		return getResult(sql,startDate,endDate);
	}
	
	public int getSumOfSalesReturn(String startDate,String endDate){
		if("".equals(startDate)||"".equals(endDate)||startDate==null||endDate==null)return 0;
		String sql="select sum(sumafterdiscount),count(discount) from salesreturnorder where unix_timestamp(createddate)>=unix_timestamp(?) and unix_timestamp(createddate)<=unix_timestamp(?)";
		return getResult(sql,startDate,endDate);
	}
	public int getDiscountOfSales(String startDate,String endDate){
		if("".equals(startDate)||"".equals(endDate)||startDate==null||endDate==null)return 0;
		String sql="select sum(discount) from salesorder where unix_timestamp(createddate)>=unix_timestamp(?) and unix_timestamp(createddate)<=unix_timestamp(?)";
		return getResult(sql,startDate,endDate);
	}
	
	public int getDiscountOfSalesReturn(String startDate,String endDate){
		if("".equals(startDate)||"".equals(endDate)||startDate==null||endDate==null)return 0;
		String sql="select sum(discount) from salesreturnorder where unix_timestamp(createddate)>=unix_timestamp(?) and unix_timestamp(createddate)<=unix_timestamp(?)";
		return getResult(sql,startDate,endDate);
	}
	
}
