package com.psssystem.server.serviceimpl.financeserviceimpl;

import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;




import com.psssystem.connection.service.financeservice.IPaymentOrderService;
import com.psssystem.connection.vo.PaymentItemVO;
import com.psssystem.connection.vo.PaymentOrderVO;
import com.psssystem.connection.vo.ResultMessage;
import com.psssystem.server.dao.factory.impl.PaymentOrderDaoFactoryImpl;
import com.psssystem.server.dao.factory.inf.PaymentReceiptsDaoFactory;
import com.psssystem.server.dao.factory.inf.PurchasingSalesDaoFactory;
import com.psssystem.server.dao.impl.order.salespay.PaymentOrderDaoImpl;
import com.psssystem.server.dao.inf.order.general.salespay.ISalesPayDao;
import com.psssystem.server.dao.inf.order.general.salespay.querier.PaymentReceiptsQuerierTemplate;
import com.psssystem.server.data.OrderInfo;

public class PaymentOrderServiceImpl extends UnicastRemoteObject implements IPaymentOrderService {
	private ISalesPayDao paymentOrderDao;
	private PaymentReceiptsDaoFactory factory=new PaymentOrderDaoFactoryImpl();
	private PaymentReceiptsQuerierTemplate querier=factory.getQuerier();
	public PaymentOrderServiceImpl() throws RemoteException {
		super();
		paymentOrderDao=factory.getInstance();
	}

	@Override
	public boolean createPaymentOrder(PaymentOrderVO vo) throws RemoteException {
		return paymentOrderDao.createOrder(vo);
	}



	@Override
	public Set getAll() throws RemoteException {
		return querier.getAll();
	}

	@Override
	public boolean approve(Set<PaymentOrderVO> orders) throws RemoteException {
		return paymentOrderDao.passOrders(orders);
	}

	@Override
	public Set<PaymentOrderVO> getNotPassed() throws RemoteException {
		return querier.getNotPassed();
	}

}
