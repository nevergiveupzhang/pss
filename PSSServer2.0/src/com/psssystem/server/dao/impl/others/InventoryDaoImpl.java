package com.psssystem.server.dao.impl.others;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import com.psssystem.connection.vo.InventoryVO;
import com.psssystem.server.dao.impl.base.CommodityDaoImpl;
import com.psssystem.server.dao.inf.base.ICommodityDao;
import com.psssystem.server.dao.inf.others.IInventoryDao;
import com.psssystem.server.util.DBUtils;

public class InventoryDaoImpl implements IInventoryDao {
	private File inventoryFile=null;
	private ICommodityDao commodityDao;
	public InventoryDaoImpl() {
		this.commodityDao=new CommodityDaoImpl();
	}

	@Override
	public void createInventory() {
		/*创建包含商品信息的Excel文件*/
		inventoryFile=commodityDao.createInventoryFileFromCommodity();
		
		/*插入字段数据，批次、批号和包含商品信息的Excel文件*/
		insertColumns();
	}

	private void insertColumns() {
		/*批次字段数据*/
		String dateTime=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		Connection conn = DBUtils.getConnection();
		PreparedStatement stat=null;
		/*商品信息字段数据，以FileInputStream的形式保存*/
		FileInputStream fis=null;
		try {
			fis = new FileInputStream(inventoryFile);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}

		/*查询语句*/
		String sql="insert into inventory(batch,comminfo) values(?,?)";
		
		/*插入数据至表中*/
		try {
			stat=conn.prepareStatement(sql);
			stat.setString(1, dateTime);
			stat.setBinaryStream(2, fis,(int)inventoryFile.length());
			stat.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DBUtils.closeConnection(conn);
			DBUtils.closeStateMent(stat);
		}
	}

	

	@Override
	public InventoryVO getInventory() {
		/*查询语句，取得刚刚创建的盘点数据，即inventory表里面的最后一条数据*/
		String sql="select *from inventory where id=(select max(id) from inventory)";
		Connection conn = DBUtils.getConnection();
		PreparedStatement stat=null;
		InventoryVO po=null;
		ResultSet rs=null;
		
		try {
			stat=conn.prepareStatement(sql);
			rs=stat.executeQuery();
			rs.next();
			/*取得查询结果,批次和批号*/
			int id=rs.getInt(1);
			Date dateTime=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(rs.getString(2));
			
			/*将文件流转换为字节数组，以便通过网络传输*/
			byte[]commInfo=new byte[(int) inventoryFile.length()];
			BufferedInputStream bis=new BufferedInputStream(new FileInputStream(inventoryFile));
			bis.read(commInfo);
			po=new InventoryVO(id,dateTime,commInfo);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}finally{
			DBUtils.closeConnection(conn);
			DBUtils.closeStateMent(stat);
			DBUtils.closeResultSet(rs);
		}
		return po;
	}
	
	public static void main(String []args){
		InventoryDaoImpl dao=new InventoryDaoImpl();
		dao.createInventory();
		InventoryVO po=dao.getInventory();
		byte[] commInfo=po.getCommInfo();
		File file=new File("resource/inventory.xls");
		try {
			BufferedOutputStream bos=new BufferedOutputStream(new FileOutputStream(file));
			bos.write(commInfo);
			Workbook book=Workbook.getWorkbook(file);
			Sheet sheet=book.getSheet(0);
			Cell cell=sheet.getCell(1,0);
			String content=cell.getContents();
			System.out.println(content);
		} catch (BiffException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
