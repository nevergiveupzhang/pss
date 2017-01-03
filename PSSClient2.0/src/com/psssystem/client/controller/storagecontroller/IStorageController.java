package com.psssystem.client.controller.storagecontroller;

import java.util.Date;
import java.util.List;
import java.util.Set;

import com.psssystem.connection.vo.StorageVO;


public interface IStorageController {
	public Set<StorageVO> getStorageInfo(String startDate, String endDate, String type);
}
