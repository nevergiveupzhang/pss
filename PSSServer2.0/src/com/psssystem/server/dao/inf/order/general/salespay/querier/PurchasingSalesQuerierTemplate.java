package com.psssystem.server.dao.inf.order.general.salespay.querier;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

import com.psssystem.server.dao.impl.order.salespay.PurchasingOrderDaoImpl;
import com.psssystem.server.dao.inf.order.general.IOrderQuerier;
import com.psssystem.server.data.OperationInfo;
import com.psssystem.server.data.StatusInfo;
import com.psssystem.server.util.DBUtils;

public abstract class PurchasingSalesQuerierTemplate<T> implements IOrderQuerier<T>{
	/*进货和销售为不同的实体，结果集对应的集合类型不一样*/
	protected abstract Set<T> getResultSet(ResultSet rs);
	
	protected abstract String getDateSQL();
	protected abstract String getCustomerSQL();
	protected abstract String getSalesmanSQL();
	protected abstract String getCommNameSQL();
	protected abstract String getNotPassedSQL();
	protected abstract String getAllSQL();
	
	@Override
	public Set<T> getByDate(String startDate, String endDate) {
		String sql=getDateSQL();
		Connection conn=DBUtils.getConnection();
		PreparedStatement stat=null;
		
		ResultSet rs=null;
		try {
			stat=conn.prepareStatement(sql);
			stat.setString(1, startDate);
			stat.setString(2, endDate);
			rs=stat.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(rs==null)return new HashSet<T>();
		Set<T> result=getResultSet(rs);
		DBUtils.closeConnection(conn);
		DBUtils.closeStateMent(stat);
		DBUtils.closeResultSet(rs);
		return result;
	}

	public Set<T> getByCustomerName(String customerName) {
		if("".equals(customerName)||customerName==null)return new HashSet<T>();
		String sql=getCommNameSQL();
		Connection conn=DBUtils.getConnection();
		PreparedStatement stat=null;
		
		ResultSet rs=null;
		try {
			stat=conn.prepareStatement(sql);
			stat.setString(1, customerName);
			rs=stat.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(rs==null)return new HashSet<T>();
		Set<T> result=getResultSet(rs);
		DBUtils.closeConnection(conn);
		DBUtils.closeStateMent(stat);
		DBUtils.closeResultSet(rs);
		return result;
	}
	
	public Set<T> getBySalesman(String salesman) {
		if("".equals(salesman)||salesman==null)return new HashSet<T>();
		String sql=getSalesmanSQL();
		Connection conn=DBUtils.getConnection();
		PreparedStatement stat=null;
		
		ResultSet rs=null;
		try {
			stat=conn.prepareStatement(sql);
			stat.setString(1, salesman);
			rs=stat.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(rs==null)return new HashSet<T>();
		Set<T> result=getResultSet(rs);
		DBUtils.closeConnection(conn);
		DBUtils.closeStateMent(stat);
		DBUtils.closeResultSet(rs);
		return result;
	}
	public Set<T> getByCommName(String commName) {
		if("".equals(commName)||commName==null)return new HashSet<T>();
		String sql=getCommNameSQL();
		Connection conn=DBUtils.getConnection();
		PreparedStatement stat=null;
		
		ResultSet rs=null;
		try {
			stat=conn.prepareStatement(sql);
			stat.setString(1, commName);
			rs=stat.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(rs==null)return new HashSet<T>();
		Set<T> result=getResultSet(rs);
		DBUtils.closeConnection(conn);
		DBUtils.closeStateMent(stat);
		DBUtils.closeResultSet(rs);
		return result;
	}
	
	@Override
	public Set<T> getNotPassed() {
		String sql=getNotPassedSQL();
		Connection conn=DBUtils.getConnection();
		PreparedStatement stat=null;
		
		ResultSet rs=null;
		try {
			stat=conn.prepareStatement(sql);
			stat.setString(1, Calendar.getInstance().get(Calendar.YEAR)+"%");
			stat.setString(2,StatusInfo.PASS);
			rs=stat.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(rs==null) return new HashSet<T>();
		Set<T> result=getResultSet(rs);
		DBUtils.closeConnection(conn);
		DBUtils.closeStateMent(stat);
		DBUtils.closeResultSet(rs);
		return result;
	}
	
	@Override
	public Set<T> getAll() {
		String sql=getAllSQL();
		Connection conn=DBUtils.getConnection();
		PreparedStatement stat=null;
		
		ResultSet rs=null;
		try {
			stat=conn.prepareStatement(sql);
			stat.setString(1, Calendar.getInstance().get(Calendar.YEAR)+"%");
			rs=stat.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(rs==null) return new HashSet<T>();
		Set<T> result=getResultSet(rs);
		DBUtils.closeConnection(conn);
		DBUtils.closeStateMent(stat);
		DBUtils.closeResultSet(rs);
		return result;
	}
	
}
