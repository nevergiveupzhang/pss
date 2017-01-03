package com.psssystem.server.util;

import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javax.sql.DataSource;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

public class DBUtils {
	private static MysqlDataSource dataSource;
	private static final String DATABASE_PROPERTIES = "config/database.properties";
	
	private static String driver ="com.mysql.jdbc.Driver";
	private static String url = "jdbc:mysql://localhost/pss";
	private static String userName ="root";
	private static String password = "";
	
	static {
		try {
			loadPropertiesFile();
			Class.forName(driver);
			dataSource=new MysqlDataSource();
			dataSource.setUrl(url);
			dataSource.setUser(userName);
			dataSource.setPassword(password);
		}catch (Exception e){
			e.printStackTrace();
		}
	}
	
	private static void loadPropertiesFile() throws Exception{
		Properties props = new Properties();
		InputStream inStream = Files.newInputStream(Paths
				.get(DATABASE_PROPERTIES));
		props.load(inStream);

		driver = props.getProperty("jdbc.drivers");
		url = props.getProperty("jdbc.url");
		userName = props.getProperty("jdbc.username");
		password = props.getProperty("jdbc.password");
	}
	
	public static Connection getConnection() {
		Connection conn = null;
		try {
//			conn = DriverManager.getConnection(url, userName, password);
			conn=dataSource.getConnection();
		}catch (SQLException e){
			e.printStackTrace();
		}
		return conn;
	}
	
	public static void closeConnection(Connection con){
		if (con != null)
			try {
				con.close();
			}catch (SQLException e){
				e.printStackTrace();
			}
	}
	
	
	public static void closeResultSet(ResultSet rs){
		if (rs != null)
			try {
				rs.close();
			}catch (SQLException e){
				e.printStackTrace();
			}
	}
	
	public static void closeStateMent(Statement st){
		if (st != null)
			try {
				st.close();
			}catch (SQLException e){
				e.printStackTrace();
			}
	}

}
