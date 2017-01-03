package com.psssystem.server.dao.inf.order.general.salespay.item;

import java.sql.Connection;
import java.sql.PreparedStatement;

import com.psssystem.connection.vo.CommodityItemVO;
import com.psssystem.server.util.DBUtils;

public interface IItemCreator <T>{
	public boolean addItem(T item);
}
