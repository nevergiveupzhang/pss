package com.psssystem.client.controller.storagecontroller;

import java.util.*;

import javax.swing.JTree;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultTreeModel;

import com.psssystem.connection.vo.CategoryVO;

//import com.psssystem.client.vo.Category;

public interface ICategoryController{
	DefaultTreeModel getCategoryTreeModel();

	List<CategoryVO> getAll();

	boolean addCategory(CategoryVO vo);
	boolean initCategory(CategoryVO vo);
	boolean deleteCategory(CategoryVO vo);

	boolean updateCategory(CategoryVO vo);
	
}
