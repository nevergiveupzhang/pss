package com.psssytem.server.dao;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.junit.Test;

import com.psssystem.connection.vo.GiftOrderVO;
import com.psssystem.server.dao.impl.order.storage.GiftOrderDaoImpl;

public class GiftOrderDaoImplTest {
	private GiftOrderDaoImpl dao=new GiftOrderDaoImpl();

	@Test
	public void testAddGiftOrder() {
		List<GiftOrderVO> list =new ArrayList<GiftOrderVO>();
		list.add(new GiftOrderVO(4,100));
		list.add(new GiftOrderVO(11111111,100));
		assertEquals(false,dao.addGiftOrder(list));
	}

	@Test
	public void testGetByDate() {
		assertEquals(0,dao.getByDate("", "").size());
	}

	@Test
	public void testGetNotPassed() {
		dao.getNotPassed();
	}

	@Test
	public void testPassOrders() {
		dao.passOrders(null);
	}

	@Test
	public void testGetAll() {
		dao.getAll();
	}

}
