package com.psssytem.server.dao;

import static org.junit.Assert.*;

import org.junit.Test;

import com.psssystem.connection.vo.CategoryVO;
import com.psssystem.server.dao.impl.base.CategoryDaoImpl;
import com.psssystem.server.util.DBUtils;

public class CategoryDaoImplTest {
	private CategoryDaoImpl dao=new CategoryDaoImpl();

	@Test
	public void testAddCategoryToTreeNode() {
		dao.addCategoryToTreeNode(null, 0);
	}

	@Test
	public void testGetAll() {
		dao.getAll();
	}

	@Test
	public void testCreate() {
//		dao.create(new CategoryVO("分类 3",0));
		dao.create(new CategoryVO("分类 4",79));
	}


	@Test
	public void testDeleteCategoryByID() {
		assertTrue(dao.deleteCategoryByID(79));
	}

	@Test
	public void testUpdate() {
		assertEquals(false,dao.update(null));
		assertEquals(false,dao.update(new CategoryVO("",0)));
		assertEquals(true,dao.update(new CategoryVO(56,"日光灯9")));
	}

	@Test
	public void testReduceHasCommodity() {
		dao.reduceHasCommodity(10);
	}

	@Test
	public void testIncreaseHasCommodity() {
		dao.increaseHasCommodity(10);
	}

	@Test
	public void testDelete() {
		assertEquals(false,dao.delete(new CategoryVO("",0)));
	}

}
