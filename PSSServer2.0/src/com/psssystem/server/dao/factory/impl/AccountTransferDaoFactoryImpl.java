package com.psssystem.server.dao.factory.impl;

import com.psssystem.server.dao.factory.inf.ItemFactory;
import com.psssystem.server.dao.impl.order.salespay.item.AccountTransferDaoImpl;
import com.psssystem.server.dao.impl.order.salespay.item.creator.AccountTransferCreatorImpl;
import com.psssystem.server.dao.impl.order.salespay.item.querier.AccountTransferQuerierImpl;
import com.psssystem.server.dao.inf.order.general.salespay.item.IItemCreator;
import com.psssystem.server.dao.inf.order.general.salespay.item.ItemDao;
import com.psssystem.server.dao.inf.order.general.salespay.item.ItemQuerier;

public class AccountTransferDaoFactoryImpl implements ItemFactory {

	@Override
	public ItemDao getInstance() {
		return new AccountTransferDaoImpl();
	}

	@Override
	public IItemCreator getCreator() {
		return new AccountTransferCreatorImpl();
	}

	@Override
	public ItemQuerier getQuerier() {
		return new AccountTransferQuerierImpl();
	}

}
