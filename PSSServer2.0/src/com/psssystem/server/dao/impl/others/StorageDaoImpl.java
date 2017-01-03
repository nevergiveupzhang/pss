package com.psssystem.server.dao.impl.others;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.psssystem.connection.vo.StorageVO;
import com.psssystem.server.dao.inf.others.IStorageDao;
import com.psssystem.server.util.DBUtils;

public class StorageDaoImpl implements IStorageDao {
	public StorageDaoImpl() {
	}

	@Override
	public List<StorageVO> getStorageInfo(String startDate, String endDate,
			String type) {
		/* jdbc查询mysql中的日期字段时，where字句中的格式应该为String类型而不是Date类型 */
		String sql = "select *from storage where type='" + type
				+ "' and date between '" + startDate + "' and '" + endDate
				+ "'";
		Connection conn = DBUtils.getConnection();
		PreparedStatement stat=null;
		List<StorageVO> poList = new ArrayList<StorageVO>();
		ResultSet rs = null;

		try {
			stat = conn.prepareStatement(sql);
			rs = stat.executeQuery();
			while (rs.next()) {
				int id = rs.getInt(1);
				int commID = rs.getInt(2);
				String commName = rs.getString(3);
				int amount = rs.getInt(4);
				int sum = rs.getInt(5);
				String date = rs.getDate(6)+" "+rs.getTime(6);
				StorageVO po = new StorageVO(id, commID, commName, amount, sum,
						date, type);
				poList.add(po);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DBUtils.closeConnection(conn);
			DBUtils.closeStateMent(stat);
			DBUtils.closeResultSet(rs);
		}
		return poList;
	}

	public static void main(String[] args) {
		new StorageDaoImpl().getStorageInfo("", "", "");
	}

}
