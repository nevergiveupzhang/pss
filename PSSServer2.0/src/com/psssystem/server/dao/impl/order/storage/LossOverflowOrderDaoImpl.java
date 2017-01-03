package com.psssystem.server.dao.impl.order.storage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.psssystem.connection.vo.GiftOrderVO;
import com.psssystem.connection.vo.LossOverflowOrderVO;
import com.psssystem.server.dao.impl.base.CommodityDaoImpl;
import com.psssystem.server.dao.inf.base.ICommodityDao;
import com.psssystem.server.dao.inf.order.type.ILossOverflowOrderDao;
import com.psssystem.server.data.OrderInfo;
import com.psssystem.server.data.StatusInfo;
import com.psssystem.server.util.DBUtils;

public class LossOverflowOrderDaoImpl implements ILossOverflowOrderDao {
	private ICommodityDao commodityDao;
	public LossOverflowOrderDaoImpl(){
		commodityDao=new CommodityDaoImpl();
	}

	@Override
	public boolean addOrder(List<LossOverflowOrderVO> orderList) {
		Connection conn = DBUtils.getConnection();
		PreparedStatement stat=null;
		String sql="insert into lossoverfloworder(comm_id,amount,type,createddate) values(?,?,?,?)";
		try {
			conn.setAutoCommit(false);
			String createdDate=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
			for(int i=0;i<orderList.size();i++){
				LossOverflowOrderVO po=orderList.get(i);
				int commID=po.getCommID();
				int amount=po.getAmount();
				String type=po.getType();
				stat=conn.prepareStatement(sql);
				stat.setInt(1, commID);
				stat.setInt(2,amount);
				stat.setString(3, type);
				stat.setString(4,createdDate );
				if(stat.executeUpdate()!=1){
					conn.rollback();
					DBUtils.closeConnection(conn);
					DBUtils.closeStateMent(stat);
					return false;
				}
			}
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			DBUtils.closeConnection(conn);
			DBUtils.closeStateMent(stat);
			return false;
		}finally{
			DBUtils.closeConnection(conn);
			DBUtils.closeStateMent(stat);
		}
		return true;
	}

	@Override
	public Set<LossOverflowOrderVO> getNotPassedByType(String type) {
		Connection conn = DBUtils.getConnection();
		PreparedStatement stat=null;
		String sql="select L.*,C.name,L.amount*C.recentsellingprice as sum from lossoverfloworder L inner join commodity C on L.comm_id=C.id where L.type=? and L.status!=? order by L.id";
		ResultSet rs=null;
		try {
			stat=conn.prepareStatement(sql);
			stat.setString(1, type);
			stat.setString(2, StatusInfo.PASS);
			rs=stat.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}	
		if(rs==null)return new HashSet<LossOverflowOrderVO>();
		Set<LossOverflowOrderVO> result=getResultSet(rs);
		DBUtils.closeConnection(conn);
		DBUtils.closeStateMent(stat);
		return result;
	}

	private List<LossOverflowOrderVO> getResultSetList(ResultSet rs) {
		Connection conn = DBUtils.getConnection();
		PreparedStatement stat=null;
		List<LossOverflowOrderVO> list=new ArrayList<LossOverflowOrderVO>();
		try {
			while(rs.next()){
				LossOverflowOrderVO vo=new LossOverflowOrderVO(rs.getInt("id"),rs.getInt("comm_id"),rs.getString("name"),rs.getInt("amount"),rs.getString("type"),rs.getString("createdate"),rs.getString("status"),rs.getInt("sum"));
				list.add(vo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	private Set<LossOverflowOrderVO> getResultSet(ResultSet rs) {
		Set<LossOverflowOrderVO> set=new HashSet<LossOverflowOrderVO>();
		try {
			while(rs.next()){
				LossOverflowOrderVO vo=new LossOverflowOrderVO(rs.getInt("id"),rs.getInt("comm_id"),rs.getString("name"),rs.getInt("amount"),rs.getString("type"),rs.getDate("createddate")+" "+rs.getTime("createddate"),rs.getString("status"),rs.getInt("sum"));
				set.add(vo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return set;
	}

	@Override
	public Set<LossOverflowOrderVO> getByDate(String type,String startDate, String endDate) {
		Connection conn = DBUtils.getConnection();
		PreparedStatement stat=null;
		String sql="select L.*,C.name,L.amount*C.recentsellingprice as sum from lossoverfloworder L inner join commodity C on L.comm_id=C.id where L.type=? and L.createddate between ? and ?  order by L.id";
		ResultSet rs=null;
		try {
			stat=conn.prepareStatement(sql);
			stat.setString(1, type);
			stat.setString(2, startDate);
			stat.setString(3, endDate);
			rs=stat.executeQuery();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(rs==null)return new HashSet<LossOverflowOrderVO>();
		Set<LossOverflowOrderVO> result=getResultSet(rs);
		DBUtils.closeConnection(conn);
		DBUtils.closeStateMent(stat);
		return result;
	}


	/*报溢报损通过审批之后自动更新商品数量*/
	@Override
	public boolean passOrdersByType(Set<LossOverflowOrderVO> orders,String type) {
		Connection conn = DBUtils.getConnection();
		PreparedStatement stat=null;
		String sql="update lossoverfloworder set status=?,passdate=? where id=?";
		try {
			String passDate=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
			conn.setAutoCommit(false);
			stat=conn.prepareStatement(sql);
			for(LossOverflowOrderVO vo:orders){
				if(type.equals(OrderInfo.LOSS))commodityDao.reduceAmountByID(vo.getCommID(),vo.getAmount());
				else if(type.equals(OrderInfo.OVERFLOW))commodityDao.increaseAmountById(vo.getCommID(),vo.getAmount());
				stat.setString(1, StatusInfo.PASS);
				stat.setString(2, passDate);
				stat.setInt(3, vo.getId());
				if(stat.executeUpdate()!=1){
					conn.rollback();
					DBUtils.closeConnection(conn);
					DBUtils.closeStateMent(stat);
					return false;
				};
			}
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			DBUtils.closeConnection(conn);
			DBUtils.closeStateMent(stat);
			return false;
		}finally{
			DBUtils.closeConnection(conn);
			DBUtils.closeStateMent(stat);
		}
		return true;
	}

	@Override
	public Set<LossOverflowOrderVO> getAllByType(String type) {
		Connection conn = DBUtils.getConnection();
		PreparedStatement stat=null;
		String sql="select L.*,C.name,L.amount*C.recentsellingprice as sum  from lossoverfloworder L inner join commodity C on L.comm_id=C.id where L.type=? order by L.id";
		ResultSet rs=null;
		try {
			stat=conn.prepareStatement(sql);
			stat.setString(1, type);
			rs=stat.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}	
		if(rs==null)return new HashSet<LossOverflowOrderVO>();
		Set<LossOverflowOrderVO> result=getResultSet(rs);
		DBUtils.closeConnection(conn);
		DBUtils.closeStateMent(stat);
		return result;
	}
	
	

	public static void main(String []args){
		new LossOverflowOrderDaoImpl().getNotPassedByType(OrderInfo.LOSS);
//		System.out.println(new LossOverflowOrderDaoImpl().getByDate(OrderInfo.LOSS,"2000-1-1","2222-2-2").size());
		
		
		
//		System.out.println(new LossOverflowOrderDaoImpl().getByDate(OrderInfo.OVERFLOW,"2000-1-1","2222-2-2").size());
		Iterator<LossOverflowOrderVO> itr=new LossOverflowOrderDaoImpl().getByDate(OrderInfo.OVERFLOW,"2000-1-1","2222-2-2").iterator();
		while(itr.hasNext()){
			LossOverflowOrderVO vo=itr.next();
			System.out.println(vo.getId()+vo.getCommID()+vo.getCommName()+vo.getCreatedDate()+vo.getStatus());
		}
	}

	@Override
	public Set<LossOverflowOrderVO> getPassedByDate(String type,String startDate,
			String endDate) {
		Connection conn = DBUtils.getConnection();
		PreparedStatement stat=null;
		String sql="select L.*,C.name,L.amount*C.recentsellingprice as sum from lossoverfloworder L inner join commodity C on L.comm_id=C.id where L.type=? and status=? and L.createddate between ? and ?  order by L.id";
		ResultSet rs=null;
		try {
			stat=conn.prepareStatement(sql);
			stat.setString(1, type);
			stat.setString(2, StatusInfo.PASS);
			stat.setString(3, startDate);
			stat.setString(4, endDate);
			rs=stat.executeQuery();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(rs==null)return new HashSet<LossOverflowOrderVO>();
		Set<LossOverflowOrderVO> result=getResultSet(rs);
		DBUtils.closeConnection(conn);
		DBUtils.closeStateMent(stat);
		return result;
	}

	
}
