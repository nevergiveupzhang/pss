package com.psssystem.client.controller.salescontroller;

import java.util.List;
import java.util.Set;

import com.psssystem.client.controller.base.ITypeController;
import com.psssystem.connection.vo.SalesOrderVO;

public interface ISalesOrderController extends ITypeController<SalesOrderVO> {
	boolean addSalesOrder(SalesOrderVO salesOrderVO);
	boolean addSalesReturnOrder(SalesOrderVO salesOrderVO);
}
