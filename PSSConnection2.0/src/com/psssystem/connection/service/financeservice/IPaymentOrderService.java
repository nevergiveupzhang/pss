package com.psssystem.connection.service.financeservice;

import java.rmi.Remote;
import java.rmi.RemoteException;

import com.psssystem.connection.service.base.IBaseService;
import com.psssystem.connection.vo.PaymentOrderVO;

public interface IPaymentOrderService extends Remote ,IBaseService<PaymentOrderVO>{
	public boolean createPaymentOrder(PaymentOrderVO order)throws RemoteException;
}
