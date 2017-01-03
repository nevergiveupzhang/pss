package com.psssytem.server.dao;

import static org.junit.Assert.*;

import org.junit.Test;

import com.psssystem.connection.vo.AccountVO;
import com.psssystem.connection.vo.ResultMessage;
import com.psssystem.server.dao.impl.base.AccountDaoImpl;
import com.psssystem.server.dao.inf.base.IAccountDao;

public class AccountDaoImplTest {
	private AccountDaoImpl dao=new AccountDaoImpl();

	@Test
	public void testCreate() {
		assertEquals(ResultMessage.SUCCESS, dao.create(new AccountVO("zt",900)));
		assertEquals(ResultMessage.FAIL,dao.create(new AccountVO("")));
		assertEquals(ResultMessage.DUPLICATE,dao.create(new AccountVO("zt")));
	}

	@Test
	public void testDelete() {
		assertEquals(true,dao.delete(new AccountVO("zt")));
		assertEquals(false,dao.delete(new AccountVO("zt")));
		assertEquals(false,dao.delete(new AccountVO("")));
	}


	@Test
	public void testUpdateAccountSum() {
		assertEquals(false,dao.updateAccountSum(new AccountVO("bt",10000)));
		assertEquals(false,dao.updateAccountSum(new AccountVO("",10000)));
	}

	@Test
	public void testSearchAccount() {
		assertEquals(0,dao.searchAccount("").size());
	}

	@Test
	public void testGetAll() {
		dao.getAll();
	}

}
