package com.psssystem.server.util;

import java.net.MalformedURLException;
import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import com.psssystem.connection.service.adminservice.*;
import com.psssystem.connection.service.financeservice.*;
import com.psssystem.connection.service.mainservice.*;
import com.psssystem.connection.service.salesservice.*;
import com.psssystem.connection.service.storageservice.*;
import com.psssystem.server.serviceimpl.adminserviceimpl.*;
import com.psssystem.server.serviceimpl.financeserviceimpl.*;
import com.psssystem.server.serviceimpl.mainserviceimpl.*;
import com.psssystem.server.serviceimpl.salesserviceimpl.*;
import com.psssystem.server.serviceimpl.storageserviceimpl.*;

 
public class RegisterServer {
	private static String url="rmi://192.168.1.103:8800/";
	public static void registryServer()  {
		  try {
			LocateRegistry.createRegistry(8800);
			ILoginService lgs=new LoginServiceImpl();
			Naming.bind(url+"LGS",lgs);
			IUserService us=new UserServiceImpl();
			Naming.bind(url+"US",us);
			ICategoryService cgs=new CategoryServiceImpl();
			Naming.bind(url+"CGS",cgs);
			ILossOverflowService los=new LossOverflowServiceImpl();
			Naming.bind(url+"LOS",los);
			ICommodityService cms=new CommodityServiceImpl();
			Naming.bind(url+"CMS",cms);
			IAlarmOrderService aos=new AlarmOrderSerivceImpl();
			Naming.bind(url+"AOS",aos);
			IStorageService ss=new StorageServiceImpl();
			Naming.bind(url+"SS",ss);
			IInventoryService is=new InventoryServiceImpl();
			Naming.bind(url+"IS",is);
			IGiftOrderService gs=new GiftOrderServiceImpl();
			Naming.bind(url+"GS",gs);
			ICustomerService cts=new CustomerServiceImpl();
			Naming.bind(url+"CTS",cts);
			IPurchasingOrderService pos=new PurchasingOrderServiceImpl();
			Naming.bind(url+"POS",pos);
			ISalesOrderService sos=new SalesOrderServiceImpl();
			Naming.bind(url+"SOS",sos);
			IAccountService as=new AccountServiceImpl();
			Naming.bind(url+"AS",as);
			IReceiptsOrderService ros=new ReceiptsOrderServiceImpl();
			Naming.bind(url+"ROS",ros);
			IPaymentOrderService pyos=new PaymentOrderServiceImpl();
			Naming.bind(url+"PYOS",pyos);
			ISalesDetailService sds=new SalesDetailServiceImpl();
			Naming.bind(url+"SDS",sds);
			IManageProcessService mps=new ManageProcessServiceImpl();
			Naming.bind(url+"MPS", mps);
			IManageSituationService mss=new ManageSituationServiceImpl();
			Naming.bind(url+"MSS", mss);
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (AlreadyBoundException e) {
			e.printStackTrace();
		}catch(MalformedURLException e){
			e.printStackTrace();
		}
	}
	
	public static void stopServer() {
		
	}

}
