package com.psssystem.client.util;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import com.psssystem.connection.service.adminservice.IUserService;
import com.psssystem.connection.service.financeservice.IAccountService;
import com.psssystem.connection.service.financeservice.IManageProcessService;
import com.psssystem.connection.service.financeservice.IManageSituationService;
import com.psssystem.connection.service.financeservice.IPaymentOrderService;
import com.psssystem.connection.service.financeservice.IReceiptsOrderService;
import com.psssystem.connection.service.financeservice.ISalesDetailService;
import com.psssystem.connection.service.mainservice.ILoginService;
import com.psssystem.connection.service.salesservice.ICustomerService;
import com.psssystem.connection.service.salesservice.IPurchasingOrderService;
import com.psssystem.connection.service.salesservice.ISalesOrderService;
import com.psssystem.connection.service.storageservice.IAlarmOrderService;
import com.psssystem.connection.service.storageservice.ICategoryService;
import com.psssystem.connection.service.storageservice.ICommodityService;
import com.psssystem.connection.service.storageservice.IGiftOrderService;
import com.psssystem.connection.service.storageservice.IInventoryService;
import com.psssystem.connection.service.storageservice.ILossOverflowService;
import com.psssystem.connection.service.storageservice.IStorageService;


public class ConnectToServer {
	private static String url="rmi://192.168.1.103:8800/";
	
	public static IUserService userService;
	public static ICategoryService categoryService;
	public static ILossOverflowService lossOverflowService;
	public static ICommodityService commodityService;
	public static IAlarmOrderService alarmOrderService;
	public static IStorageService storageService;
	public static IInventoryService inventoryService;
	public static IGiftOrderService giftOrderService;

	public static ICustomerService customerService;

	public static ILoginService loginService;

	public static IPurchasingOrderService purchasingOrderService;

	public static ISalesOrderService salesOrderService;

	public static IAccountService accountService;

	public static IReceiptsOrderService receiptsOrderService;

	public static IPaymentOrderService paymentOrderService;

	public static ISalesDetailService salesDetailService;
	
	public static IManageProcessService manageProcessService;

	public static IManageSituationService manageSituationService;
	public static String connect(){
		try {
			loginService=(ILoginService) Naming.lookup(url+"LGS");
			userService=(IUserService) Naming.lookup(url+"US");
			categoryService= (ICategoryService) Naming.lookup(url+"CGS");
			lossOverflowService=(ILossOverflowService) Naming.lookup(url+"LOS");
			commodityService= (ICommodityService) Naming.lookup(url+"CMS");
			alarmOrderService=(IAlarmOrderService) Naming.lookup(url+"AOS");
			storageService=(IStorageService)Naming.lookup(url+"SS");
			inventoryService=(IInventoryService) Naming.lookup(url+"IS");
			giftOrderService=(IGiftOrderService) Naming.lookup(url+"GS");
			customerService=(ICustomerService) Naming.lookup(url+"CTS");
			purchasingOrderService=(IPurchasingOrderService) Naming.lookup(url+"POS");
			salesOrderService=(ISalesOrderService) Naming.lookup(url+"SOS");
			accountService=(IAccountService) Naming.lookup(url+"AS");
			receiptsOrderService=(IReceiptsOrderService) Naming.lookup(url+"ROS");
			paymentOrderService=(IPaymentOrderService) Naming.lookup(url+"PYOS");
			salesDetailService=(ISalesDetailService) Naming.lookup(url+"SDS");
			manageProcessService=(IManageProcessService) Naming.lookup(url+"MPS");
			manageSituationService=(IManageSituationService) Naming.lookup(url+"MSS");
		} catch (MalformedURLException | RemoteException | NotBoundException e) {
			e.printStackTrace();
		}
		return null;
	}
}
