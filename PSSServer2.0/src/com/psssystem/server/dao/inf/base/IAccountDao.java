package com.psssystem.server.dao.inf.base;

import java.util.List;

import com.psssystem.connection.vo.AccountVO;


public interface IAccountDao extends IBaseDao<AccountVO>{
	public boolean updateAccountSum(AccountVO account);
	public List<AccountVO> searchAccount(String info);
}
