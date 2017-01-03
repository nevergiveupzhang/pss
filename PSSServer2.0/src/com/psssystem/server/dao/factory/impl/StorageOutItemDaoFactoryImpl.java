package com.psssystem.server.dao.factory.impl;

import com.psssystem.server.dao.factory.inf.ItemFactory;
import com.psssystem.server.dao.impl.order.salespay.item.StorageOutItemDaoImpl;
import com.psssystem.server.dao.impl.order.salespay.item.creator.StorageOutItemCreatorImpl;
import com.psssystem.server.dao.impl.order.salespay.item.querier.StorageOutItemQuerierImpl;
import com.psssystem.server.dao.inf.order.general.salespay.item.IItemCreator;
import com.psssystem.server.dao.inf.order.general.salespay.item.ItemDao;
import com.psssystem.server.dao.inf.order.general.salespay.item.ItemQuerier;

public class StorageOutItemDaoFactoryImpl implements ItemFactory {

	@Override
	public ItemDao getInstance() {
		return new StorageOutItemDaoImpl();
	}

	@Override
	public IItemCreator getCreator() {
		return new StorageOutItemCreatorImpl();
	}

	@Override
	public ItemQuerier getQuerier() {
		return new StorageOutItemQuerierImpl();
	}

}
