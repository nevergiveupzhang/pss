package com.psssystem.connection.service.storageservice;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import com.psssystem.connection.service.base.IBaseService;
import com.psssystem.connection.vo.AlarmOrderVO;

public interface IAlarmOrderService  extends Remote,IBaseService<AlarmOrderVO>{
	public boolean addOrder(List<AlarmOrderVO> orderList) throws RemoteException;
}
