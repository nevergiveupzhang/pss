package com.psssystem.server.dao.inf.order.general.salespay.item;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.psssystem.connection.vo.CommodityItemVO;
import com.psssystem.server.data.OperationInfo;
import com.psssystem.server.data.StatusInfo;
import com.psssystem.server.util.DBUtils;

public abstract class ItemQuerier<T> {
	protected abstract String getQuerySQL();
	protected abstract String getPassedSQL();
	protected abstract Set<T> getResultSet(ResultSet rs);
		
	public Set<T> getByOrderId(String id){
		if("".equals(id)||id==null)return new HashSet<T>();
		String sql=getQuerySQL();
		Connection conn=DBUtils.getConnection();
		PreparedStatement stat=null;
		ResultSet rs=null;
		try {
			stat=conn.prepareStatement(sql);
			stat.setString(1, id);
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
	
	public Set<T> getPassedByDate(String startDate,String endDate){
		String sql=getPassedSQL();
		Connection conn=DBUtils.getConnection();
		PreparedStatement stat=null;
		ResultSet rs=null;
		try {
			stat=conn.prepareStatement(sql);
			stat.setString(1, StatusInfo.PASS);
			stat.setString(2, startDate);
			stat.setString(3, endDate);
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
