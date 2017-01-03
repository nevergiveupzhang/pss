package com.psssystem.server.serviceimpl.financeserviceimpl;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.Set;

import com.psssystem.connection.service.financeservice.ISalesDetailService;
import com.psssystem.connection.vo.SalesDetailVO;
import com.psssystem.server.dao.impl.order.salespay.SalesDetailDaoImpl;
import com.psssystem.server.dao.impl.order.salespay.SalesOrderDaoImpl;
import com.psssystem.server.dao.inf.order.general.salespay.querier.PurchasingSalesQuerierTemplate;

public class SalesDetailServiceImpl extends UnicastRemoteObject implements ISalesDetailService {
	private PurchasingSalesQuerierTemplate purchasingSalesQuerier;
	public SalesDetailServiceImpl() throws RemoteException {
		super();
		this.purchasingSalesQuerier=new SalesDetailDaoImpl();
	}

	@Override
	public Set<SalesDetailVO> getSalesDetail(String[] condition) throws RemoteException {
		/*时间区间，商品名，客户，业务员，仓库*/
		String startDate=condition[0];
		String endDate=condition[1];
		String commName=condition[2];
		String customerName=condition[3];
		String salesman=condition[4];
		Set<SalesDetailVO> voList=purchasingSalesQuerier.getByCommName(commName);		
		voList.addAll(purchasingSalesQuerier.getByCustomerName(customerName));
		voList.addAll(purchasingSalesQuerier.getByDate(startDate, endDate));
		voList.addAll(purchasingSalesQuerier.getBySalesman(salesman));
		return voList;
	}

	public static void main(String []args){
		
	}
	
}
