package com.psssytem.server.dao;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import com.psssystem.connection.vo.AlarmOrderVO;
import com.psssystem.server.dao.impl.order.storage.AlarmOrderDaoImpl;

public class AlarmOrderDaoImplTest {
	private AlarmOrderDaoImpl dao=new AlarmOrderDaoImpl();

	@Test
	public void testAddOrders() {
		assertEquals(false,dao.addOrders(null));
		assertEquals(false,dao.addOrders(new ArrayList<AlarmOrderVO>()));
	}

	@Test
	public void testGetByDate() {
		assertEquals(0,dao.getByDate("", "").size());
		assertEquals(0,dao.getByDate("1000-1-1 9:9:9", "2999-1-1 9:9:9").size());
	}

	@Test
	public void testGetNotPassed() {
		dao.getNotPassed();
	}

	@Test
	public void testPassOrders() {
		assertEquals(false,dao.passOrders(null));
		assertEquals(false,dao.passOrders(new HashSet<AlarmOrderVO>()));
	}

	@Test
	public void testGetAll() {
		dao.getAll();
	}

}
