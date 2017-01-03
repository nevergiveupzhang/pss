package com.psssystem.server.serviceimpl.financeserviceimpl;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.psssystem.connection.service.financeservice.IManageProcessService;
import com.psssystem.connection.vo.AlarmOrderVO;
import com.psssystem.connection.vo.GiftOrderVO;
import com.psssystem.connection.vo.LossOverflowOrderVO;
import com.psssystem.connection.vo.PaymentOrderVO;
import com.psssystem.connection.vo.PurchasingOrderVO;
import com.psssystem.connection.vo.ReceiptsOrderVO;
import com.psssystem.connection.vo.SalesOrderVO;
import com.psssystem.server.dao.factory.impl.PaymentOrderDaoFactoryImpl;
import com.psssystem.server.dao.factory.impl.PurchasingOrderDaoFactoryImpl;
import com.psssystem.server.dao.factory.impl.PurchasingReturnOrderDaoFactoryImpl;
import com.psssystem.server.dao.factory.impl.ReceiptsOrderDaoFactoryImpl;
import com.psssystem.server.dao.factory.impl.SalesOrderDaoFactoryImpl;
import com.psssystem.server.dao.factory.impl.SalesReturnOrderDaoFactoryImpl;
import com.psssystem.server.dao.factory.inf.PaymentReceiptsDaoFactory;
import com.psssystem.server.dao.factory.inf.PurchasingSalesDaoFactory;
import com.psssystem.server.dao.factory.inf.SalesPayDaoFactory;
import com.psssystem.server.dao.impl.order.salespay.PaymentOrderDaoImpl;
import com.psssystem.server.dao.impl.order.salespay.PurchasingOrderDaoImpl;
import com.psssystem.server.dao.impl.order.salespay.PurchasingReturnOrderDaoImpl;
import com.psssystem.server.dao.impl.order.salespay.ReceiptsOrderDaoImpl;
import com.psssystem.server.dao.impl.order.salespay.SalesOrderDaoImpl;
import com.psssystem.server.dao.impl.order.salespay.SalesReturnOrderDaoImpl;
import com.psssystem.server.dao.impl.order.storage.AlarmOrderDaoImpl;
import com.psssystem.server.dao.impl.order.storage.GiftOrderDaoImpl;
import com.psssystem.server.dao.impl.order.storage.LossOverflowOrderDaoImpl;
import com.psssystem.server.dao.inf.order.general.IOrderQuerier;
import com.psssystem.server.dao.inf.order.general.salespay.ISalesPayDao;
import com.psssystem.server.dao.inf.order.general.salespay.querier.PaymentReceiptsQuerierTemplate;
import com.psssystem.server.dao.inf.order.general.salespay.querier.PurchasingSalesQuerierTemplate;
import com.psssystem.server.dao.inf.order.general.storage.IAlarmOrderDao;
import com.psssystem.server.dao.inf.order.general.storage.IGiftOrderDao;
import com.psssystem.server.dao.inf.order.type.ILossOverflowOrderDao;
import com.psssystem.server.data.OrderInfo;

public class ManageProcessServiceImpl extends UnicastRemoteObject implements
		IManageProcessService {
	private IGiftOrderDao giftOrderDao;
	private IAlarmOrderDao alarmOrderDao;
	private ILossOverflowOrderDao lossOverflowOrderDao;
	private PaymentReceiptsQuerierTemplate paymentQuerier;
	private PaymentReceiptsQuerierTemplate receiptsQuerier;
	private PurchasingSalesQuerierTemplate purchasingQuerier;
	private PurchasingSalesQuerierTemplate purchasingReturnQuerier;
	private PurchasingSalesQuerierTemplate salesQuerier;
	private PurchasingSalesQuerierTemplate salesReturnQuerier;
	private List<PurchasingSalesDaoFactory> purchasingSalesfactories = new ArrayList<PurchasingSalesDaoFactory>();
	private List<PaymentReceiptsDaoFactory> paymentReceiptsfactories = new ArrayList<PaymentReceiptsDaoFactory>();

	public ManageProcessServiceImpl() throws RemoteException {
		super();
		setFactories();
		giftOrderDao = new GiftOrderDaoImpl();
		alarmOrderDao = new AlarmOrderDaoImpl();
		lossOverflowOrderDao = new LossOverflowOrderDaoImpl();
		paymentQuerier = paymentReceiptsfactories.get(0).getQuerier();
		receiptsQuerier = paymentReceiptsfactories.get(1).getQuerier();
		purchasingQuerier = purchasingSalesfactories.get(0).getQuerier();
		purchasingReturnQuerier = purchasingSalesfactories.get(1).getQuerier();
		salesQuerier = purchasingSalesfactories.get(2).getQuerier();
		salesReturnQuerier = purchasingSalesfactories.get(3).getQuerier();
	}

	private void setFactories() {
		paymentReceiptsfactories.add(new PaymentOrderDaoFactoryImpl());
		paymentReceiptsfactories.add(new ReceiptsOrderDaoFactoryImpl());
		purchasingSalesfactories.add(new PurchasingOrderDaoFactoryImpl());
		purchasingSalesfactories.add(new PurchasingReturnOrderDaoFactoryImpl());
		purchasingSalesfactories.add(new SalesOrderDaoFactoryImpl());
		purchasingSalesfactories.add(new SalesReturnOrderDaoFactoryImpl());
	}

	@Override
	public Set<AlarmOrderVO> getAlarmOrder(String[] info)
			throws RemoteException {
		return alarmOrderDao.getByDate(info[0], info[1]);
	}

	@Override
	public Set<LossOverflowOrderVO> getLossOverflowOrder(String type,
			String[] info) throws RemoteException {
		Set<LossOverflowOrderVO> resultSet = lossOverflowOrderDao.getByDate(
				type, info[0], info[1]);
		return resultSet;
	}

	@Override
	public Set<PaymentOrderVO> getPaymentOrder(String[] info)
			throws RemoteException {
		Set<PaymentOrderVO> resultList = paymentQuerier.getByDate(info[0],
				info[1]);
		if (!"".equals(info[2]))
			resultList.addAll(paymentQuerier.getByCustomerName(info[2]));

		return resultList;
	}

	@Override
	public Set<PurchasingOrderVO> getPurchasingOrder(String[] info)
			throws RemoteException {
		Set<PurchasingOrderVO> resultList = purchasingQuerier.getByDate(
				info[0], info[1]);
		if (!"".equals(info[2]))
			resultList.addAll(purchasingQuerier.getByCustomerName(info[2]));
		if (!"".equals(info[3]))
			resultList.addAll(purchasingQuerier.getBySalesman(info[3]));
		return resultList;
	}

	@Override
	public Set<PurchasingOrderVO> getPurchasingReturnOrder(String[] info)
			throws RemoteException {
		Set<PurchasingOrderVO> resultList = purchasingReturnQuerier.getByDate(
				info[0], info[1]);
		if (!"".equals(info[2]))
			resultList.addAll(purchasingReturnQuerier
					.getByCustomerName(info[2]));
		if (!"".equals(info[3]))
			resultList.addAll(purchasingReturnQuerier.getBySalesman(info[3]));
		return resultList;
	}

	@Override
	public Set<ReceiptsOrderVO> getReceiptsOrder(String[] info)
			throws RemoteException {
		Set<ReceiptsOrderVO> resultList = receiptsQuerier.getByDate(info[0],
				info[1]);
		if (!"".equals(info[2]))
			resultList.addAll(receiptsQuerier.getByCustomerName(info[2]));
		return resultList;
	}

	@Override
	public Set<SalesOrderVO> getSalesOrder(String[] info)
			throws RemoteException {
		Set<SalesOrderVO> resultList = salesQuerier.getByDate(info[0], info[1]);
		if (!"".equals(info[2]))
			resultList.addAll(salesQuerier.getByCustomerName(info[2]));
		if (!"".equals(info[3]))
			resultList.addAll(salesQuerier.getBySalesman(info[3]));
		return resultList;
	}

	@Override
	public Set<SalesOrderVO> getSalesReturnOrder(String[] info)
			throws RemoteException {
		Set<SalesOrderVO> resultList = salesReturnQuerier.getByDate(info[0],
				info[1]);
		if (!"".equals(info[2]))
			resultList.addAll(salesReturnQuerier.getByCustomerName(info[2]));
		if (!"".equals(info[3]))
			resultList.addAll(salesReturnQuerier.getBySalesman(info[3]));
		return resultList;
	}

	@Override
	public Set<GiftOrderVO> getGiftOrder(String[] info) throws RemoteException {
		return giftOrderDao.getByDate(info[0], info[1]);
	}

	public static void main(String[] args) throws RemoteException {
		ManageProcessServiceImpl m = new ManageProcessServiceImpl();

		// System.out.println(m.getLossOverflowOrder(OrderInfo.LOSS,new String[]{"2013-9-9","2019-9-9"}).size());

		// System.out.println(m.getGiftOrder(new String[]{"2013-9-9","2019-9-9"}).size());

//		 System.out.println(m.getAlarmOrder(new String[]{"2013-9-9","2019-9-9"}).size());

		// for(PaymentOrderVO vo:m.getPaymentOrder(new
		// String[]{"2013-9-9","2019-9-9","毛泽东"})){
		// System.out.println(vo.getId()+"|"+vo.getCustomerID()+":"+vo.getCustomerName()+"|"+vo.getUserID()+":"+vo.getUserName());
		// }
//		 System.out.println(m.getPaymentOrder(new String[]{"2013-9-9","2019-9-9","毛泽东"}).size());

//		System.out.println(m.getReceiptsOrder(new String[]{"2013-9-9","2019-9-9",""}).size());
		
//		 System.out.println(m.getPurchasingOrder(new String[]{"2013-9-9","2019-9-9","毛泽东","毛泽东"}).size());
		
		
//		 System.out.println(m.getSalesOrder(new String[]{"2013-9-9","2019-9-9","毛泽东","毛泽东"}).size());

//		 System.out.println(m.getPurchasingReturnOrder(new String[]{"2013-9-9","2019-9-9","",""}).size());
		
//		System.out.println(m.getSalesReturnOrder(new String[]{"2013-9-9","2019-9-9","毛泽东","毛泽东"}).size());
	}
}
