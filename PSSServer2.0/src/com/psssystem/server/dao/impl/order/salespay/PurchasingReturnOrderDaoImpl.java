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
import com.psssystem.connection.vo.PurchasingOrderVO;
import com.psssystem.server.dao.factory.impl.PurchasingOrderDaoFactoryImpl;
import com.psssystem.server.dao.factory.impl.PurchasingReturnOrderDaoFactoryImpl;
import com.psssystem.server.dao.factory.inf.PurchasingSalesDaoFactory;
import com.psssystem.server.dao.impl.order.salespay.item.StorageInReturnItemDaoImpl;
import com.psssystem.server.dao.inf.order.general.salespay.ISalesPayDao;
import com.psssystem.server.dao.inf.order.general.salespay.creator.SalesPayCreator;
import com.psssystem.server.dao.inf.order.general.salespay.passer.ISalesPayPasser;
import com.psssystem.server.dao.inf.order.general.salespay.querier.PurchasingSalesQuerierTemplate;
import com.psssystem.server.data.OperationInfo;
import com.psssystem.server.data.StatusInfo;

public class PurchasingReturnOrderDaoImpl extends PurchasingSalesQuerierTemplate<PurchasingOrderVO> implements ISalesPayDao<PurchasingOrderVO>{
	private static PurchasingReturnOrderDaoImpl INSTANCE=new PurchasingReturnOrderDaoImpl();
	private PurchasingSalesDaoFactory factory=new PurchasingReturnOrderDaoFactoryImpl();
	private SalesPayCreator creator;
	private ISalesPayPasser passer;
	
	private String dateSQL="select P.*,C.name,U.username from purchasingreturnorder P join customer C on P.customer_id=C.id join user U on P.user_id=U.id where unix_timestamp(P.createddate)>=unix_timestamp(?) and unix_timestamp(P.createddate)<=unix_timestamp(?)  order by P.id";
	private String customerNameSQL="select P.*,C.name,U.username from purchasingreturnorder P join customer C on P.customer_id=C.id join user U on P.user_id=U.id where C.name=?  order by P.id";
	private String notPassedSQL="select P.*,C.name,U.username from purchasingreturnorder P join customer C on P.customer_id=C.id join user U on P.user_id=U.id where P.createddate like ? and status!=?  order by P.id";
	private String allSQL="select P.*,C.name,U.username from purchasingreturnorder P join customer C on P.customer_id=C.id join user U on P.user_id=U.id where P.createddate like ?  order by P.id";
	private String salesmanSQL="select P.*,C.name,U.username from purchasingreturnorder P join customer C on P.customer_id=C.id join user U on P.user_id=U.id where P.salesman=?  order by P.id";
	private String commNameSQL="select P.*,C.name,U.username from purchasingreturnorder P join customer C on C.id=P.customer_id join user U on P.user_id=U.id join storageinreturnlist L on P.id=L.purreturn_id inner join commodity CM on L.comm_id=CM.id where CM.name=?  order by P.id";
	public PurchasingReturnOrderDaoImpl(){
		this.creator=factory.getCreator();
		this.passer=factory.getPasser();
	}
	@Override
	protected Set<PurchasingOrderVO> getResultSet(ResultSet rs) {
		Set<PurchasingOrderVO> set=new HashSet<PurchasingOrderVO>();
		try {
			while(rs.next()){
				PurchasingOrderVO vo=new PurchasingOrderVO.Builder(rs.getInt("customer_id"),rs.getInt("user_id")).id(rs.getString("id")).customerName(rs.getString("name")).username(rs.getString("username")).remarks(rs.getString("remarks")).salesman(rs.getString("salesman")).status(rs.getString("status")).sum(rs.getInt("sum")).createdDate(rs.getDate("createddate")+" "+rs.getTime("createddate")).build();
				set.add(vo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return set;
	}
	
	public static ISalesPayDao getInstance() {
		return INSTANCE;
	}
	
	@Override
	public boolean createOrder(PurchasingOrderVO order) {
		return creator.createOrder(order);
	}
	@Override
	public boolean passOrders(Set<PurchasingOrderVO> orders) {
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
	
	
	public static void main(String []args){
//		System.out.println(new PurchasingReturnOrderDaoImpl().getNotPassed().size());
		System.out.println(new PurchasingReturnOrderDaoImpl().getAll().size());
	}
}
