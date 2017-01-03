package com.psssystem.client.controllerimpl.storagecontrollerimpl;

import java.rmi.RemoteException;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.psssystem.client.controller.storagecontroller.IGiftOrderController;
import com.psssystem.client.util.ConnectToServer;
import com.psssystem.connection.service.storageservice.IGiftOrderService;
import com.psssystem.connection.vo.GiftOrderVO;

public class GiftOrderControllerImpl implements IGiftOrderController {
	private IGiftOrderService giftOrderService;
	public GiftOrderControllerImpl(){
		ConnectToServer.connect();
		this.giftOrderService=ConnectToServer.giftOrderService;
	}
	@Override
	public boolean addGiftOrder(List<GiftOrderVO> giftList) {
		boolean status=false;
		try {
			status=giftOrderService.addGiftOrder(giftList);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return status;
	}
	@Override
	public Set<GiftOrderVO> getAll() {
		Set<GiftOrderVO> list=null;
		try {
			list=giftOrderService.getAll();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return list;
	}
	@Override
	public boolean approve(Set<GiftOrderVO> orders) {
		try {
			return giftOrderService.approve(orders);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return false;
	}
	@Override
	public Set<GiftOrderVO> getNotPassed() {
		Set<GiftOrderVO> list=null;
		try {
			list=giftOrderService.getNotPassed();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return list;
	}

	
	public static void main(String []args){
//		System.out.println(new GiftOrderControllerImpl().getNotPassed().size());
		long start=System.currentTimeMillis();
		for(GiftOrderVO vo:new GiftOrderControllerImpl().getNotPassed()){
			System.out.println(vo.getCommID()+" "+vo.getCommName()+" "+vo.getAmount()+" "+vo.getStatus()+" "+vo.getCreatedDate());
		}
		long end=System.currentTimeMillis();
		System.out.println(end-start);
		
		start=System.currentTimeMillis();
		Iterator<GiftOrderVO> itr=new GiftOrderControllerImpl().getNotPassed().iterator();
		while(itr.hasNext()){
			GiftOrderVO vo=itr.next();
			System.out.println(vo.getCommID()+" "+vo.getCommName()+" "+vo.getAmount()+" "+vo.getStatus()+" "+vo.getCreatedDate());
		}
		end=System.currentTimeMillis();
		System.out.println(end-start);
	}
}
