package com.psssystem.client.controllerimpl.admincontrollerimpl;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.List;

import com.psssystem.client.controller.admincontroller.IUserController;
import com.psssystem.client.util.ConnectToServer;
import com.psssystem.connection.service.adminservice.IUserService;
import com.psssystem.connection.vo.ResultMessage;
import com.psssystem.connection.vo.UserVO;

public class UserControllerImpl implements IUserController{
	private IUserService userService;
	public UserControllerImpl(){
		ConnectToServer.connect();
		userService=ConnectToServer.userService;
	}
	
	@Override
	public int createUser(UserVO userVO) {
		int status = 0;
		try {
			status=userService.createUser(userVO);
		} catch (RemoteException e) {
			e.printStackTrace();
		}		
		return status;
	}

	@Override
	public List<UserVO> getUsers(String userType) {
		List<UserVO> users=null;
		try {
			users=userService.getUsers(userType);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return users;
	}

	@Override
	public boolean deleteUser(UserVO userVO) {
		boolean status=false;
		try {
			status=userService.deleteUser(userVO);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return status;
	}

	@Override
	public ResultMessage updateUsers(List<UserVO> userList) {
		ResultMessage rm=null;
		try {
			rm=userService.updateUsers(userList);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return rm;
	}

}
