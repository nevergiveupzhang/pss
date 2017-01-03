package com.psssystem.client.controller.admincontroller;

import java.util.List;

import com.psssystem.connection.vo.ResultMessage;
import com.psssystem.connection.vo.UserVO;

public interface IUserController {
	public int createUser(UserVO userVO);

	public List<UserVO> getUsers(String userType);

	public boolean deleteUser(UserVO userVO);

	public ResultMessage updateUsers(List<UserVO> userList);
}
