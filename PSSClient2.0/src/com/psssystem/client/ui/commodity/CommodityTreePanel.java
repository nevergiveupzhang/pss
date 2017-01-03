package com.psssystem.client.ui.commodity;

import java.util.Enumeration;
import java.util.List;

import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import com.psssystem.client.controller.storagecontroller.ICategoryController;
import com.psssystem.client.controller.storagecontroller.ICommodityController;
import com.psssystem.client.controllerimpl.storagecontrollerimpl.CategoryControllerImpl;
import com.psssystem.client.controllerimpl.storagecontrollerimpl.CommodityControllerImpl;
import com.psssystem.client.data.OperationInfoConstants;
import com.psssystem.client.data.OrderInfoConstants;
import com.psssystem.connection.vo.CategoryVO;
import com.psssystem.connection.vo.CommodityVO;

public class CommodityTreePanel extends JScrollPane {
	private ICategoryController categoryController;
	private ICommodityController commodityController;
	private List<CategoryVO> categoryList;
	private List<CommodityVO> commodityList;
	
	private JTree tree;
	private DefaultTreeModel model;
	
	public CommodityTreePanel(){
		this.categoryController=new CategoryControllerImpl();
		this.commodityController=new CommodityControllerImpl();
		this.categoryList=categoryController.getAll();
		this.commodityList=commodityController.getAllCommodities();
		init();
	}
	private void init() {
		model=categoryController.getCategoryTreeModel();
		tree=new JTree(model);
		addCommodityToTreeModel();
		this.setViewportView(tree);
	}
	
	/*向分类树里面添加商品，构建商品树*/
	private void addCommodityToTreeModel() {
		/*遍历商品列表和分类列表，将商品添加到相应分类节点下*/
		for(int i=0;i<commodityList.size();i++){
			CommodityVO commodity=commodityList.get(i);
			String commodityName=commodity.getName();
			
			int categoryID=commodity.getCategoryID();
			for(int j=0;j<categoryList.size();j++){
				CategoryVO category=categoryList.get(j);
				String categoryName=category.getName();
				if(category.getId()==categoryID){
					addCommodityToNamedCategoryNode(categoryName,commodityName);		
				}
			}
		}
	}

	/*从根节点开始遍历分类树，将商品添加到指定的分类节点下*/
	private void addCommodityToNamedCategoryNode(String categoryName, String commodityName) {
		DefaultMutableTreeNode root=(DefaultMutableTreeNode) model.getRoot();
		Enumeration e=root.breadthFirstEnumeration();
		while(e.hasMoreElements()){
			DefaultMutableTreeNode node=(DefaultMutableTreeNode) e.nextElement();
			if(node.toString().equals(categoryName)){
				DefaultMutableTreeNode newNode=new DefaultMutableTreeNode(commodityName,false);
				model.insertNodeInto(newNode, node, node.getChildCount());
				TreeNode[] nodes=model.getPathToRoot(newNode);
				TreePath path=new TreePath(nodes);
				tree.scrollPathToVisible(path);
			}
		}
	}

	/*能够添加商品的分类节点*/
	public boolean isLeafCategoryNode(DefaultMutableTreeNode selectedNode) {
		/*所选择的的节点为商品节点，非法*/
		if(!selectedNode.getAllowsChildren()) return false;
		
		for(int i=0;i<categoryList.size();i++){
			CategoryVO category=categoryList.get(i);
			String name=category.getName();
			System.out.println(name);
			/*所选择的的节点是分类节点并且分类节点下不再有子分类，即为合法分类节点*/
			if(name.equals(selectedNode.toString())&&category.getHasCategoryAmount()==0){
				return true;
			}
		}
		return false;
	}
	
	public boolean isCommodityNode(DefaultMutableTreeNode selectedNode){
		return !selectedNode.getAllowsChildren();
	}
	public void freshTree(DefaultMutableTreeNode selectedNode,String newName,String operation,String type) {
		/*更新商品树视图*/
		if(operation.equals(OperationInfoConstants.ADD)){
			DefaultMutableTreeNode newNode=new DefaultMutableTreeNode(newName);
			if(type.equals(OrderInfoConstants.COMMODITY))newNode.setAllowsChildren(false);
			model.insertNodeInto(newNode, selectedNode, selectedNode.getChildCount());
			TreeNode[] nodes=model.getPathToRoot(newNode);
			TreePath path=new TreePath(nodes);
			tree.scrollPathToVisible(path);		
		}else if(operation.equals(OperationInfoConstants.DELETE)){
			model.removeNodeFromParent(selectedNode);
		}else if(operation.equals(OperationInfoConstants.UPDATE)){
			selectedNode.setUserObject(newName);
		}
		/*每次更新完视图之后都重新从数据库获取categoryList和commodityList*/
		this.categoryList=categoryController.getAll();
		this.commodityList=commodityController.getAllCommodities();
	}
	
	/*根据选中的分类节点名称返回分类列表当中的CategoryVO*/
	public CategoryVO getSelectedCategory(String selectedName) {
		CategoryVO vo=null;
		for(int i=0;i<categoryList.size();i++){
			vo=categoryList.get(i);
			if(vo.getName().equals(selectedName)){
				return vo;
			}
		}
		/*选中的是根节点，则不在分类列表中，单独创建,且规定根节点的父节点ID为-1*/
		vo=new CategoryVO (0,selectedName,-1);
		return vo;
	}
	
	/*根据选中的商品节点名称返回商品列表中CommodityVO*/
	public CommodityVO getSelectedCommodity(String selectedName) {
		for(int i=0;i<commodityList.size();i++){
			CommodityVO commodity=commodityList.get(i);
			if(selectedName.equals(commodity.getName())){
				return commodity;
			}
		}
		return null;
	}
	public JTree getTree() {
		return tree;
	}
	public DefaultTreeModel getTreeModel() {
		return model;
	}
	public void setTree(JTree tree) {
		this.tree = tree;
	}
	public void setTreeModel(DefaultTreeModel treeModel) {
		this.model = treeModel;
	}
	
	/*判断新添加的商品是否已经存在于现有的商品列表中*/
	public boolean isCommodityExists(String newName) {
		for(int i=0;i<commodityList.size();i++){
			CommodityVO commodity=commodityList.get(i);
			if(newName.equals(commodity.getName())){
				return true;
			}
		}
		return false;
	}
	
	/*判断新添加的分类是否已经存在于现有的分类列表中*/
	public boolean isCategoryExists(String name) {
		DefaultMutableTreeNode root=(DefaultMutableTreeNode) model.getRoot();
		Enumeration e=root.breadthFirstEnumeration();
		while(e.hasMoreElements()){
			DefaultMutableTreeNode node=(DefaultMutableTreeNode) e.nextElement();
			if(node.getUserObject().equals(name)){
				return true;
			}
		}
		return false;
	}

	/*
	 * 遍历判断选中的分类节点以及子节点以确定是否有商品存在
	 * @param DefaultMutableTreeNode selectedNode
	 * */
	public boolean isHasCommodity(DefaultMutableTreeNode selectedNode, String op) {
		if(op.equals(OperationInfoConstants.ADD)){
			return !(getSelectedCategory(selectedNode.toString()).getHasCommodityAmount()==0);
		}else if(op.equals(OperationInfoConstants.DELETE)){
			Enumeration e=selectedNode.breadthFirstEnumeration();
			int hasCommodityAmount=0;
			while(e.hasMoreElements()){
				DefaultMutableTreeNode node=(DefaultMutableTreeNode) e.nextElement();
				hasCommodityAmount=getSelectedCategory(node.toString()).getHasCommodityAmount();
				if(hasCommodityAmount>0){
					return true;
				}
			}
		}
		
		return false;
	}
	public List<CommodityVO> getCommodityList() {
		return commodityList;
	}
	public void setCommodityList(List<CommodityVO> commodityList) {
		this.commodityList = commodityList;
	}
	
}
