package com.psssystem.server.dao.impl.order.salespay.item.querier;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import com.psssystem.connection.vo.AccountTransferVO;
import com.psssystem.connection.vo.CommodityItemVO;
import com.psssystem.server.dao.inf.order.general.salespay.item.ItemQuerier;
import com.psssystem.server.util.DBUtils;

public class StorageOutItemQuerierImpl extends ItemQuerier<CommodityItemVO> {
	private static StorageOutItemQuerierImpl INSTANCE=new StorageOutItemQuerierImpl();
	private final String querySQL="select L.*,C.name from storageoutlist L join commodity C on C.id=L.comm_id where L.sales_id=?";
	private final String passedSQL="select L.*,C.name from storageoutlist L join salesorder S on S.id=L.sales_id join commodity C on C.id=L.comm_id where S.status=? and L.createddate between ? and ?";
	public StorageOutItemQuerierImpl(){}
	protected Set<CommodityItemVO> getResultSet(ResultSet rs) {
		Set<CommodityItemVO> set=new HashSet<CommodityItemVO>();
		try {
			while(rs.next()){
				set.add(new CommodityItemVO.Builder().operationID(rs.getString("sales_id")).commID(rs.getInt("comm_id")).commName(rs.getString("name")).amount(rs.getInt("amount")).price(rs.getInt("price")).sum(rs.getInt("sum")).remarks(rs.getString("remarks")).build());
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

	public static void main(String []args){
		System.out.println(new StorageOutItemQuerierImpl().getPassedByDate("1111-1-1", "2222-2-2").size());
	}
}
