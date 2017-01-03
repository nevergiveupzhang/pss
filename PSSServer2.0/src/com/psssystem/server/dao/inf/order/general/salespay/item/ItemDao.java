package com.psssystem.server.dao.inf.order.general.salespay.item;

import java.util.Set;

public abstract class ItemDao<T> {
	protected IItemCreator creator;
	protected ItemQuerier querier;
	public boolean addItem(T item){
		return creator.addItem(item);
	}
	public Set<T> getByOrderId(String id){
		return querier.getByOrderId(id);
	}
}
