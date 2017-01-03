package com.psssystem.client.ui.finaceui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import com.psssystem.client.controller.financecontroller.IAccountController;
import com.psssystem.client.controllerimpl.financecontrollerimpl.AccountControllerImpl;
import com.psssystem.client.data.ColumnsConstants;
import com.psssystem.client.data.OperationInfoConstants;
import com.psssystem.connection.vo.AccountVO;

public class AccountManagePanel extends JTabbedPane {
	private String username;

	/*添加账户*/
	private JPanel createAccountPanel;
	private JTextField accountNameTF;
	private JTextField accountSumTF;
	private JScrollPane addedScrollPane;
	private JTable addedTable;
	private TableModel addedModel;
	private JButton addToListBtn;
	private JButton addSubmitBtn;
	
	/*删除账户*/
	private JPanel deleteAccountPanel;
	private JScrollPane deleteScrollPane;
	private JTable deleteTable;
	private TableModel deleteModel;
	private JButton deleteBtn;
	
	/*查找账户*/
	private JPanel searchAccountPanel;
	private JTextField searchTF;
	private JButton searchBtn;
	private JScrollPane searchScrollPane;
	private JTable searchTable;
	private TableModel searchModel;

	private Object[][]addedCells=new Object[][]{};
	private Object[][]deleteCells=new Object[][]{};
	private Object[][]searchCells=new Object[][]{};
	
	private IAccountController accountController;
	private List<AccountVO> accountList;
	public AccountManagePanel(String username){
		username=username;
		accountController=new AccountControllerImpl();
		accountList=accountController.getAllAccounts();
		init();
	}
	private void init() {
		makeComponents();
		addListeners();
	}
	private void addListeners() {
		if(accountList.size()==0){
			addToListBtn.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent arg0) {
					JOptionPane.showMessageDialog(null, "还没有进行期初建账！");
				}
				
			});
			addSubmitBtn.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent arg0) {
					JOptionPane.showMessageDialog(null, "还没有进行期初建账！");
				}
				
			});
			return;
		}
		addToListBtn.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				if("".equals(accountNameTF.getText())||"".equals(accountSumTF.getText())){
					JOptionPane.showMessageDialog(null, "账户名称和总额不能为空！");
					return;
				}
				if(!Pattern.compile("^[0-9]+$").matcher(accountSumTF.getText()).find()){
					accountSumTF.setText("0");
				}
				for(AccountVO vo:accountList){
					if(vo.getName().equals(accountNameTF.getText())){
						JOptionPane.showMessageDialog(null, "账户已存在！");
						return;
					}
				}
				if(Integer.parseInt(accountSumTF.getText())==0){
					JOptionPane.showMessageDialog(null, "账户总额不能为零！");
				}
				
				addedCells=freshAddedCells(accountNameTF.getText(),accountSumTF.getText());
				refreshTables(OperationInfoConstants.ADD);
			}
			
			private Object[][] freshAddedCells(String name, String sum) {
				Object[][]temp=new Object[addedCells.length+1][];
				for(int i=0;i<addedCells.length;i++){
					temp[i]=addedCells[i];
				}
				temp[addedCells.length]=new Object[]{name,sum};
				return temp;
			}
			
		});
		
		addSubmitBtn.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				if(addedCells.length==0){
					JOptionPane.showMessageDialog(null, "请输入账户信息！");
					return;
				}
				List<AccountVO> createList=new ArrayList<AccountVO>();
				for(Object[]o:addedCells){
					createList.add(new AccountVO((String)o[0],Integer.parseInt((String) o[1])));
				}
				if(accountController.addAccounts(createList)){
					JOptionPane.showMessageDialog(null, "账户添加成功！");
					accountList=accountController.getAllAccounts();
				}
				else{
					JOptionPane.showMessageDialog(null, "账户添加失败！");
				}
				addedCells=new Object[][]{};	
				refreshTables(OperationInfoConstants.ADD);
			}
			
		});
		
		deleteBtn.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				List<AccountVO> deleteList=new ArrayList<AccountVO>();
				for(int i=0;i<deleteCells.length;i++){
					if((boolean) deleteTable.getValueAt(i, 2)){
						deleteList.add(accountList.get(i));
					}
				}
				if(deleteList.size()==0){
					JOptionPane.showMessageDialog(null, "请选择账户！");
					return;
				}
				if(accountController.deleteAccounts(deleteList)){
					JOptionPane.showMessageDialog(null, "账户删除成功！");
				};
				accountList=accountController.getAllAccounts();
				refreshTables(OperationInfoConstants.DELETE);				
			}
			
		});
		
		
		searchBtn.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				if("".equals(searchTF.getText()))return;
				List<AccountVO> searchList=accountController.searchAccount(searchTF.getText());
				searchCells=refreshSearchCells(searchList);	
				refreshTables(OperationInfoConstants.SEARCH);				
			}

			private Object[][] refreshSearchCells(List<AccountVO> searchList) {
				Object[][]temp=new Object[searchList.size()][];
				for(int i=0;i<searchList.size();i++){
					AccountVO vo=searchList.get(i);
					temp[i]=new Object[]{vo.getName(),vo.getSum()};
				}
				return temp;
			}
			
		});
		
		this.addChangeListener(new ChangeListener(){

			@Override
			public void stateChanged(ChangeEvent e) {
				switch(AccountManagePanel.this.getSelectedIndex()){
				case 0:
					refreshTables(OperationInfoConstants.ADD);
					break;
				case 1:
					refreshTables(OperationInfoConstants.DELETE);
					break;
				case 2:
					refreshTables(OperationInfoConstants.UPDATE);
					break;
				case 3:
					refreshTables(OperationInfoConstants.SEARCH);
					break;
				default:
				}
			}
			
		});
	}
	private void refreshTables(final String op) {
		Thread t=new Thread(new Runnable(){

			@Override
			public void run() {
				if(op.equals(OperationInfoConstants.ADD)){
					addedModel=new DefaultTableModel(addedCells,ColumnsConstants.ACCOUNT_COLUMNS_CREATE);
					addedTable=new JTable(addedModel);
					addedScrollPane.setViewportView(addedTable);
					addedScrollPane.validate();
				}else if(op.equals(OperationInfoConstants.DELETE)){
					deleteCells=initCells();
					deleteModel=new DefaultTableModel(deleteCells,ColumnsConstants.ACCOUNT_COLUMNS_MODIFY){
						public Class<?> getColumnClass(int c){
							return deleteCells[0][c].getClass();
						}
						public boolean isCellEditable(int r,int c){
							return c==2;
						}
					};
					deleteTable=new JTable(deleteModel);
					deleteScrollPane.setViewportView(deleteTable);
					deleteScrollPane.validate();
				}else if(op.equals(OperationInfoConstants.SEARCH)){
					searchModel=new DefaultTableModel(searchCells,ColumnsConstants.ACCOUNT_COLUMNS);
					searchTable=new JTable(searchModel);
					searchScrollPane.setViewportView(searchTable);
					searchScrollPane.validate();
				}
			}
		});
		t.start();		
	}
	
	
	private void makeComponents() {
		JLabel nameL=new JLabel("账户名称：");
		JLabel sumL=new JLabel("账户总额：");
		
		createAccountPanel=new JPanel();
		JPanel addedTopPanel=new JPanel();
		accountNameTF=new JTextField(10);
		accountSumTF=new JTextField(10);
		addedModel=new DefaultTableModel(addedCells,ColumnsConstants.ACCOUNT_COLUMNS_CREATE);
		addedTable=new JTable(addedModel);
		addedScrollPane=new JScrollPane();
		addedScrollPane.setViewportView(addedTable);
		addToListBtn=new JButton("加入列表");
		addedTopPanel.add(nameL);
		addedTopPanel.add(accountNameTF);
		addedTopPanel.add(sumL);
		addedTopPanel.add(accountSumTF);
		addedTopPanel.add(addToListBtn);
		JPanel addedBottomPanel=new JPanel();
		addSubmitBtn=new JButton("提交");
		addedBottomPanel.add(addSubmitBtn);
		createAccountPanel.setLayout(new BorderLayout());
		createAccountPanel.add(addedTopPanel,BorderLayout.NORTH);
		createAccountPanel.add(addedScrollPane,BorderLayout.CENTER);
		createAccountPanel.add(addedBottomPanel,BorderLayout.SOUTH);
		
		deleteAccountPanel=new JPanel();
		deleteCells=initCells();
		deleteModel=new DefaultTableModel(deleteCells,ColumnsConstants.ACCOUNT_COLUMNS_MODIFY){
			public Class<?> getColumnClass(int c){
				return deleteCells[0][c].getClass();
			}
			public boolean isCellEditable(int r,int c){
				return c==2||c==1;
			}
		};
		deleteTable=new JTable(deleteModel);
		deleteScrollPane=new JScrollPane();
		deleteScrollPane.setViewportView(deleteTable);
		JPanel deleteBottomPanel=new JPanel();
		deleteBtn=new JButton("删除");
		deleteBottomPanel.add(deleteBtn);
		deleteAccountPanel.setLayout(new BorderLayout());
		deleteAccountPanel.add(deleteScrollPane,BorderLayout.CENTER);
		deleteAccountPanel.add(deleteBottomPanel,BorderLayout.SOUTH);
		
		searchAccountPanel=new JPanel();
		JPanel searchTopPanel=new JPanel();
		searchTF=new JTextField(10);
		searchBtn=new JButton("查找");
		searchTopPanel.add(searchTF);
		searchTopPanel.add(searchBtn);
		searchModel=new DefaultTableModel(searchCells,ColumnsConstants.ACCOUNT_COLUMNS);
		searchTable=new JTable(searchModel);
		searchScrollPane=new JScrollPane();
		searchScrollPane.setViewportView(searchTable);
		searchAccountPanel.setLayout(new BorderLayout());
		searchAccountPanel.add(searchTopPanel,BorderLayout.NORTH);
		searchAccountPanel.add(searchScrollPane,BorderLayout.CENTER);
		
		this.setTabPlacement(JTabbedPane.LEFT);
		this.add("添加账户",createAccountPanel);
		this.add("删除账户",deleteAccountPanel);
		this.add("查找账户",searchAccountPanel);
		
	}
	private Object[][] initCells() {
		Object[][]temp=new Object[accountList.size()][];
		for(int i=0;i<accountList.size();i++){
			AccountVO vo=accountList.get(i);
			temp[i]=new Object[]{vo.getName(),vo.getSum(),false};
		}
		return temp;
	}
}
