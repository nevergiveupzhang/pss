package com.psssystem.server.dao.impl.order.salespay.passer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

import com.psssystem.connection.vo.CommodityItemVO;
import com.psssystem.connection.vo.PurchasingOrderVO;
import com.psssystem.connection.vo.SalesOrderVO;
import com.psssystem.server.dao.factory.impl.StorageInItemDaoFactoryImpl;
import com.psssystem.server.dao.factory.inf.ItemFactory;
import com.psssystem.server.dao.impl.order.salespay.item.querier.StorageInItemQuerierImpl;
import com.psssystem.server.dao.inf.order.general.salespay.passer.PurchasingSalesPasserTemplate;
import com.psssystem.server.data.StatusInfo;
import com.psssystem.server.util.DBUtils;

public class PurchasingOrderPasser extends PurchasingSalesPasserTemplate<PurchasingOrderVO> {
	private ItemFactory factory=new StorageInItemDaoFactoryImpl();
	private static PurchasingOrderPasser INSTANCE=new PurchasingOrderPasser();
	private String sql="update purchasingorder set status=?,passdate=? where id=?";
	public PurchasingOrderPasser(){
		this.itemQuerier=factory.getQuerier();
	}
	
	/*更新进货单状态为已审批，同时更新库存中商品的数量和最近一次进价，以及客户的应付金额*/
	@Override
	protected boolean updateStatus(Set<PurchasingOrderVO> orders) {
		String passDate=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		try {
			stat=conn.prepareStatement(sql);
			for(PurchasingOrderVO vo:orders){
				stat.setString(1, StatusInfo.PASS);
				stat.setString(2, passDate);
				stat.setString(3, vo.getId());
				if(stat.executeUpdate()!=1){
					return false;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	/*更新进货单列表当中商品的数量和最近一次进价*/
	@Override
	protected boolean updateStorage(Set<PurchasingOrderVO> orders) {
		for(PurchasingOrderVO order:orders){
			Set<CommodityItemVO> storageInList=itemQuerier.getByOrderId(order.getId());
			for(CommodityItemVO cl:storageInList){
				if(!commodityDao.increaseAmountById(cl.getCommID(), cl.getAmount()))return false;
				if(!commodityDao.updateRecentPurchasingPriceById(cl.getCommID(),cl.getPrice()))return false;
			}
		}
		return true;
	}
	@Override
	protected boolean updateCustomer(Set<PurchasingOrderVO> orders) {
		for(PurchasingOrderVO order:orders){
			if(!customerDao.increasePayableById(order.getCustomerID(),order.getSum()))return false;
		}
		return true;
	}
	
	public static PurchasingSalesPasserTemplate getInstance() {
		return INSTANCE;
	}

}
