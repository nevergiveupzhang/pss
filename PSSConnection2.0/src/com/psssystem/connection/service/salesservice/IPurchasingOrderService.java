package com.psssystem.connection.service.salesservice;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Set;

import com.psssystem.connection.service.base.ITypeService;
import com.psssystem.connection.vo.PurchasingOrderVO;

public interface IPurchasingOrderService extends Remote,ITypeService<PurchasingOrderVO>{
	public boolean addPurchasingOrder(PurchasingOrderVO order)throws RemoteException;
	public boolean addPurchasingReturnOrder(PurchasingOrderVO order)throws RemoteException;
}
