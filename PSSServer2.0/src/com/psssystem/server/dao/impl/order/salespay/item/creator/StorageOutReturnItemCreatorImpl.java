package com.psssystem.server.dao.impl.order.salespay.item.creator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.psssystem.connection.vo.AccountTransferVO;
import com.psssystem.connection.vo.CommodityItemVO;
import com.psssystem.server.dao.inf.order.general.salespay.item.CommodityItemCreator;
import com.psssystem.server.dao.inf.order.general.salespay.item.IItemCreator;
import com.psssystem.server.util.DBUtils;

public class StorageOutReturnItemCreatorImpl extends CommodityItemCreator<CommodityItemVO> {
	private static StorageOutReturnItemCreatorImpl INSTANCE=new StorageOutReturnItemCreatorImpl();
	public StorageOutReturnItemCreatorImpl(){}
	private final String insertSQL="insert into storageoutreturnlist(salesreturn_id,comm_id,amount,price,sum,remarks,createddate) values(?,?,?,?,?,?,?)";
	
	@Override
	protected String getSQL() {
		return insertSQL;
	}

	public static IItemCreator getInstance() {
		return INSTANCE;
	}

}
