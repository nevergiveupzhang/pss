package com.psssystem.server.serviceimpl.storageserviceimpl;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.psssystem.connection.service.storageservice.IStorageService;
import com.psssystem.connection.vo.CommodityItemVO;
import com.psssystem.connection.vo.GiftOrderVO;
import com.psssystem.connection.vo.LossOverflowOrderVO;
import com.psssystem.connection.vo.StorageVO;
import com.psssystem.server.dao.factory.ItemQuerierFactory;
import com.psssystem.server.dao.impl.order.storage.GiftOrderDaoImpl;
import com.psssystem.server.dao.impl.order.storage.LossOverflowOrderDaoImpl;
import com.psssystem.server.dao.inf.order.general.salespay.item.ItemQuerier;
import com.psssystem.server.dao.inf.order.general.storage.IGiftOrderDao;
import com.psssystem.server.dao.inf.order.type.ILossOverflowOrderDao;
import com.psssystem.server.dao.inf.others.IStorageDao;
import com.psssystem.server.data.OperationInfo;
import com.psssystem.server.data.OrderInfo;

public class StorageServiceImpl extends UnicastRemoteObject implements IStorageService {
	private IStorageDao storageDao;
	private ItemQuerier storageInQuerier;
	private ItemQuerier storageOutQuerier;
	private ItemQuerier storageInReturnQuerier;
	private ItemQuerier storageOutReturnQuerier;
	private ILossOverflowOrderDao lossOverflowDao;
	private IGiftOrderDao giftDao;
	public StorageServiceImpl() throws RemoteException {
		super();
		storageInQuerier=ItemQuerierFactory.getStorageInItemQuerier();
		storageOutQuerier=ItemQuerierFactory.getStorageOutItemQuerier();
		storageInReturnQuerier=ItemQuerierFactory.getStorageInReturnItemQuerier();
		storageOutReturnQuerier=ItemQuerierFactory.getStorageOutReturnItemQuerier();
		lossOverflowDao=new LossOverflowOrderDaoImpl();
		giftDao=new GiftOrderDaoImpl();
	}

	@Override
	public Set<StorageVO> getStorageInfo(String startDate,String endDate,String type) throws RemoteException {
		switch(type){
		case OperationInfo.IMPORT_INFO:
			return getImportInfo( startDate, endDate);
		case OperationInfo.EXPORT_INFO:
			return getExportInfo(startDate, endDate);
		case OperationInfo.PURCHASING_INFO:
			return getPurchasingInfo( startDate, endDate);
		case OperationInfo.SALES_INFO:
			return getSaleInfo( startDate, endDate);
		}
		return null;
	}


	
	private Set<StorageVO> getSaleInfo(String startDate, String endDate) {
		Set<StorageVO> salesInfo=new HashSet<StorageVO>();
		Iterator<CommodityItemVO> cItr=storageOutQuerier.getPassedByDate(startDate, endDate).iterator();
		while(cItr.hasNext()){
//			int commID, String commName, int amount, int sum,
//			String date, String type
			CommodityItemVO c=cItr.next();
			salesInfo.add(new StorageVO(c.getCommID(),c.getCommName(),c.getAmount(),c.getSum(),c.getCreatedDate(),OrderInfo.SALES));
		}
		return salesInfo;
	}

	private Set<StorageVO> getPurchasingInfo(String startDate, String endDate) {
		Set<StorageVO> purchasingInfo=new HashSet<StorageVO>();
		Iterator<CommodityItemVO> cItr=storageInQuerier.getPassedByDate(startDate, endDate).iterator();
		while(cItr.hasNext()){
//			int commID, String commName, int amount, int sum,
//			String date, String type
			CommodityItemVO c=cItr.next();
			purchasingInfo.add(new StorageVO(c.getCommID(),c.getCommName(),c.getAmount(),c.getSum(),c.getCreatedDate(),OrderInfo.PURCHASING));
		}
		return purchasingInfo;
	}

	private Set<StorageVO> getExportInfo(String startDate, String endDate) {
		Set<StorageVO> exportInfo=new HashSet<StorageVO>();
		Iterator<CommodityItemVO> cItr=storageOutQuerier.getPassedByDate(startDate, endDate).iterator();
		while(cItr.hasNext()){
//			int commID, String commName, int amount, int sum,
//			String date, String type
			CommodityItemVO c=cItr.next();
			exportInfo.add(new StorageVO(c.getCommID(),c.getCommName(),c.getAmount(),c.getSum(),c.getCreatedDate(),OrderInfo.SALES));
		}
		
		cItr=storageInReturnQuerier.getPassedByDate(startDate, endDate).iterator();
		while(cItr.hasNext()){
//			int commID, String commName, int amount, int sum,
//			String date, String type
			CommodityItemVO c=cItr.next();
			exportInfo.add(new StorageVO(c.getCommID(),c.getCommName(),c.getAmount(),c.getSum(),c.getCreatedDate(),OrderInfo.PURCHASINGRETURN));
		}
		
		Iterator<LossOverflowOrderVO>lossItr=lossOverflowDao.getPassedByDate(OrderInfo.LOSS,startDate, endDate).iterator();
		while(lossItr.hasNext()){
//			int commID, String commName, int amount, int sum,
//			String date, String type
			LossOverflowOrderVO loss=lossItr.next();
			exportInfo.add(new StorageVO(loss.getCommID(),loss.getCommName(),loss.getAmount(),loss.getSum(),loss.getCreatedDate(),OrderInfo.LOSS));
		}
		
		Iterator<GiftOrderVO> giftItr=giftDao.getPassedByDate(startDate, endDate).iterator();
		while(giftItr.hasNext()){
			GiftOrderVO gift=giftItr.next();
			exportInfo.add(new StorageVO(gift.getCommID(),gift.getCommName(),gift.getAmount(),gift.getSum(),gift.getCreatedDate(),OrderInfo.GIFT));
		}
		return exportInfo;
	}

	/*销售、进货退货、赠送、报损*/
	private Set<StorageVO> getImportInfo(String startDate, String endDate) {
		Set<StorageVO> importInfo=new HashSet<StorageVO>();
		Iterator<CommodityItemVO> cItr=storageInQuerier.getPassedByDate(startDate, endDate).iterator();
		while(cItr.hasNext()){
//			int commID, String commName, int amount, int sum,
//			String date, String type
			CommodityItemVO c=cItr.next();
			importInfo.add(new StorageVO(c.getCommID(),c.getCommName(),c.getAmount(),c.getSum(),c.getCreatedDate(),OrderInfo.PURCHASING));
		}
		
		cItr=storageOutReturnQuerier.getPassedByDate(startDate, endDate).iterator();
		while(cItr.hasNext()){
//			int commID, String commName, int amount, int sum,
//			String date, String type
			CommodityItemVO c=cItr.next();
			importInfo.add(new StorageVO(c.getCommID(),c.getCommName(),c.getAmount(),c.getSum(),c.getCreatedDate(),OrderInfo.SALESRETURN));
		}
		
		Iterator<LossOverflowOrderVO>overflowItr=lossOverflowDao.getPassedByDate(OrderInfo.OVERFLOW,startDate, endDate).iterator();
		while(overflowItr.hasNext()){
//			int commID, String commName, int amount, int sum,
//			String date, String type
			LossOverflowOrderVO overflow=overflowItr.next();
			importInfo.add(new StorageVO(overflow.getCommID(),overflow.getCommName(),overflow.getAmount(),overflow.getSum(),overflow.getCreatedDate(),OrderInfo.OVERFLOW));
		}
		return importInfo;
	}

	public static void main(String []args) throws RemoteException{
		System.out.println(new StorageServiceImpl().getStorageInfo("1111-11-1", "2222-2-2", OperationInfo.IMPORT_INFO).size());
		
//		System.out.println(new StorageServiceImpl().getStorageInfo("1111-11-1", "2222-2-2", OperationInfo.EXPORT_INFO).size());
//		
//		System.out.println(new StorageServiceImpl().getStorageInfo("1111-11-1", "2222-2-2", OperationInfo.SALES_INFO).size());
//		
//		System.out.println(new StorageServiceImpl().getStorageInfo("1111-11-1", "2222-2-2", OperationInfo.PURCHASING_INFO).size());
	}
}
