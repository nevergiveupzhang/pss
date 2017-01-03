package com.psssystem.client.controllerimpl.financecontrollerimpl;

import java.rmi.RemoteException;
import java.util.Set;

import com.psssystem.client.controller.financecontroller.IManageProcessController;
import com.psssystem.client.util.ConnectToServer;
import com.psssystem.connection.service.financeservice.IManageProcessService;
import com.psssystem.connection.vo.AlarmOrderVO;
import com.psssystem.connection.vo.GiftOrderVO;
import com.psssystem.connection.vo.LossOverflowOrderVO;
import com.psssystem.connection.vo.PaymentOrderVO;
import com.psssystem.connection.vo.PurchasingOrderVO;
import com.psssystem.connection.vo.ReceiptsOrderVO;
import com.psssystem.connection.vo.SalesOrderVO;

public class ManageProcessControllerImpl implements IManageProcessController {
	private IManageProcessService manageProcessService;
	public ManageProcessControllerImpl(){
		ConnectToServer.connect();
		this.manageProcessService=ConnectToServer.manageProcessService;
	}
	@Override
	public Set<SalesOrderVO> getSalesOrder(String[] info) {
		Set<SalesOrderVO> set=null;
		try {
			set=manageProcessService.getSalesOrder(info);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return set;
	}

	@Override
	public Set<SalesOrderVO> getSalesReturnOrder(String[] info) {
		Set<SalesOrderVO> set=null;
		try {
			set=manageProcessService.getSalesReturnOrder(info);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return set;
	}

	@Override
	public Set<PurchasingOrderVO> getPurchasingOrder(String[] info) {
		Set<PurchasingOrderVO> set=null;
		try {
			set=manageProcessService.getPurchasingOrder(info);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return set;
	}

	@Override
	public Set<PurchasingOrderVO> getPurchasingReturnOrder(String[] info) {
		Set<PurchasingOrderVO> set=null;
		try {
			set=manageProcessService.getPurchasingReturnOrder(info);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return set;
	}

	@Override
	public Set<PaymentOrderVO> getPaymentOrder(String[] info) {
		Set<PaymentOrderVO> set=null;
		try {
			set=manageProcessService.getPaymentOrder(info);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return set;
	}

	@Override
	public Set<ReceiptsOrderVO> getReceiptsOrder(String[] info) {
		Set<ReceiptsOrderVO> set=null;
		try {
			set=manageProcessService.getReceiptsOrder(info);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return set;
	}

	@Override
	public Set<LossOverflowOrderVO> getLossOverflowOrder(String type,String []info) {
		Set<LossOverflowOrderVO> set=null;
		try {
			set=manageProcessService.getLossOverflowOrder(type,info);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return set;
	}

	@Override
	public Set<AlarmOrderVO> getAlarmOrder(String[] info) {
		Set<AlarmOrderVO> set=null;
		try {
			set=manageProcessService.getAlarmOrder(info);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return set;
	}
	@Override
	public Set<GiftOrderVO> getGiftOrder(String[] info) {
		Set<GiftOrderVO> set=null;
		try {
			set=manageProcessService.getGiftOrder(info);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return set;
	}

}
