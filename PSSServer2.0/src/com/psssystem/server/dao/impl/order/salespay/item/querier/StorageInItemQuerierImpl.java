package com.psssystem.server.dao.impl.order.salespay.item.querier;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.psssystem.connection.vo.AccountTransferVO;
import com.psssystem.connection.vo.CommodityItemVO;
import com.psssystem.server.dao.inf.order.general.salespay.item.ItemQuerier;
import com.psssystem.server.util.DBUtils;

public class StorageInItemQuerierImpl extends ItemQuerier<CommodityItemVO> {
	private static StorageInItemQuerierImpl INSTANCE=new StorageInItemQuerierImpl();
	private final String querySQL="select L.*,C.name from storageinlist L join commodity C on C.id=L.comm_id where L.pur_id=?";
	private final String passedSQL="select L.*,C.name from storageinlist L join purchasingorder P on P.id=L.pur_id join commodity C on L.comm_id=C.id where P.status=? and L.createddate between ? and ?";
	public StorageInItemQuerierImpl(){}
	protected Set<CommodityItemVO> getResultSet(ResultSet rs) {
		Set<CommodityItemVO> set=new HashSet<CommodityItemVO>();
		try {
			while(rs.next()){
				set.add(new CommodityItemVO.Builder().operationID(rs.getString("pur_id")).commName(rs.getString("name")).commID(rs.getInt("comm_id")).amount(rs.getInt("amount")).price(rs.getInt("price")).sum(rs.getInt("sum")).remarks(rs.getString("remarks")).createdDate(rs.getDate("createddate")+" "+rs.getTime("createddate")).build());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return set;
	}
	
	@Override
	protected String getQuerySQL() {
		return querySQL;
	}
	public static ItemQuerier getInstance() {
		return INSTANCE;
	}
	@Override
	protected String getPassedSQL() {
		return passedSQL;
	}

}
