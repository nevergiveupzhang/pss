package com.psssystem.client.ui.finaceui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import com.psssystem.client.controller.admincontroller.IUserController;
import com.psssystem.client.controller.financecontroller.IAccountController;
import com.psssystem.client.controller.financecontroller.IReceiptsOrderController;
import com.psssystem.client.controller.salescontroller.ICustomerController;
import com.psssystem.client.controllerimpl.admincontrollerimpl.UserControllerImpl;
import com.psssystem.client.controllerimpl.financecontrollerimpl.AccountControllerImpl;
import com.psssystem.client.controllerimpl.financecontrollerimpl.ReceiptsOrderControllerImpl;
import com.psssystem.client.controllerimpl.salescontrollerimpl.CustomerControllerImpl;
import com.psssystem.client.data.ColumnsConstants;
import com.psssystem.client.data.IdentityInfoConstants;
import com.psssystem.client.ui.mainui.GBC;
import com.psssystem.client.ui.mainui.LoginFrame;
import com.psssystem.connection.vo.AccountTransferVO;
import com.psssystem.connection.vo.AccountVO;
import com.psssystem.connection.vo.CommodityVO;
import com.psssystem.connection.vo.CustomerVO;
import com.psssystem.connection.vo.ReceiptsOrderVO;
import com.psssystem.connection.vo.UserVO;

public class ReceiptsPanel extends JPanel {
	private JButton transferBtn;
	private JScrollPane scrollPane;
	private JTable table;
	private TableModel model;
	private JComboBox customerCB;
	private JTextField sumTF;
	private JButton submitBtn;
	
	private AccountTransferChooser chooser;
	
	private Object[][]cells=new Object[][]{};
	
	private String username;
	
	private IReceiptsOrderController receiptsOrderController;
	private ICustomerController customerController;
	private IAccountController accountController;
	private IUserController userController;
	private List<AccountVO> accountList;
	private List<CustomerVO> customerList;
	private List<UserVO> userList;
	private List<AccountTransferVO> transferList=new ArrayList<AccountTransferVO>();
	public ReceiptsPanel(String username) {
		this.username=username;
		receiptsOrderController=new ReceiptsOrderControllerImpl();
		customerController=new CustomerControllerImpl();
		customerList=customerController.getAllCustomers();
		accountController=new AccountControllerImpl();
		accountList=accountController.getAllAccounts();
		userController=new UserControllerImpl();
		userList=userController.getUsers(IdentityInfoConstants.FINANCE);
		init();
	}
	
	
	
	private void init() {
		makeComponents();
		addListeners();
	}



	private void addListeners() {
		if(customerList.size()==0){
			transferBtn.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent arg0) {
					JOptionPane.showMessageDialog(null, "请联系进货销售人员建立客户！");
				}
				
			});
			submitBtn.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent arg0) {
					JOptionPane.showMessageDialog(null, "请联系进货销售人员建立客户！");
				}
				
			});
			return;
			
		}
		transferBtn.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				if(accountList.size()==0){
					JOptionPane.showMessageDialog(null, "您还没创建账户！");
					return;
				}
				
				if(chooser==null)chooser=new AccountTransferChooser();
				AccountTransferVO transfer=null;
				if(chooser.showDialog(ReceiptsPanel.this,"connect")){
					transfer=chooser.getAccountTransfer();
				}
				if(transfer==null) return;
				for(AccountTransferVO vo:transferList){
					if(vo.getAccountName().equals(transfer.getAccountName())){
						JOptionPane.showMessageDialog(null, "此账户已在列表中！");
						return;
					}
				}
				transferList.add(transfer);
				cells=increaseCellsElements(transfer);
				
				Thread t=new Thread(new Runnable(){

					@Override
					public void run() {
						model=new DefaultTableModel(cells,ColumnsConstants.RECEIPTS_COLUMNS_CREATE);
						table=new JTable(model);
						scrollPane.setViewportView(table);
						scrollPane.validate();
						sumTF.setText(calculateSum()+"");
					}

					private int calculateSum() {
						int sum=0;
						for(AccountTransferVO transfer:transferList){
							sum+=transfer.getSum();
						}
						return sum;
					}

				});
				
				t.start();
			}
			
			private Object[][] increaseCellsElements(AccountTransferVO transfer) {
				Object[][]temp=new Object[cells.length+1][];
				for(int i=0;i<cells.length;i++){
					temp[i]=cells[i];
				}
				temp[cells.length]=new Object[]{transfer.getAccountName(),transfer.getSum(),transfer.getRemarks()};
				return temp;
			}

			
		});
	
		submitBtn.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				if(customerList.size()==0){
					JOptionPane.showMessageDialog(null, "请先通过期初建账创建客户！");
					return;
				}
				if(transferList.size()==0){
					JOptionPane.showMessageDialog(null, "请输入转账信息！");
					return;
				}
				int customerID=getCustomerIDByName((String)customerCB.getSelectedItem());
				int userID=getUserIDByName(username);
				int sum=Integer.parseInt(sumTF.getText());
				boolean status=receiptsOrderController.createReceipts(new ReceiptsOrderVO.Builder().CustomerID(customerID).UserID(userID).Transfers(transferList).Sum(sum).build());
				if(status){
					JOptionPane.showMessageDialog(null, "收款成功！");
				}else{
					JOptionPane.showMessageDialog(null, "收款失败！");
				}
				cells=new Object[][]{};
				transferList.removeAll(transferList);
				
				Thread t=new Thread(new Runnable(){

					@Override
					public void run() {
						model=new DefaultTableModel(cells,ColumnsConstants.RECEIPTS_COLUMNS_CREATE);
						table=new JTable(model);
						scrollPane.setViewportView(table);
						scrollPane.validate();
						sumTF.setText("0");
					}
				});
				t.start();
				
			}

			private int getUserIDByName(String username) {
				for(UserVO vo:userList){
					if(vo.getUsername().equals(username))return vo.getId();
				}
				return 0;
			}

			private int getCustomerIDByName(String customerName) {
				for(CustomerVO vo:customerList){
					if(vo.getName().equals(customerName))return vo.getId();
				}
				return 0;
			}
			
		});
	}



	private void makeComponents() {
		JPanel topPanel=new JPanel();
		transferBtn=new JButton("输入转账信息");
		topPanel.add(transferBtn);
		
		scrollPane=new JScrollPane();
		model=new DefaultTableModel(cells,ColumnsConstants.RECEIPTS_COLUMNS_CREATE);
		table=new JTable(model);
		scrollPane.setViewportView(table);
		
		String []customers=new String[customerList.size()];
		for(int i=0;i<customerList.size();i++){
			customers[i]=customerList.get(i).getName();
		}
		JPanel bottomPanel=new JPanel();
		customerCB=new JComboBox(customers);
		sumTF=new JTextField(10);
		sumTF.setEditable(false);
		sumTF.setText("0");
		submitBtn=new JButton("提交");
		JLabel customerL=new JLabel("客户：");
		JLabel sumL=new JLabel("总额：");
		bottomPanel.setLayout(new GridBagLayout());
		bottomPanel.add(customerL,new GBC(0,0).setInsets(10,0,10,0));
		bottomPanel.add(customerCB,new GBC(1,0).setInsets(10,0,10,10));
		bottomPanel.add(sumL,new GBC(2,0).setInsets(10,100,10,0));
		bottomPanel.add(sumTF,new GBC(3,0).setInsets(10,0,10,0));
		bottomPanel.add(submitBtn,new GBC(1,1,2,1).setInsets(10).setFill(GridBagConstraints.BOTH));
		this.setLayout(new BorderLayout());
		this.add(topPanel,BorderLayout.NORTH);
		this.add(scrollPane,BorderLayout.CENTER);
		this.add(bottomPanel,BorderLayout.SOUTH);
	}





	private class AccountTransferChooser extends JPanel{
		
		private JComboBox accountCB;
		private JTextField moneyTF;
		private JTextField remarksTF;
		
		private JButton okBtn;
		private JButton cancelBtn;
		private JDialog dialog;
		private boolean ok;
		public AccountTransferChooser(){
			this.init();
		}


		public boolean showDialog(Component parent, String title) {
			Frame owner=null;
			if(parent instanceof Frame) owner=(Frame) parent;
			else owner=(Frame) SwingUtilities.getAncestorOfClass(Frame.class, parent);
			
			if(dialog==null||dialog.getOwner()!=owner){
				dialog=new JDialog(owner,true);
				dialog.add(this);
				dialog.getRootPane().setDefaultButton(okBtn);
				dialog.pack();
			}
			dialog.setTitle(title);
			dialog.setLocation(owner.getMousePosition());
			dialog.setVisible(true);
			return ok;
		}

		private void init() {
			this.makeComponents();
			this.addListeners();
			
		}

		private void addListeners() {
			okBtn.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent e) {
					if(!Pattern.compile("^[0-9]+$").matcher(moneyTF.getText()).find()){
						moneyTF.setText("0");
					}
					if("".equals(remarksTF.getText())){
						return;
					}
					
					if(Integer.parseInt(moneyTF.getText())==0){
						return;
					}
					ok=true;
					dialog.setVisible(false);
				}
				
			});
			
			cancelBtn.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent e) {
					ok=false;
					dialog.setVisible(false);
				}
				
			});
		}

		private void makeComponents() {
			JPanel panel=new JPanel();
			JLabel accountL=new JLabel("账户：");
			String[]accounts=new String[accountList.size()];
			for(int i=0;i<accountList.size();i++){
				accounts[i]=accountList.get(i).getName();
			}
			accountCB=new JComboBox(accounts);
			JLabel moneyL=new JLabel("转账金额：");
			moneyTF=new JTextField("0");
			JLabel remarksL=new JLabel("备注：");
			remarksTF=new JTextField();
			GridLayout layout=new GridLayout(3,4);
			layout.setVgap(10);
			panel.setLayout(layout);
			panel.add(new JLabel());
			panel.add(accountL);
			panel.add(accountCB);
			panel.add(new JLabel());
			panel.add(new JLabel());
			panel.add(moneyL);
			panel.add(moneyTF);
			panel.add(new JLabel());
			panel.add(new JLabel());
			panel.add(remarksL);
			panel.add(remarksTF);
			panel.add(new JLabel());
			okBtn=new JButton("确定");
			cancelBtn=new JButton("取消");
			JPanel buttonPanel=new JPanel();
			buttonPanel.add(okBtn);
			buttonPanel.add(cancelBtn);
			
			this.setLayout(new BorderLayout());
			this.add(panel,BorderLayout.CENTER);
			this.add(buttonPanel,BorderLayout.SOUTH);
		}
		
		
		public AccountTransferVO getAccountTransfer(){
			return new AccountTransferVO(accountCB.getSelectedItem().toString(),(String) accountCB.getSelectedItem(),Integer.parseInt(moneyTF.getText()),remarksTF.getText());
		}
	}
}
