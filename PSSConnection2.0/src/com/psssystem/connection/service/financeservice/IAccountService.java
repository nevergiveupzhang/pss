package com.psssystem.connection.service.financeservice;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import com.psssystem.connection.vo.AccountVO;


public interface IAccountService extends  Remote{
	public boolean addAccount(AccountVO account)throws RemoteException;
	public boolean addAccounts(List<AccountVO> createList)throws RemoteException;
	public boolean initAccounts(List<AccountVO> createList)throws RemoteException;
	public boolean deleteAccounts(List<AccountVO> deleteList)throws RemoteException;
	public boolean deleteAccount(AccountVO account)throws RemoteException;
	public boolean updateAccountSum(AccountVO account)throws RemoteException;
	public List<AccountVO> searchAccount(String info)throws RemoteException;
	public List<AccountVO> getAllAccounts()throws RemoteException;
	
}
