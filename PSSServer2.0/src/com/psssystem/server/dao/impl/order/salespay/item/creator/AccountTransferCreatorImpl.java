package com.psssystem.server.dao.impl.order.salespay.item.creator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.psssystem.connection.vo.AccountTransferVO;
import com.psssystem.server.dao.inf.order.general.salespay.item.IItemCreator;
import com.psssystem.server.util.DBUtils;

public class AccountTransferCreatorImpl implements IItemCreator<AccountTransferVO> {
	private static AccountTransferCreatorImpl INSTANCE=new AccountTransferCreatorImpl();
	private final String insertSQL="insert into accounttransferlist(receipts_id,account_name,sum,remarks)values(?,?,?,?)";
	
	@Override
	public boolean addItem(AccountTransferVO item) {
		Connection conn=DBUtils.getConnection();
		PreparedStatement stat=null;
		if(item.getReceiptsID()==null||"".equals(item.getAccountName()))return false;
		try {
			stat=conn.prepareStatement(insertSQL);
			stat.setString(1, item.getReceiptsID());
			stat.setString(2, item.getAccountName());
			stat.setInt(3, item.getSum());
			stat.setString(4, item.getRemarks());
			if(stat.executeUpdate()!=1){
				DBUtils.closeConnection(conn);
				DBUtils.closeStateMent(stat);
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			DBUtils.closeConnection(conn);
			DBUtils.closeStateMent(stat);
			return false;
		}finally{
			DBUtils.closeConnection(conn);
			DBUtils.closeStateMent(stat);
		}
		return true;
	}

	public static IItemCreator getInstance() {
		return INSTANCE;
	}

}
