package com.psssytem.server.dao;

import static org.junit.Assert.*;

import org.junit.Test;

import com.psssystem.server.dao.factory.impl.PaymentOrderDaoFactoryImpl;
import com.psssystem.server.dao.factory.inf.PaymentReceiptsDaoFactory;
import com.psssystem.server.dao.impl.order.salespay.PaymentOrderDaoImpl;
import com.psssystem.server.dao.inf.order.general.salespay.ISalesPayDao;
import com.psssystem.server.dao.inf.order.general.salespay.querier.PaymentReceiptsQuerierTemplate;

public class PaymentOrderDaoImplTest {
	private PaymentReceiptsDaoFactory facotry=new PaymentOrderDaoFactoryImpl();
	private ISalesPayDao dao=facotry.getInstance();
	private PaymentReceiptsQuerierTemplate querier=facotry.getQuerier();



	@Test
	public void testPass() {
		dao.passOrders(null);
	}

	@Test
	public void testGetByDate() {
		querier.getNotPassed();
	}

	@Test
	public void testGetByCustomerName() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetNotPassed() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetAll() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetInstanceString() {
		fail("Not yet implemented");
	}

}
