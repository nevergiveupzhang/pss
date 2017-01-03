package com.psssystem.server.dao.inf.others;

import java.io.File;

import com.psssystem.connection.vo.InventoryVO;

public interface IInventoryDao {
	public void createInventory();
	public InventoryVO getInventory();
}
