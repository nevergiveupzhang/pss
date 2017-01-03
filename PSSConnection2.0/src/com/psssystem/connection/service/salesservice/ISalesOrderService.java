package com.psssystem.connection.service.salesservice;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Set;

import com.psssystem.connection.service.base.ITypeService;
import com.psssystem.connection.vo.PurchasingOrderVO;
import com.psssystem.connection.vo.SalesOrderVO;

public interface ISalesOrderService extends Remote,ITypeService<SalesOrderVO>{
	public boolean addSalesOrder(SalesOrderVO order)throws RemoteException;
	public boolean addSalesReturnOrder(SalesOrderVO order)throws RemoteException;
}
