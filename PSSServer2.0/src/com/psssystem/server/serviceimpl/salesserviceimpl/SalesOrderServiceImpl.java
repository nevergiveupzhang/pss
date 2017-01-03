package com.psssystem.server.serviceimpl.salesserviceimpl;

import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


import com.psssystem.connection.service.salesservice.ISalesOrderService;
import com.psssystem.connection.vo.CommodityItemVO;
import com.psssystem.connection.vo.PurchasingOrderVO;
import com.psssystem.connection.vo.SalesOrderVO;
import com.psssystem.server.dao.factory.impl.SalesOrderDaoFactoryImpl;
import com.psssystem.server.dao.factory.impl.SalesReturnOrderDaoFactoryImpl;
import com.psssystem.server.dao.impl.order.salespay.SalesOrderDaoImpl;
import com.psssystem.server.dao.impl.order.salespay.SalesReturnOrderDaoImpl;
import com.psssystem.server.dao.inf.order.general.salespay.ISalesPayDao;
import com.psssystem.server.dao.inf.order.general.salespay.querier.PurchasingSalesQuerierTemplate;
import com.psssystem.server.data.OrderInfo;

public class SalesOrderServiceImpl extends UnicastRemoteObject 
implements ISalesOrderService {
	private ISalesPayDao salesOrderDao;
	private ISalesPayDao salesReturnOrderDao;
	private PurchasingSalesQuerierTemplate salesQuerier;
	private PurchasingSalesQuerierTemplate salesReturnQuerier;
	public SalesOrderServiceImpl() throws RemoteException{
		salesOrderDao=new SalesOrderDaoFactoryImpl().getInstance();
		salesQuerier=new SalesOrderDaoFactoryImpl().getQuerier();
		salesReturnOrderDao=new SalesReturnOrderDaoFactoryImpl().getInstance();
		salesReturnQuerier=new SalesReturnOrderDaoFactoryImpl().getQuerier();
	}
	
	@Override
	public boolean addSalesOrder(SalesOrderVO vo) throws RemoteException {
		return salesOrderDao.createOrder(vo);
	}


	@Override
	public boolean addSalesReturnOrder(SalesOrderVO vo) throws RemoteException {
		return salesReturnOrderDao.createOrder(vo);
	}

	public static void main(String []args){
//		try {
////			new SalesOrderServiceImpl().addSalesOrder(new SalesOrderVO());
//		} catch (RemoteException e) {
//			e.printStackTrace();
//		}
	}

	@Override
	public boolean approveByType(Set<SalesOrderVO> orders, String type)
			throws RemoteException {
		if(type.equals(OrderInfo.SALES)){
			return salesOrderDao.passOrders(orders);
		}else if(type.equals(OrderInfo.SALESRETURN)){
			return salesReturnOrderDao.passOrders(orders);
		}
		return false;
	}

	@Override
	public Set<SalesOrderVO> getAllByType(String type) throws RemoteException {
		if(type.equals(OrderInfo.SALES)){
			return salesQuerier.getAll();
		}else if(type.equals(OrderInfo.SALESRETURN)){
			return salesReturnQuerier.getAll();
		}
		return null;
	}

	@Override
	public Set<SalesOrderVO> getNotPassedByType(String type)
			throws RemoteException {
		if(type.equals(OrderInfo.SALES)){
			return salesQuerier.getNotPassed();
		}else if(type.equals(OrderInfo.SALESRETURN)){
			return salesReturnQuerier.getNotPassed();
		}
		return null;
	}
}
