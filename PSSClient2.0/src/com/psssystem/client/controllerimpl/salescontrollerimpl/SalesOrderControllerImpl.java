package com.psssystem.client.controllerimpl.salescontrollerimpl;

import java.rmi.RemoteException;
import java.util.List;
import java.util.Set;

import com.psssystem.client.controller.salescontroller.ISalesOrderController;
import com.psssystem.client.util.ConnectToServer;
import com.psssystem.connection.service.salesservice.ISalesOrderService;
import com.psssystem.connection.vo.PurchasingOrderVO;
import com.psssystem.connection.vo.SalesOrderVO;

public class SalesOrderControllerImpl implements ISalesOrderController {
	private ISalesOrderService salesOrderService;
	public SalesOrderControllerImpl(){
		ConnectToServer.connect();
		this.salesOrderService=ConnectToServer.salesOrderService;
	}
	@Override
	public boolean addSalesOrder(SalesOrderVO salesOrderVO) {
		try {
			return salesOrderService.addSalesOrder(salesOrderVO);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean addSalesReturnOrder(SalesOrderVO salesOrderVO) {
		try {
			return salesOrderService.addSalesReturnOrder(salesOrderVO);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return false;
	}
	@Override
	public Set<SalesOrderVO> getNotPassedByType(String type) {
		Set<SalesOrderVO> list=null; 
		try {
			list=salesOrderService.getNotPassedByType(type);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return list;
	}
	@Override
	public boolean approveByType(Set<SalesOrderVO> orders, String type) {
		try {
			return salesOrderService.approveByType(orders, type);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return false;
	}
	@Override
	public Set<SalesOrderVO> getAllByType(String type) {
		Set<SalesOrderVO> list=null; 
		try {
			list=salesOrderService.getAllByType(type);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return list;
	}

}
