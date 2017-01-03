package com.psssystem.connection.service.financeservice;

import java.rmi.Remote;
import java.rmi.RemoteException;

import com.psssystem.connection.service.base.IBaseService;
import com.psssystem.connection.vo.ReceiptsOrderVO;


public interface IReceiptsOrderService extends Remote ,IBaseService<ReceiptsOrderVO>{
	public boolean createReceipts(ReceiptsOrderVO order) throws RemoteException;
}
