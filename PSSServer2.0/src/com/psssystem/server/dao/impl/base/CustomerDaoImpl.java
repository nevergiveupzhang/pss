package com.psssystem.server.dao.impl.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.psssystem.connection.vo.CustomerVO;
import com.psssystem.connection.vo.ResultMessage;
import com.psssystem.server.dao.inf.base.ICustomerDao;
import com.psssystem.server.util.DBUtils;

public class CustomerDaoImpl implements ICustomerDao {

	public CustomerDaoImpl() {
	}

	@Override
	public boolean addCustomers(Set<CustomerVO> poList) {
		if(poList.size()==0)return false;
		Connection conn = DBUtils.getConnection();
		PreparedStatement stat=null;
		String sql = "insert into customer(type,name,phonenumber,postcode,email,level,address,defaultsalesman,createddate) values(?,?,?,?,?,?,?,?,?)";
		try {
			conn.setAutoCommit(false);
			for (CustomerVO customer : poList) {
				stat = conn.prepareStatement(sql);
				stat.setString(1, customer.getType());
				stat.setString(2, customer.getName());
				stat.setString(3, customer.getPhoneNumber());
				stat.setInt(4, customer.getPostcode());
				stat.setString(5, customer.getEmail());
				stat.setInt(6, customer.getLevel());
				stat.setString(7, customer.getAddr());
				stat.setString(8, customer.getDefaultSalesman());
				stat.setString(9, new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()));
				if("".equals(customer.getName())||stat.executeUpdate()!=1){
					conn.rollback();
					DBUtils.closeConnection(conn);
					DBUtils.closeStateMent(stat);
					return false;
				};
			}
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
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
	public boolean deleteCustomerByID(int id) {
		if(id<=0)return false;
		Connection conn = DBUtils.getConnection();
		PreparedStatement stat=null;
		String sql = "delete from customer where id=?";
		try {
			stat = conn.prepareStatement(sql);
			stat.setInt(1, id);
			stat.execute();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}finally{
			DBUtils.closeConnection(conn);
			DBUtils.closeStateMent(stat);
		}
		return true;
	}

	@Override
	public boolean update(CustomerVO customer) {
		if(customer.getId()<=0||"".equals(customer.getName())||"".equals(customer.getPostcode())||"".equals(customer.getAddr())||"".equals(customer.getDefaultSalesman()))return false;
		String sql = "update customer set type=?,name=?,phonenumber=?,postcode=?,email=?,defaultsalesman=?,level=?,address=? where id=?";
		if (customer.getLevel() == 5) {
			sql = "update customer set type=?,name=?,phonenumber=?,postcode=?,email=?,defaultsalesman=?,level=?,address=?,amountreceivable=? where id=?";
		}
		Connection conn = DBUtils.getConnection();
		PreparedStatement stat=null;
		try {
			stat = conn.prepareStatement(sql);
			stat.setString(1, customer.getType());
			stat.setString(2, customer.getName());
			stat.setString(3, customer.getPhoneNumber());
			stat.setInt(4, customer.getPostcode());
			stat.setString(5, customer.getEmail());
			stat.setString(6, customer.getDefaultSalesman());
			stat.setInt(7, customer.getLevel());
			stat.setString(8, customer.getAddr());
			if (customer.getLevel() == 5) {
				stat.setInt(9, customer.getAmuntReceivable());
				stat.setInt(10, customer.getId());
			} else {
				stat.setInt(9, customer.getId());
			}
			if(stat.executeUpdate()!=1)return false;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}finally{
			DBUtils.closeConnection(conn);
			DBUtils.closeStateMent(stat);
		}
		return true;	
	}

	@Override
	public List<CustomerVO> searchCustomer(String info) {
		if(info==null||"".equals(info)) return new ArrayList<CustomerVO>();
		System.out.println("e");
		String[] values = info.split(" ");
		Pattern numberPattern = Pattern.compile("^[0-9]+$");
		Matcher numberMatcher = null;
		String sql = "";
		List<CustomerVO> poList = new ArrayList<CustomerVO>();
		Connection conn = DBUtils.getConnection();
		PreparedStatement stat=null;
		ResultSet rs=null;
		try {
			for (String value : values) {
				numberMatcher = numberPattern.matcher(value);
				if (numberMatcher.find()) {
					sql = "select *from customer where id=?";
					stat = conn.prepareStatement(sql);
					stat.setInt(1, Integer.parseInt(value));
					rs = stat.executeQuery();
					poList.addAll(getResultSet(rs));
				} else {
					sql = "select *from customer where type like ? or name like ?";
					stat = conn.prepareStatement(sql);
					stat.setString(1, "%" + value + "%");
					stat.setString(2, "%" + value + "%");
					rs = stat.executeQuery();
					poList.addAll(getResultSet(rs));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DBUtils.closeConnection(conn);
			DBUtils.closeStateMent(stat);
			DBUtils.closeResultSet(rs);
		}
		return poList;
	}

	private List<CustomerVO> getResultSet(ResultSet rs) {
		/*
		 * String name, String type, int level, String phoneNumber, String addr,
		 * int postcode, String email, String defaultSalesman
		 */
		List<CustomerVO> poList = new ArrayList<CustomerVO>();
		try {
			while (rs.next()) {
				CustomerVO po = new CustomerVO.Builder(rs.getString("name"),rs.getString("type")).addr(rs.getString("address")).amuntReceivable(rs.getInt("amountreceivable")).defaultSalesman(rs.getString("defaultsalesman")).email(rs.getString("email")).id(rs.getInt("id")).level(rs.getInt("level")).payable(rs.getInt("payable")).phoneNumber(rs.getString("phonenumber")).postcode(rs.getInt("postcode")).receivable(rs.getInt("receivable")).build();
				poList.add(po);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DBUtils.closeResultSet(rs);
		}
		return poList;
	}

	@Override
	public List<CustomerVO> getAll() {
		String sql = "select *from customer";
		Connection conn = DBUtils.getConnection();
		PreparedStatement stat=null;
		ResultSet rs = null;
		try {
			stat = conn.prepareStatement(sql);
			rs = stat.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(rs==null)return new ArrayList<CustomerVO>();
		List<CustomerVO> result=getResultSet(rs);
		DBUtils.closeConnection(conn);
		DBUtils.closeStateMent(stat);
		DBUtils.closeResultSet(rs);
		return result;
	}

	@Override
	public boolean deleteCustomers(List<CustomerVO> poList) {
		if(poList.size()==0){
			return false;
		}
		for (CustomerVO po : poList) {
			if(!deleteCustomerByID(po.getId()))return false;
		}
		return true;
	}

	@Override
	public boolean updateCustomers(List<CustomerVO> customerList) {
		if(customerList.size()==0)return false;
		for (CustomerVO po : customerList) {
			if(!update(po))return false;
		}
		return true;
	}

	public static void main(String[] args) {
		new CustomerDaoImpl().searchCustomer("8");
	}

	@Override
	public int create(CustomerVO order) {
		return ResultMessage.FAIL;
	}

	@Override
	public boolean delete(CustomerVO order) {
		return false;

	}

	@Override
	public boolean increasePayableById(int customerID, int sum) {
		Connection conn = DBUtils.getConnection();
		PreparedStatement stat=null;
		String sql="update customer set payable=payable+? where id=?";
		try {
			stat=conn.prepareStatement(sql);
			stat.setInt(1, sum);
			stat.setInt(2, customerID);
			if(stat.executeUpdate()!=1){
				DBUtils.closeConnection(conn);
				DBUtils.closeStateMent(stat);
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
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
	public boolean reducePayableById(int customerID, int sum) {
		Connection conn = DBUtils.getConnection();
		PreparedStatement stat=null;
		String sql="update customer set payable=payable-? where id=?";
		try {
			stat=conn.prepareStatement(sql);
			stat.setInt(1, sum);
			stat.setInt(2, customerID);
			if(stat.executeUpdate()!=1){
				DBUtils.closeConnection(conn);
				DBUtils.closeStateMent(stat);
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
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
	public boolean increaseReceivableById(int customerID, int sumAfterDiscount) {
		Connection conn = DBUtils.getConnection();
		PreparedStatement stat=null;
		String sql="update customer set receivable=receivable+? where id=?";
		try {
			stat=conn.prepareStatement(sql);
			stat.setInt(1, sumAfterDiscount);
			stat.setInt(2, customerID);
			if(stat.executeUpdate()!=1){
				DBUtils.closeConnection(conn);
				DBUtils.closeStateMent(stat);
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
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
	public boolean reduceReceivableById(int customerID, int sumAfterDiscount) {
		Connection conn = DBUtils.getConnection();
		PreparedStatement stat=null;
		String sql="update customer set receivable=receivable-? where id=?";
		try {
			stat=conn.prepareStatement(sql);
			stat.setInt(1, sumAfterDiscount);
			stat.setInt(2, customerID);
			if(stat.executeUpdate()!=1){
				DBUtils.closeConnection(conn);
				DBUtils.closeStateMent(stat);
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			DBUtils.closeConnection(conn);
			DBUtils.closeStateMent(stat);
			return false;
		}finally{
			DBUtils.closeConnection(conn);
			DBUtils.closeStateMent(stat);
		}
		return true;		
	}
}
