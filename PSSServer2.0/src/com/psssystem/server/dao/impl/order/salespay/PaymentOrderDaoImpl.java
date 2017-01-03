package com.psssystem.server.dao.impl.order.salespay;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.psssystem.connection.vo.PaymentItemVO;
import com.psssystem.connection.vo.PaymentOrderVO;
import com.psssystem.connection.vo.PurchasingOrderVO;
import com.psssystem.server.dao.factory.impl.PaymentOrderDaoFactoryImpl;
import com.psssystem.server.dao.factory.inf.PaymentReceiptsDaoFactory;
import com.psssystem.server.dao.factory.inf.PurchasingSalesDaoFactory;
import com.psssystem.server.dao.impl.order.salespay.item.PaymentItemDaoImpl;
import com.psssystem.server.dao.inf.order.general.salespay.ISalesPayDao;
import com.psssystem.server.dao.inf.order.general.salespay.creator.SalesPayCreator;
import com.psssystem.server.dao.inf.order.general.salespay.passer.ISalesPayPasser;
import com.psssystem.server.dao.inf.order.general.salespay.querier.PaymentReceiptsQuerierTemplate;
import com.psssystem.server.dao.inf.order.general.salespay.querier.PurchasingSalesQuerierTemplate;
import com.psssystem.server.data.OperationInfo;
import com.psssystem.server.data.StatusInfo;

public class PaymentOrderDaoImpl extends PaymentReceiptsQuerierTemplate<PaymentOrderVO>
		implements ISalesPayDao<PaymentOrderVO> {
	private static PaymentOrderDaoImpl INSTANCE = new PaymentOrderDaoImpl();
	private PaymentReceiptsDaoFactory factory = new PaymentOrderDaoFactoryImpl();

	private SalesPayCreator creator;
	private ISalesPayPasser passer;

	private String dateSQL = "select P.*,C.name,U.username from paymentorder P join customer C on p.customer_id=C.id join user U on U.id=P.user_id where unix_timestamp(P.createddate)>=unix_timestamp(?) and unix_timestamp(P.createddate)<=unix_timestamp(?) order by P.id";
	private String customerNameSQL = "select P.*,C.name,U.username from paymentorder P join customer C on P.customer_id=C.id join user U on U.id=P.user_id where C.name=?  order by P.id";
	private String notPassedSQL = "select P.*,C.name,U.username from paymentorder P join customer C on P.customer_id=C.id join user U on U.id=P.user_id where P.createddate like ? and status!=?  order by P.id";
	private String allSQL = "select P.*,C.name,U.username from paymentorder P join customer C on P.customer_id=C.id join user U on U.id=P.user_id where P.createddate like ?  order by P.id";

	public PaymentOrderDaoImpl() {
		this.creator = factory.getCreator();
		this.passer = factory.getPasser();
	}

	protected Set<PaymentOrderVO> getResultSet(ResultSet rs) {
		Set<PaymentOrderVO> set = new HashSet<PaymentOrderVO>();
		try {
			while (rs.next()) {
				PaymentOrderVO vo = new PaymentOrderVO.Builder()
						.Id(rs.getString("id"))
						.CustomerID(rs.getInt("customer_id")).CustomerName(rs.getString("name")).UserName(rs.getString("username"))
						.UserID(rs.getInt("user_id"))
						.AccountName(rs.getString("account_name"))
						.Sum(rs.getInt("sum"))
						.CreatedDate(
								rs.getDate("createddate").toString() + " "
										+ rs.getTime("createddate").toString())
						.Status(rs.getString("status")).build();
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

	public static PaymentReceiptsQuerierTemplate getQuerierInstance() {
		return INSTANCE;
	}

	@Override
	public boolean createOrder(PaymentOrderVO order) {
		return creator.createOrder(order);
	}

	@Override
	public boolean passOrders(Set<PaymentOrderVO> orders) {
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
}
