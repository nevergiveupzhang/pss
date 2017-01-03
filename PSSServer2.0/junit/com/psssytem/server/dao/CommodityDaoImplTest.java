package com.psssytem.server.dao;

import static org.junit.Assert.*;

import org.junit.Test;

import com.psssystem.server.dao.impl.base.CommodityDaoImpl;

public class CommodityDaoImplTest {
	private CommodityDaoImpl dao=new CommodityDaoImpl();

	@Test
	public void testGetAll() {
		dao.getAll();
	}

	@Test
	public void testSetWarningLines() {
		assertEquals(false,dao.setWarningLines(null));
	}

	@Test
	public void testCreateInventoryFileFromCommodity() {
		assert (dao.createInventoryFileFromCommodity()) != null;
	}

	@Test
	public void testAddCommodites() {
		dao.addCommodites(null);
	}

	@Test
	public void testCreate() {
		dao.create(null);
	}

	@Test
	public void testDeleteCommodites() {
		dao.deleteCommodites(null);
	}

	@Test
	public void testDelete() {
	}

	@Test
	public void testUpdateCommodites() {
	}

	@Test
	public void testUpdate() {
	}

	@Test
	public void testSearchCommodity() {
	}


	@Test
	public void testReduceAmountByID() {
	}

	@Test
	public void testIncreaseAmountById() {
	}

	@Test
	public void testGetWarninglineById() {
	}

	@Test
	public void testGetAmountById() {
	}

	@Test
	public void testUpdateRecentPurchasingPriceById() {
	}

	@Test
	public void testReduceAmountById() {
	}

	@Test
	public void testUpdateRecentSellingPriceById() {
	}
	@Test
	public void testIsNamedAndIDExists(){
		assertEquals(true,dao.isNamedAndIDExists(4, "n2"));
	}

}
