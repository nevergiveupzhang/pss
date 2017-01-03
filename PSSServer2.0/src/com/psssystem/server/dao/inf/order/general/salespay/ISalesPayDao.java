package com.psssystem.server.dao.inf.order.general.salespay;

import java.util.Set;

import com.psssystem.server.dao.inf.order.general.salespay.creator.SalesPayCreator;
import com.psssystem.server.dao.inf.order.general.salespay.passer.ISalesPayPasser;

public interface ISalesPayDao<T> {
	public boolean createOrder(T order);
	public boolean passOrders(Set<T> orders);
}
