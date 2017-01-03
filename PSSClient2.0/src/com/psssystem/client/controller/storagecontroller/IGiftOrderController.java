package com.psssystem.client.controller.storagecontroller;

import java.util.List;

import com.psssystem.client.controller.base.IBaseController;
import com.psssystem.connection.vo.GiftOrderVO;

public interface IGiftOrderController extends IBaseController<GiftOrderVO>{
	public boolean addGiftOrder(List<GiftOrderVO> giftList);
}
