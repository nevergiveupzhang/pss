package com.psssytem.server.dao;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.psssystem.connection.vo.AccountTransferVO;
import com.psssystem.server.dao.impl.order.salespay.item.AccountTransferDaoImpl;

public class AccountTransferDaoImplTest {
	private AccountTransferDaoImpl dao=(AccountTransferDaoImpl) AccountTransferDaoImpl.getInstance();
	@Test
	public void testAddItemAccountTransferVO() {
		assertEquals(false,dao.addItem(new AccountTransferVO("","",0,"")));
	}

	@Test
	public void testGetByOrderId() {
		assertEquals(0,dao.getByOrderId("").size());
		assertEquals(0,dao.getByOrderId("hahahaha").size());
	}

}
