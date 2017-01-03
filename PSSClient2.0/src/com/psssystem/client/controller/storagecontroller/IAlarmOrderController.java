package com.psssystem.client.controller.storagecontroller;

import java.util.List;
import java.util.Set;

import com.psssystem.client.controller.base.IBaseController;
import com.psssystem.connection.vo.AlarmOrderVO;

public interface IAlarmOrderController extends IBaseController<AlarmOrderVO>{
	public boolean addOrder(List<AlarmOrderVO> orderList);
}
