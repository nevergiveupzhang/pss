package com.psssystem.server.dao.factory.impl;

import com.psssystem.server.dao.factory.inf.PurchasingSalesDaoFactory;
import com.psssystem.server.dao.impl.order.salespay.PurchasingOrderDaoImpl;
import com.psssystem.server.dao.impl.order.salespay.SalesOrderDaoImpl;
import com.psssystem.server.dao.impl.order.salespay.creator.PurchasingOrderCreatorImpl;
import com.psssystem.server.dao.impl.order.salespay.creator.SalesOrderCreatorImpl;
import com.psssystem.server.dao.impl.order.salespay.passer.PurchasingOrderPasser;
import com.psssystem.server.dao.impl.order.salespay.passer.SalesOrderPasserImpl;
import com.psssystem.server.dao.inf.order.general.IOrderQuerier;
import com.psssystem.server.dao.inf.order.general.salespay.ISalesPayDao;
import com.psssystem.server.dao.inf.order.general.salespay.creator.SalesPayCreator;
import com.psssystem.server.dao.inf.order.general.salespay.passer.ISalesPayPasser;
import com.psssystem.server.dao.inf.order.general.salespay.querier.PurchasingSalesQuerierTemplate;

public class PurchasingOrderDaoFactoryImpl implements PurchasingSalesDaoFactory{
	@Override
	public ISalesPayDao getInstance() {
		return new PurchasingOrderDaoImpl();
	}

	@Override
	public SalesPayCreator getCreator() {
		return new PurchasingOrderCreatorImpl();
	}

	@Override
	public ISalesPayPasser getPasser() {
		return new PurchasingOrderPasser();
	}

	@Override
	public PurchasingSalesQuerierTemplate getQuerier() {
		return new PurchasingOrderDaoImpl() ;
	}
}
