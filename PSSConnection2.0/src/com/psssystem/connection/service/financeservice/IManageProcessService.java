package com.psssystem.connection.service.financeservice;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Set;

import com.psssystem.connection.vo.AlarmOrderVO;
import com.psssystem.connection.vo.GiftOrderVO;
import com.psssystem.connection.vo.LossOverflowOrderVO;
import com.psssystem.connection.vo.PaymentOrderVO;
import com.psssystem.connection.vo.PurchasingOrderVO;
import com.psssystem.connection.vo.ReceiptsOrderVO;
import com.psssystem.connection.vo.SalesOrderVO;

public interface IManageProcessService extends Remote {
	public Set<SalesOrderVO> getSalesOrder(String []info) throws RemoteException;
	public Set<SalesOrderVO> getSalesReturnOrder(String []info) throws RemoteException;
	public Set<PurchasingOrderVO> getPurchasingOrder(String []info) throws RemoteException;
	public Set<PurchasingOrderVO> getPurchasingReturnOrder(String []info) throws RemoteException;
	public Set<PaymentOrderVO> getPaymentOrder(String []info) throws RemoteException;
	public Set<ReceiptsOrderVO> getReceiptsOrder(String []info) throws RemoteException;
	public Set<LossOverflowOrderVO> getLossOverflowOrder(String type,String []info) throws RemoteException;
	public Set<AlarmOrderVO> getAlarmOrder(String []info) throws RemoteException;
	public Set<GiftOrderVO> getGiftOrder(String []info) throws RemoteException;
}
