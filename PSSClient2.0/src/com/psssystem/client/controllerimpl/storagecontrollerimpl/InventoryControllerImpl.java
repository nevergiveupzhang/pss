package com.psssystem.client.controllerimpl.storagecontrollerimpl;

import java.io.File;
import java.rmi.RemoteException;

import com.psssystem.client.controller.storagecontroller.IInventoryController;
import com.psssystem.client.util.ConnectToServer;
import com.psssystem.connection.service.storageservice.IInventoryService;
import com.psssystem.connection.vo.InventoryVO;

public class InventoryControllerImpl implements IInventoryController {
	private IInventoryService inventoryService;
	public InventoryControllerImpl(){
		ConnectToServer.connect();
		this.inventoryService=ConnectToServer.inventoryService;
	}
	@Override
	public InventoryVO getInventory() {
		InventoryVO inventoryFile=null;
		try {
			inventoryFile=inventoryService.getInventroy();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return inventoryFile;
	}

}
