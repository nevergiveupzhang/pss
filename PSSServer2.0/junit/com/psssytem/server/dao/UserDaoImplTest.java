package com.psssytem.server.dao;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Test;

import com.psssystem.connection.vo.ResultMessage;
import com.psssystem.connection.vo.UserVO;
import com.psssystem.server.dao.impl.base.UserDaoImpl;

public class UserDaoImplTest {
	private UserDaoImpl dao=new UserDaoImpl(); 
	@Test
	public void testCreate() {
		assertEquals(ResultMessage.SUCCESS,dao.create(new UserVO("zt","zt","库存管理人员")));
		assertEquals(ResultMessage.DUPLICATE,dao.create(new UserVO("zt","zt","库存管理人员")));
	}

	@Test
	public void testDelete() {
		assertEquals(true,dao.delete(new UserVO("zt","库存管理人员")));
		assertEquals(false,dao.delete(new UserVO("","库存管理人员")));
	}

	@Test
	public void testUpdateListOfUserVO() {
		dao.update(new ArrayList<UserVO>());
	}

	@Test
	public void testGetUsersByType() {
		assertEquals(1,dao.getUsersByType("库存管理人员").size());
		assertEquals(0,dao.getUsersByType(null).size());
	}

	@Test
	public void testLogin() {
		assertEquals(false,dao.login(new UserVO("","","")));
	}

	@Test
	public void testUpdateUserVO() {
	}

	@Test
	public void testGetAll() {
		dao.getAll();
	}

}
