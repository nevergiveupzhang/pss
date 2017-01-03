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

import com.psssystem.connection.vo.AlarmOrderVO;
import com.psssystem.server.dao.impl.base.CommodityDaoImpl;
import com.psssystem.server.dao.inf.base.ICommodityDao;
import com.psssystem.server.dao.inf.order.general.storage.IAlarmOrderDao;
import com.psssystem.server.data.Message;
import com.psssystem.server.data.StatusInfo;
import com.psssystem.server.util.DBUtils;
import com.psssystem.server.util.EmailUtils;

public class AlarmOrderDaoImpl implements IAlarmOrderDao {
	private ICommodityDao commodityDao;
	public AlarmOrderDaoImpl(){
		commodityDao=new CommodityDaoImpl();
	}
	@Override
	public boolean addOrders(List<AlarmOrderVO> orderVOList) {
		if(orderVOList==null||orderVOList.size()==0)return false;
		Connection conn = DBUtils.getConnection();
		PreparedStatement stat=null;
		String sql="insert into alarmorder(comm_id,createddate) values(?,?)";
		boolean flag=false;
		try {
			String createdDate=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
			conn.setAutoCommit(false);
			stat=conn.prepareStatement(sql);
			for(int i=0;i<orderVOList.size();i++){
				AlarmOrderVO vo=orderVOList.get(i);
				stat.setInt(1, vo.getCommID());
				stat.setString(2, createdDate);
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
				DBUtils.closeConnection(conn);
				DBUtils.closeStateMent(stat);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			return false;
		}finally{
			DBUtils.closeConnection(conn);
			DBUtils.closeStateMent(stat);
		}
		return true;
	}
	@Override
	public Set<AlarmOrderVO> getByDate(String startDate, String endDate) {
		Connection conn = DBUtils.getConnection();
		PreparedStatement stat=null;
		if(startDate==null||endDate==null||"".equals(startDate)||"".equals(endDate))return new HashSet<AlarmOrderVO>();
		
		String sql="select A.id,A.comm_id,A.createddate,A.status,C.name,C.stockamount,C.warningline from alarmorder A join commodity C on A.comm_id=C.id where A.createddate between ? and ?";
		ResultSet rs=null;
		System.out.println("get alarmorder");
		try {
			stat=conn.prepareStatement(sql);
			stat.setString(1, startDate);
			stat.setString(2,endDate);
			rs=stat.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(rs==null)return new HashSet<AlarmOrderVO>();
		Set<AlarmOrderVO> result=getResultSet(rs);
		DBUtils.closeConnection(conn);
		DBUtils.closeStateMent(stat);
		DBUtils.closeResultSet(rs);
		return result;
	}
	private Set<AlarmOrderVO> getResultSet(ResultSet rs) {
		Set<AlarmOrderVO> set=new HashSet<AlarmOrderVO>();
		try {
			while(rs.next()){
				AlarmOrderVO vo=new AlarmOrderVO(rs.getInt("id"),rs.getInt("comm_id"),rs.getString("name"),rs.getInt("stockamount"),rs.getInt("warningline"),rs.getString("status"),rs.getDate("createddate").toString()+" "+rs.getTime("createddate").toString());
				set.add(vo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return set;
	}
	@Override
	public Set<AlarmOrderVO> getNotPassed() {
		Connection conn = DBUtils.getConnection();
		PreparedStatement stat=null;
		String sql="select A.id,A.comm_id,A.createddate,A.status,C.name,C.stockamount,C.warningline from alarmorder A inner join commodity C on A.comm_id=C.id where A.createddate like ? and A.status!=?";
		ResultSet rs=null;
		try {
			stat=conn.prepareStatement(sql);
			stat.setString(1, Calendar.getInstance().get(Calendar.YEAR)+"%");
			stat.setString(2, StatusInfo.PASS);
			rs=stat.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(rs==null) return new HashSet<AlarmOrderVO>();
		Set<AlarmOrderVO> result=getResultSet(rs);
		DBUtils.closeConnection(conn);
		DBUtils.closeStateMent(stat);
		DBUtils.closeResultSet(rs);
		return result;
	}
	
	/*更新报警单的状态为已审批，同时给仓库管理员发送消息*/
	@Override
	public boolean passOrders(Set<AlarmOrderVO> orders) {
		if(orders==null||orders.size()==0)return false;
		Connection conn = DBUtils.getConnection();
		PreparedStatement stat=null;
		String sql="update alarmorder set status=?,passdate=? where id=?";
		String emailContent ="";
		try {
			String passDate=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
			conn.setAutoCommit(false);
			stat=conn.prepareStatement(sql);
			for(AlarmOrderVO vo:orders){
				emailContent+="商品ID:"+vo.getCommID()+"|商品名称："+vo.getCommName()+"|警戒线"+vo.getWarningline()+"|库存数量："+vo.getAmount();
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
				DBUtils.closeConnection(conn);
				DBUtils.closeStateMent(stat);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			return false;
		}finally{
			DBUtils.closeConnection(conn);
			DBUtils.closeStateMent(stat);
		}
		EmailUtils.sendEmail(Message.FROM, Message.TO_SYSTEMMANAGER, Message.SUBJECT_ALAMR, emailContent);
		return true;
	}
	@Override
	public Set<AlarmOrderVO> getAll() {
		Connection conn = DBUtils.getConnection();
		PreparedStatement stat=null;
		String sql="select A.id,A.comm_id,A.createddate,A.status,C.name,C.stockamount,C.warningline from alarmorder A inner join commodity C on A.comm_id=C.id where A.createddate like ?";
		ResultSet rs=null;
		try {
			stat=conn.prepareStatement(sql);
			stat.setString(1, Calendar.getInstance().get(Calendar.YEAR)+"%");
			rs=stat.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(rs==null) return new HashSet<AlarmOrderVO>();
		Set<AlarmOrderVO> result=getResultSet(rs);
		DBUtils.closeConnection(conn);
		DBUtils.closeStateMent(stat);
		DBUtils.closeResultSet(rs);
		return result;
	}
	
	public static void main(String []args){
		List<AlarmOrderVO> orders=new ArrayList<AlarmOrderVO>();
		orders.add(new AlarmOrderVO(7,"7",9,10));
//		new AlarmOrderDaoImpl().addOrders(orders);
		
		
		System.out.println(new AlarmOrderDaoImpl().getAll().size());
	}
}
