package com.psssystem.server.serviceimpl.storageserviceimpl;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;


import com.psssystem.connection.service.storageservice.ICommodityService;
import com.psssystem.connection.vo.CategoryVO;
import com.psssystem.connection.vo.CommodityVO;
import com.psssystem.connection.vo.LossOverflowOrderVO;
import com.psssystem.connection.vo.ResultMessage;
import com.psssystem.server.dao.impl.base.CommodityDaoImpl;
import com.psssystem.server.dao.inf.base.ICommodityDao;

public class CommodityServiceImpl extends UnicastRemoteObject implements
		ICommodityService {
	private ICommodityDao commodityDao;
	private final String initFileName="resource/commodity.txt";
	public CommodityServiceImpl() throws RemoteException {
		super();
		commodityDao = new CommodityDaoImpl();
	}

	@Override
	public List<CommodityVO> getAllCommodities() throws RemoteException {
		return commodityDao.getAll();
	}
	
	@Override
	public boolean setWarningLines(List<CommodityVO> commVOList) throws RemoteException {
		return commodityDao.setWarningLines(commVOList);
	}
	

	@Override
	public boolean addCommodites(List<CommodityVO> voList) throws RemoteException {
		return commodityDao.addCommodites(voList);
	}

	@Override
	public boolean addCommodity(CommodityVO vo) throws RemoteException {
		if(!commodityDao.isInit())return false;
		int status= commodityDao.create(vo);
		if(status==ResultMessage.SUCCESS){
			return true;
		}else{
			return false;
		}
	}

	@Override
	public boolean deleteCommodites(List<CommodityVO> voList) throws RemoteException {
		return commodityDao.deleteCommodites(voList);
	}

	@Override
	public boolean deleteCommodity(CommodityVO vo) throws RemoteException {
		return commodityDao.delete(vo);
	}


	@Override
	public List<CommodityVO> searchCommodity(String info)
			throws RemoteException {
		return commodityDao.searchCommodity(info);
	}

	
	@Override
	public boolean updateCommodites(List<CommodityVO> voList) throws RemoteException {
		return commodityDao.updateCommodites(voList);
	}

	@Override
	public boolean updateCommodity(CommodityVO vo) throws RemoteException {
		return commodityDao.update(vo);
		
	}

	public static void main(String []args){
		try {
			new CommodityServiceImpl().searchCommodity("就");
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean initCommodity(CommodityVO commodity) throws RemoteException {
		commodityDao.create(commodity);
		FileWriter writer = null;
		try {
			writer = new FileWriter(initFileName, true);
			for (CommodityVO vo:getAllCommodities()) {
				writer.write(vo.getId() + "|"+vo.getName()+"|"+vo.getCategoryID()+"|"+vo.getType()+"|"+vo.getStockAmount()+"|"+vo.getPurchasingPrice()+"|"+vo.getSellingPrice()+"|"+vo.getRecentPurchasingPrice()+"|"+vo.getRecentSellingPrice()+"|"+vo.getWarningLine()+"|"+vo.getCreatedDate()+"\n");
			}
			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public boolean isCommodityExists(CommodityVO comm) throws RemoteException {
		return commodityDao.isNamedAndIDExists(comm.getId(), comm.getName());
	}
}
