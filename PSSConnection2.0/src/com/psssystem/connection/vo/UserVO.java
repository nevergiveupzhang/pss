package com.psssystem.connection.vo;

import java.io.Serializable;

public class UserVO implements Serializable{
	private int id;
	private String username;
	private String password;
	private String userType;
	
	public UserVO(String username, String userType) {
		this(0,username,null,userType);
	}
	public UserVO(String username, String password, String userType) {
		this(0,username,password,userType);
	}
	
	public UserVO(int id, String username, String password, String userType) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.userType = userType;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}
	public String getPassword() {
		return password;
	}
	public String getUserType() {
		return userType;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	
}
