package com.psssystem.server.serviceimpl.adminserviceimpl;

import java.lang.reflect.InvocationTargetException;
import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;
import java.sql.*;
import java.util.*;

import com.psssystem.connection.service.adminservice.IUserService;
import com.psssystem.connection.vo.ResultMessage;
import com.psssystem.connection.vo.UserVO;
import com.psssystem.server.dao.impl.base.UserDaoImpl;
import com.psssystem.server.dao.inf.base.IUserDao;
import com.psssystem.server.data.IdentityInfoConstants;
import com.psssystem.server.util.DBUtils;

public class UserServiceImpl extends UnicastRemoteObject implements
		IUserService {
	private IUserDao userDao;
	public UserServiceImpl() throws RemoteException {
		userDao=new UserDaoImpl();
	}

	@Override
	public int createUser(UserVO userVO) throws RemoteException{
		return userDao.create(userVO);
	}

	@Override
	public boolean deleteUser(UserVO userVO) throws RemoteException {
		return userDao.delete(userVO);
		
	}

	@Override
	public List<UserVO> getUsers(String userType) throws RemoteException {
		return userDao.getUsersByType(userType);
	}


	@Override
	public ResultMessage updateUsers(List<UserVO> usersVO) throws RemoteException {
		return userDao.update(usersVO);
	}
}
