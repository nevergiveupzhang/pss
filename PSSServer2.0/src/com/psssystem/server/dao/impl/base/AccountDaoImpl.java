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

import com.psssystem.connection.vo.AccountVO;
import com.psssystem.connection.vo.ResultMessage;
import com.psssystem.server.dao.inf.base.IAccountDao;
import com.psssystem.server.util.ArrayUtils;
import com.psssystem.server.util.DBUtils;

public class AccountDaoImpl implements IAccountDao {
	public AccountDaoImpl() {
	}

	@Override
	public int create(AccountVO account) {
		if("".equals(account.getName())){return ResultMessage.FAIL;}
		Connection conn = DBUtils.getConnection();
		PreparedStatement stat=null;
		String sql = "insert into account(name,sum,createddate)values(?,?,?)";
		try {
			stat = conn.prepareStatement(sql);
			stat.setString(1, account.getName());
			stat.setInt(2, account.getSum());
			stat.setString(3, new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()));
			if(stat.executeUpdate()!=1)return ResultMessage.FAIL;
		} catch (SQLException e) {
			e.printStackTrace();
			return ResultMessage.DUPLICATE;
		}finally{
			DBUtils.closeConnection(conn);
		}
		return ResultMessage.SUCCESS; 
	}

	@Override
	public boolean delete(AccountVO account) {
		Connection conn = DBUtils.getConnection();
		PreparedStatement stat=null;
		String sql = "delete from account where name=?";
		try {
			stat = conn.prepareStatement(sql);
			stat.setString(1, account.getName());
			if(stat.executeUpdate()!=1)return false;
		} catch (SQLException e) {
			e.printStackTrace();
			return false; 
		}finally{
			DBUtils.closeConnection(conn);
		}
		return true;

	}


	@Override
	public boolean updateAccountSum(AccountVO account) {
		if("".equals(account.getName()))return false;
		Connection conn = DBUtils.getConnection();
		PreparedStatement stat=null;
		String sql = "update account set sum=? where name=?";
		try {
			stat = conn.prepareStatement(sql);
			stat.setInt(1, account.getSum());
			stat.setString(2, account.getName());
			if(stat.executeUpdate()!=1){
				System.out.println("no");
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}finally{
			DBUtils.closeConnection(conn);
		}
		return true;
	}

	@Override
	public List<AccountVO> searchAccount(String info) {
		Connection conn = DBUtils.getConnection();
		PreparedStatement stat=null;
		List<AccountVO> accountList = new ArrayList<AccountVO>();
		String[] values = (String[]) ArrayUtils.removeDuplicated(info
				.split(" "));
		String sql = "select *from account where name like ?";
		ResultSet rs = null;
		try {
			for (String value : values) {
				stat = conn.prepareStatement(sql);
				stat.setString(1, "%" + value + "%");
				rs = stat.executeQuery();
				List<AccountVO> list=getAccountResultSet(rs);
				accountList.addAll(list);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DBUtils.closeConnection(conn);
		}

		return accountList;
	}

	private  List<AccountVO> getAccountResultSet(ResultSet rs) {
		List<AccountVO> accountList = new ArrayList<AccountVO>();
		try {
			while (rs.next()) {
				accountList.add(new AccountVO(rs.getString(1), rs
						.getInt(2)));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DBUtils.closeResultSet(rs);
		} 
		return accountList;
	}

	public static void main(String[] args) {
		System.out.println(new AccountDaoImpl().create(new AccountVO("zt",100)));
//		System.out.println(Calendar.getInstance().get(Calendar.YEAR));
//		System.out.println(new AccountDaoImpl().getAll().size());
	}

	@Override
	public List<AccountVO> getAll() {
		Connection conn = DBUtils.getConnection();
		PreparedStatement stat=null;
		String sql = "select *from account";
		ResultSet rs = null;
		List<AccountVO> result = new ArrayList<AccountVO>();
		try {
			stat = conn.prepareStatement(sql);
			rs = stat.executeQuery();
			result=getAccountResultSet(rs);
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DBUtils.closeConnection(conn);
			DBUtils.closeStateMent(stat);
		}
		return result;
	}

	@Override
	public boolean update(AccountVO order) {
		return false;

	}
}
