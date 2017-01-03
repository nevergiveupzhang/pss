package com.psssystem.connection.service.storageservice;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import com.psssystem.connection.service.base.IBaseService;
import com.psssystem.connection.vo.GiftOrderVO;

public interface IGiftOrderService extends Remote,IBaseService<GiftOrderVO>{
	public boolean addGiftOrder(List<GiftOrderVO> giftList) throws RemoteException;
}
