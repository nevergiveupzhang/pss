package com.psssystem.client.controller.financecontroller;

import java.rmi.RemoteException;
import java.util.List;

import com.psssystem.connection.vo.AccountVO;

public interface IAccountController {
	public void addAccount(AccountVO account);
	public void deleteAccount(AccountVO account);
	public void updateAccountSum(AccountVO account);
	public List<AccountVO> searchAccount(String info);
	public List<AccountVO> getAllAccounts();
	public boolean addAccounts(List<AccountVO> createList);
	public void initAccounts(List<AccountVO> createList);
	public boolean deleteAccounts(List<AccountVO> deleteList);
}
