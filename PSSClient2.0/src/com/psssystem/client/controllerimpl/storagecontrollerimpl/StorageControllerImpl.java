package com.psssystem.client.controllerimpl.storagecontrollerimpl;

import java.rmi.RemoteException;
import java.util.Date;
import java.util.List;
import java.util.Set;

import com.psssystem.client.controller.storagecontroller.IStorageController;
import com.psssystem.client.util.ConnectToServer;
import com.psssystem.connection.service.storageservice.IStorageService;
import com.psssystem.connection.vo.StorageVO;

public class StorageControllerImpl implements IStorageController {
	private IStorageService storageService;
	public StorageControllerImpl(){
		ConnectToServer.connect();
		this.storageService=ConnectToServer.storageService;
		
	}
	@Override
	public Set<StorageVO> getStorageInfo(String startDate, String endDate, String type) {
		Set<StorageVO> storageList=null;
		try {
			storageList=storageService.getStorageInfo(startDate,endDate,type);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return storageList;
	}
	
}
