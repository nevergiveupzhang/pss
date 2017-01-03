package com.psssystem.server.dao.factory.inf;

import com.psssystem.server.dao.inf.order.general.salespay.item.IItemCreator;
import com.psssystem.server.dao.inf.order.general.salespay.item.ItemDao;
import com.psssystem.server.dao.inf.order.general.salespay.item.ItemQuerier;

public interface ItemFactory {
	public ItemDao getInstance();
	public IItemCreator getCreator();
	public ItemQuerier getQuerier();
}
