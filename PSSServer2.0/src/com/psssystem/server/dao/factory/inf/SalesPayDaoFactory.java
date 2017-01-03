package com.psssystem.server.dao.factory.inf;

import com.psssystem.server.dao.inf.order.general.salespay.ISalesPayDao;
import com.psssystem.server.dao.inf.order.general.salespay.creator.SalesPayCreator;
import com.psssystem.server.dao.inf.order.general.salespay.passer.ISalesPayPasser;

public interface SalesPayDaoFactory {
	public ISalesPayDao getInstance();
	public SalesPayCreator getCreator();
	public ISalesPayPasser getPasser();
}
