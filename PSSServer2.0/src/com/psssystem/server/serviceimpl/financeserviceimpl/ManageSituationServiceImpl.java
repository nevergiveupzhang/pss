package com.psssystem.server.serviceimpl.financeserviceimpl;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Set;

import com.psssystem.connection.service.financeservice.IManageSituationService;
import com.psssystem.connection.vo.BalanceVO;
import com.psssystem.server.dao.impl.others.BalanceDaoImpl;
import com.psssystem.server.dao.inf.others.BalanceDaoTemplate;

public class ManageSituationServiceImpl extends UnicastRemoteObject implements
		IManageSituationService {
	private BalanceDaoTemplate dao;
	public ManageSituationServiceImpl() throws RemoteException {
		super();
		dao=new BalanceDaoImpl();
	}

	@Override
	public BalanceVO getManageSituationByDate(String startDate, String endDate)
			throws RemoteException {
		int income=dao.getSumOfSales(startDate, endDate)+dao.getSumOfPurchasingReturn(startDate, endDate);
		int outcome=dao.getSumOfPurchasing(startDate, endDate)+dao.getSumOfSalesReturn(startDate, endDate);
		int profit=income-outcome;
		int discount=dao.getDiscountOfSales(startDate, endDate);
		return new BalanceVO(income,outcome,profit,discount);
	}

}
