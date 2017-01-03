package com.psssystem.server.dao.impl.order.salespay;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.Set;

import com.psssystem.connection.vo.SalesDetailVO;
import com.psssystem.server.dao.inf.order.general.salespay.querier.PurchasingSalesQuerierTemplate;
import com.psssystem.server.util.DBUtils;

public class SalesDetailDaoImpl extends PurchasingSalesQuerierTemplate<SalesDetailVO> {
	@Override
	public Set<SalesDetailVO> getByDate(String startDate, String endDate) {
		String sql="select S.createddate,C.name,C.type,L.amount,L.price,L.sum from salesorder S inner join storageoutlist L on S.id=L.sales_id inner join commodity C on L.comm_id=C.id where unix_timestamp(S.createddate)>=unix_timestamp('"+startDate+"') and unix_timestamp(S.createddate)<=unix_timestamp('"+endDate+"')  order by S.id";
		return getSalesDetail(sql);
	}
	@Override
	public Set<SalesDetailVO> getByCommName(String commName) {
		String sql="select S.createddate,C.name,C.type,L.amount,L.price,L.sum from salesorder S inner join storageoutlist L on S.id=L.sales_id inner join commodity C on L.comm_id=C.id where C.name='"+commName+"'  order by S.id";
		return getSalesDetail(sql);
	}
	@Override
	public Set<SalesDetailVO> getByCustomerName(String customerName) {
		String sql="select S.createddate,C.name,C.type,L.amount,L.price,L.sum from salesorder S inner join storageoutlist L on S.id=L.sales_id inner join commodity C on L.comm_id=C.id where S.customer_id=(select id from customer where name='"+customerName+"')  order by S.id";
		return getSalesDetail(sql);
	}
	@Override
	public Set<SalesDetailVO> getBySalesman(String salesman) {
		String sql="select S.createddate,C.name,C.type,L.amount,L.price,L.sum from salesorder S inner join storageoutlist L on S.id=L.sales_id inner join commodity C on L.comm_id=C.id where S.salesman='"+salesman+"'  order by S.id";
		return getSalesDetail(sql);
	}
	private Set<SalesDetailVO> getSalesDetail(String sql) {
		/*时间（精确到天），商品名，型号，数量，单价，总额*/
		Set<SalesDetailVO> voList=new HashSet<SalesDetailVO>();
		Connection conn=DBUtils.getConnection();
		PreparedStatement stat=null;
		ResultSet rs=null;
		try {
			stat=conn.prepareStatement(sql);
			rs=stat.executeQuery();
			while(rs.next()){
				voList.add(new SalesDetailVO(rs.getDate(1).toString()+" "+rs.getTime(1).toString(),rs.getString(2),rs.getString(3),rs.getInt(4),rs.getInt(5),rs.getInt(6)));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DBUtils.closeConnection(conn);
			DBUtils.closeStateMent(stat);
		}
		return voList;
	}
	@Override
	public Set<SalesDetailVO> getNotPassed() {
		return null;
	}
	@Override
	public Set<SalesDetailVO> getAll() {
		return null;
	}
	@Override
	protected Set<SalesDetailVO> getResultSet(ResultSet rs) {
		return null;
	}
	@Override
	protected String getDateSQL() {
		return null;
	}
	@Override
	protected String getCustomerSQL() {
		return null;
	}
	@Override
	protected String getSalesmanSQL() {
		return null;
	}
	@Override
	protected String getCommNameSQL() {
		return null;
	}
	@Override
	protected String getNotPassedSQL() {
		return null;
	}
	@Override
	protected String getAllSQL() {
		return null;
	}
}
