package com.psssystem.server.dao.factory.impl;

import com.psssystem.server.dao.factory.inf.ItemFactory;
import com.psssystem.server.dao.impl.order.salespay.item.StorageInItemDaoImpl;
import com.psssystem.server.dao.impl.order.salespay.item.creator.StorageInItemCreatorImpl;
import com.psssystem.server.dao.impl.order.salespay.item.querier.StorageInItemQuerierImpl;
import com.psssystem.server.dao.inf.order.general.salespay.item.IItemCreator;
import com.psssystem.server.dao.inf.order.general.salespay.item.ItemDao;
import com.psssystem.server.dao.inf.order.general.salespay.item.ItemQuerier;

public class StorageInItemDaoFactoryImpl implements ItemFactory {

	@Override
	public ItemDao getInstance() {
		return new StorageInItemDaoImpl();
	}

	@Override
	public IItemCreator getCreator() {
		return new StorageInItemCreatorImpl();
	}

	@Override
	public ItemQuerier getQuerier() {
		return new StorageInItemQuerierImpl();
	}

}
