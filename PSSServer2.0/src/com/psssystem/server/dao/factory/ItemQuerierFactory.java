package com.psssystem.server.dao.factory;


import com.psssystem.server.dao.impl.order.salespay.item.querier.AccountTransferQuerierImpl;
import com.psssystem.server.dao.impl.order.salespay.item.querier.PaymentItemQuerierImpl;
import com.psssystem.server.dao.impl.order.salespay.item.querier.StorageInItemQuerierImpl;
import com.psssystem.server.dao.impl.order.salespay.item.querier.StorageInReturnItemQuerierImpl;
import com.psssystem.server.dao.impl.order.salespay.item.querier.StorageOutItemQuerierImpl;
import com.psssystem.server.dao.impl.order.salespay.item.querier.StorageOutReturnItemQuerierImpl;
import com.psssystem.server.dao.inf.order.general.salespay.item.ItemQuerier;

public class ItemQuerierFactory {
	public static ItemQuerier getStorageInItemQuerier(){
		return new StorageInItemQuerierImpl();
	}
	
	public static ItemQuerier getStorageOutItemQuerier(){
		return new StorageOutItemQuerierImpl();
	}
	public static ItemQuerier getStorageOutReturnItemQuerier(){
		return new StorageOutReturnItemQuerierImpl();
	}
	public static ItemQuerier getStorageInReturnItemQuerier(){
		return new StorageInReturnItemQuerierImpl();
	}
	public static ItemQuerier getPaymentItemQuerier(){
		return new PaymentItemQuerierImpl();
	}
	
	public static ItemQuerier getReceiptsItemQuerier(){
		return new AccountTransferQuerierImpl();
	}
	
	
}
