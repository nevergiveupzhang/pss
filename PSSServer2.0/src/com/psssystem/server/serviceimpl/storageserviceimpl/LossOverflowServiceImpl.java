package com.psssystem.server.serviceimpl.storageserviceimpl;

import java.lang.reflect.InvocationTargetException;
import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;


import com.psssystem.connection.service.storageservice.ILossOverflowService;
import com.psssystem.connection.vo.LossOverflowOrderVO;
import com.psssystem.server.dao.impl.order.storage.LossOverflowOrderDaoImpl;
import com.psssystem.server.dao.inf.order.type.ILossOverflowOrderDao;
import com.psssystem.server.data.OrderInfo;

public class LossOverflowServiceImpl extends UnicastRemoteObject implements ILossOverflowService {
	private ILossOverflowOrderDao lossOverflowDao;
	public LossOverflowServiceImpl() throws RemoteException{
		lossOverflowDao=new LossOverflowOrderDaoImpl();
	}
	

	@Override
	public boolean addLossOverflowOrder(List<LossOverflowOrderVO> orderVOList)
			throws RemoteException {
		return lossOverflowDao.addOrder(orderVOList);
	}


	@Override
	public boolean approveByType(Set<LossOverflowOrderVO> orders,String type)
			throws RemoteException {
		return lossOverflowDao.passOrdersByType(orders,type);
	}


	@Override
	public Set<LossOverflowOrderVO> getAllByType(String type)
			throws RemoteException {
		return lossOverflowDao.getAllByType(type);
	}


	@Override
	public Set<LossOverflowOrderVO> getNotPassedByType(String type)
			throws RemoteException {
		return lossOverflowDao.getNotPassedByType(type);
	}



	
	

}
