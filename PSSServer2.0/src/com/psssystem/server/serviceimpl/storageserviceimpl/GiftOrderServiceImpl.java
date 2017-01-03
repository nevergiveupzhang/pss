package com.psssystem.server.serviceimpl.storageserviceimpl;

import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


import com.psssystem.connection.service.storageservice.IGiftOrderService;
import com.psssystem.connection.vo.GiftOrderVO;
import com.psssystem.server.dao.impl.order.storage.GiftOrderDaoImpl;
import com.psssystem.server.dao.inf.order.general.storage.IGiftOrderDao;

public class GiftOrderServiceImpl extends UnicastRemoteObject implements IGiftOrderService {
	private IGiftOrderDao giftOrderDao;
	public GiftOrderServiceImpl() throws RemoteException {
		super();
		this.giftOrderDao=new GiftOrderDaoImpl();
		
	}

	@Override
	public boolean addGiftOrder(List<GiftOrderVO> voList) throws RemoteException {
		return giftOrderDao.addGiftOrder(voList);
	}


	@Override
	public Set<GiftOrderVO> getAll() throws RemoteException {
		return giftOrderDao.getAll();
	}

	@Override
	public boolean approve(Set<GiftOrderVO> orders) throws RemoteException {
		return giftOrderDao.passOrders(orders);		
	}

	@Override
	public Set<GiftOrderVO> getNotPassed() throws RemoteException {
		return giftOrderDao.getNotPassed();
	}
	
	
	public static void main(String []args) throws RemoteException{
		System.out.println(new GiftOrderServiceImpl().getNotPassed().size());
		
		for(GiftOrderVO vo:new GiftOrderServiceImpl().getNotPassed()){
			System.out.println(vo.getCommID()+" "+vo.getCommName()+" "+vo.getAmount()+" "+vo.getStatus()+" "+vo.getCreatedDate());
		}
	}

}
