package com.psssystem.client.controllerimpl.storagecontrollerimpl;

import java.rmi.RemoteException;
import java.util.List;
import java.util.Set;

import com.psssystem.client.controller.storagecontroller.ILossOverflowController;
import com.psssystem.client.util.ConnectToServer;
import com.psssystem.connection.service.storageservice.ILossOverflowService;
import com.psssystem.connection.vo.GiftOrderVO;
import com.psssystem.connection.vo.LossOverflowOrderVO;

public class LossOverflowControllerImpl implements ILossOverflowController {
	private ILossOverflowService lossOverflowService;
	public LossOverflowControllerImpl(){
		ConnectToServer.connect();
		this.lossOverflowService=ConnectToServer.lossOverflowService;
	}


	@Override
	public boolean addOrder(List<LossOverflowOrderVO> orderList) {
		boolean status=false;
		try {
			status=lossOverflowService.addLossOverflowOrder(orderList);
		} catch (RemoteException e) {
			e.printStackTrace();
		}			
		return status;
	}


	@Override
	public Set<LossOverflowOrderVO> getNotPassedByType(String type) {
		Set<LossOverflowOrderVO> list=null;
		try {
			list=lossOverflowService.getNotPassedByType(type);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return list;
	}


	@Override
	public boolean approveByType(Set<LossOverflowOrderVO> orders, String type) {
		try {
			return lossOverflowService.approveByType(orders, type);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return false;
	}


	@Override
	public Set<LossOverflowOrderVO> getAllByType(String type) {
		Set<LossOverflowOrderVO> list=null;
		try {
			list=lossOverflowService.getAllByType(type);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return list;
	}

}
