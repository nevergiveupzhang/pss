package com.psssystem.client.controllerimpl.storagecontrollerimpl;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import com.psssystem.client.controller.storagecontroller.ICommodityController;
import com.psssystem.client.util.ConnectToServer;
import com.psssystem.connection.service.storageservice.ICommodityService;
import com.psssystem.connection.vo.CommodityVO;

public class CommodityControllerImpl implements ICommodityController {
	private ICommodityService commodityService;
	public CommodityControllerImpl(){
		ConnectToServer.connect();
		commodityService=ConnectToServer.commodityService;
	}
	@Override
	public List<CommodityVO> getAllCommodities() {
		List<CommodityVO> commList=null;
		try {
			commList=commodityService.getAllCommodities();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return commList;
	}
	@Override
	public boolean setWarningLines(List<CommodityVO> commList) {
		try {
			return commodityService.setWarningLines(commList);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return false;
	}
	@Override
	public boolean addCommodity(CommodityVO commodity) {
		boolean status=false;
		try {
			status=commodityService.addCommodity(commodity);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return status;
	}
	@Override
	public boolean deleteCommodity(CommodityVO commodity) {
		boolean status=false;
		try {
			status=commodityService.deleteCommodity(commodity);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return status;
	}
	@Override
	public boolean updateCommodity(CommodityVO commodity) {
		boolean status=false;
		try {
			status=commodityService.updateCommodity(commodity);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return status;
	}
	@Override
	public List<CommodityVO> searchCommodity(String info) {
		List<CommodityVO> result=null;
		try {
			result=commodityService.searchCommodity(info);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	@Override
	public boolean isCommodityIDAndNameExists(CommodityVO commodity) {
		try {
			return commodityService.isCommodityExists(commodity);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return false;
	}
	@Override
	public boolean initCommodity(CommodityVO commodity) {
		boolean status=false;
		try {
			status=commodityService.initCommodity(commodity);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return status;
	}

}
