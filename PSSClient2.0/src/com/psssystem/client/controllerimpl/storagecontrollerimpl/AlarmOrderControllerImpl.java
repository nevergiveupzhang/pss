package com.psssystem.client.controllerimpl.storagecontrollerimpl;

import java.rmi.RemoteException;
import java.util.List;
import java.util.Set;

import com.psssystem.client.controller.storagecontroller.IAlarmOrderController;
import com.psssystem.client.util.ConnectToServer;
import com.psssystem.connection.service.storageservice.IAlarmOrderService;
import com.psssystem.connection.vo.AlarmOrderVO;

public class AlarmOrderControllerImpl implements IAlarmOrderController {
	private IAlarmOrderService alarmOrderService;
	public AlarmOrderControllerImpl(){
		ConnectToServer.connect();
		this.alarmOrderService=ConnectToServer.alarmOrderService;
	}

	@Override
	public boolean addOrder(List<AlarmOrderVO> orderList) {
		boolean status=false;
		try {
			status=alarmOrderService.addOrder(orderList);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return status;
	}

	@Override
	public Set<AlarmOrderVO> getAll() {
		 Set<AlarmOrderVO> list=null;
		try {
			list=alarmOrderService.getAll();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public boolean approve(Set<AlarmOrderVO> orders) {
		try {
			return alarmOrderService.approve(orders);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public Set<AlarmOrderVO> getNotPassed() {
		 Set<AlarmOrderVO> list=null;
			try {
				list=alarmOrderService.getNotPassed();
			} catch (RemoteException e) {
				e.printStackTrace();
			}
			return list;
	}

}
