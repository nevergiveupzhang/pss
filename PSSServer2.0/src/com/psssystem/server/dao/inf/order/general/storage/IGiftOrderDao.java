package com.psssystem.server.dao.inf.order.general.storage;

import java.util.List;
import java.util.Set;

import com.psssystem.connection.vo.AlarmOrderVO;
import com.psssystem.connection.vo.GiftOrderVO;
import com.psssystem.server.dao.inf.order.general.IOrderQuerier;

public interface IGiftOrderDao extends IOrderQuerier<GiftOrderVO>,IOrderPasser<GiftOrderVO>{
	public boolean addGiftOrder(List<GiftOrderVO> poList);
	public Set<GiftOrderVO> getPassedByDate(String startDate,String endDate);
}
