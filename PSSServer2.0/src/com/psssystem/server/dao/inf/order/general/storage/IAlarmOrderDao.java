package com.psssystem.server.dao.inf.order.general.storage;

import java.util.List;
import java.util.Set;

import com.psssystem.connection.vo.AlarmOrderVO;
import com.psssystem.server.dao.inf.order.general.IOrderQuerier;

public interface IAlarmOrderDao extends IOrderQuerier<AlarmOrderVO>,IOrderPasser<AlarmOrderVO>{
	public boolean addOrders(List<AlarmOrderVO> orderVOList);
}
