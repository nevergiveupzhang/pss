package com.psssystem.server.dao.impl.order.salespay.passer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

import com.psssystem.connection.vo.CommodityItemVO;
import com.psssystem.connection.vo.SalesOrderVO;
import com.psssystem.server.dao.factory.impl.StorageOutItemDaoFactoryImpl;
import com.psssystem.server.dao.factory.inf.ItemFactory;
import com.psssystem.server.dao.inf.order.general.salespay.passer.PurchasingSalesPasserTemplate;
import com.psssystem.server.data.StatusInfo;
import com.psssystem.server.util.DBUtils;

public class SalesOrderPasserImpl extends PurchasingSalesPasserTemplate<SalesOrderVO> {
	private ItemFactory factory=new StorageOutItemDaoFactoryImpl();
	private static SalesOrderPasserImpl INSTANCE = new SalesOrderPasserImpl();
	private String sql = "update salesorder set status=?,passdate=? where id=?";

	public SalesOrderPasserImpl() {
		this.itemQuerier=factory.getQuerier();
	}

	@Override
	protected boolean updateStatus(Set<SalesOrderVO> orders) {
		String passDate=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		try {
			stat = conn.prepareStatement(sql);
			for (SalesOrderVO vo : orders) {
				stat.setString(1, StatusInfo.PASS);
				stat.setString(2,passDate);
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

	@Override
	protected boolean updateStorage(Set<SalesOrderVO> orders) {
		for (SalesOrderVO order : orders) {
			Set<CommodityItemVO> storageInList = itemQuerier.getByOrderId(order
					.getId());
			for (CommodityItemVO cl : storageInList) {
				if (!commodityDao.reduceAmountById(cl.getCommID(),
						cl.getAmount()))
					return false;
				if (!commodityDao.updateRecentSellingPriceById(cl.getCommID(),
						cl.getPrice()))
					return false;
			}
		}
		return true;
	}

	@Override
	protected boolean updateCustomer(Set<SalesOrderVO> orders) {
		for (SalesOrderVO order : orders) {
			if (!customerDao.increaseReceivableById(order.getCustomerID(),
					order.getSumAfterDiscount()))
				return false;
		}
		return true;
	}

	public static PurchasingSalesPasserTemplate getInstance() {
		return INSTANCE;
	}

}
