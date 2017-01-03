package com.psssystem.server.dao.impl.order.storage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.psssystem.connection.vo.GiftOrderVO;
import com.psssystem.server.dao.impl.base.CommodityDaoImpl;
import com.psssystem.server.dao.inf.base.ICommodityDao;
import com.psssystem.server.dao.inf.order.general.storage.IGiftOrderDao;
import com.psssystem.server.data.StatusInfo;
import com.psssystem.server.util.DBUtils;

public class GiftOrderDaoImpl implements IGiftOrderDao {
	private ICommodityDao commodityDao;
	
	public GiftOrderDaoImpl() {
		commodityDao=new CommodityDaoImpl();
	}

	@Override
	public boolean addGiftOrder(List<GiftOrderVO> poList) {
		if(poList==null||poList.size()==0)return false;
		Connection conn = DBUtils.getConnection();
		PreparedStatement stat=null;
		String sql = "insert into giftorder(comm_id,amount,createddate)values(?,?,?)";
		try {
			String createdDate=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
			conn.setAutoCommit(false);
			stat = conn.prepareStatement(sql);
			for (int i = 0; i < poList.size(); i++) {
				GiftOrderVO po = poList.get(i);
				int commID = po.getCommID();
				int amount = po.getAmount();
				stat.setInt(1, commID);
				stat.setInt(2, amount);
				stat.setString(3,createdDate );
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
		} finally{
			DBUtils.closeConnection(conn);
			DBUtils.closeStateMent(stat);
		}
		
		return true;
	}

	@Override
	public Set<GiftOrderVO> getByDate(String startDate, String endDate) {
		String sql="select G.*,C.name,C.recentsellingprice*G.amount as sum from giftorder G inner join commodity C on G.comm_id=C.id where G.createddate between ? and ? order by G.id";
		Connection conn = DBUtils.getConnection();
		PreparedStatement stat=null;
		ResultSet rs=null;
		try {
			stat=conn.prepareStatement(sql);
			stat.setString(1, startDate);
			stat.setString(2,endDate);
			rs=stat.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(rs==null){
			System.out.println("空");
			return new HashSet<GiftOrderVO>();
		}
		Set<GiftOrderVO> result=getResultSet(rs);
		DBUtils.closeConnection(conn);
		DBUtils.closeStateMent(stat);
		return result;
	}
	private Set<GiftOrderVO> getResultSet(ResultSet rs) {
		Set<GiftOrderVO> set=new HashSet<GiftOrderVO>();
		try {
			while(rs.next()){
				GiftOrderVO vo=new GiftOrderVO(rs.getInt("id"),rs.getInt("comm_id"),rs.getString("name"),rs.getInt("amount"),rs.getDate("createddate").toString()+" "+rs.getTime("createddate").toString(),rs.getString("status"),rs.getInt("sum"));
				set.add(vo);
			}
		} catch (SQLException  e) {
			e.printStackTrace();
		}
		return set;
	}

	@Override
	public Set<GiftOrderVO> getNotPassed() {
		String sql="select G.*,C.name,C.recentsellingprice*G.amount as sum from giftorder G inner join commodity C on G.comm_id=C.id where G.createddate like ? and G.status!=? order by G.id";
		Connection conn = DBUtils.getConnection();
		PreparedStatement stat=null;
		ResultSet rs=null;
		try {
			stat=conn.prepareStatement(sql);
			stat.setString(1, Calendar.getInstance().get(Calendar.YEAR)+"%");
			stat.setString(2, StatusInfo.PASS);
			rs=stat.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(rs==null) return new HashSet<GiftOrderVO>();
		Set<GiftOrderVO> result=getResultSet(rs);
		DBUtils.closeConnection(conn);
		DBUtils.closeStateMent(stat);
		return result;
	}

	/*赠送的通过审批，设置单据状态为已审批状态，同时修改赠送单中包含的商品的库存数量*/
	@Override
	public boolean passOrders(Set<GiftOrderVO> orders) {
		if(orders==null||orders.size()==0)return false;
		Connection conn = DBUtils.getConnection();
		PreparedStatement stat=null;
		String sql="update giftorder set status=?,passdate=? where id=?";
		try {
			String passDate=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
			conn.setAutoCommit(false);
			stat=conn.prepareStatement(sql);
			for(GiftOrderVO vo:orders){
				commodityDao.reduceAmountByID(vo.getCommID(),vo.getAmount());
				stat.setString(1, StatusInfo.PASS);
				stat.setString(2,passDate );
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
	public Set<GiftOrderVO> getAll() {
		Connection conn = DBUtils.getConnection();
		PreparedStatement stat=null;
		String sql="select G.*,C.name,C.recentsellingprice*G.amount as sum from giftorder G inner join commodity C on G.comm_id=C.id where YEAR(G.createddate)= ?  order by G.id";
		ResultSet rs=null;
		try {
			stat=conn.prepareStatement(sql);
			stat.setInt(1, Calendar.getInstance().get(Calendar.YEAR));
			rs=stat.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(rs==null) return new HashSet<GiftOrderVO>();
		Set<GiftOrderVO> result=getResultSet(rs);
		DBUtils.closeConnection(conn);
		DBUtils.closeStateMent(stat);
		return result;
	}
	
	
	public static void main(String[] args) throws ParseException {
		
		List<GiftOrderVO> poList = new ArrayList<GiftOrderVO>();
		poList.add(new GiftOrderVO(4, 10));
		poList.add(new GiftOrderVO(5, 10));
//		new GiftOrderDaoImpl().addGiftOrder(poList);
		
		Set<GiftOrderVO> set = new HashSet<GiftOrderVO>();
		set.add(new GiftOrderVO(4, "2014-12-07 16:27:59"));
		set.add(new GiftOrderVO(5, "2014-12-07 16:27:59"));
//		System.out.println(new GiftOrderDaoImpl().passOrders(set));

		
//		System.out.println(new GiftOrderDaoImpl().getNotPassed().size());
		
		
		
//		System.out.println(new GiftOrderDaoImpl().getAll().size());
		
		
//		System.out.println(new GiftOrderDaoImpl().getByDate("2000-9-9", "2999-9-9 10:10:10").size());
		
		System.out.println(new GiftOrderDaoImpl().getPassedByDate("1111-11-11", "2222-2-2").size());
	}

	@Override
	public Set<GiftOrderVO> getPassedByDate(String startDate, String endDate) {
		String sql="select G.*,C.name,C.recentsellingprice*G.amount as sum from giftorder G inner join commodity C on G.comm_id=C.id where G.createddate between ? and ? and G.status=?  order by G.id";
		Connection conn = DBUtils.getConnection();
		PreparedStatement stat=null;
		ResultSet rs=null;
		try {
			stat=conn.prepareStatement(sql);
			stat.setString(1, startDate);
			stat.setString(2, endDate);
			stat.setString(3, StatusInfo.PASS);
			rs=stat.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(rs==null) return new HashSet<GiftOrderVO>();
		return this.getResultSet(rs);
	}


}
