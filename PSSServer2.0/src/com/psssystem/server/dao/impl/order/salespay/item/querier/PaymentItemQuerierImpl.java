package com.psssystem.server.dao.impl.order.salespay.item.querier;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.psssystem.connection.vo.AccountTransferVO;
import com.psssystem.connection.vo.PaymentItemVO;
import com.psssystem.server.dao.inf.order.general.salespay.item.ItemQuerier;
import com.psssystem.server.util.DBUtils;

public class PaymentItemQuerierImpl extends ItemQuerier<PaymentItemVO> {
	private static PaymentItemQuerierImpl INSTANCE=new PaymentItemQuerierImpl();
	private final String querySQL="select *from paymentorder where pay_id=?";
	public PaymentItemQuerierImpl(){}
	@Override
	protected Set<PaymentItemVO> getResultSet(ResultSet rs) {
		Set<PaymentItemVO> set=new HashSet<PaymentItemVO>();
		try {
			while(rs.next()){
				set.add(new PaymentItemVO(rs.getString("pay_id"),rs.getString("name"),rs.getInt("sum"),rs.getString("remarks")));
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
	public Set<PaymentItemVO> getPassedByDate(String startDate,
			String endDate) {
		return null;
	}
	@Override
	protected String getPassedSQL() {
		// TODO Auto-generated method stub
		return null;
	}

}
