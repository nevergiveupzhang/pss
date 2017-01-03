package com.psssystem.server.dao.impl.order.salespay.creator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.psssystem.connection.vo.PaymentItemVO;
import com.psssystem.connection.vo.PaymentOrderVO;
import com.psssystem.server.dao.factory.impl.PaymentItemDaoFactoryImpl;
import com.psssystem.server.dao.factory.inf.ItemFactory;
import com.psssystem.server.dao.inf.order.general.salespay.creator.SalesPayCreator;
import com.psssystem.server.util.DBUtils;

public class PaymentOrderCreatorImpl extends SalesPayCreator<PaymentOrderVO> {
	private static PaymentOrderCreatorImpl INSTANCE=new PaymentOrderCreatorImpl();
	private ItemFactory itemFactory=new PaymentItemDaoFactoryImpl();
	public PaymentOrderCreatorImpl(){
		this.itemDao=itemFactory.getInstance();
	}
	@Override
	protected String[] getOrderInfo() {
		return new String[]{"paymentorder","XJFYD"};
	}
	@Override
	protected boolean updateDB(String id, PaymentOrderVO order) {
		Connection conn=DBUtils.getConnection();
		PreparedStatement stat=null;
		String sql="insert into paymentorder(id,customer_id,user_id,account_name,sum,createddate)values(?,?,?,?,?,?)";
		try {
			stat=conn.prepareStatement(sql);
			stat.setString(1, id);
			stat.setInt(2, order.getCustomerID());
			stat.setInt(3, order.getUserID());
			stat.setString(4, order.getAccountName());
			stat.setInt(5, order.getSum());
			stat.setString(6, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
			stat.execute();
		} catch (SQLException e) {
			e.printStackTrace();
			DBUtils.closeConnection(conn);
			DBUtils.closeStateMent(stat);
			return false;
		}finally{
			DBUtils.closeConnection(conn);
			DBUtils.closeStateMent(stat);
		}
		
		for(PaymentItemVO item:order.getItems()){
			item.setPaymentOrderID(id);
			if(!itemDao.addItem(item)){
				deleteLastAdded(id);
				return false;
			};
		}		
		return true;
	}
	private void deleteLastAdded(String id) {
		Connection conn=DBUtils.getConnection();
		PreparedStatement stat=null;
		String sql="delete from paymentorder where id=?";
		try {
			stat=conn.prepareStatement(sql);
			stat.setString(1, id);
			stat.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DBUtils.closeConnection(conn);
			DBUtils.closeStateMent(stat);
		}
		
	}
	public static SalesPayCreator getInstance() {
		return INSTANCE;
	}
}
