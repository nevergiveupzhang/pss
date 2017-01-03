package com.psssystem.client.controllerimpl.financecontrollerimpl;

import java.rmi.RemoteException;
import java.util.List;
import java.util.Set;

import com.psssystem.client.controller.financecontroller.IPaymentOrderController;
import com.psssystem.client.util.ConnectToServer;
import com.psssystem.connection.service.financeservice.IPaymentOrderService;
import com.psssystem.connection.vo.PaymentOrderVO;

public class PaymentOrderControllerImpl implements IPaymentOrderController {
	private IPaymentOrderService paymentOrderService;
	public PaymentOrderControllerImpl(){
		ConnectToServer.connect();
		this.paymentOrderService=ConnectToServer.paymentOrderService;
	}
	@Override
	public boolean createOrder(PaymentOrderVO order) {
		try {
			return paymentOrderService.createPaymentOrder(order);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return false;
	}
	@Override
	public Set<PaymentOrderVO> getAll() {
		Set<PaymentOrderVO> list=null;
		try {
			list=paymentOrderService.getAll();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return list;
	}
	@Override
	public boolean approve(Set<PaymentOrderVO> orders) {
		try {
			return paymentOrderService.approve(orders);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return false;
	}
	@Override
	public Set<PaymentOrderVO> getNotPassed() {
		Set<PaymentOrderVO> list=null;
		try {
			list=paymentOrderService.getNotPassed();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return list;
	}
	
}
