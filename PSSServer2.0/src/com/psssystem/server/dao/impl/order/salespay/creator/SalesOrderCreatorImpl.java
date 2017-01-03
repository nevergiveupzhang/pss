package com.psssystem.server.dao.impl.order.salespay.creator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.psssystem.connection.vo.CommodityItemVO;
import com.psssystem.connection.vo.SalesOrderVO;
import com.psssystem.server.dao.factory.impl.SalesOrderDaoFactoryImpl;
import com.psssystem.server.dao.factory.impl.StorageInReturnItemDaoFactoryImpl;
import com.psssystem.server.dao.factory.impl.StorageOutItemDaoFactoryImpl;
import com.psssystem.server.dao.factory.impl.StorageOutReturnItemDaoFactoryImpl;
import com.psssystem.server.dao.factory.inf.ItemFactory;
import com.psssystem.server.dao.inf.order.general.salespay.creator.SalesPayCreator;
import com.psssystem.server.util.DBUtils;

public class SalesOrderCreatorImpl extends SalesPayCreator<SalesOrderVO> {
	private static SalesOrderCreatorImpl INSTANCE=new SalesOrderCreatorImpl();;
	private ItemFactory factory=new StorageOutItemDaoFactoryImpl();
	public SalesOrderCreatorImpl(){
		this.itemDao=factory.getInstance();
	}
	@Override
	protected String[] getOrderInfo() {
		return new String[]{"salesorder","XSD"};
	}
	@Override
	protected boolean updateDB(String id,SalesOrderVO order) {
		Connection conn=DBUtils.getConnection();
		PreparedStatement stat=null;
		/*生成新的销售单编号*/
		String sql="insert into salesorder(id,customer_id,salesman,user_id,sumbeforediscount,sumafterdiscount,discount,voucher,remarks,createddate)values(?,?,?,?,?,?,?,?,?,?)";
		try {
			stat=conn.prepareStatement(sql);
			stat.setString(1, id);
			stat.setInt(2, order.getCustomerID());
			stat.setString(3, order.getSalesman());
			stat.setInt(4, order.getUserID());
			stat.setInt(5, order.getSumBeforeDiscount());
			stat.setInt(6, order.getSumAfterDiscount());
			stat.setInt(7, order.getDiscount());
			stat.setInt(8, order.getVoucher());
			stat.setString(9, order.getRemarks());
			stat.setString(10, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
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
		for(CommodityItemVO vo:order.getStorageList()){
			vo=new CommodityItemVO.Builder().copyProperties(vo).operationID(id).build();
			if(!itemDao.addItem(vo)){
				deleteLastAdded(id);
				return false;
			};
		}			
		return true;
	}
	private void deleteLastAdded(String id) {
		Connection conn=DBUtils.getConnection();
		PreparedStatement stat=null;
		String sql="delete from salesorder where id=?";
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
	
	
	public static void main(String []args){
//		SalesOrderVO vo=new	SalesOrderVO();
//		vo.setCustomerID(7);
//		vo.setSalesman("毛泽东");
//		vo.setUserID(67);
//		vo.setStorageList(null);
//		new SalesOrderDaoFactoryImpl().getCreator().createOrder(vo);
	}
}
