package com.psssystem.connection.service.adminservice;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import com.psssystem.connection.vo.ResultMessage;
import com.psssystem.connection.vo.UserVO;

public interface IUserService  extends Remote {
	public int createUser(UserVO userVO) throws RemoteException;
	public List<UserVO> getUsers(String userType) throws RemoteException;
	public boolean deleteUser(UserVO userVO) throws RemoteException;
	public ResultMessage updateUsers(List<UserVO> userList) throws RemoteException;
}
