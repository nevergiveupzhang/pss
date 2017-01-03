package com.psssystem.server.dao.impl.order.salespay.creator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.psssystem.connection.vo.AccountTransferVO;
import com.psssystem.connection.vo.ReceiptsOrderVO;
import com.psssystem.server.dao.factory.impl.AccountTransferDaoFactoryImpl;
import com.psssystem.server.dao.factory.inf.ItemFactory;
import com.psssystem.server.dao.inf.order.general.salespay.ISalesPayDao;
import com.psssystem.server.dao.inf.order.general.salespay.creator.SalesPayCreator;
import com.psssystem.server.util.DBUtils;

public class ReceiptsOrderCreatorImpl extends SalesPayCreator<ReceiptsOrderVO> {
	private static SalesPayCreator INSTANCE=new ReceiptsOrderCreatorImpl();

	private ItemFactory factory=new AccountTransferDaoFactoryImpl();
	public ReceiptsOrderCreatorImpl(){
		this.itemDao=factory.getInstance();
	}
	@Override
	protected String[] getOrderInfo() {
		return new String[]{"receiptsorder","SKD"};
	}
	@Override
	protected boolean updateDB(String id, ReceiptsOrderVO order) {
		Connection conn=DBUtils.getConnection();
		PreparedStatement stat=null;
		String sql="insert into receiptsorder(id,customer_id,user_id,sum,createddate)values(?,?,?,?,?)";
		try {
			stat=conn.prepareStatement(sql);
			stat.setString(1, id);
			stat.setInt(2, order.getCustomerID());
			stat.setInt(3, order.getUserID());
			stat.setInt(4,order.getSum());
			stat.setString(5, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
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
		
		for(AccountTransferVO item:order.getTransfers()){
			item.setReceiptsID(id);
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
		String sql="delete from receiptsorder where id=?";
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
