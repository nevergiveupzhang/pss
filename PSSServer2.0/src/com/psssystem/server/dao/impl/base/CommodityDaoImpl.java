package com.psssystem.server.dao.impl.base;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import com.psssystem.connection.vo.CommodityVO;
import com.psssystem.connection.vo.ResultMessage;
import com.psssystem.server.dao.inf.base.ICategoryDao;
import com.psssystem.server.dao.inf.base.ICommodityDao;
import com.psssystem.server.data.Message;
import com.psssystem.server.util.DBUtils;
import com.psssystem.server.util.EmailUtils;

public class CommodityDaoImpl implements ICommodityDao {
	private ICategoryDao categoryDao;
	public CommodityDaoImpl(){
		this.categoryDao=new CategoryDaoImpl();
	}
	@Override
	public List<CommodityVO> getAll() {
		Connection conn = DBUtils.getConnection();
		PreparedStatement stat=null;
		List<CommodityVO> commList=new ArrayList<CommodityVO>();
		String sql="select *from commodity where createddate like ?";
		ResultSet rs=null;
		try {
			stat=conn.prepareStatement(sql);
			stat.setString(1, Calendar.getInstance().get(Calendar.YEAR)+"%");
			rs=stat.executeQuery();
			commList=getCommoditiesOfResultSet(rs);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DBUtils.closeConnection(conn);
			DBUtils.closeStateMent(stat);
			DBUtils.closeResultSet(rs);
		}
		return commList;
	}
	private List<CommodityVO> getCommoditiesOfResultSet(ResultSet rs) throws SQLException {
		List<CommodityVO> commList=new ArrayList<CommodityVO>();
		while(rs.next()){
			int commID=rs.getInt(1);
			String name=rs.getString(2);
			String type=rs.getString(3);
			int amount=rs.getInt(4);
			int purPrice=rs.getInt(5);
			int selPrice=rs.getInt(6);
			int recentpurPrice=rs.getInt(7);
			int recentselPrice=rs.getInt(8);
			int categoryID=rs.getInt(9);
			int warningLine=rs.getInt(10);
			String createDate=rs.getDate(11).toString()+" "+rs.getTime(11).toString();
			CommodityVO comm=new CommodityVO(commID,categoryID,name,type,amount,purPrice,selPrice,recentpurPrice,recentselPrice,warningLine,createDate);
			commList.add(comm);
		}
		DBUtils.closeResultSet(rs);
		return commList;
	}
	@Override
	public boolean setWarningLines(List<CommodityVO> commVOList) {
		if(commVOList==null||commVOList.size()==0)return false;
		Connection conn = DBUtils.getConnection();
		PreparedStatement stat=null;
		String sql="update  commodity set warningline=? where id=?";
		try {
			stat=conn.prepareStatement(sql);
			for(int i=0;i<commVOList.size();i++){
				CommodityVO po=commVOList.get(i);
				int id=po.getId();
				int warningLine=po.getWarningLine();
				stat.setInt(1, warningLine);
				stat.setInt(2, id);
				stat.execute();
			}
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
	public File createInventoryFileFromCommodity() {
		/*寄存商品信息的文件的创建*/
		File inventoryFile=new File("resource/inventory.xls");
		/*查询语句，从commodity表中取得数据*/
		String sql = "select name,type,stockamount from commodity where createddate like ?";
		Connection conn = DBUtils.getConnection();
		PreparedStatement stat=null;
		ResultSet rs = null;
		int i=1,amount;
		String name,type;
		
		WritableWorkbook book=null;
		WritableSheet sheet=null;
		try {
			/*jdbc查询*/
			stat = conn.prepareStatement(sql);
			stat.setString(1, Calendar.getInstance().get(Calendar.YEAR)+"%");
			rs = stat.executeQuery();
			
			/*添加一页*/
			book= Workbook.createWorkbook(inventoryFile);
			sheet = book.createSheet("第一页", 0);
			/*添加Excel标题栏*/
			sheet.addCell(new Label(0,0,"商品名称"));
			sheet.addCell(new Label(1,0,"商品型号"));
			sheet.addCell(new Label(2,0,"库存数量"));
			/*添加Excel中的商品信息数据*/
			while (rs.next()) {
				name = rs.getString(1);
				type = rs.getString(2);
				amount = rs.getInt(3);
				Label nameLab = new Label(0, i,name);
				Label typeLab=new Label(1,i,type);
				Number amountCell=new Number(2,i,amount);
				sheet.addCell(nameLab);
				sheet.addCell(typeLab);
				sheet.addCell(amountCell);
				i++;
			}
			
			book.write();
			book.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (RowsExceededException e) {
			e.printStackTrace();
		} catch (WriteException e) {
			e.printStackTrace();
		}finally{
			DBUtils.closeConnection(conn);
			DBUtils.closeStateMent(stat);
			DBUtils.closeResultSet(rs);
		}
		return inventoryFile;		
	}
	@Override
	public boolean addCommodites(List<CommodityVO> poList) {
		return false;
		
	}
	@Override
	public int create(CommodityVO po) {
		if(po==null)return ResultMessage.FAIL;
		int categoryID=po.getCategoryID();
		categoryDao.increaseHasCommodity(categoryID);
		String name=po.getName();
		String type=po.getType();
		int stockAmount=po.getStockAmount();
		int purchasingPrice=po.getPurchasingPrice();
		int sellingPrice=po.getSellingPrice();
		Connection conn = DBUtils.getConnection();
		PreparedStatement stat=null;
		String sql="insert into commodity(category_id,name,type,stockamount,purchasingprice,sellingprice,recentpurchasingprice,recentsellingprice,createddate)values(?,?,?,?,?,?,?,?,?)";
		
		try {
			stat=conn.prepareStatement(sql);
			stat.setInt(1, categoryID);
			stat.setString(2, name);
			stat.setString(3, type);
			stat.setInt(4, stockAmount);
			stat.setInt(5, purchasingPrice);
			stat.setInt(6, sellingPrice);
			stat.setInt(7, purchasingPrice);
			stat.setInt(8, sellingPrice);
			stat.setString(9, new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()));
			stat.execute();
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
	public boolean deleteCommodites(List<CommodityVO> poList) {
		return false;
		
	}
	@Override
	/*删除商品，更新分类*/
	public boolean delete(CommodityVO po) {
		int id=po.getId();
		int categoryID=po.getCategoryID();
		categoryDao.reduceHasCommodity(categoryID);
		String sql="delete  from commodity where id=?";
		Connection conn = DBUtils.getConnection();
		PreparedStatement stat=null;
		try {
			stat=conn.prepareStatement(sql);
			stat.setInt(1, id);
			if(stat.executeUpdate()!=1)return false;
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DBUtils.closeConnection(conn);
			DBUtils.closeStateMent(stat);
		}
		return false;
	}
	@Override
	public boolean updateCommodites(List<CommodityVO> poList) {
		return false;
		
	}
	@Override
	public boolean update(CommodityVO po) {
		int id=po.getId();
		String name=po.getName();
		String type=po.getType();
		int stockAmount=po.getStockAmount();
		int recentPurchasingPrice=po.getRecentPurchasingPrice();
		int recentSellingPrice=po.getRecentSellingPrice();
		String sql="update commodity set name=? ,type=? ,stockamount=? ,recentpurchasingprice=? , recentsellingprice=? where id=?";
		Connection conn = DBUtils.getConnection();
		PreparedStatement stat=null;
		try {
			stat=conn.prepareStatement(sql);
			stat.setString(1, name);
			stat.setString(2, type);
			stat.setInt(3, stockAmount);
			stat.setInt(4, recentPurchasingPrice);
			stat.setInt(5, recentSellingPrice);
			stat.setInt(6, id);
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
	public List<CommodityVO> searchCommodity(String info) {
		String values[]=info.split(" ");
		
		List<CommodityVO> poList=new ArrayList<CommodityVO>();
		String sql="select *from commodity where id=?";
		String sql2="select *from commodity where type like ? or name like ? and createddate like ?";
		Connection conn = DBUtils.getConnection();
		PreparedStatement stat=null;
		Pattern numberPattern = Pattern.compile("^[0-9]$");
		Matcher numberMatcher =null;
		for(String value:values){
			numberMatcher= numberPattern.matcher(value);
			if(numberMatcher.find()){
				int id=Integer.parseInt(value);
				try {
					stat=conn.prepareStatement(sql);
					stat.setInt(1, id);
					ResultSet rs=stat.executeQuery();
					poList.addAll(getCommoditiesOfResultSet(rs));
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}else{
				try {
					stat=conn.prepareStatement(sql2);
					stat.setString(1, "%"+value+"%");
					stat.setString(2, "%"+value+"%");
					stat.setString(3, Calendar.getInstance().get(Calendar.YEAR)+"%");
					ResultSet rs=stat.executeQuery();
					poList.addAll(getCommoditiesOfResultSet(rs));
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		DBUtils.closeConnection(conn);
		DBUtils.closeStateMent(stat);
		return poList;
	}
	
	
	public static void main(String []args){
		new CommodityDaoImpl().searchCommodity("飞");
	}
	@Override
	public boolean reduceAmountByID(int commID, int amount) {
		String  sql="update commodity set stockamount=stockamount-? where id=?";
		Connection conn = DBUtils.getConnection();
		PreparedStatement stat=null;
		try {
			stat=conn.prepareStatement(sql);
			stat.setInt(1, amount);
			stat.setInt(2, commID);
			if(stat.executeUpdate()!=1)return false;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}finally{
			DBUtils.closeConnection(conn);
			DBUtils.closeStateMent(stat);
		}
		isAlarmed(commID);
		return true;
	}
	private void isAlarmed(int commID) {
		Connection conn = DBUtils.getConnection();
		PreparedStatement stat=null;
		String sql="select warningline,stockamount from commodity where id=?";
		ResultSet rs=null;
		try {
			stat=conn.prepareStatement(sql);
			stat.setInt(1, commID);
			rs=stat.executeQuery();
			if(rs.next()){
				int warningline=rs.getInt("warningline");
				int amount=rs.getInt("stockamount");
				if(warningline>amount){
					String content="商品ID:"+commID+"|商品名称："+this.getNameById(commID)+"|库存数量："+amount+"|警戒线："+warningline;
					EmailUtils.sendEmail(Message.FROM, Message.TO_SYSTEMMANAGER, Message.SUBJECT_COMMODITY, content);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DBUtils.closeConnection(conn);
			DBUtils.closeStateMent(stat);
		}
		
	}
	private String getNameById(int commID) {
		String sql="select name from commodity where id=?";
		Connection conn = DBUtils.getConnection();
		PreparedStatement stat=null;
		ResultSet rs=null;
		try {
			stat=conn.prepareStatement(sql);
			stat.setInt(1, commID);
			rs=stat.executeQuery();
			if(rs.next())return rs.getString(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DBUtils.closeConnection(conn);
			DBUtils.closeStateMent(stat);
		}
		return null;
	}
	@Override
	public boolean increaseAmountById(int commID, int amount) {
		Connection conn = DBUtils.getConnection();
		PreparedStatement stat=null;
		String  sql="update commodity set stockamount=stockamount+? where id=?";
		try {
			stat=conn.prepareStatement(sql);
			stat.setInt(1, amount);
			stat.setInt(2, commID);
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
	public int getWarninglineById(int commID) {
		Connection conn = DBUtils.getConnection();
		PreparedStatement stat=null;
		String sql="select warningline from commodity where id=?";
		ResultSet rs=null;
		try {
			stat=conn.prepareStatement(sql);
			stat.setInt(1, commID);
			rs=stat.executeQuery();
			if(rs.next()){
				int warningline=rs.getInt(1);
				DBUtils.closeConnection(conn);
				DBUtils.closeStateMent(stat);
				DBUtils.closeResultSet(rs);
				return warningline;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DBUtils.closeConnection(conn);
			DBUtils.closeStateMent(stat);
			DBUtils.closeResultSet(rs);
		}
		return 0;
	}
	@Override
	public int getAmountById(int commID) {
		Connection conn = DBUtils.getConnection();
		PreparedStatement stat=null;
		String sql="select amount from commodity where id=?";
		ResultSet rs=null;
		try {
			stat=conn.prepareStatement(sql);
			stat.setInt(1, commID);
			rs=stat.executeQuery();
			if(rs.next()){
				int amount=rs.getInt(1);
				DBUtils.closeConnection(conn);
				DBUtils.closeStateMent(stat);
				DBUtils.closeResultSet(rs);
				return amount;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DBUtils.closeConnection(conn);
			DBUtils.closeStateMent(stat);
			DBUtils.closeResultSet(rs);
		}
		return 0;
	}
	@Override
	public boolean updateRecentPurchasingPriceById(int commID, int price) {
		String sql="update commodity set recentpurchasingprice=? where id=?";
		Connection conn = DBUtils.getConnection();
		PreparedStatement stat=null;
		try {
			stat=conn.prepareStatement(sql);
			stat.setInt(1, price);
			stat.setInt(2, commID);
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
	public boolean reduceAmountById(int commID, int amount) {
		Connection conn = DBUtils.getConnection();
		PreparedStatement stat=null;
		String  sql="update commodity set stockamount=stockamount-? where id=?";
		try {
			stat=conn.prepareStatement(sql);
			stat.setInt(1, amount);
			stat.setInt(2, commID);
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
	public boolean updateRecentSellingPriceById(int commID, int price) {
		Connection conn = DBUtils.getConnection();
		PreparedStatement stat=null;
		String sql="update commodity set recentsellingprice=? where id=?";
		try {
			stat=conn.prepareStatement(sql);
			stat.setInt(1, price);
			stat.setInt(2, commID);
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
	public boolean isNamedAndIDExists(int id, String name) {
		Connection conn = DBUtils.getConnection();
		PreparedStatement stat=null;
		String sql="select *from commodity where id=? and name=?";
		try {
			stat=conn.prepareStatement(sql);
			stat.setInt(1, id);
			stat.setString(2, name);
			if(stat.executeQuery().next()){
				DBUtils.closeConnection(conn);
				DBUtils.closeStateMent(stat);
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
	public boolean isInit() {
		Connection conn = DBUtils.getConnection();
		PreparedStatement stat=null;
		String sql="select *from commodity where createddate like ?";
		try {
			stat=conn.prepareStatement(sql);
			stat.setString(1, "%"+Calendar.getInstance().get(Calendar.YEAR)+"%");
			if(stat.executeQuery().next()){
				DBUtils.closeConnection(conn);
				DBUtils.closeStateMent(stat);
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			DBUtils.closeConnection(conn);
			DBUtils.closeStateMent(stat);
		}finally{
			DBUtils.closeConnection(conn);
			DBUtils.closeStateMent(stat);
		}
		return false;
	}
}
