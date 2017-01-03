package com.psssystem.server.dao.inf.order.general.salespay.passer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Set;

import com.psssystem.server.dao.inf.order.general.salespay.item.ItemQuerier;
import com.psssystem.server.util.DBUtils;

public interface ISalesPayPasser<T>{
	public boolean passOrders(Set<T> orders);
}
