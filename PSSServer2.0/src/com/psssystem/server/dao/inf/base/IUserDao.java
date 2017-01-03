package com.psssystem.server.dao.inf.base;

import java.util.List;

import com.psssystem.connection.vo.ResultMessage;
import com.psssystem.connection.vo.UserVO;


public interface IUserDao extends IBaseDao<UserVO>{
	public List<UserVO> getUsersByType(String userType);
	public boolean login(UserVO po);
	public ResultMessage update(List<UserVO> users);
	
}
