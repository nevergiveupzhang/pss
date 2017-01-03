package com.psssystem.server.dao.impl.order.salespay.item;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.psssystem.connection.vo.CommodityItemVO;
import com.psssystem.server.dao.factory.impl.StorageOutItemDaoFactoryImpl;
import com.psssystem.server.dao.factory.inf.ItemFactory;
import com.psssystem.server.dao.inf.order.general.salespay.item.CommodityItemCreator;
import com.psssystem.server.dao.inf.order.general.salespay.item.IItemCreator;
import com.psssystem.server.dao.inf.order.general.salespay.item.ItemDao;
import com.psssystem.server.data.OperationInfo;

public class StorageOutItemDaoImpl extends ItemDao<CommodityItemVO>{
	private static StorageOutItemDaoImpl INSTANCE=new StorageOutItemDaoImpl();
	private ItemFactory factory=new StorageOutItemDaoFactoryImpl(); 
	public StorageOutItemDaoImpl(){
		this.creator=factory.getCreator();
		this.querier=factory.getQuerier();
	}
	
	public static ItemDao getInstance(){
		return INSTANCE;
	}

}
