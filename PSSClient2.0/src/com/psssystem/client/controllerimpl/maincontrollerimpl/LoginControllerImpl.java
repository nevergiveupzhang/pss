package com.psssystem.client.controllerimpl.maincontrollerimpl;

import java.rmi.RemoteException;

import com.psssystem.client.controller.maincontroller.ILoginController;
import com.psssystem.client.util.ConnectToServer;
import com.psssystem.connection.service.mainservice.ILoginService;
import com.psssystem.connection.vo.UserVO;

public class LoginControllerImpl implements ILoginController {
	private ILoginService loginService;
	public LoginControllerImpl(){
		ConnectToServer.connect();
		this.loginService=ConnectToServer.loginService;
	}
	@Override
	public boolean login(UserVO user) {
		boolean success=false;
		try {
			success=loginService.login(user);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return success;
	}

}
