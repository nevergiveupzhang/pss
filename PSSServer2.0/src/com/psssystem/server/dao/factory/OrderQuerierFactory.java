package com.psssystem.server.dao.factory;

import com.psssystem.server.dao.impl.order.salespay.PaymentOrderDaoImpl;
import com.psssystem.server.dao.impl.order.salespay.PurchasingOrderDaoImpl;
import com.psssystem.server.dao.impl.order.salespay.PurchasingReturnOrderDaoImpl;
import com.psssystem.server.dao.impl.order.salespay.ReceiptsOrderDaoImpl;
import com.psssystem.server.dao.impl.order.salespay.SalesOrderDaoImpl;
import com.psssystem.server.dao.impl.order.salespay.SalesReturnOrderDaoImpl;
import com.psssystem.server.dao.impl.order.storage.AlarmOrderDaoImpl;
import com.psssystem.server.dao.impl.order.storage.GiftOrderDaoImpl;
import com.psssystem.server.dao.impl.order.storage.LossOverflowOrderDaoImpl;
import com.psssystem.server.dao.inf.order.general.IOrderQuerier;
import com.psssystem.server.dao.inf.order.general.salespay.querier.PaymentReceiptsQuerierTemplate;
import com.psssystem.server.dao.inf.order.general.salespay.querier.PurchasingSalesQuerierTemplate;
import com.psssystem.server.dao.inf.order.type.ITypeQuerier;

public class OrderQuerierFactory {
	public static PurchasingSalesQuerierTemplate getPurchasingQuerier(){
		return new PurchasingOrderDaoImpl();
	}
	
	public static PurchasingSalesQuerierTemplate getPurchasingReturnQuerier(){
		return new PurchasingReturnOrderDaoImpl();
	}
	
	public static PurchasingSalesQuerierTemplate getSalesReturnQuerier(){
		return new SalesReturnOrderDaoImpl();
	}
	
	public static PurchasingSalesQuerierTemplate getSalesQuerier(){
		return new SalesOrderDaoImpl();
	}
	
	public static IOrderQuerier getAlarmQuerier(){
		return new AlarmOrderDaoImpl();
	}
	
	public static IOrderQuerier getGiftQuerier(){
		return new GiftOrderDaoImpl();
	}
	
	public static PaymentReceiptsQuerierTemplate getPaymentQuerier(){
		return new PaymentOrderDaoImpl();
	}
	public static PaymentReceiptsQuerierTemplate getReceiptsQuerier(){
		return new ReceiptsOrderDaoImpl();
	}
	
	public static ITypeQuerier getLossOverflowQuerier(){
		return new LossOverflowOrderDaoImpl();
	}
	
}
