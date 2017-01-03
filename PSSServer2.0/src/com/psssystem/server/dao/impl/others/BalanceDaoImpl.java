package com.psssystem.server.dao.impl.others;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.psssystem.server.dao.inf.others.BalanceDaoTemplate;
import com.psssystem.server.util.DBUtils;

public class BalanceDaoImpl extends BalanceDaoTemplate{
	@Override
	protected int getResult(String sql, String startDate,
			String endDate) {
		Connection conn = DBUtils.getConnection();
		PreparedStatement stat=null;
		ResultSet rs=null;
		try {
			stat=conn.prepareStatement(sql);
			stat.setString(1, startDate);
			stat.setString(2, endDate);
			rs=stat.executeQuery();
			if(rs.next()){
				int result=rs.getInt(1);
				DBUtils.closeConnection(conn);
				DBUtils.closeStateMent(stat);
				DBUtils.closeResultSet(rs);
				return result;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DBUtils.closeConnection(conn);
			DBUtils.closeStateMent(stat);
			DBUtils.closeResultSet(rs);
		}
		return 0;
	}

	
}
