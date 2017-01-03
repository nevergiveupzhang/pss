package com.psssystem.server.serviceimpl.storageserviceimpl;

import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;



import java.util.logging.Logger;

import com.psssystem.connection.service.storageservice.IAlarmOrderService;
import com.psssystem.connection.vo.AlarmOrderVO;
import com.psssystem.server.dao.impl.order.storage.AlarmOrderDaoImpl;
import com.psssystem.server.dao.inf.order.general.storage.IAlarmOrderDao;
import com.psssystem.server.util.LogUtils;

public class AlarmOrderSerivceImpl extends UnicastRemoteObject implements IAlarmOrderService {
	private IAlarmOrderDao alarmOrderDao;
	public AlarmOrderSerivceImpl() throws RemoteException{
		this.alarmOrderDao=new AlarmOrderDaoImpl();
	}
	@Override
	public boolean addOrder(List<AlarmOrderVO> orderVOList) throws RemoteException {
		Logger logger=Logger.getLogger("com.psssystem.server.AlarmOrderSerivceImpl");
		LogUtils.setLogProperties(logger,AlarmOrderSerivceImpl.class.getName());
		logger.entering("AlarmOrderSerivceImpl", "before addOrder");
		boolean flag=alarmOrderDao.addOrders(orderVOList);
		logger.exiting("AlarmOrderSerivceImpl", "after addOrder,and the result status is "+flag);
		return flag;
	}
	@Override
	public Set<AlarmOrderVO> getAll() throws RemoteException {
		return alarmOrderDao.getAll();
	}
	@Override
	public boolean approve(Set<AlarmOrderVO> orders) throws RemoteException {
		return alarmOrderDao.passOrders(orders);
	}
	@Override
	public Set<AlarmOrderVO> getNotPassed() throws RemoteException {
		return alarmOrderDao.getNotPassed();
	}
	
	public static void main(String []args) {
		try {
			new AlarmOrderSerivceImpl().addOrder(null);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		
	}

}
