package com.psssystem.client.controllerimpl.financecontrollerimpl;

import java.rmi.RemoteException;

import com.psssystem.client.controller.financecontroller.IManageSituationController;
import com.psssystem.client.util.ConnectToServer;
import com.psssystem.connection.service.financeservice.IManageSituationService;
import com.psssystem.connection.vo.BalanceVO;

public class ManageSituationControllerImpl implements IManageSituationController {
	private IManageSituationService manageSituationService;
	
	public ManageSituationControllerImpl(){
		ConnectToServer.connect();
		manageSituationService=ConnectToServer.manageSituationService;
	}
	@Override
	public BalanceVO getManageSituationByDate(String startDate, String endDate) {
		BalanceVO vo=null;
		try {
			vo=manageSituationService.getManageSituationByDate(startDate, endDate);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return vo;
	}

}
