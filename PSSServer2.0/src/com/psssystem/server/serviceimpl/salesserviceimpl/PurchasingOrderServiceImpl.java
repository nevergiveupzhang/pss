package com.psssystem.server.serviceimpl.salesserviceimpl;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


import com.psssystem.connection.service.salesservice.IPurchasingOrderService;
import com.psssystem.connection.vo.CommodityItemVO;
import com.psssystem.connection.vo.PurchasingOrderVO;
import com.psssystem.server.dao.factory.impl.PurchasingOrderDaoFactoryImpl;
import com.psssystem.server.dao.factory.impl.PurchasingReturnOrderDaoFactoryImpl;
import com.psssystem.server.dao.impl.order.salespay.PurchasingOrderDaoImpl;
import com.psssystem.server.dao.impl.order.salespay.PurchasingReturnOrderDaoImpl;
import com.psssystem.server.dao.inf.order.general.salespay.ISalesPayDao;
import com.psssystem.server.dao.inf.order.general.salespay.querier.PurchasingSalesQuerierTemplate;
import com.psssystem.server.data.OrderInfo;

public class PurchasingOrderServiceImpl extends UnicastRemoteObject implements IPurchasingOrderService {
	private ISalesPayDao purchasingOrderDao;
	private ISalesPayDao purchasingReturnOrderDao;
	private PurchasingSalesQuerierTemplate purchasingQuerier;
	private PurchasingSalesQuerierTemplate purchasingReturnQuerier;
	public PurchasingOrderServiceImpl() throws RemoteException {
		super();
		purchasingOrderDao=new PurchasingOrderDaoFactoryImpl().getInstance();
		purchasingReturnOrderDao=new PurchasingReturnOrderDaoFactoryImpl().getInstance();
		purchasingQuerier=new PurchasingOrderDaoFactoryImpl().getQuerier();
		purchasingReturnQuerier=new PurchasingReturnOrderDaoFactoryImpl().getQuerier();
	}

	@Override
	public boolean addPurchasingOrder(PurchasingOrderVO vo)
			throws RemoteException {
		System.out.println(vo.getCustomerName()+vo.getStorageList().size());
		return purchasingOrderDao.createOrder(vo);
	}


	@Override
	public boolean addPurchasingReturnOrder(PurchasingOrderVO vo)
			throws RemoteException {
		return purchasingReturnOrderDao.createOrder(vo);
	}


	@Override
	public boolean approveByType(Set<PurchasingOrderVO> orders, String type)
			throws RemoteException {
		if(type.equals(OrderInfo.PURCHASING)){
			return purchasingOrderDao.passOrders(orders);
		}else if(type.equals(com.psssystem.server.data.OrderInfo.PURCHASINGRETURN)){
			return purchasingReturnOrderDao.passOrders(orders);
		}
		return false;
	}

	@Override
	public Set<PurchasingOrderVO> getAllByType(String type)
			throws RemoteException {
		if(type.equals(OrderInfo.PURCHASING)){
			return purchasingQuerier.getAll();
		}else if(type.equals(com.psssystem.server.data.OrderInfo.PURCHASINGRETURN)){
			return purchasingReturnQuerier.getAll();
		}
		return null;
	}

	@Override
	public Set<PurchasingOrderVO> getNotPassedByType(String type)
			throws RemoteException {
		if(type.equals(OrderInfo.PURCHASING)){
			return purchasingQuerier.getNotPassed();
		}else if(type.equals(OrderInfo.PURCHASINGRETURN)){
			return purchasingReturnQuerier.getNotPassed();
		}
		return null;
	}

	
	public static void main(String []args) throws RemoteException{
		System.out.println(new PurchasingOrderServiceImpl().getNotPassedByType(OrderInfo.PURCHASINGRETURN).size());
	}
}
