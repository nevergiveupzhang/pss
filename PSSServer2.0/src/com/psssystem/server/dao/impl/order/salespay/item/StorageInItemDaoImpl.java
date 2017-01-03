package com.psssystem.server.dao.impl.order.salespay.item;

import com.psssystem.connection.vo.CommodityItemVO;
import com.psssystem.server.dao.factory.impl.StorageInItemDaoFactoryImpl;
import com.psssystem.server.dao.factory.inf.ItemFactory;
import com.psssystem.server.dao.inf.order.general.salespay.item.ItemDao;

public class StorageInItemDaoImpl extends  ItemDao<CommodityItemVO>{
	private static StorageInItemDaoImpl INSTANCE=new StorageInItemDaoImpl();
	private ItemFactory factory=new StorageInItemDaoFactoryImpl();
	public StorageInItemDaoImpl(){
		this.creator=factory.getCreator();
		this.querier=factory.getQuerier();
	}
	
	public static ItemDao getInstance() {
		return INSTANCE;
	}
}
