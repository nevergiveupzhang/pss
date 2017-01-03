package com.psssystem.server.dao.impl.order.salespay.passer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

import com.psssystem.connection.vo.PaymentOrderVO;
import com.psssystem.server.dao.inf.order.general.salespay.passer.ISalesPayPasser;
import com.psssystem.server.dao.inf.order.general.salespay.passer.PaymentReceiptsPasserTemplate;
import com.psssystem.server.data.StatusInfo;
import com.psssystem.server.util.DBUtils;

public class PaymentOrderPasserImpl extends PaymentReceiptsPasserTemplate<PaymentOrderVO> {
	private static PaymentOrderPasserImpl INSTANCE=new PaymentOrderPasserImpl();
	private String sql="update paymentorder set status=?,passdate=? where id=?";
	
	public PaymentOrderPasserImpl(){}
	
	@Override
	protected boolean updateStatus(Set<PaymentOrderVO> orders) {
		String passDate=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		try {
			stat=conn.prepareStatement(sql);
			for(PaymentOrderVO vo:orders){
				stat.setString(1, StatusInfo.PASS);
				stat.setString(2, passDate);
				stat.setString(3, vo.getId());
				if(stat.executeUpdate()!=1){
					System.out.println("update status false");
					return false;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("update status exception");
			return false;
		}
		return true;
	}

	@Override
	protected boolean updateCustomer(Set<PaymentOrderVO> orders) {
		for(PaymentOrderVO order:orders){
			if(!customerDao.reduceReceivableById(order.getCustomerID(),order.getSum())){
				System.out.println("update customer false");
				return false;
			}
		}
		return true;
	}
	public static ISalesPayPasser getInstance() {
		return INSTANCE;
	}
	
	
	public static void main(String []args){
		
//		new PaymentOrderPasserImpl().passOrders(orders);
		
	}

}
