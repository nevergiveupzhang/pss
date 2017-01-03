package com.psssystem.server.dao.factory.impl;

import com.psssystem.server.dao.factory.inf.PaymentReceiptsDaoFactory;
import com.psssystem.server.dao.factory.inf.PurchasingSalesDaoFactory;
import com.psssystem.server.dao.impl.order.salespay.PaymentOrderDaoImpl;
import com.psssystem.server.dao.impl.order.salespay.ReceiptsOrderDaoImpl;
import com.psssystem.server.dao.impl.order.salespay.creator.PaymentOrderCreatorImpl;
import com.psssystem.server.dao.impl.order.salespay.creator.ReceiptsOrderCreatorImpl;
import com.psssystem.server.dao.impl.order.salespay.passer.PaymentOrderPasserImpl;
import com.psssystem.server.dao.impl.order.salespay.passer.ReceiptsOrderPasserImpl;
import com.psssystem.server.dao.inf.order.general.IOrderQuerier;
import com.psssystem.server.dao.inf.order.general.salespay.ISalesPayDao;
import com.psssystem.server.dao.inf.order.general.salespay.creator.SalesPayCreator;
import com.psssystem.server.dao.inf.order.general.salespay.passer.ISalesPayPasser;
import com.psssystem.server.dao.inf.order.general.salespay.querier.PaymentReceiptsQuerierTemplate;

public class ReceiptsOrderDaoFactoryImpl implements PaymentReceiptsDaoFactory {

	@Override
	public ISalesPayDao getInstance() {
		return new ReceiptsOrderDaoImpl();
	}

	@Override
	public SalesPayCreator getCreator() {
		return new ReceiptsOrderCreatorImpl();
	}

	@Override
	public ISalesPayPasser getPasser() {
		return new ReceiptsOrderPasserImpl();
	}


	@Override
	public PaymentReceiptsQuerierTemplate getQuerier() {
		return new ReceiptsOrderDaoImpl();
	}


}
