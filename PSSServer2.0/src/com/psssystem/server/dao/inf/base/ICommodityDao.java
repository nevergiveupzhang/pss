package com.psssystem.server.dao.inf.base;

import java.io.File;
import java.util.List;

import com.psssystem.connection.vo.CommodityVO;

public interface ICommodityDao extends IBaseDao<CommodityVO>{
	public boolean setWarningLines(List<CommodityVO> commVOList);

	public File createInventoryFileFromCommodity();

	public boolean addCommodites(List<CommodityVO> poList);

	public boolean deleteCommodites(List<CommodityVO> poList);

	public boolean updateCommodites(List<CommodityVO> poList);

	public List<CommodityVO> searchCommodity(String info);

	public boolean reduceAmountByID(int commID, int amount);

	public boolean increaseAmountById(int commID, int amount);

	public int getWarninglineById(int commID);

	public int getAmountById(int commID);

	public boolean updateRecentPurchasingPriceById(int commID, int price);

	public boolean reduceAmountById(int commID, int amount);

	public boolean updateRecentSellingPriceById(int commID, int price);
	
	public boolean isNamedAndIDExists(int id,String name);

	public boolean isInit();

}
