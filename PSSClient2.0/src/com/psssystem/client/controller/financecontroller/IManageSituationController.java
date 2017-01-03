package com.psssystem.client.controller.financecontroller;

import com.psssystem.connection.vo.BalanceVO;

public interface IManageSituationController {
	public BalanceVO getManageSituationByDate(String startDate,String endDate);
}
