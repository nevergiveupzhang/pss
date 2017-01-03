package com.psssystem.server.dao.factory.impl;

import com.psssystem.server.dao.factory.inf.PurchasingSalesDaoFactory;
import com.psssystem.server.dao.impl.order.salespay.SalesReturnOrderDaoImpl;
import com.psssystem.server.dao.impl.order.salespay.creator.SalesReturnOrderCreatorImpl;
import com.psssystem.server.dao.impl.order.salespay.passer.SalesReturnOrderPasserImpl;
import com.psssystem.server.dao.inf.order.general.IOrderQuerier;
import com.psssystem.server.dao.inf.order.general.salespay.ISalesPayDao;
import com.psssystem.server.dao.inf.order.general.salespay.creator.SalesPayCreator;
import com.psssystem.server.dao.inf.order.general.salespay.passer.ISalesPayPasser;
import com.psssystem.server.dao.inf.order.general.salespay.querier.PurchasingSalesQuerierTemplate;

public class SalesReturnOrderDaoFactoryImpl implements PurchasingSalesDaoFactory {

	@Override
	public ISalesPayDao getInstance() {
		return new SalesReturnOrderDaoImpl();
	}

	@Override
	public SalesPayCreator getCreator() {
		return new SalesReturnOrderCreatorImpl();
	}

	@Override
	public ISalesPayPasser getPasser() {
		return new SalesReturnOrderPasserImpl();
	}

	@Override
	public PurchasingSalesQuerierTemplate getQuerier() {
		return new SalesReturnOrderDaoImpl();
	}


}
