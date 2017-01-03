package com.psssystem.client.controller.financecontroller;

import java.util.Set;

import com.psssystem.connection.vo.SalesDetailVO;

public interface ISalesDetailController {
	public Set<SalesDetailVO> getSalesDetail(String [] condition);
}
