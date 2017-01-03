package com.psssystem.client.controllerimpl.financecontrollerimpl;

import java.rmi.RemoteException;
import java.util.List;
import java.util.Set;

import com.psssystem.client.controller.financecontroller.IReceiptsOrderController;
import com.psssystem.client.util.ConnectToServer;
import com.psssystem.connection.service.financeservice.IReceiptsOrderService;
import com.psssystem.connection.vo.PaymentOrderVO;
import com.psssystem.connection.vo.ReceiptsOrderVO;

public class ReceiptsOrderControllerImpl implements IReceiptsOrderController {
	private IReceiptsOrderService receiptsOrderService;
	public ReceiptsOrderControllerImpl(){
		receiptsOrderService=ConnectToServer.receiptsOrderService;
	}
	@Override
	public boolean createReceipts(ReceiptsOrderVO order) {
		try {
			return receiptsOrderService.createReceipts(order);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return false;
	}
	@Override
	public Set<ReceiptsOrderVO> getAll() {
		Set<ReceiptsOrderVO> list=null;
		try {
			list=receiptsOrderService.getAll();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return list;
	}
	@Override
	public boolean approve(Set<ReceiptsOrderVO> orders) {
		try {
			return receiptsOrderService.approve(orders);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return false;
	}
	@Override
	public Set<ReceiptsOrderVO> getNotPassed() {
		Set<ReceiptsOrderVO> list=null;
		try {
			list=receiptsOrderService.getNotPassed();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return list;
	}

}
