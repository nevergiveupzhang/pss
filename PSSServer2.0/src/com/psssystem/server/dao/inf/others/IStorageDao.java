package com.psssystem.server.dao.inf.others;

import java.util.Date;
import java.util.List;

import com.psssystem.connection.vo.StorageVO;

public interface IStorageDao {
	public List<StorageVO> getStorageInfo(String start, String end, String type);
}
