package com.psssystem.connection.service.storageservice;

import java.rmi.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

import com.psssystem.connection.vo.StorageVO;

public interface IStorageService extends Remote{
	public Set<StorageVO> getStorageInfo(String startDate,String endDate,String type) throws RemoteException;
}
