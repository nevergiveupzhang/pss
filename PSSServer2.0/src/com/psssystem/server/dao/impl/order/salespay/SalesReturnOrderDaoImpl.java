package com.psssystem.server.dao.impl.order.salespay;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.psssystem.connection.vo.CommodityItemVO;
import com.psssystem.connection.vo.SalesOrderVO;
import com.psssystem.server.dao.factory.impl.SalesOrderDaoFactoryImpl;
import com.psssystem.server.dao.factory.impl.SalesReturnOrderDaoFactoryImpl;
import com.psssystem.server.dao.factory.inf.PurchasingSalesDaoFactory;
import com.psssystem.server.dao.impl.order.salespay.item.StorageOutReturnItemDaoImpl;
import com.psssystem.server.dao.inf.order.general.salespay.ISalesPayDao;
import com.psssystem.server.dao.inf.order.general.salespay.creator.SalesPayCreator;
import com.psssystem.server.dao.inf.order.general.salespay.passer.ISalesPayPasser;
import com.psssystem.server.dao.inf.order.general.salespay.querier.PurchasingSalesQuerierTemplate;
import com.psssystem.server.data.OperationInfo;
import com.psssystem.server.data.StatusInfo;


public class SalesReturnOrderDaoImpl extends  PurchasingSalesQuerierTemplate<SalesOrderVO> implements ISalesPayDao<SalesOrderVO>{
	private static SalesReturnOrderDaoImpl INSTANCE=new SalesReturnOrderDaoImpl();
	private PurchasingSalesDaoFactory factory=new SalesReturnOrderDaoFactoryImpl();
	
	private String dateSQL = "select S.*,C.name,U.username from salesreturnorder S join customer C on C.id=S.customer_id join user U on S.user_id=U.id where unix_timestamp(S.createddate)>=unix_timestamp(?) and unix_timestamp(S.createddate)<=unix_timestamp(?)  order by S.id";
	private String customerNameSQL = "select S.*,C.name,U.username from salesreturnorder S join customer C on C.id=S.customer_id join user U on S.user_id=U.id where C.name=?  order by S.id";
	private String notPassedSQL = "select S.*,C.name,U.username from salesreturnorder S join customer C on C.id=S.customer_id join user U on S.user_id=U.id where S.createddate like ? and status!=?  order by S.id";
	private String allSQL = "select S.*,C.name,U.username from salesreturnorder S join customer C on C.id=S.customer_id join user U on S.user_id=U.id where S.createddate like ?  order by S.id";
	private String salesmanSQL = "select S.*,C.name,U.username from salesreturnorder S join customer C on C.id=S.customer_id join user U on S.user_id=U.id where S.salesman=?  order by S.id";
	private String commNameSQL = "select S.*,C.name,U.username from salesreturnorder S join customer C on C.id=S.customer_id join user U on S.user_id=U.id join storageoutlist L on S.id=L.sales_id inner join commodity CM on L.comm_id=CM.id where CM.name=?  order by S.id";

	private SalesPayCreator creator;
	private ISalesPayPasser passer;
	public SalesReturnOrderDaoImpl(){
		this.creator=factory.getCreator();
		this.passer=factory.getPasser();
	}
	
	@Override
	protected Set<SalesOrderVO> getResultSet(ResultSet rs) {
		Set<SalesOrderVO> set=new HashSet<SalesOrderVO>();
		try {
			while(rs.next()){
				SalesOrderVO vo = new SalesOrderVO.Builder(
						rs.getInt("customer_id"), rs.getInt("user_id")).customerName(rs.getString("name")).username(rs.getString("username"))
						.id(rs.getString("id"))
						.remarks(rs.getString("remarks"))
						.salesman(rs.getString("salesman"))
						.status(rs.getString("status"))
						.createdDate(
								rs.getDate("createddate")
												.toString()
												+ " "
												+ rs.getTime("createddate")
														.toString())
						.sumBeforeDiscount(rs.getInt("sumbeforediscount"))
						.sumAfterDiscount(rs.getInt("sumafterdiscount"))
						.discount(rs.getInt("discount"))
						.voucher(rs.getInt("voucher")).build();
				set.add(vo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return set;
	}
	
	@Override
	public boolean createOrder(SalesOrderVO order) {
		return creator.createOrder(order);
	}
	@Override
	public boolean passOrders(Set<SalesOrderVO> orders) {
		return passer.passOrders(orders);
	}
	@Override
	protected String getDateSQL() {
		return this.dateSQL;
	}
	@Override
	protected String getCustomerSQL() {
		return this.customerNameSQL;
	}
	@Override
	protected String getSalesmanSQL() {
		return this.salesmanSQL;
	}
	@Override
	protected String getCommNameSQL() {
		return this.salesmanSQL;
	}
	@Override
	protected String getNotPassedSQL() {
		return this.notPassedSQL;
	}
	@Override
	protected String getAllSQL() {
		return this.allSQL;
	}

	
	public static ISalesPayDao getInstance() {
		return INSTANCE;
	}

}
