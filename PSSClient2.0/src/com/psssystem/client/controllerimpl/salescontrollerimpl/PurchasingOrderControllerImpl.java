package com.psssystem.client.controllerimpl.salescontrollerimpl;

import java.rmi.RemoteException;
import java.util.List;
import java.util.Set;

import com.psssystem.client.controller.salescontroller.IPurchasingOrderController;
import com.psssystem.client.util.ConnectToServer;
import com.psssystem.connection.service.salesservice.IPurchasingOrderService;
import com.psssystem.connection.vo.PurchasingOrderVO;

public class PurchasingOrderControllerImpl implements
		IPurchasingOrderController {
	private IPurchasingOrderService purchasingOrderService;
	public PurchasingOrderControllerImpl(){
		ConnectToServer.connect();
		this.purchasingOrderService=ConnectToServer.purchasingOrderService;
	}
	@Override
	public boolean addPurchasingOrder(PurchasingOrderVO order) {
		try {
			return purchasingOrderService.addPurchasingOrder(order);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return false;
	}
	@Override
	public boolean addPurchasingReturnOrder(PurchasingOrderVO order) {
		try {
			return purchasingOrderService.addPurchasingReturnOrder(order);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return false;
	}
	@Override
	public Set<PurchasingOrderVO> getNotPassedByType(String type) {
		Set<PurchasingOrderVO> list=null; 
		try {
			list=purchasingOrderService.getNotPassedByType(type);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	
	@Override
	public Set<PurchasingOrderVO> getAllByType(String type) {
		Set<PurchasingOrderVO> list=null; 
		try {
			list=purchasingOrderService.getAllByType(type);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return list;
	}
	@Override
	public boolean approveByType(Set<PurchasingOrderVO> orders,String type) {
		try {
			return purchasingOrderService.approveByType(orders, type);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return false;
	}

}
