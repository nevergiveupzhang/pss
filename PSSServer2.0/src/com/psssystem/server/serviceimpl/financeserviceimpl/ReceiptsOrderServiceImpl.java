package com.psssystem.server.serviceimpl.financeserviceimpl;

import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


import com.psssystem.connection.service.financeservice.IReceiptsOrderService;
import com.psssystem.connection.vo.AccountTransferVO;
import com.psssystem.connection.vo.ReceiptsOrderVO;
import com.psssystem.server.dao.factory.impl.ReceiptsOrderDaoFactoryImpl;
import com.psssystem.server.dao.factory.inf.PurchasingSalesDaoFactory;
import com.psssystem.server.dao.impl.order.salespay.ReceiptsOrderDaoImpl;
import com.psssystem.server.dao.inf.order.general.salespay.ISalesPayDao;
import com.psssystem.server.dao.inf.order.general.salespay.querier.PaymentReceiptsQuerierTemplate;
import com.psssystem.server.data.OrderInfo;

public class ReceiptsOrderServiceImpl extends UnicastRemoteObject implements IReceiptsOrderService {
	private ISalesPayDao receiptsOrderDao;
	private PaymentReceiptsQuerierTemplate querier;
	public ReceiptsOrderServiceImpl() throws RemoteException{
		receiptsOrderDao=new ReceiptsOrderDaoFactoryImpl().getInstance();
		querier=new ReceiptsOrderDaoFactoryImpl().getQuerier();
	}
	
	@Override
	public boolean createReceipts(ReceiptsOrderVO vo) throws RemoteException {
		return receiptsOrderDao.createOrder(vo);
		
	}


	@Override
	public Set getAll() throws RemoteException {
		return querier.getAll();
	}

	@Override
	public boolean approve(Set<ReceiptsOrderVO> orders) throws RemoteException {
		return receiptsOrderDao.passOrders(orders);
	}

	@Override
	public Set<ReceiptsOrderVO> getNotPassed() throws RemoteException {
		return querier.getNotPassed();
	}

}
