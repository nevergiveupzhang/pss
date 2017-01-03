package com.psssystem.server.dao.inf.order.general.salespay.passer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Set;

import com.psssystem.server.dao.impl.base.CommodityDaoImpl;
import com.psssystem.server.dao.impl.base.CustomerDaoImpl;
import com.psssystem.server.dao.inf.base.ICommodityDao;
import com.psssystem.server.dao.inf.base.ICustomerDao;
import com.psssystem.server.dao.inf.order.general.salespay.item.ItemQuerier;
import com.psssystem.server.util.DBUtils;

public abstract class PurchasingSalesPasserTemplate<T> implements ISalesPayPasser<T>{
	protected Connection conn=DBUtils.getConnection();
	protected PreparedStatement stat=null;
	
	protected ItemQuerier itemQuerier=null;
	
	/*审批时需要更新库存商品信息和客户应收应付信息*/
	protected ICommodityDao commodityDao=new CommodityDaoImpl();
	protected ICustomerDao customerDao=new CustomerDaoImpl();
	
	/*审批时要执行的三个方法*/
	protected abstract boolean updateStatus(Set<T> orders);
	protected abstract boolean updateStorage(Set<T> orders);
	protected abstract boolean updateCustomer(Set<T> orders);
	
	/*根据父类传递的sql语句来执行审批单据时要执行的操作*/
	public boolean passOrders(Set<T> orders){
		try {
			conn.setAutoCommit(false);
			if(!updateStatus(orders)){
				conn.rollback();
				DBUtils.closeConnection(conn);
				DBUtils.closeStateMent(stat);
				return false;
			}
			if(!updateCustomer(orders)){
				conn.rollback();
				DBUtils.closeConnection(conn);
				DBUtils.closeStateMent(stat);
				return false;
			}
			if(!updateStorage(orders)){
				conn.rollback();
				DBUtils.closeConnection(conn);
				DBUtils.closeStateMent(stat);
				return false;
			}
			conn.commit();
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
	
}
