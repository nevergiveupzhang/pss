package com.psssystem.client.ui.finaceui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.tree.DefaultMutableTreeNode;

import com.psssystem.client.controller.financecontroller.IAccountController;
import com.psssystem.client.controller.salescontroller.ICustomerController;
import com.psssystem.client.controller.storagecontroller.ICategoryController;
import com.psssystem.client.controller.storagecontroller.ICommodityController;
import com.psssystem.client.controllerimpl.financecontrollerimpl.AccountControllerImpl;
import com.psssystem.client.controllerimpl.salescontrollerimpl.CustomerControllerImpl;
import com.psssystem.client.controllerimpl.storagecontrollerimpl.CategoryControllerImpl;
import com.psssystem.client.controllerimpl.storagecontrollerimpl.CommodityControllerImpl;
import com.psssystem.client.data.ColumnsConstants;
import com.psssystem.client.data.OperationInfoConstants;
import com.psssystem.client.data.OrderInfoConstants;
import com.psssystem.client.ui.chooser.AccountChooser;
import com.psssystem.client.ui.chooser.CommodityChooser;
import com.psssystem.client.ui.chooser.CustomerChooser;
import com.psssystem.client.ui.commodity.CommodityTreePanel;
import com.psssystem.connection.vo.AccountVO;
import com.psssystem.connection.vo.CategoryVO;
import com.psssystem.connection.vo.CommodityVO;
import com.psssystem.connection.vo.CustomerVO;

public class BeginAccountPanel extends JSplitPane {
	private CommodityTreePanel leftPanel;
	private JPanel rightPanel;
	
	private JScrollPane scrollPane;
	
	private JTable customerTable;
	private TableModel customerModel;
	
	private JTable accountTable;
	private TableModel accountModel;
	
	private JButton commodityAddBtn;
	private JButton categoryAddBtn;
	private JButton customerAddBtn;
	private JButton customerOkBtn;
	private JButton accountAddBtn;
	private JButton accountOkBtn;
	
	private CustomerChooser customerChooser;
	private CommodityChooser commodityChooser;
	private AccountChooser accountChooser;
	
	private Object[][]customerCells=new Object[][]{};
	private Object[][]accountCells=new Object[][]{};
	
	private List<AccountVO> accountList=new ArrayList<AccountVO>();
	private Set<CustomerVO> customerList=new HashSet<CustomerVO>();
	
	private final IAccountController accountController=new AccountControllerImpl();
	private final ICustomerController customerController=new CustomerControllerImpl();
	private final ICommodityController commodityController=new CommodityControllerImpl();
	private final ICategoryController categoryController=new CategoryControllerImpl();
	
	public BeginAccountPanel(String username) {
		init();
	}
	private void init() {
		makeComponents();
		addListeners();
	}
	private void addListeners() {
		System.out.println("accountsize="+accountController.getAllAccounts().size());
		if(accountController.getAllAccounts().size()!=0){
			accountAddBtn.addActionListener(new FirstInitListener(OrderInfoConstants.ACCOUNT));
			accountOkBtn.addActionListener(new FirstInitListener(OrderInfoConstants.ACCOUNT));
		}else{
			accountAddBtn.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent e) {
					if(accountChooser==null)accountChooser=new AccountChooser();
					if(!accountChooser.showDialog(BeginAccountPanel.this,"connect")){
						return;
					}
					accountList.add(accountChooser.getAccount());
					accountCells=increaseAccountCells(accountChooser.getAccount());
					Thread t=new Thread(new InitRunnable(OrderInfoConstants.ACCOUNT));
					t.start();
				}
				private Object[][] increaseAccountCells(AccountVO account) {
					Object[][]temp=new Object[accountCells.length+1][];
					for(int i=0;i<accountCells.length;i++){
						temp[i]=accountCells[i];
					}
					temp[accountCells.length]=new Object[]{account.getName(),account.getSum()};
					return temp;
				}
				
			});
			
			accountOkBtn.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent e) {
					System.out.println(accountList.get(0).getName());
					accountController.initAccounts(accountList);
					accountList.removeAll(accountList);
					accountCells=new Object[][]{};
					Thread t=new Thread(new InitRunnable(OrderInfoConstants.ACCOUNT));
					t.start();
				}
				
			});
		}
		if(customerController.getAllCustomers().size()!=0){
			customerAddBtn.addActionListener(new FirstInitListener(OrderInfoConstants.CUSTOMER));
			customerOkBtn.addActionListener(new FirstInitListener(OrderInfoConstants.CUSTOMER));
		}else{
			customerAddBtn.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent e) {
					if(customerChooser==null)customerChooser=new CustomerChooser();
					if(!customerChooser.showDialog(BeginAccountPanel.this, "connect")){
						return;
					}
					customerList.add(customerChooser.getCustomer());
					customerCells=getCustomerCells(customerChooser.getCustomer());
					Thread t=new Thread(new InitRunnable(OrderInfoConstants.CUSTOMER));
					t.start();
				}
				private Object[][] getCustomerCells(CustomerVO customer) {
					Object[][]temp=new Object[customerCells.length+1][];
					for(int i=0;i<customerCells.length;i++){
						temp[i]=customerCells[i];
					}
					temp[customerCells.length]=new Object[]{ customer.getName(),
							customer.getType(), customer.getLevel(),
							customer.getPhoneNumber(), customer.getAddr(),
							customer.getPostcode(), customer.getEmail(),
							customer.getDefaultSalesman() };
					return temp;
				}
				
			});
			
			customerOkBtn.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent e) {
					customerController.addCustomers(customerList);
					customerList.removeAll(customerList);
					customerCells=new Object[][]{};
					Thread t=new Thread(new InitRunnable(OrderInfoConstants.CUSTOMER));
					t.start();
				}
				
			});
		}
		if(commodityController.getAllCommodities().size()!=0){
			commodityAddBtn.addActionListener(new FirstInitListener(OrderInfoConstants.COMMODITY));
		}
		else{
			commodityAddBtn.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent e) {
					DefaultMutableTreeNode selectedNode=(DefaultMutableTreeNode) leftPanel.getTree().getLastSelectedPathComponent();
					if(selectedNode==null){
						JOptionPane.showMessageDialog(null, "请选择节点！");
						return;
					}
					if(!leftPanel.isLeafCategoryNode(selectedNode)){
						JOptionPane.showMessageDialog(null, "非法节点！");
						return;
					}
					
					if(commodityChooser==null){ commodityChooser=new CommodityChooser();}
					if(!commodityChooser.showDialog(BeginAccountPanel.this,"connect")){
						return;
					}
					CommodityVO commodity=commodityChooser.getCommodity();
					if(leftPanel.isCommodityExists(commodity.getName())){
						return;
					}
					leftPanel.freshTree(selectedNode, commodity.getName(),OperationInfoConstants.ADD, OrderInfoConstants.COMMODITY);
					commodity.setCategoryID(leftPanel.getSelectedCategory(selectedNode.toString()).getId());
					System.out.println(commodity);
					commodityController.initCommodity(commodity);
				}
				
			});
		}
		if(categoryController.getAll().size()!=0){
			categoryAddBtn.addActionListener(new FirstInitListener(OrderInfoConstants.CATEGORY));
		}else{
			categoryAddBtn.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent e) {
					DefaultMutableTreeNode selectedNode=(DefaultMutableTreeNode) leftPanel.getTree().getLastSelectedPathComponent();
					if(selectedNode==null){
						JOptionPane.showMessageDialog(null, "请选择节点！");
						return;
					}
					
					if(leftPanel.isCommodityNode(selectedNode)){
						JOptionPane.showMessageDialog(null, "商品节点！");
						return;
					}
					
					if(leftPanel.isHasCommodity(selectedNode, OperationInfoConstants.ADD)){
						JOptionPane.showMessageDialog(null, "分类下存在商品！");
						return;
					}
					String newName=JOptionPane.showInputDialog("分类名称");
					if(newName==null||"".equals(newName)){
						return;
					}
					
					leftPanel.freshTree(selectedNode, newName, OperationInfoConstants.ADD, OrderInfoConstants.CATEGORY);
					System.out.println(new CategoryVO(newName,leftPanel.getSelectedCategory(selectedNode.toString()).getId()));
					categoryController.initCategory(new CategoryVO(newName,leftPanel.getSelectedCategory(selectedNode.toString()).getId()));
				}
				
			});
		}
	}
	
	private void makeComponents() {
		leftPanel=new CommodityTreePanel();
		rightPanel=new JPanel();
		
		JPanel topPanel=new JPanel();
		commodityAddBtn=new JButton("添加商品");
		categoryAddBtn=new JButton("添加分类");
		customerAddBtn=new JButton("添加客户");
		accountAddBtn=new JButton("添加账户");
		
		topPanel.add(commodityAddBtn);
		topPanel.add(categoryAddBtn);
		topPanel.add(customerAddBtn);
		topPanel.add(accountAddBtn);
		
		scrollPane=new JScrollPane();
		customerModel=new DefaultTableModel(customerCells,ColumnsConstants.CUSTOMER_COLUMNS_CREATE);
		customerTable=new JTable(customerModel);
		scrollPane.setViewportView(customerTable);
		
		JPanel bottomPanel=new JPanel();
		customerOkBtn=new JButton("提交客户");
		accountOkBtn=new JButton("提交账户");
		bottomPanel.add(customerOkBtn);
		bottomPanel.add(accountOkBtn);
		
		
		rightPanel.setLayout(new BorderLayout());
		rightPanel.add(topPanel,BorderLayout.NORTH);
		rightPanel.add(scrollPane,BorderLayout.CENTER);
		rightPanel.add(bottomPanel,BorderLayout.SOUTH);
		
		this.setLeftComponent(leftPanel);
		this.setRightComponent(rightPanel);
	}
	
	
	private class InitRunnable implements Runnable{
		String op;
		public InitRunnable(String op){
			this.op=op;
		}
		@Override
		public void run() {
			switch(op){
			case OrderInfoConstants.ACCOUNT:
				initAccount();
				break;
			case OrderInfoConstants.CUSTOMER:
				initCustomer();
				break;
			}
		}
		private void initCustomer() {
			customerModel=new DefaultTableModel(customerCells,ColumnsConstants.CUSTOMER_COLUMNS_CREATE);
			customerTable=new JTable(customerModel);
			scrollPane.setViewportView(customerTable);
			scrollPane.setViewportView(customerTable);
			scrollPane.validate();
		}

		
		private void initAccount() {
			accountModel=new DefaultTableModel(accountCells,ColumnsConstants.ACCOUNT_COLUMNS_CREATE);
			accountTable=new JTable(accountModel);
			scrollPane.setViewportView(accountTable);
			scrollPane.validate();
		}
		
	}

	
	private class FirstInitListener implements ActionListener{
		private final String info;
		public FirstInitListener(String info){
			this.info=info;
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			JOptionPane.showMessageDialog(null, info+"已初始化！");
			return;
		}
	}
}
