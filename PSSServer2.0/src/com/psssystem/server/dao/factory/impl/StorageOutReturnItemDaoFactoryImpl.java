package com.psssystem.server.dao.factory.impl;

import com.psssystem.server.dao.factory.inf.ItemFactory;
import com.psssystem.server.dao.impl.order.salespay.item.StorageOutReturnItemDaoImpl;
import com.psssystem.server.dao.impl.order.salespay.item.creator.StorageOutReturnItemCreatorImpl;
import com.psssystem.server.dao.impl.order.salespay.item.querier.StorageOutReturnItemQuerierImpl;
import com.psssystem.server.dao.inf.order.general.salespay.item.IItemCreator;
import com.psssystem.server.dao.inf.order.general.salespay.item.ItemDao;
import com.psssystem.server.dao.inf.order.general.salespay.item.ItemQuerier;

public class StorageOutReturnItemDaoFactoryImpl implements ItemFactory {

	@Override
	public ItemDao getInstance() {
		return new StorageOutReturnItemDaoImpl();
	}

	@Override
	public IItemCreator getCreator() {
		return new StorageOutReturnItemCreatorImpl();
	}

	@Override
	public ItemQuerier getQuerier() {
		return new StorageOutReturnItemQuerierImpl();
	}

}
