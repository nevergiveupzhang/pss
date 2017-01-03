package com.psssystem.server.dao.inf.order.type;

import java.util.List;
import java.util.Set;

import com.psssystem.connection.vo.AlarmOrderVO;
import com.psssystem.connection.vo.LossOverflowOrderVO;

public interface ILossOverflowOrderDao extends ITypeQuerier{
	public boolean addOrder(List<LossOverflowOrderVO> orderList);
	public boolean passOrdersByType(Set<LossOverflowOrderVO> orders,String type);
	public Set<LossOverflowOrderVO> getPassedByDate(String type,String startDate,String endDate);
}
