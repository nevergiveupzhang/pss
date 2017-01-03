package com.psssystem.client.controllerimpl.storagecontrollerimpl;

import java.rmi.RemoteException;
import java.util.*;

import javax.swing.JTree;
import javax.swing.tree.DefaultTreeModel;

import com.psssystem.client.controller.storagecontroller.ICategoryController;
import com.psssystem.client.util.ConnectToServer;
import com.psssystem.connection.service.storageservice.ICategoryService;
import com.psssystem.connection.vo.CategoryVO;

public class CategoryControllerImpl implements ICategoryController{
	private ICategoryService categoryService;
	public CategoryControllerImpl(){
		ConnectToServer.connect();
		categoryService=ConnectToServer.categoryService;
	}
	@Override
	public DefaultTreeModel getCategoryTreeModel() {
		DefaultTreeModel model=null;
		try {
			 model=categoryService.getCategoryTreeModel();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return model;
	}
	@Override
	public List<CategoryVO> getAll() {
		List<CategoryVO> categoryList=null;
		try {
			categoryList=categoryService.getCategories();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return categoryList;
	}
	@Override
	public boolean addCategory(CategoryVO vo) {
		boolean status=false;
		try {
			status=categoryService.addCategory(vo);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return status;
	}
	@Override
	public boolean deleteCategory(CategoryVO vo) {
		boolean status=false;
		try {
			status=categoryService.deleteCategory(vo);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return status;
		
	}
	@Override
	public boolean updateCategory(CategoryVO vo) {
		boolean status=false;
		try {
			status=categoryService.updateCategory(vo);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return status;
	}
	@Override
	public boolean initCategory(CategoryVO vo) {
		boolean status=false;
		try {
			status=categoryService.initCategory(vo);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return status;
	}
	

}
