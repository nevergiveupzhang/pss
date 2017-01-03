package com.psssystem.server.dao.factory.impl;

import com.psssystem.server.dao.factory.inf.PaymentReceiptsDaoFactory;
import com.psssystem.server.dao.factory.inf.PurchasingSalesDaoFactory;
import com.psssystem.server.dao.impl.order.salespay.PaymentOrderDaoImpl;
import com.psssystem.server.dao.impl.order.salespay.creator.PaymentOrderCreatorImpl;
import com.psssystem.server.dao.impl.order.salespay.passer.PaymentOrderPasserImpl;
import com.psssystem.server.dao.inf.order.general.IOrderQuerier;
import com.psssystem.server.dao.inf.order.general.salespay.ISalesPayDao;
import com.psssystem.server.dao.inf.order.general.salespay.creator.SalesPayCreator;
import com.psssystem.server.dao.inf.order.general.salespay.passer.ISalesPayPasser;
import com.psssystem.server.dao.inf.order.general.salespay.querier.PaymentReceiptsQuerierTemplate;

public class PaymentOrderDaoFactoryImpl implements PaymentReceiptsDaoFactory {

	@Override
	public ISalesPayDao getInstance() {
		return new PaymentOrderDaoImpl();
	}

	@Override
	public SalesPayCreator getCreator() {
		return new PaymentOrderCreatorImpl();
	}

	@Override
	public ISalesPayPasser getPasser() {
		return new PaymentOrderPasserImpl();
	}

	@Override
	public PaymentReceiptsQuerierTemplate getQuerier() {
		return new PaymentOrderDaoImpl();
	}
	
}
