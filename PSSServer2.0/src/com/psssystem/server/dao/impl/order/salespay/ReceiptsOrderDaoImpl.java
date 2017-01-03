package com.psssystem.server.dao.impl.order.salespay;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.psssystem.connection.vo.AccountTransferVO;
import com.psssystem.connection.vo.PaymentOrderVO;
import com.psssystem.connection.vo.ReceiptsOrderVO;
import com.psssystem.server.dao.factory.impl.PaymentOrderDaoFactoryImpl;
import com.psssystem.server.dao.factory.impl.ReceiptsOrderDaoFactoryImpl;
import com.psssystem.server.dao.factory.inf.PaymentReceiptsDaoFactory;
import com.psssystem.server.dao.factory.inf.PurchasingSalesDaoFactory;
import com.psssystem.server.dao.impl.order.salespay.item.AccountTransferDaoImpl;
import com.psssystem.server.dao.inf.order.general.salespay.ISalesPayDao;
import com.psssystem.server.dao.inf.order.general.salespay.creator.SalesPayCreator;
import com.psssystem.server.dao.inf.order.general.salespay.passer.ISalesPayPasser;
import com.psssystem.server.dao.inf.order.general.salespay.querier.PaymentReceiptsQuerierTemplate;
import com.psssystem.server.data.OperationInfo;
import com.psssystem.server.data.StatusInfo;

public class ReceiptsOrderDaoImpl extends PaymentReceiptsQuerierTemplate<ReceiptsOrderVO> implements ISalesPayDao<ReceiptsOrderVO>{
	private static ReceiptsOrderDaoImpl INSTANCE=new ReceiptsOrderDaoImpl();
	private PaymentReceiptsDaoFactory factory=new ReceiptsOrderDaoFactoryImpl();
	private SalesPayCreator creator;
	private ISalesPayPasser passer;
	
	private String dateSQL = "select R.*,C.name,U.username from receiptsorder R join customer C on R.customer_id=C.id join user U on U.id=R.user_id where R.createddate between ? and ? order by R.id";
	private String customerNameSQL = "select R.*,C.name,U.username from receiptsorder R join customer C on R.customer_id=C.id join user U on U.id=R.user_id  where C.name=?  order by R.id";
	private String notPassedSQL = "select R.*,C.name,U.username from receiptsorder R join customer C on R.customer_id=C.id join user U on U.id=R.user_id where R.createddate like ? and status!=?  order by R.id";
	private String allSQL = "select R.*,C.name,U.username from receiptsorder R join customer C on R.customer_id=C.id join user U on U.id=R.user_id where R.createddate like ?  order by R.id";

	public ReceiptsOrderDaoImpl(){
		this.creator=factory.getCreator();
		this.passer=factory.getPasser();
	}
	
	protected Set<ReceiptsOrderVO> getResultSet(ResultSet rs) {
		Set<ReceiptsOrderVO> set=new HashSet<ReceiptsOrderVO>();
		try {
			while(rs.next()){
				ReceiptsOrderVO vo=new ReceiptsOrderVO.Builder().Id(rs.getString("id"))
						.CustomerID(rs.getInt("customer_id")).CustomerName(rs.getString("name"))
						.UserID(rs.getInt("user_id")).Username(rs.getString("username"))
						.CreatedDate(
								rs.getDate("createddate").toString() + " "
										+ rs.getTime("createddate").toString())
						.Sum(rs.getInt("sum")).Status(rs.getString("status")).build();
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
	public boolean createOrder(ReceiptsOrderVO order) {
		return creator.createOrder(order);
	}


	@Override
	public boolean passOrders(Set<ReceiptsOrderVO> orders) {
		return passer.passOrders(orders);
	}


	@Override
	protected String getDateSQL() {
		return dateSQL;
	}


	@Override
	protected String getCustomerSQL() {
		return customerNameSQL;
	}


	@Override
	protected String getNotPassSQL() {
		return this.notPassedSQL;
	}


	@Override
	protected String getAllSQL() {
		return this.allSQL;
	}

	
	
	public static void main(String []args){
		System.out.println(new ReceiptsOrderDaoImpl().getNotPassed().size());
	}
}
