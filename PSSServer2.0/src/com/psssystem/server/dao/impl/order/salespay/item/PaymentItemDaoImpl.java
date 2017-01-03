package com.psssystem.server.dao.impl.order.salespay.item;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import com.psssystem.connection.vo.AccountTransferVO;
import com.psssystem.connection.vo.PaymentItemVO;
import com.psssystem.server.dao.factory.impl.PaymentItemDaoFactoryImpl;
import com.psssystem.server.dao.factory.inf.ItemFactory;
import com.psssystem.server.dao.inf.order.general.salespay.item.IItemCreator;
import com.psssystem.server.dao.inf.order.general.salespay.item.ItemDao;
import com.psssystem.server.dao.inf.order.general.salespay.item.ItemQuerier;
import com.psssystem.server.data.OperationInfo;
import com.psssystem.server.util.DBUtils;

public class PaymentItemDaoImpl extends ItemDao<PaymentItemVO>{
	private static PaymentItemDaoImpl INSTANCE=new PaymentItemDaoImpl();
	private ItemFactory factory=new PaymentItemDaoFactoryImpl();
	public PaymentItemDaoImpl(){
		this.creator=factory.getCreator();
		this.querier=factory.getQuerier();
	}
	
	public static ItemDao getInstance() {
		return INSTANCE;
	}
}
