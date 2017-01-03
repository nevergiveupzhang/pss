package com.psssystem.server.dao.impl.order.salespay.item.creator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.psssystem.connection.vo.AccountTransferVO;
import com.psssystem.connection.vo.PaymentItemVO;
import com.psssystem.server.dao.inf.order.general.salespay.item.IItemCreator;
import com.psssystem.server.util.DBUtils;

public class PaymentItemCreatorImpl implements IItemCreator<PaymentItemVO> {
	private static PaymentItemCreatorImpl INSTANCE=new PaymentItemCreatorImpl();
	private final String insertSQL="insert into paymentitem(pay_id,name,sum,remarks)values(?,?,?,?)";
	public PaymentItemCreatorImpl(){}
	@Override
	public boolean addItem(PaymentItemVO item) {
		Connection conn=DBUtils.getConnection();
		PreparedStatement stat=null;
		if(item.getPaymentOrderID()==null||"".equals(item.getName()))return false;
		try {
			stat=conn.prepareStatement(insertSQL);
			stat.setString(1, item.getPaymentOrderID());
			stat.setString(2, item.getName());
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
