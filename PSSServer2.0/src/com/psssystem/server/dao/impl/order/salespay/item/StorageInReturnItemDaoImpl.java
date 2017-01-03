package com.psssystem.server.dao.impl.order.salespay.item;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import com.psssystem.connection.vo.CommodityItemVO;
import com.psssystem.server.dao.factory.impl.StorageInReturnItemDaoFactoryImpl;
import com.psssystem.server.dao.factory.inf.ItemFactory;
import com.psssystem.server.dao.inf.order.general.salespay.item.CommodityItemCreator;
import com.psssystem.server.dao.inf.order.general.salespay.item.IItemCreator;
import com.psssystem.server.dao.inf.order.general.salespay.item.ItemDao;
import com.psssystem.server.data.OperationInfo;

public class StorageInReturnItemDaoImpl extends ItemDao<CommodityItemVO>{
	private static StorageInReturnItemDaoImpl INSTANCE=new StorageInReturnItemDaoImpl();
	private ItemFactory factory=new StorageInReturnItemDaoFactoryImpl(); 
	public StorageInReturnItemDaoImpl(){
		this.creator=factory.getCreator();
		this.querier=factory.getQuerier();
	}
	
	public static ItemDao getInstance(){
		return INSTANCE;
	}

}
