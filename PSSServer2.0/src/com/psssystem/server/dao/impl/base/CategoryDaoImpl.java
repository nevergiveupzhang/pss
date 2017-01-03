package com.psssystem.server.dao.impl.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.tree.DefaultMutableTreeNode;

import com.psssystem.connection.vo.CategoryVO;
import com.psssystem.connection.vo.ResultMessage;
import com.psssystem.server.dao.inf.base.ICategoryDao;
import com.psssystem.server.util.DBUtils;

public class CategoryDaoImpl implements ICategoryDao {
	
	public CategoryDaoImpl() {
	}

	@Override
	public void addCategoryToTreeNode(DefaultMutableTreeNode parent,
			int parentID) {
		if(parent==null)return;
		Connection conn = DBUtils.getConnection();
		PreparedStatement stat=null;
		DefaultMutableTreeNode temp = null;
		String strsql = "select  id,name,parent_id from category where   parent_id =? and createddate like ?";
		ResultSet rs=null;
		// 光标的相对位置
		//	int i = 0;
		try {
			stat = conn.prepareStatement(strsql);
			stat.setInt(1,parentID);
			stat.setString(2, Calendar.getInstance().get(Calendar.YEAR)+"%");
			rs = stat.executeQuery();
			while (rs.next()) {
				//i++;
				temp = new DefaultMutableTreeNode(rs.getString("name"));
				parent.add(temp);
				addCategoryToTreeNode(temp, rs.getInt(1));
				/*当ResultSet作为成员变量时使用如下方法*/
				/*rs = stat.executeQuery(strsql);
				// 重新定位光标
				rs.relative(i);*/
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBUtils.closeConnection(conn);
			DBUtils.closeStateMent(stat);
			DBUtils.closeResultSet(rs);
		}
	}

	@Override
	public List<CategoryVO> getAll() {
		Connection conn = DBUtils.getConnection();
		PreparedStatement stat=null;
		String sql="select *from category where createddate like ?";
		List<CategoryVO> poList = new ArrayList<CategoryVO>();
		ResultSet rs = null;
		try {
			stat = conn.prepareStatement(sql);
			stat.setString(1, Calendar.getInstance().get(Calendar.YEAR)+"%");
			rs = stat.executeQuery();
			while (rs.next()) {
				int id = rs.getInt(1);
				int parentID = rs.getInt(2);
				String name = rs.getString(3);
				int hasCommodity=rs.getInt(4);
				int hasCategory=rs.getInt(5);
				String createDate=rs.getDate(6).toString()+" "+rs.getTime(6).toString();
				CategoryVO category = new CategoryVO(id, name, parentID,hasCommodity,hasCategory,createDate);
				poList.add(category);
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

	@Override
	public int create(CategoryVO po) {
		if(po==null)return ResultMessage.FAIL;
		if(po.getName()==null||"".equals(po.getName())||po.getParentID()<0)return ResultMessage.FAIL;
		Connection conn = DBUtils.getConnection();
		PreparedStatement stat=null;
		System.out.println(po);
		int parentID=po.getParentID();
		String name=po.getName();
		String sql="insert into category(name,parent_id,createddate) values(?,?,?)";
		
		/*更新父节点分类的hasCategory属性为true*/
		if(parentID!=0){
			increaseHasCategory(parentID);
		}
		/*添加新的分类节点到数据库中*/
		try {
			stat=conn.prepareStatement(sql);
			stat.setString(1, name);
			stat.setInt(2, parentID);
			stat.setString(3, new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date()));
			stat.execute();
		} catch (SQLException e) {
			e.printStackTrace();
			return ResultMessage.DUPLICATE;
		}finally{
			DBUtils.closeConnection(conn);
			DBUtils.closeStateMent(stat);
		}
		return ResultMessage.SUCCESS;
	}
	@Override
	public boolean isInit() {
		Connection conn = DBUtils.getConnection();
		PreparedStatement stat=null;
		String sql="select *from category where createddate like ?";
		try {
			stat=conn.prepareStatement(sql);
			stat.setString(1, "%"+Calendar.getInstance().get(Calendar.YEAR)+"%");
			if(stat.executeQuery().next())return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DBUtils.closeConnection(conn);
			DBUtils.closeStateMent(stat);
		}
		return false;
	}
	private void increaseHasCategory(int parentID) {
		Connection conn = DBUtils.getConnection();
		PreparedStatement stat=null;
		String updateSql="update category set hascategory=hascategory+1 where id=?";
		try {
			stat=conn.prepareStatement(updateSql);
			stat.setInt(1, parentID);
			stat.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DBUtils.closeConnection(conn);
			DBUtils.closeStateMent(stat);
		}
	}

	public static void main(String[] args) {
		System.out.println(new CategoryDaoImpl().deleteCategoryByID(50));
//		new CategoryDaoImpl().create(new CategoryVO("日光灯5",50));
	}

	@Override
	public boolean deleteCategoryByID(int id) {
		if(id<=0)return false;
		Connection conn =DBUtils.getConnection();
		PreparedStatement stat=null;
		String sqlDel="delete from category where id=?";
		boolean flag=true;
		/*删除本节点*/
		try {
			stat=conn.prepareStatement(sqlDel);
			stat.setInt(1, id);
			if(stat.executeUpdate()!=1){
				flag= false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			flag= false;
		}
		
		if(!flag){
			DBUtils.closeConnection(conn);
			DBUtils.closeStateMent(stat);
			return false;
		}
		
		/*删除子节点*/
		String sqlSel="select id from category where parent_id=?";
		ResultSet rs=null;
		try {
			stat=conn.prepareStatement(sqlSel);
			stat.setInt(1, id);
			rs=stat.executeQuery();
			while(rs.next()){
				id=rs.getInt(1);
				if(!deleteCategoryByID(id)){
					DBUtils.closeConnection(conn);
					DBUtils.closeStateMent(stat);
					return false;
				};
			}
			
			/*更新父节点的hascategory字段值*/
			reduceHasCategoryOfParent(id);
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DBUtils.closeConnection(conn);
			DBUtils.closeStateMent(stat);
			DBUtils.closeResultSet(rs);
		}
		
		return true;
	}

	private void reduceHasCategoryOfParent(int id) {
		Connection conn = DBUtils.getConnection();
		PreparedStatement stat=null;
		String sqlSel="select parent_id from category where id=?";
		String sqlUpd="update category set hascategory=hascategory-1 where id=?";
		ResultSet rs=null;
		try {
			stat=conn.prepareStatement(sqlSel);
			stat.setInt(1, id);
			rs=stat.executeQuery();
			if(rs.next()){
				int parentID=rs.getInt(1);
				stat=conn.prepareStatement(sqlUpd);
				stat.setInt(1, parentID);
				stat.execute();	
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DBUtils.closeConnection(conn);
			DBUtils.closeStateMent(stat);
			DBUtils.closeResultSet(rs);
		}
		
	}

	@Override
	public boolean update(CategoryVO po) {
		if(po==null||po.getId()<=0||po.getName()==null||"".equals(po.getName()))return false;
		Connection conn = DBUtils.getConnection();
		PreparedStatement stat=null;
		String sql="update  category set name=? where id=?";
		int id=po.getId();
		String name=po.getName();
		try {
			stat=conn.prepareStatement(sql);
			stat.setString(1, name);
			stat.setInt(2, id);
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
	public void reduceHasCommodity(int categoryID) {
		if(categoryID<=0)return;
		Connection conn = DBUtils.getConnection();
		PreparedStatement stat=null;
		String sql="update category set hascommodity=hascommodity-1 where id=?";
		try {
			stat=conn.prepareStatement(sql);
			stat.setInt(1,categoryID);
			stat.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DBUtils.closeConnection(conn);
			DBUtils.closeStateMent(stat);
		}
	}

	@Override
	public void increaseHasCommodity(int categoryID) {
		if(categoryID<=0)return;
		Connection conn = DBUtils.getConnection();
		PreparedStatement stat=null;
		String sql="update category set hascommodity=hascommodity+1 where id=?";
		try {
			stat=conn.prepareStatement(sql);
			stat.setInt(1,categoryID);
			stat.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			DBUtils.closeConnection(conn);
			DBUtils.closeStateMent(stat);
		}
	}

	@Override
	public boolean delete(CategoryVO order) {
		return false;
		
	}

	
	
}
