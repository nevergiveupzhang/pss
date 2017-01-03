package com.psssystem.connection.service.mainservice;

import java.rmi.Remote;
import java.rmi.RemoteException;

import com.psssystem.connection.vo.UserVO;

public interface ILoginService extends Remote{
	public boolean login(UserVO user) throws RemoteException;
}
