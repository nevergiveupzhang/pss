package com.psssystem.connection.service.financeservice;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Set;

import com.psssystem.connection.vo.BalanceVO;

public interface IManageSituationService extends Remote {
	public BalanceVO getManageSituationByDate(String startDate,String endDate) throws RemoteException;
}
