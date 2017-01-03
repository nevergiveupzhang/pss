package com.psssystem.client.controller.storagecontroller;

import java.util.List;

import com.psssystem.connection.vo.CommodityVO;

public interface ICommodityController {
	public List<CommodityVO> getAllCommodities();

	public boolean setWarningLines(List<CommodityVO> commList);

	public boolean addCommodity(CommodityVO commodity);
	public boolean initCommodity(CommodityVO commodity);
	public boolean deleteCommodity(CommodityVO commodity);

	public boolean updateCommodity(CommodityVO commodity);

	public List<CommodityVO> searchCommodity(String info);
	
	public boolean isCommodityIDAndNameExists(CommodityVO commodity);
}
