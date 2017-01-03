package com.psssystem.connection.service.storageservice;

import java.rmi.*;
import java.util.List;

import javax.swing.JTree;
import javax.swing.tree.DefaultTreeModel;

import com.psssystem.connection.vo.CategoryVO;

public interface ICategoryService extends Remote{
	public DefaultTreeModel getCategoryTreeModel() throws RemoteException;
	public List<CategoryVO> getCategories() throws RemoteException;
	public boolean addCategory(CategoryVO category) throws RemoteException;
	public boolean initCategory(CategoryVO category) throws RemoteException;
	public boolean deleteCategory(CategoryVO category) throws RemoteException;
	public boolean updateCategory(CategoryVO category) throws RemoteException;
}
