package com.psssystem.server.dao.impl.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.psssystem.connection.vo.ResultMessage;
import com.psssystem.connection.vo.UserVO;
import com.psssystem.server.dao.inf.base.IUserDao;
import com.psssystem.server.util.DBUtils;

public class UserDaoImpl implements IUserDao {

	public UserDaoImpl() {
	}

	@Override
	public int create(UserVO userVO) {
		if (userVO.getUsername() == null || "".equals(userVO.getUsername())
				|| userVO.getPassword() == null
				| "".equals(userVO.getPassword())) {
			return ResultMessage.FAIL;
		}
		Connection conn = DBUtils.getConnection();
		PreparedStatement stat=null;
		try {
			stat = conn.prepareStatement("insert into user(username,password,usertype) values(?,?,?)");
			stat.setString(1, userVO.getUsername());
			stat.setString(2, userVO.getPassword());
			stat.setString(3, userVO.getUserType());
			if(stat.executeUpdate()!=1){
				DBUtils.closeConnection(conn);
				DBUtils.closeStateMent(stat);
				return ResultMessage.FAIL;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			DBUtils.closeConnection(conn);
			DBUtils.closeStateMent(stat);
			return ResultMessage.DUPLICATE;
		}finally{
			DBUtils.closeConnection(conn);
			DBUtils.closeStateMent(stat);
		}
		return ResultMessage.SUCCESS;
	}

	@Override
	public boolean delete(UserVO userVO) {
		if(userVO.getUsername()==null||"".equals(userVO.getUsername()))return false;
		String sql = "delete from user where username=? and usertype=?";
		Connection conn = DBUtils.getConnection();
		PreparedStatement stat=null;
		try {
			stat = conn.prepareStatement(sql);
			stat.setString(1, userVO.getUsername());
			stat.setString(2, userVO.getUserType());
			if (stat.executeUpdate() != 1){
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
	public ResultMessage update(List<UserVO> users) {
		String sql = "update user set username=? , password=? where id=?";
		Connection conn = DBUtils.getConnection();
		PreparedStatement stat=null;
		ResultMessage result = new ResultMessage(users.size());
		boolean flag = false;// 默认user列表当中没有与数据库重复的数据
		try {
			conn.setAutoCommit(false);
			stat = conn.prepareStatement(sql);
			for (int i = 0; i < users.size(); i++) {
				stat.setString(1, users.get(i).getUsername());
				stat.setString(2, users.get(i).getPassword());
				stat.setInt(3, users.get(i).getId());
				if (conn.prepareStatement(
						"select *from user where username='"
								+ users.get(i).getUsername()
								+ "'and usertype='"
								+ users.get(i).getUserType() + "'and id!='"
								+ users.get(i).getId() + "'").executeQuery()
						.next()) {
					result.setFailRows(i);
					flag = true;
				} else {
					stat.execute();
				}
			}
			if (flag) {
				result.setStatus(false);
				conn.rollback();
			}
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DBUtils.closeConnection(conn);
			DBUtils.closeStateMent(stat);
		}
		return result;
	}

	@Override
	public List<UserVO> getUsersByType(String userType) {
		String sql = "select id,username,password from user where usertype=?";
		ResultSet rs = null;
		Connection conn = DBUtils.getConnection();
		PreparedStatement stat=null;
		List<UserVO> userList = new ArrayList<UserVO>();
		try {
			stat = conn.prepareStatement(sql);
			stat.setString(1, userType);
			rs = stat.executeQuery();
			while (rs.next()) {
				userList.add(new UserVO(rs.getInt(1), rs.getString(2), rs
						.getString(3), userType));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DBUtils.closeConnection(conn);
			DBUtils.closeStateMent(stat);
			DBUtils.closeResultSet(rs);
		}
		return userList;
	}

	@Override
	public boolean login(UserVO po) {
		Connection conn = DBUtils.getConnection();
		PreparedStatement stat=null;
		String sql = "select *from user where usertype=? and username=? and password=?";
		try {
			stat = conn.prepareStatement(sql);
			stat.setString(1, po.getUserType());
			stat.setString(2, po.getUsername());
			stat.setString(3, po.getPassword());
			ResultSet rs = stat.executeQuery();
			while (rs.next()) {
				DBUtils.closeConnection(conn);
				DBUtils.closeStateMent(stat);
				DBUtils.closeResultSet(rs);
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DBUtils.closeConnection(conn);
			DBUtils.closeStateMent(stat);
		}
		return false;
	}

	@Override
	public boolean update(UserVO order) {
		return false;

	}

	@Override
	public List<UserVO> getAll() {
		return null;
	}

	public static void main(String[] args) {
		// System.out.println(new UserDaoImpl().create(new
		// UserVO("f","f","库存管理人员")));
		System.out.println(new UserDaoImpl().delete(new UserVO("f", "e",
				"库存管理人员")));
		List<UserVO> users = new ArrayList<UserVO>();
		// users.add(new UserVO("k","k","库存管理人员"));
		users.add(new UserVO(33, "m", "k", "库存管理人员"));
		// System.out.println(new UserDaoImpl().update(users).getStatus());
	}
}
