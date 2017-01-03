package com.psssystem.server.dao.impl.order.salespay;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.psssystem.connection.vo.CommodityItemVO;
import com.psssystem.connection.vo.CommodityVO;
import com.psssystem.connection.vo.PurchasingOrderVO;
import com.psssystem.server.dao.factory.impl.PurchasingOrderDaoFactoryImpl;
import com.psssystem.server.dao.factory.inf.PurchasingSalesDaoFactory;
import com.psssystem.server.dao.impl.base.CommodityDaoImpl;
import com.psssystem.server.dao.impl.base.CustomerDaoImpl;
import com.psssystem.server.dao.impl.order.salespay.item.StorageInItemDaoImpl;
import com.psssystem.server.dao.inf.base.ICommodityDao;
import com.psssystem.server.dao.inf.base.ICustomerDao;
import com.psssystem.server.dao.inf.order.general.salespay.ISalesPayDao;
import com.psssystem.server.dao.inf.order.general.salespay.creator.SalesPayCreator;
import com.psssystem.server.dao.inf.order.general.salespay.passer.ISalesPayPasser;
import com.psssystem.server.dao.inf.order.general.salespay.querier.PurchasingSalesQuerierTemplate;
import com.psssystem.server.data.OperationInfo;
import com.psssystem.server.data.StatusInfo;

public class PurchasingOrderDaoImpl extends PurchasingSalesQuerierTemplate<PurchasingOrderVO> implements ISalesPayDao<PurchasingOrderVO> {
	private static PurchasingOrderDaoImpl INSTANCE=new PurchasingOrderDaoImpl();
	private PurchasingSalesDaoFactory factory=new PurchasingOrderDaoFactoryImpl();
	private SalesPayCreator creator;
	private ISalesPayPasser passer;
	
	private String dateSQL="select P.*,C.name,U.username from purchasingorder P join customer C on P.customer_id=C.id join user U on P.user_id=U.id where unix_timestamp(P.createddate)>=unix_timestamp(?) and unix_timestamp(P.createddate)<=unix_timestamp(?)  order by P.id";
	private String customerNameSQL="select P.*,C.name,U.username from purchasingorder P join customer C on P.customer_id=C.id join user U on P.user_id=U.id where C.name=?  order by P.id";
	private String notPassedSQL="select P.*,C.name,U.username from purchasingorder P join customer C on P.customer_id=C.id join user U on P.user_id=U.id where P.createddate like ? and status!=?  order by P.id";
	private String allSQL="select P.*,C.name,U.username from purchasingorder P join customer C on P.customer_id=C.id join user U on P.user_id=U.id where P.createddate like ?  order by P.id";
	private String salesmanSQL="select P.*,C.name,U.username from purchasingorder P join customer C on P.customer_id=C.id join user U on P.user_id=U.id where P.salesman=?  order by P.id";
	private String commNameSQL="select P.*,C.name,U.username from purchasingorder P join customer C on C.id=P.customer_id join user U on P.user_id=U.id join storageoutlist L on P.id=L.pur_id inner join commodity CM on L.comm_id=CM.id where CM.name=?  order by P.id";
	public PurchasingOrderDaoImpl(){
		this.creator=factory.getCreator();
		this.passer=factory.getPasser();
	}
	@Override
	protected Set<PurchasingOrderVO> getResultSet(ResultSet rs) {
		Set<PurchasingOrderVO> set=new HashSet<PurchasingOrderVO>();
		try {
			while(rs.next()){
				PurchasingOrderVO vo=new PurchasingOrderVO.Builder(rs.getInt("customer_id"),rs.getInt("user_id")).id(rs.getString("id")).username(rs.getString("username")).customerName(rs.getString("name")).remarks(rs.getString("remarks")).salesman(rs.getString("salesman")).status(rs.getString("status")).sum(rs.getInt("sum")).createdDate(rs.getDate("createddate")+" "+rs.getTime("createddate")).build();
				System.out.println(vo.getCreatedDate());
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
	public static PurchasingSalesQuerierTemplate getQuerierInstance(){
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
//		System.out.println(new PurchasingOrderDaoImpl().getNotPassed().size());
//		System.out.println(new PurchasingOrderDaoImpl().getAll().size());
		
		Iterator<PurchasingOrderVO>itr =new PurchasingOrderDaoImpl().getAll().iterator();
		while(itr.hasNext()){
//			System.out.println(itr.next().getCreatedDate());
		}
	}
}
