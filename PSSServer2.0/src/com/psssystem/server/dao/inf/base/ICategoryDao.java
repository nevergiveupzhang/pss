package com.psssystem.server.dao.inf.base;

import java.sql.Connection;
import java.util.List;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

import com.psssystem.connection.vo.CategoryVO;


public interface ICategoryDao extends IBaseDao<CategoryVO>{
	public void addCategoryToTreeNode(DefaultMutableTreeNode parent, int ParentID);

	public boolean deleteCategoryByID(int id);
	
	public void reduceHasCommodity(int categoryID);

	public void increaseHasCommodity(int categoryID);

	boolean isInit();

}
