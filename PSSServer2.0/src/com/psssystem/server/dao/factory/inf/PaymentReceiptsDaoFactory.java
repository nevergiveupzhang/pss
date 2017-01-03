package com.psssystem.server.dao.factory.inf;

import com.psssystem.server.dao.inf.order.general.salespay.querier.PaymentReceiptsQuerierTemplate;

public interface PaymentReceiptsDaoFactory extends SalesPayDaoFactory {
	public PaymentReceiptsQuerierTemplate getQuerier();
}
