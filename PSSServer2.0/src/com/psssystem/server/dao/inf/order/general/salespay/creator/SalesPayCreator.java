package com.psssystem.server.dao.inf.order.general.salespay.creator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.psssystem.server.dao.inf.order.general.salespay.item.ItemDao;
import com.psssystem.server.dao.inf.order.general.salespay.item.ItemQuerier;
import com.psssystem.server.util.DBUtils;
import com.psssystem.server.util.DateUtils;

public abstract class SalesPayCreator <T>{
	/*添加子项的策略接口，由于项目特殊性这里的策略由模板子类决定，客户不能改变*/
	protected ItemDao itemDao;
	
	/*创建单据时根据单据类型的不同，推迟到子类执行相应的操作*/
	protected abstract String[] getOrderInfo();//获取单据表的表名和单据表ID的标示符号
	protected abstract boolean updateDB(String id,T po);
	
	/*创建单据时公开的模板方法*/
	public boolean createOrder(T po){
		String[]info=getOrderInfo();
		String id=createID(info[0],info[1]);
		boolean status=updateDB(id,po);
		return status;
	}
	
	
	/*根据单据表的表名和标示符号来创建新的ID，*/
	private String createID(String table,String tag){
		Connection conn=DBUtils.getConnection();
		PreparedStatement stat=null;
		String sql="SELECT id FROM "+table+" WHERE id = (SELECT max( id ) FROM "+table+" ) ";
		String id=tag+"-"+new SimpleDateFormat("yyyyMMdd").format(new Date())+"-"+1;
		try {
			stat=conn.prepareStatement(sql);
			ResultSet rs=stat.executeQuery();
			while(rs.next()){
				String values[]=rs.getString(1).split("-");
				if(DateUtils.isToday(values[1])){
					id=values[0]+"-"+values[1]+"-"+(Integer.parseInt(values[2])+1);
				}else{
					id=values[0]+"-"+new SimpleDateFormat("yyyyMMdd").format(new Date())+"-"+1;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return id;
	};
}
