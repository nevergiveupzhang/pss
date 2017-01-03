package com.psssytem.server.dao;

import static org.junit.Assert.*;

import org.junit.Test;

import com.psssystem.server.dao.impl.others.BalanceDaoImpl;

public class BalanceDaoImplTest {
	private BalanceDaoImpl dao=new BalanceDaoImpl();

	@Test
	public void testGetSumOfPurchasing() {
		assertEquals(0,dao.getSumOfPurchasing("", ""));
		assertEquals(0,dao.getSumOfPurchasing(null, null));
		assertEquals(0,dao.getSumOfPurchasing("", "2019-10-10"));
		assertEquals(0,dao.getSumOfPurchasing("2019-10-10", ""));
		assertEquals(0,dao.getSumOfPurchasing("2019", ""));
		assertEquals(0,dao.getSumOfPurchasing("1000-1-1", "1112-2-2"));
	}

	@Test
	public void testGetSumOfPurchasingReturn() {
		assertEquals(0,dao.getSumOfPurchasingReturn("", ""));
		assertEquals(0,dao.getSumOfPurchasingReturn(null, null));
		assertEquals(0,dao.getSumOfPurchasingReturn("", "2019-10-10"));
		assertEquals(0,dao.getSumOfPurchasingReturn("2019-10-10", ""));
		assertEquals(0,dao.getSumOfPurchasingReturn("2019", ""));
		assertEquals(0,dao.getSumOfPurchasingReturn("1000-1-1", "1112-2-2"));
	}

	@Test
	public void testGetSumOfSales() {
		assertEquals(0,dao.getSumOfSales("", ""));
		assertEquals(0,dao.getSumOfSales("", "2019-10-10"));
		assertEquals(0,dao.getSumOfSales("2019-10-10", ""));
		assertEquals(0,dao.getSumOfSales("2019", ""));
		assertEquals(0,dao.getSumOfSales("1000-1-1", "1112-2-2"));
	}

	@Test
	public void testGetSumOfSalesReturn() {
		assertEquals(0,dao.getSumOfSalesReturn("", ""));
		assertEquals(0,dao.getSumOfSalesReturn("", "2019-10-10"));
		assertEquals(0,dao.getSumOfSalesReturn("2019-10-10", ""));
		assertEquals(0,dao.getSumOfSalesReturn("2019", ""));
		assertEquals(0,dao.getSumOfSalesReturn("1000-1-1", "1112-2-2"));
	}

	@Test
	public void testGetDiscountOfSales() {
		assertEquals(0,dao.getDiscountOfSales("", ""));
		assertEquals(0,dao.getDiscountOfSales("", "2019-10-10"));
		assertEquals(0,dao.getDiscountOfSales("2019-10-10", ""));
		assertEquals(0,dao.getDiscountOfSales("2019", ""));
		assertEquals(0,dao.getDiscountOfSales("1000-1-1", "1112-2-2"));
	}

	@Test
	public void testGetDiscountOfSalesReturn() {
		assertEquals(0,dao.getDiscountOfSalesReturn("", ""));
		assertEquals(0,dao.getDiscountOfSalesReturn("", "2019-10-10"));
		assertEquals(0,dao.getDiscountOfSalesReturn("2019-10-10", ""));
		assertEquals(0,dao.getDiscountOfSalesReturn("2019", ""));
		assertEquals(0,dao.getDiscountOfSalesReturn("1000-1-1", "1112-2-2"));
	}

}
