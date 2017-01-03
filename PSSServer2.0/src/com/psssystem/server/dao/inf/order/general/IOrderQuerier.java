package com.psssystem.server.dao.inf.order.general;

import java.util.List;
import java.util.Set;

import com.psssystem.connection.vo.PurchasingOrderVO;

public interface IOrderQuerier<T>{
	public Set<T> getByDate(String startDate,String endDate);
	public Set<T> getNotPassed();
	public Set<T> getAll();
}
