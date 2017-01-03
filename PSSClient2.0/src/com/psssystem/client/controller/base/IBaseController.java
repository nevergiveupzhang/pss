package com.psssystem.client.controller.base;

import java.util.List;
import java.util.Set;

import com.psssystem.connection.vo.AlarmOrderVO;

public interface IBaseController<T> {
	public Set<T> getAll();
	public Set<T> getNotPassed();
	public boolean approve(Set<T> orders);
}
