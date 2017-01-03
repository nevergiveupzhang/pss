package com.psssystem.server.serviceimpl.storageserviceimpl;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;


import com.psssystem.connection.service.storageservice.IInventoryService;
import com.psssystem.connection.vo.InventoryVO;
import com.psssystem.server.dao.impl.others.InventoryDaoImpl;
import com.psssystem.server.dao.inf.others.IInventoryDao;

public class InventoryServiceImpl extends UnicastRemoteObject implements IInventoryService {
	private IInventoryDao inventoryDao;
	public InventoryServiceImpl() throws RemoteException{
		this.inventoryDao=new InventoryDaoImpl();
	}
	@Override
	public InventoryVO getInventroy() throws RemoteException {
		inventoryDao.createInventory();
		InventoryVO po=inventoryDao.getInventory();
		return po;
	}
}
