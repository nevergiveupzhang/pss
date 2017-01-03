package com.psssystem.server.dao.impl.order.salespay.item.querier;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.psssystem.connection.vo.AccountTransferVO;
import com.psssystem.server.dao.inf.order.general.salespay.item.ItemQuerier;
import com.psssystem.server.util.DBUtils;

public class AccountTransferQuerierImpl extends ItemQuerier<AccountTransferVO> {
	private static AccountTransferQuerierImpl INSTANCE=new AccountTransferQuerierImpl();
	private final String querySQL="select *from accounttransferlist where receipts_id=?";
	public AccountTransferQuerierImpl(){}
	
	protected Set<AccountTransferVO> getResultSet(ResultSet rs) {
		Set<AccountTransferVO> set=new HashSet<AccountTransferVO>();
		try {
			while(rs.next()){
				set.add(new AccountTransferVO(rs.getString("receipts_id"),rs.getString("account_name"),rs.getInt("sum"),rs.getString("remarks")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return set;
	}
	
	@Override
	protected String getQuerySQL() {
		return querySQL;
	}

	public static ItemQuerier getInstance() {
		return INSTANCE;
	}

	@Override
	public Set<AccountTransferVO> getPassedByDate(String startDate,
			String endDate) {
		return null;
	}

	@Override
	protected String getPassedSQL() {
		return null;
	}

}
