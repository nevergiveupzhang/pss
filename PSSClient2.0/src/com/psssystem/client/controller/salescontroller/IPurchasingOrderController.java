package com.psssystem.client.controller.salescontroller;

import java.util.List;
import java.util.Set;

import com.psssystem.client.controller.base.IBaseController;
import com.psssystem.client.controller.base.ITypeController;
import com.psssystem.connection.vo.PurchasingOrderVO;
import com.psssystem.connection.vo.SalesOrderVO;

public interface IPurchasingOrderController extends ITypeController<PurchasingOrderVO>{
	public boolean addPurchasingOrder(PurchasingOrderVO order);
	public boolean addPurchasingReturnOrder(PurchasingOrderVO order);
}
