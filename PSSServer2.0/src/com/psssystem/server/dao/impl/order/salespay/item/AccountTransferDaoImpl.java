package com.psssystem.server.dao.impl.order.salespay.item;

import com.psssystem.connection.vo.AccountTransferVO;
import com.psssystem.server.dao.factory.impl.AccountTransferDaoFactoryImpl;
import com.psssystem.server.dao.factory.inf.ItemFactory;
import com.psssystem.server.dao.inf.order.general.salespay.item.ItemDao;

public class AccountTransferDaoImpl extends ItemDao<AccountTransferVO> {
	private static AccountTransferDaoImpl INSTANCE=new AccountTransferDaoImpl();
	private ItemFactory factory=new AccountTransferDaoFactoryImpl(); 
	public AccountTransferDaoImpl(){
		this.creator=factory.getCreator();
		this.querier=factory.getQuerier();
	}

	public static ItemDao getInstance() {
		return INSTANCE;
	}

}
