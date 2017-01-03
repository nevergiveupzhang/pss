package com.psssystem.client.controller.financecontroller;

import com.psssystem.client.controller.base.IBaseController;
import com.psssystem.connection.vo.PaymentOrderVO;

public interface IPaymentOrderController extends IBaseController<PaymentOrderVO>{
	public boolean createOrder(PaymentOrderVO order);
}
