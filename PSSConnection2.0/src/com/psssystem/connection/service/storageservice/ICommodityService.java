package com.psssystem.connection.service.storageservice;

import java.rmi.*;
import java.util.List;

import javax.swing.tree.DefaultTreeModel;

import com.psssystem.connection.vo.CommodityVO;

public interface ICommodityService extends Remote{
	public List<CommodityVO> getAllCommodities() throws RemoteException;
	public boolean setWarningLines(List<CommodityVO> commList) throws RemoteException;
	
	public boolean addCommodites(List<CommodityVO> commList) throws RemoteException;
	
	public boolean deleteCommodites(List<CommodityVO> commList) throws RemoteException;
	public boolean updateCommodites(List<CommodityVO> commList) throws RemoteException;
	
	public boolean addCommodity(CommodityVO commodity) throws RemoteException;
	public boolean initCommodity(CommodityVO commodity) throws RemoteException;
	public boolean deleteCommodity(CommodityVO commodity) throws RemoteException;
	public boolean updateCommodity(CommodityVO commodity) throws RemoteException;
	
	public List<CommodityVO> searchCommodity(String info) throws RemoteException;
	
	public boolean isCommodityExists(CommodityVO commodity)throws RemoteException;
}
