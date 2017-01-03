package com.psssystem.server.dao.impl.order.salespay.passer;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

import com.psssystem.connection.vo.CommodityItemVO;
import com.psssystem.connection.vo.SalesOrderVO;
import com.psssystem.server.dao.factory.impl.StorageOutReturnItemDaoFactoryImpl;
import com.psssystem.server.dao.factory.inf.ItemFactory;
import com.psssystem.server.dao.inf.order.general.salespay.passer.ISalesPayPasser;
import com.psssystem.server.dao.inf.order.general.salespay.passer.PurchasingSalesPasserTemplate;
import com.psssystem.server.data.StatusInfo;

public class SalesReturnOrderPasserImpl extends
		PurchasingSalesPasserTemplate<SalesOrderVO> {
	private ItemFactory factory=new StorageOutReturnItemDaoFactoryImpl();
	private String sql = "update salesreturnorder set status=?,passdate=? where id=?";
	private static SalesReturnOrderPasserImpl INSTANCE = new SalesReturnOrderPasserImpl();

	public SalesReturnOrderPasserImpl() {
		this.itemQuerier=factory.getQuerier();
	}

	@Override
	protected boolean updateStatus(Set<SalesOrderVO> orders) {
		String passDate=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		try {
			stat = conn.prepareStatement(sql);
			for (SalesOrderVO vo : orders) {
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

	@Override
	protected boolean updateStorage(Set<SalesOrderVO> orders) {
		for (SalesOrderVO order : orders) {
			Set<CommodityItemVO> storageInList = itemQuerier.getByOrderId(order
					.getId());
			for (CommodityItemVO cl : storageInList) {
				if (!commodityDao.increaseAmountById(cl.getCommID(),
						cl.getAmount()))
					return false;
			}
		}
		return true;

	}

	@Override
	protected boolean updateCustomer(Set<SalesOrderVO> orders) {
		for (SalesOrderVO order : orders) {
			if (!customerDao.reduceReceivableById(order.getCustomerID(),
					order.getSumAfterDiscount()))
				return false;
		}
		return true;

	}

	public static ISalesPayPasser getInstance() {
		return INSTANCE;
	}

}
