package com.psssystem.server.dao.factory.impl;

import com.psssystem.server.dao.factory.inf.ItemFactory;
import com.psssystem.server.dao.impl.order.salespay.item.StorageInReturnItemDaoImpl;
import com.psssystem.server.dao.impl.order.salespay.item.creator.StorageInReturnItemCreatorImpl;
import com.psssystem.server.dao.impl.order.salespay.item.querier.StorageInReturnItemQuerierImpl;
import com.psssystem.server.dao.inf.order.general.salespay.item.IItemCreator;
import com.psssystem.server.dao.inf.order.general.salespay.item.ItemDao;
import com.psssystem.server.dao.inf.order.general.salespay.item.ItemQuerier;

public class StorageInReturnItemDaoFactoryImpl implements ItemFactory {

	@Override
	public ItemDao getInstance() {
		return new StorageInReturnItemDaoImpl();
	}

	@Override
	public IItemCreator getCreator() {
		return new StorageInReturnItemCreatorImpl();
	}

	@Override
	public ItemQuerier getQuerier() {
		return new StorageInReturnItemQuerierImpl();
	}

}
