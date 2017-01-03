package com.psssystem.server.dao.factory.impl;

import com.psssystem.server.dao.factory.inf.PurchasingSalesDaoFactory;
import com.psssystem.server.dao.impl.order.salespay.SalesOrderDaoImpl;
import com.psssystem.server.dao.impl.order.salespay.creator.SalesOrderCreatorImpl;
import com.psssystem.server.dao.impl.order.salespay.passer.SalesOrderPasserImpl;
import com.psssystem.server.dao.inf.order.general.IOrderQuerier;
import com.psssystem.server.dao.inf.order.general.salespay.ISalesPayDao;
import com.psssystem.server.dao.inf.order.general.salespay.creator.SalesPayCreator;
import com.psssystem.server.dao.inf.order.general.salespay.passer.ISalesPayPasser;
import com.psssystem.server.dao.inf.order.general.salespay.querier.PurchasingSalesQuerierTemplate;

public class SalesOrderDaoFactoryImpl implements PurchasingSalesDaoFactory{
	@Override
	public ISalesPayDao getInstance() {
		return new SalesOrderDaoImpl();
	}

	@Override
	public SalesPayCreator getCreator() {
		return new SalesOrderCreatorImpl();
	}

	@Override
	public ISalesPayPasser getPasser() {
		return new SalesOrderPasserImpl();
	}

	@Override
	public PurchasingSalesQuerierTemplate getQuerier() {
		return new SalesOrderDaoImpl();
	}


}
