package com.psssystem.server.serviceimpl.mainserviceimpl;

import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;


import com.psssystem.connection.service.mainservice.ILoginService;
import com.psssystem.connection.vo.UserVO;
import com.psssystem.server.dao.impl.base.UserDaoImpl;
import com.psssystem.server.dao.inf.base.IUserDao;

public class LoginServiceImpl extends UnicastRemoteObject implements ILoginService {
	private IUserDao userDao;
	public LoginServiceImpl() throws RemoteException{
		userDao=new UserDaoImpl();
	}
	@Override
	public boolean login(UserVO vo) throws RemoteException {
		return userDao.login(vo);
	}

}
