package com.psssystem.server.dao.factory.inf;

import com.psssystem.server.dao.inf.order.general.IOrderQuerier;
import com.psssystem.server.dao.inf.order.general.salespay.ISalesPayDao;
import com.psssystem.server.dao.inf.order.general.salespay.creator.SalesPayCreator;
import com.psssystem.server.dao.inf.order.general.salespay.passer.ISalesPayPasser;
import com.psssystem.server.dao.inf.order.general.salespay.querier.PurchasingSalesQuerierTemplate;


public interface PurchasingSalesDaoFactory extends SalesPayDaoFactory{
	public PurchasingSalesQuerierTemplate getQuerier(); 
}
