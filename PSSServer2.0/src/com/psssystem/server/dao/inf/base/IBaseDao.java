package com.psssystem.server.dao.inf.base;

import java.util.List;

import com.psssystem.connection.vo.CustomerVO;

public interface IBaseDao<T> {
	public int create(T order);
	public boolean delete(T order);
	public boolean update(T order);
	public List<T> getAll();
}
