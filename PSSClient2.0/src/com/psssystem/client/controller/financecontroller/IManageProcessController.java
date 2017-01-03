package com.psssystem.client.controller.financecontroller;

import java.rmi.RemoteException;
import java.util.Set;

import com.psssystem.connection.vo.AlarmOrderVO;
import com.psssystem.connection.vo.GiftOrderVO;
import com.psssystem.connection.vo.LossOverflowOrderVO;
import com.psssystem.connection.vo.PaymentOrderVO;
import com.psssystem.connection.vo.PurchasingOrderVO;
import com.psssystem.connection.vo.ReceiptsOrderVO;
import com.psssystem.connection.vo.SalesOrderVO;

public interface IManageProcessController {
	public Set<SalesOrderVO> getSalesOrder(String []info); 
	public Set<SalesOrderVO> getSalesReturnOrder(String []info); 
	public Set<PurchasingOrderVO> getPurchasingOrder(String []info); 
	public Set<PurchasingOrderVO> getPurchasingReturnOrder(String []info); 
	public Set<PaymentOrderVO> getPaymentOrder(String []info) ;
	public Set<ReceiptsOrderVO> getReceiptsOrder(String []info) ;
	public Set<LossOverflowOrderVO> getLossOverflowOrder(String type,String []info); 
	public Set<AlarmOrderVO> getAlarmOrder(String []info) ;
	public Set<GiftOrderVO> getGiftOrder(String []info) ;
}
