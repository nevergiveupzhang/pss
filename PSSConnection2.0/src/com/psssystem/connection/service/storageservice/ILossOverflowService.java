package com.psssystem.connection.service.storageservice;

import java.rmi.*;
import java.util.List;
import java.util.Set;

import com.psssystem.connection.service.base.ITypeService;
import com.psssystem.connection.vo.LossOverflowOrderVO;

public interface ILossOverflowService extends Remote,ITypeService<LossOverflowOrderVO>{
	public boolean addLossOverflowOrder(List<LossOverflowOrderVO> orderList) throws RemoteException;
}
