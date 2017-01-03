package com.psssystem.server.dao.impl.order.salespay.item;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.psssystem.connection.vo.CommodityItemVO;
import com.psssystem.server.dao.factory.impl.StorageOutReturnItemDaoFactoryImpl;
import com.psssystem.server.dao.factory.inf.ItemFactory;
import com.psssystem.server.dao.inf.order.general.salespay.item.CommodityItemCreator;
import com.psssystem.server.dao.inf.order.general.salespay.item.IItemCreator;
import com.psssystem.server.dao.inf.order.general.salespay.item.ItemDao;
import com.psssystem.server.data.OperationInfo;

public class StorageOutReturnItemDaoImpl extends ItemDao<CommodityItemVO>{
	private static ItemDao INSTANCE=new StorageOutReturnItemDaoImpl();
	private ItemFactory factory=new StorageOutReturnItemDaoFactoryImpl(); 
	public StorageOutReturnItemDaoImpl(){
		this.creator=factory.getCreator();
		this.querier=factory.getQuerier();
	}
	
	public static ItemDao getInstance(){
		return INSTANCE;
	}

}
