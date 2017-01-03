package com.psssystem.connection.service.storageservice;

import java.io.File;
import java.rmi.Remote;
import java.rmi.RemoteException;

import com.psssystem.connection.vo.InventoryVO;

public interface IInventoryService extends Remote{
	public InventoryVO getInventroy() throws RemoteException;
}
