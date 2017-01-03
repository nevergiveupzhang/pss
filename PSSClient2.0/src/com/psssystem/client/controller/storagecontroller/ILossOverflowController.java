package com.psssystem.client.controller.storagecontroller;

import java.util.List;
import java.util.Set;

import com.psssystem.client.controller.base.IBaseController;
import com.psssystem.client.controller.base.ITypeController;
import com.psssystem.connection.vo.LossOverflowOrderVO;

public interface ILossOverflowController extends ITypeController<LossOverflowOrderVO>{
	public boolean addOrder(List<LossOverflowOrderVO> orderList);
}
