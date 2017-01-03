package com.psssystem.server.serviceimpl.financeserviceimpl;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;


import com.psssystem.connection.service.financeservice.IAccountService;
import com.psssystem.connection.vo.AccountVO;
import com.psssystem.connection.vo.ResultMessage;
import com.psssystem.server.dao.impl.base.AccountDaoImpl;
import com.psssystem.server.dao.inf.base.IAccountDao;

public class AccountServiceImpl extends UnicastRemoteObject implements IAccountService {
	private IAccountDao accountDao;
	private final String initFileName="resource/account.txt";
	public AccountServiceImpl() throws RemoteException {
		super();
		this.accountDao=new AccountDaoImpl();
	}

	@Override
	public boolean addAccount(AccountVO vo) throws RemoteException {
		int status=accountDao.create(vo);
		if(status==ResultMessage.SUCCESS){
			return true;
		}else{
			return false;
		}
	}

	@Override
	public boolean deleteAccount(AccountVO vo) throws RemoteException {
		return accountDao.delete(vo);
	}


	@Override
	public List<AccountVO> searchAccount(String info) throws RemoteException {
		return accountDao.searchAccount(info);
	}



	@Override
	public boolean updateAccountSum(AccountVO vo) throws RemoteException {
		return accountDao.updateAccountSum(vo);
	}

	@Override
	public List<AccountVO> getAllAccounts() throws RemoteException {
		return accountDao.getAll();
	}

	@Override
	public boolean addAccounts(List<AccountVO> voList) throws RemoteException {
		int status;
		for(AccountVO po:voList){
			status=accountDao.create(po);
			if(status!=ResultMessage.SUCCESS){
				return false;
			}
		}
		return true;
	}


	@Override
	public boolean deleteAccounts(List<AccountVO> voList) throws RemoteException {
		boolean status=false;
		for(AccountVO po:voList){
			status=accountDao.delete(po);	
		}
		return status;
	}


	@Override
	public boolean initAccounts(List<AccountVO> accountList) throws RemoteException {
		if(accountDao.getAll().size()!=0)return false;
		addAccounts(accountList);
		
		FileWriter writer=null;
		try {
			writer=new FileWriter(initFileName,true);
			for(AccountVO vo:getAllAccounts()){
				writer.write(vo.getName()+"|"+vo.getSum()+"\n");
			}
			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	
	public static void main(String []args){
		try {
			List<AccountVO> a=new ArrayList<AccountVO>();
			a.add(new AccountVO("HHH", 10));
			new AccountServiceImpl().initAccounts(a);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

}
