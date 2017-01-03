package com.psssystem.server.dao.inf.order.general.salespay.item;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.psssystem.connection.vo.CommodityItemVO;
import com.psssystem.server.util.DBUtils;

public abstract class CommodityItemCreator<T> implements IItemCreator<T>{
	protected abstract String getSQL();
	public boolean addItem(CommodityItemVO item){
		String sql=getSQL();
		if(!updateDB(sql,item))return false;
		return true;
	}
	
	private boolean updateDB(String sql, CommodityItemVO item) {
		Connection conn=DBUtils.getConnection();
		PreparedStatement stat=null;
		try {
			stat=conn.prepareStatement(sql);
			stat.setString(1,item.getOperationID());
			stat.setInt(2, item.getCommID());
			stat.setInt(3,item.getAmount());
			stat.setInt(4, item.getPrice());
			stat.setInt(5, item.getSum());
			stat.setString(6, item.getRemarks());
			stat.setString(7, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
			stat.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
}
