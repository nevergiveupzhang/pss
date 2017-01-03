package com.psssystem.connection.service.financeservice;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Set;

import com.psssystem.connection.vo.SalesDetailVO;

public interface ISalesDetailService extends Remote{
	public Set<SalesDetailVO> getSalesDetail(String [] condition) throws RemoteException;
}
