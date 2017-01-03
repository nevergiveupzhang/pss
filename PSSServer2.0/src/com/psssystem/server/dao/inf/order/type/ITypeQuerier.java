package com.psssystem.server.dao.inf.order.type;

import java.util.Set;

import com.psssystem.connection.vo.LossOverflowOrderVO;

public interface ITypeQuerier {
	public Set<LossOverflowOrderVO> getNotPassedByType(String type);
	public Set<LossOverflowOrderVO> getByDate(String type, String startDate,
			String endDate);
	public Set<LossOverflowOrderVO> getAllByType(String type);
}
