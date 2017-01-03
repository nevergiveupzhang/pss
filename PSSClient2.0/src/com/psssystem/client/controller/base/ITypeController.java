package com.psssystem.client.controller.base;

import java.util.Set;

import com.psssystem.connection.vo.PurchasingOrderVO;

public interface ITypeController <T>{
	public Set<T> getNotPassedByType(String type);
	public boolean approveByType(Set<T> orders,String type);
	public Set<T> getAllByType(String type);
}
