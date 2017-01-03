package com.psssystem.server.dao.impl.order.salespay.passer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

import com.psssystem.connection.vo.PaymentOrderVO;
import com.psssystem.connection.vo.ReceiptsOrderVO;
import com.psssystem.server.dao.factory.impl.PaymentItemDaoFactoryImpl;
import com.psssystem.server.dao.factory.inf.ItemFactory;
import com.psssystem.server.dao.inf.order.general.salespay.passer.ISalesPayPasser;
import com.psssystem.server.dao.inf.order.general.salespay.passer.PaymentReceiptsPasserTemplate;
import com.psssystem.server.data.StatusInfo;
import com.psssystem.server.util.DBUtils;

public class ReceiptsOrderPasserImpl extends PaymentReceiptsPasserTemplate<ReceiptsOrderVO> {
	private static ReceiptsOrderPasserImpl INSTANCE=new ReceiptsOrderPasserImpl();
	private String sql="update receiptsorder set status=?,passdate=? where id=?";
	
	public ReceiptsOrderPasserImpl(){
	}
	@Override
	protected boolean updateStatus(Set<ReceiptsOrderVO> orders) {
		String passDate=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		try {
			stat=conn.prepareStatement(sql);
			for(ReceiptsOrderVO vo:orders){
				stat.setString(1, StatusInfo.PASS);
				stat.setString(2, passDate);
				stat.setString(3, vo.getId());
				if(stat.executeUpdate()!=1){
					return false;
				}
			}
		} catch (SQLException e) {
				e.printStackTrace();
				return false;
			}
			return true;
		
	}
	@Override
	protected boolean updateCustomer(Set<ReceiptsOrderVO> orders) {
		for(ReceiptsOrderVO order:orders){
			if(!customerDao.reducePayableById(order.getCustomerID(),order.getSum()))return false;
		}
		return true;
	}
	public static ISalesPayPasser getInstance() {
		return INSTANCE;
	}

}
