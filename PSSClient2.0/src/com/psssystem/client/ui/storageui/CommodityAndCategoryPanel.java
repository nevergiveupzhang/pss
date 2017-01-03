package com.psssystem.client.ui.storageui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import com.psssystem.client.controller.storagecontroller.ICategoryController;
import com.psssystem.client.controller.storagecontroller.ICommodityController;
import com.psssystem.client.controllerimpl.storagecontrollerimpl.CategoryControllerImpl;
import com.psssystem.client.controllerimpl.storagecontrollerimpl.CommodityControllerImpl;
import com.psssystem.client.data.ColumnsConstants;
import com.psssystem.client.data.OperationInfoConstants;
import com.psssystem.client.data.OrderInfoConstants;
import com.psssystem.client.ui.chooser.CommodityChooser;
import com.psssystem.client.ui.commodity.CommodityTreePanel;
import com.psssystem.connection.vo.CategoryVO;
import com.psssystem.connection.vo.CommodityVO;


public class CommodityAndCategoryPanel extends JSplitPane {
	private CommodityTreePanel treeScrollPane;
	private JScrollPane centerPane;
	private JButton categoryAddBtn;
	private JButton categoryDelBtn;
	private JButton categoryUpdateBtn;
	private JButton commodityAddBtn;
	private JButton commodityDelBtn;
	private JButton commodityUpdateBtn;
	private JButton commoditySearchBtn;
	private JTextField searchTF;
	private JTable searchTable;
	private TableModel tableModel;
	private Object[][]cells=new Object[][]{};
	
	private CommodityChooser chooser;
	
	private ICommodityController commodityController;
	private ICategoryController categoryController;
	
	public CommodityAndCategoryPanel(){
		this.commodityController=new CommodityControllerImpl();
		this.categoryController=new CategoryControllerImpl();
		init();
	}

	private void init() {
		makeComponents();
		addListeners();
	}
	
	private void addListeners() {
		commodityAddBtn.addActionListener(new CommodityListener(OperationInfoConstants.ADD));
		commodityDelBtn.addActionListener(new CommodityListener(OperationInfoConstants.DELETE));
		commodityUpdateBtn.addActionListener(new CommodityListener(OperationInfoConstants.UPDATE));
		commoditySearchBtn.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				Thread t=new Thread(new Runnable(){

					@Override
					public void run() {
						String info=searchTF.getText();
						if(info==null||"".equals(info)) return;
						List<CommodityVO> result=commodityController.searchCommodity(info);
						cells=getCells(result);
						tableModel=new DefaultTableModel(cells,ColumnsConstants.COMMODITY_COLUMNS_CREATE);
						searchTable=new JTable(tableModel);
						centerPane.setViewportView(searchTable);
						centerPane.validate();						
					}
				});

				t.start();
			}

			private Object[][] getCells(List<CommodityVO> result) {
				Object [][]temp=new Object[result.size()][];
				
				for(int i=0;i<result.size();i++){
					CommodityVO vo=result.get(i);
					temp[i]=new Object[]{vo.getId(),vo.getName(),vo.getType(),vo.getCategoryID(),vo.getStockAmount(),vo.getPurchasingPrice(),vo.getSellingPrice(),vo.getRecentPurchasingPrice(),vo.getRecentSellingPrice(),vo.getWarningLine()};
				}
				return temp;
			}
			
		});
		
		if(categoryController.getAll().size()==0){
			categoryAddBtn.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent e) {
					JOptionPane.showMessageDialog(null, "请联系财务人员进行期初商品建账！");
				}
				
			});
		}else{
			categoryAddBtn.addActionListener(new CategoryListener(OperationInfoConstants.ADD));	
		}
		
		categoryDelBtn.addActionListener(new CategoryListener(OperationInfoConstants.DELETE));
		categoryUpdateBtn.addActionListener(new CategoryListener(OperationInfoConstants.UPDATE));
	}

	private void makeComponents() {
		treeScrollPane=new CommodityTreePanel();
		
		JPanel opPanel=new JPanel();
		categoryAddBtn=new JButton("添加分类");
		categoryDelBtn=new JButton("删除分类");
		categoryUpdateBtn=new JButton("修改分类");
		commodityAddBtn=new JButton("增加商品");
		commodityDelBtn=new JButton("删除商品");
		commodityUpdateBtn=new JButton("修改商品");
		commoditySearchBtn=new JButton("搜索商品");
		searchTF=new JTextField(10);
		tableModel=new DefaultTableModel(cells,ColumnsConstants.COMMODITY_COLUMNS_CREATE);
		searchTable=new JTable(tableModel);
		JPanel topPanel=new JPanel();
		opPanel.setLayout(new BorderLayout());
		centerPane=new JScrollPane();
		centerPane.setViewportView(searchTable);
		topPanel.add(categoryAddBtn);
		topPanel.add(categoryDelBtn);
		topPanel.add(categoryUpdateBtn);
		topPanel.add(commodityAddBtn);
		topPanel.add(commodityDelBtn);
		topPanel.add(commodityUpdateBtn);
		topPanel.add(commoditySearchBtn);
		topPanel.add(searchTF);
		
		opPanel.add(topPanel,BorderLayout.NORTH);
		opPanel.add(centerPane,BorderLayout.CENTER);
		
		this.setLeftComponent(treeScrollPane);
		this.setRightComponent(opPanel);
	}

	private class CommodityListener implements ActionListener{
		private String op;
		
		public CommodityListener(String op){
			this.op=op;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			DefaultMutableTreeNode selectedNode=(DefaultMutableTreeNode) treeScrollPane.getTree().getLastSelectedPathComponent();
			if(selectedNode==null) {
				JOptionPane.showMessageDialog(null, "请选择节点！");
				return;
			}
			
			/*添加商品*/
			if(op.equals(OperationInfoConstants.ADD)){
				/*首先判断节点是否合法*/
				if(!treeScrollPane.isLeafCategoryNode(selectedNode)) {
					JOptionPane.showMessageDialog(null, "非法节点！");
					return;
				}

				/*从对话框中创建商品对象*/
				CommodityVO commodity = null;
				if(chooser==null)chooser=new CommodityChooser(); 
				if(chooser.showDialog(CommodityAndCategoryPanel.this,"Connect"))commodity=chooser.getCommodity();
				else return;
				
				/*新创建的商品名称已经存在*/
				if(treeScrollPane.isCommodityExists(commodity.getName())) {
					JOptionPane.showMessageDialog(null, "商品已存在！");
					return;
				}
				
				/*根据对话框创建的商品对象以及所选择的分类节点更新库存信息*/
				commodity.setCategoryID(treeScrollPane.getSelectedCategory(selectedNode.toString()).getId());
				if(!commodityController.addCommodity(commodity)){
					JOptionPane.showMessageDialog(null, "失败！");
					return;
				};
				
				/*根据商品名称添加商品到JTree*/
				treeScrollPane.freshTree(selectedNode,commodity.getName(),OperationInfoConstants.ADD,OrderInfoConstants.COMMODITY);
				
			}
			/*删除商品*/
			else if(op.equals(OperationInfoConstants.DELETE)){
				if(!treeScrollPane.isCommodityNode(selectedNode)) {
					JOptionPane.showMessageDialog(null, "非商品节点！");
					return;
				}
				treeScrollPane.freshTree(selectedNode,"",OperationInfoConstants.DELETE,OrderInfoConstants.COMMODITY);
				commodityController.deleteCommodity(treeScrollPane.getSelectedCommodity(selectedNode.toString()));
			}
			/*修改商品*/
			else if(op.equals(OperationInfoConstants.UPDATE)){
				if(!treeScrollPane.isCommodityNode(selectedNode)) {
					JOptionPane.showMessageDialog(null, "非商品节点！");
					return;
				}
				/*从对话框中创建商品对象*/
				CommodityVO commodity = null;
				CommodityVO selectedCommodity=treeScrollPane.getSelectedCommodity(selectedNode.toString());
				if(chooser==null){
					chooser=new CommodityChooser();
					chooser.setCommodity(selectedCommodity);
				} 
				if(chooser.showDialog(CommodityAndCategoryPanel.this,"Connect")){
					commodity=chooser.getCommodity();
				}else{
					return;
				}
				commodity.setId(selectedCommodity.getId());
				if(!commodityController.updateCommodity(commodity)){
					JOptionPane.showMessageDialog(null, "失败！");
					return;
				}
				treeScrollPane.freshTree(selectedNode,commodity.getName(),OperationInfoConstants.UPDATE,OrderInfoConstants.COMMODITY);
			}
		}
	}
	
	
	
	private class CategoryListener implements ActionListener{
		private String operation;
		public CategoryListener(String operation){
			this.operation=operation;
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			DefaultMutableTreeNode selectedNode=(DefaultMutableTreeNode) treeScrollPane.getTree().getLastSelectedPathComponent();
			/*判断用户是否选择了分类节点*/
			if(selectedNode==null){
				JOptionPane.showMessageDialog(null, "请选择分类！");
				return;
			}
			/*判断用户选择的节点是否为商品节点*/
			if(treeScrollPane.isCommodityNode(selectedNode)){
				JOptionPane.showMessageDialog(null, "商品节点！");
				return;
			}
			
			CategoryVO selectedCategory=treeScrollPane.getSelectedCategory(selectedNode.toString());
			
			if(operation.equals(OperationInfoConstants.ADD)){
				/* 判断用户选择的分类下是否有商品 ,与删除时的判断不同，这里表示分类下面的直接子节点不能有商品*/
				if(treeScrollPane.isHasCommodity(selectedNode,OperationInfoConstants.ADD)){
					JOptionPane.showMessageDialog(null, "分类下已存在商品！");
					return;
				}
				String newName=JOptionPane.showInputDialog("商品分类名称：");
				/*判断对话框中用户输入的商品分类名称是否为空*/
				if(newName==null||newName.equals("")){
					return;
				}
				/*判断用户输入的分类是否已经存在于现有的分类中*/
				if(treeScrollPane.isCategoryExists(newName)){
					JOptionPane.showMessageDialog(null, "名称已存在！");
					return;
				}
				System.out.println("newCategory="+new CategoryVO(newName,selectedCategory.getId()));
				if(!categoryController.addCategory(new CategoryVO(newName,selectedCategory.getId()))){
					JOptionPane.showMessageDialog(null, "失败！");
					return;
				};
				treeScrollPane.freshTree(selectedNode,newName,OperationInfoConstants.ADD,OrderInfoConstants.CATEGORY);
			}else if(operation.equals(OperationInfoConstants.DELETE)){
				/*判断所选择的节点不是根节点*/
				if(selectedNode.getParent()==null){
					JOptionPane.showMessageDialog(null, "根节点！");
					return;
				}
				
				/*判断分类下是否存在商品,与添加时的判断不同，这里表示分类下面的所有子节点是否有商品*/
				if(treeScrollPane.isHasCommodity(selectedNode,OperationInfoConstants.DELETE)){
					JOptionPane.showMessageDialog(null, "分类下已存在商品！");
					return;
				}
				treeScrollPane.freshTree(selectedNode,"",OperationInfoConstants.DELETE,OrderInfoConstants.CATEGORY);
				categoryController.deleteCategory(selectedCategory);
			}else if(operation.equals(OperationInfoConstants.UPDATE)){
				/*判断所选择的节点不是根节点*/
				if(selectedNode.getParent()==null){
					JOptionPane.showMessageDialog(null, "根节点！");
					return;
				}
				String newName=JOptionPane.showInputDialog("商品分类名称：");
				/*判断用户输入的商品名称是否为空*/
				if(newName==null||newName.equals("")) return;
				/*判断用户输入的商品名称是否已经存在*/
				if(treeScrollPane.isCategoryExists(newName)) {
					JOptionPane.showMessageDialog(null, "名称已存在！");
					return;
				}
				selectedCategory.setName(newName);
				if(!categoryController.updateCategory(selectedCategory)){
					JOptionPane.showMessageDialog(null, "失败！");
					return;
				};
				treeScrollPane.freshTree(selectedNode,newName,OperationInfoConstants.UPDATE,OrderInfoConstants.CATEGORY);
				
			}
		}
		
	}
}


