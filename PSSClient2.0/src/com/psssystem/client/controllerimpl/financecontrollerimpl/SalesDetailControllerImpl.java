package com.psssystem.client.controllerimpl.financecontrollerimpl;

import java.rmi.RemoteException;
import java.util.Set;

import com.psssystem.client.controller.financecontroller.ISalesDetailController;
import com.psssystem.client.util.ConnectToServer;
import com.psssystem.connection.service.financeservice.ISalesDetailService;
import com.psssystem.connection.vo.SalesDetailVO;

public class SalesDetailControllerImpl implements ISalesDetailController {
	private ISalesDetailService salesDetailService;
	public SalesDetailControllerImpl(){
		ConnectToServer.connect();
		salesDetailService=ConnectToServer.salesDetailService;
	}
	@Override
	public Set<SalesDetailVO> getSalesDetail(String[] condition) {
		Set<SalesDetailVO> list=null;
		try {
			list=salesDetailService.getSalesDetail(condition);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return list;
	}

}
