package com.psssystem.server.serviceimpl.storageserviceimpl;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;



import com.psssystem.connection.service.storageservice.ICategoryService;
import com.psssystem.connection.vo.CategoryVO;
import com.psssystem.connection.vo.CustomerVO;
import com.psssystem.connection.vo.ResultMessage;
import com.psssystem.server.dao.impl.base.CategoryDaoImpl;
import com.psssystem.server.dao.inf.base.ICategoryDao;
import com.psssystem.server.util.DBUtils;

public class CategoryServiceImpl extends UnicastRemoteObject implements ICategoryService{
	private ICategoryDao categoryDao;
	private final String initFileName="resource/category.txt";
	public CategoryServiceImpl() throws RemoteException {
		super();
		categoryDao=new CategoryDaoImpl();
	}

	@Override
	public DefaultTreeModel getCategoryTreeModel() throws RemoteException {
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("分类");
		categoryDao.addCategoryToTreeNode(root, 0);
        DefaultTreeModel model = new DefaultTreeModel(root,true);
		return model; 
	}

	@Override
	public boolean addCategory(CategoryVO vo) throws RemoteException {
		if(!categoryDao.isInit())return false;
		int status=categoryDao.create(vo);
		if(status==ResultMessage.SUCCESS){
			return true;
		}else{
			return false;
		}
	}

	@Override
	public boolean deleteCategory(CategoryVO vo) throws RemoteException {
		return categoryDao.deleteCategoryByID(vo.getId());
	}

	@Override
	public boolean updateCategory(CategoryVO vo) throws RemoteException {
		return categoryDao.update(vo);
	}
	
	@Override
	public List<CategoryVO> getCategories() throws RemoteException {
		return categoryDao.getAll();
	}


	@Override
	public boolean initCategory(CategoryVO category) throws RemoteException {
		System.out.println("server-"+category);
		int status=categoryDao.create(category);
		if(status!=ResultMessage.SUCCESS){
			return false;
		}
		FileWriter writer = null;
		try {
			writer = new FileWriter(initFileName, true);
			for (CategoryVO vo:getCategories()) {
				writer.write(vo.getId() + "|"+vo.getName()+"|"+vo.getParentID()+"|"+vo.getHasCategoryAmount()+"|"+vo.getHasCommodityAmount()+"|"+vo.getCreatedDate()+"\n");
			}
			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	

}
