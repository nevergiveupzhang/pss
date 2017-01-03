package com.psssystem.server.dao.factory.impl;

import com.psssystem.server.dao.factory.inf.ItemFactory;
import com.psssystem.server.dao.impl.order.salespay.item.PaymentItemDaoImpl;
import com.psssystem.server.dao.impl.order.salespay.item.creator.PaymentItemCreatorImpl;
import com.psssystem.server.dao.impl.order.salespay.item.querier.PaymentItemQuerierImpl;
import com.psssystem.server.dao.inf.order.general.salespay.item.IItemCreator;
import com.psssystem.server.dao.inf.order.general.salespay.item.ItemDao;
import com.psssystem.server.dao.inf.order.general.salespay.item.ItemQuerier;

public class PaymentItemDaoFactoryImpl implements ItemFactory {

	@Override
	public ItemDao getInstance() {
		return new PaymentItemDaoImpl();
	}

	@Override
	public IItemCreator getCreator() {
		return new PaymentItemCreatorImpl();
	}

	@Override
	public ItemQuerier getQuerier() {
		return new PaymentItemQuerierImpl();
	}

}
