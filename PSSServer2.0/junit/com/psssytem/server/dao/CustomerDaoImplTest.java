package com.psssytem.server.dao;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import com.psssystem.connection.vo.CustomerVO;
import com.psssystem.server.dao.impl.base.CustomerDaoImpl;

public class CustomerDaoImplTest {
	private CustomerDaoImpl dao=new CustomerDaoImpl();
	@Test
	public void testConstruct(){
		dao=new CustomerDaoImpl();
	}
	@Test
	public void testAddCustomers() {
		Set<CustomerVO> list=new HashSet<CustomerVO>();
		list.add(new CustomerVO.Builder("", "进货商").build());
		assertEquals(false,dao.addCustomers(list));
	}

	@Test
	public void testDeleteCustomerByID() {
		assertEquals(false,dao.deleteCustomerByID(-1));
	}

	@Test
	public void testUpdate() {
		assertEquals(false,dao.update(new CustomerVO.Builder("毛泽东","dd").id(3).build()));
	}

	@Test
	public void testSearchCustomer() {
		assertEquals(2,dao.searchCustomer("").size());
	}

	@Test
	public void testGetAll() {
		dao.getAll();
	}

	@Test
	public void testDeleteCustomers() {
		assertEquals(false,dao.deleteCustomers(new ArrayList<CustomerVO>()));
	}

	@Test
	public void testUpdateCustomers() {
		assertEquals(false,dao.updateCustomers(new ArrayList<CustomerVO>()));
	}


	@Test
	public void testCreate() {
//		assertEquals(false,dao.create(null));
	}

	@Test
	public void testDelete() {
//		dao.delete(null);
	}

	@Test
	public void testIncreasePayableById() {
		assertEquals(false,dao.increasePayableById(0, 0));
	}

	@Test
	public void testReducePayableById() {
		assertEquals(false,dao.reducePayableById(0, 0));
	}

	@Test
	public void testIncreaseReceivableById() {
		assertEquals(false,dao.increaseReceivableById(0, 0));
	}

	@Test
	public void testReduceReceivableById() {
		assertEquals(false,dao.reduceReceivableById(0, 0));
	}

}
