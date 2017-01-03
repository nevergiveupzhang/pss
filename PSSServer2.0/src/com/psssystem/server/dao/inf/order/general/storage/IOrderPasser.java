package com.psssystem.server.dao.inf.order.general.storage;

import java.util.Set;

public interface IOrderPasser<T>{
	public boolean passOrders(Set<T> orders);
}
