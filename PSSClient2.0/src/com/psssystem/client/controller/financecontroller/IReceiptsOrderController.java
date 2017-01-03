package com.psssystem.client.controller.financecontroller;

import com.psssystem.client.controller.base.IBaseController;
import com.psssystem.connection.vo.ReceiptsOrderVO;

public interface IReceiptsOrderController extends IBaseController<ReceiptsOrderVO>{
	public boolean createReceipts(ReceiptsOrderVO order);
}
