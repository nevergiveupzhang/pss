package com.psssystem.server.startup;

import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.util.*;

import javax.swing.JFrame;

import com.psssystem.connection.service.adminservice.IUserService;
import com.psssystem.connection.service.storageservice.ICommodityService;
import com.psssystem.connection.vo.CommodityVO;
import com.psssystem.connection.vo.UserVO;
import com.psssystem.server.serviceimpl.adminserviceimpl.UserServiceImpl;
import com.psssystem.server.serviceimpl.storageserviceimpl.CommodityServiceImpl;
import com.psssystem.server.ui.ServerFrame;
import com.psssystem.server.util.RegisterServer;

public class Main {
	public static void main(String[] args) {
		// JFrame frame=new ServerFrame();
		RegisterServer rs = new RegisterServer();
		rs.registryServer();
	}
}
