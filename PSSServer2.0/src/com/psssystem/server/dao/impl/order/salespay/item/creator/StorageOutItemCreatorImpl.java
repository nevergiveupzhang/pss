package com.psssystem.server.dao.impl.order.salespay.item.creator;

import com.psssystem.connection.vo.CommodityItemVO;
import com.psssystem.server.dao.inf.order.general.salespay.item.CommodityItemCreator;
import com.psssystem.server.dao.inf.order.general.salespay.item.IItemCreator;

public class StorageOutItemCreatorImpl extends CommodityItemCreator<CommodityItemVO> {
	private static StorageOutItemCreatorImpl INSTANCE=new StorageOutItemCreatorImpl(); 
	public StorageOutItemCreatorImpl(){}
	private final String insertSQL="insert into storageoutlist(sales_id,comm_id,amount,price,sum,remarks,createddate) values(?,?,?,?,?,?,?)";
	
	@Override
	protected String getSQL() {
		return insertSQL;
	}

	public static IItemCreator getInstance() {
		return null;
	}

}
