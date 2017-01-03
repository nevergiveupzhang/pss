package com.psssystem.client.controllerimpl.financecontrollerimpl;

import java.rmi.RemoteException;
import java.util.List;

import com.psssystem.client.controller.financecontroller.IAccountController;
import com.psssystem.client.util.ConnectToServer;
import com.psssystem.connection.service.financeservice.IAccountService;
import com.psssystem.connection.vo.AccountVO;

public class AccountControllerImpl implements IAccountController {
	private IAccountService accountService;
	public AccountControllerImpl(){
		ConnectToServer.connect();
		this.accountService=ConnectToServer.accountService;
	}
	@Override
	public void addAccount(AccountVO account) {
		
	}

	@Override
	public void deleteAccount(AccountVO account) {
		
	}


	@Override
	public void updateAccountSum(AccountVO account) {
		
	}

	@Override
	public List<AccountVO> searchAccount(String info) {
		List<AccountVO> accountList=null;
		try {
			accountList=accountService.searchAccount(info);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return accountList;
	}
	@Override
	public List<AccountVO> getAllAccounts() {
		List<AccountVO> accountList=null;
		try {
			accountList=accountService.getAllAccounts();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return accountList;
	}
	@Override
	public boolean addAccounts(List<AccountVO> createList) {
		try {
			return accountService.addAccounts(createList);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return false;
	}
	@Override
	public boolean deleteAccounts(List<AccountVO> deleteList) {
		try {
			return accountService.deleteAccounts(deleteList);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return false;
		
	}
	@Override
	public void initAccounts(List<AccountVO> createList) {
		System.out.println(createList.size());
		try {
			accountService.initAccounts(createList);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

}
