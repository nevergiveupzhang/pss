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
import com.psssystem.client.controller.financecontroller.IPaymentOrderController;
import com.psssystem.client.controller.salescontroller.ICustomerController;
import com.psssystem.client.controllerimpl.admincontrollerimpl.UserControllerImpl;
import com.psssystem.client.controllerimpl.financecontrollerimpl.AccountControllerImpl;
import com.psssystem.client.controllerimpl.financecontrollerimpl.PaymentOrderControllerImpl;
import com.psssystem.client.controllerimpl.salescontrollerimpl.CustomerControllerImpl;
import com.psssystem.client.data.ColumnsConstants;
import com.psssystem.client.data.IdentityInfoConstants;
import com.psssystem.client.ui.mainui.GBC;
import com.psssystem.client.ui.mainui.LoginFrame;
import com.psssystem.connection.vo.AccountVO;
import com.psssystem.connection.vo.CustomerVO;
import com.psssystem.connection.vo.PaymentItemVO;
import com.psssystem.connection.vo.PaymentOrderVO;
import com.psssystem.connection.vo.UserVO;

public class PaymentPanel extends JPanel {
	/* ,操作员（当前登录用户），客户，银行账户，条目清单，总额。
	 * 条目清单中包括：条目名，金额，备注。 */
	private JButton itemBtn;
	private ItemChooser chooser;
	private JTable table;
	private TableModel model;
	private JScrollPane scrollPane;
	private JTextField userTF;
	private JComboBox accountCB;
	private JComboBox customerCB;
	private JTextField sumTF;
	private JButton submitBtn;
	
	private Object[][]cells=new Object[][]{};
	
	private ICustomerController customerController;
	private IAccountController accountController;
	private IUserController userController;
	private IPaymentOrderController paymentOrderController;
	private List<CustomerVO> customerList;
	private List<UserVO> userList;
	private List<AccountVO> accountList;
	private List<PaymentItemVO> itemList=new ArrayList<PaymentItemVO>();
	private String username;
	
	public PaymentPanel(String username) {
		this.username=username;
		this.customerController=new CustomerControllerImpl();
		this.userController=new UserControllerImpl();
		this.accountController=new AccountControllerImpl();
		this.paymentOrderController=new PaymentOrderControllerImpl();
		this.userList=userController.getUsers(IdentityInfoConstants.FINANCE);
		this.customerList=customerController.getAllCustomers();
		this.accountList=accountController.getAllAccounts();
		init();
	}

	
	private void init() {
		makeComponents();
		addListeners();
	}


	private void addListeners() {
		if(customerList.size()==0){
			itemBtn.addActionListener(new ActionListener(){

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
		if(accountList.size()==0){
			itemBtn.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent arg0) {
					JOptionPane.showMessageDialog(null, "请先创建账户！");
				}
				
			});
			submitBtn.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent arg0) {
					JOptionPane.showMessageDialog(null, "请先创建账户！");
				}
				
			});
			return;
		}
		itemBtn.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				if(chooser==null) chooser=new ItemChooser();
				if(!chooser.showDialog(PaymentPanel.this, "connect"))return;
				
				PaymentItemVO item=chooser.getItem();
				for(PaymentItemVO vo:itemList){
					if(vo.getName().equals(item.getName())){
						JOptionPane.showMessageDialog(null, "此条目已存在列表中！");
						return;
					}
				}
				itemList.add(item);
				cells=increaseCells(item);
				sumTF.setText((Integer.parseInt(sumTF.getText())+item.getSum())+"");
				Thread t=new Thread(new Runnable(){

					@Override
					public void run() {
						model=new DefaultTableModel(cells,ColumnsConstants.PAYMENT_COLUMNS_CREATE);
						table=new JTable(model);
						scrollPane.setViewportView(table);
						scrollPane.validate();
					}
					
				});
				t.start();
			}

			private Object[][] increaseCells(PaymentItemVO item) {
				Object[][]temp=new Object[cells.length+1][];
				for(int i=0;i<cells.length;i++){
					temp[i]=cells[i];
				}
				temp[cells.length]=new Object[]{item.getName(),item.getSum(),item.getRemarks()};
				return temp;
			}
			
		});
		
		submitBtn.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				boolean status=paymentOrderController.createOrder(new PaymentOrderVO.Builder().CustomerID(getCustomerIDByName(customerCB.getSelectedItem().toString())).UserID(getUserIDByName(username)).AccountName(accountCB.getSelectedItem().toString()).Sum(Integer.parseInt(sumTF.getText())).Items(itemList).build());
				if(status){
					JOptionPane.showMessageDialog(null, "付款成功！");
				}else{
					JOptionPane.showMessageDialog(null, "付款失败！");
				}
				cells=new Object[][]{};
				itemList.removeAll(itemList);
				sumTF.setText("0");
				Thread t=new Thread(new Runnable(){

					@Override
					public void run() {
						model=new DefaultTableModel(cells,ColumnsConstants.PAYMENT_COLUMNS_CREATE);
						table=new JTable(model);
						scrollPane.setViewportView(table);
						scrollPane.validate();
					}
					
				});
				t.start();
			}


			private int getUserIDByName(String username) {
				for(UserVO user:userList){
					if(user.getUsername().equals(username)){
						return user.getId();
					}
				}
				return 0;
			}

			private int getCustomerIDByName(String name) {
				System.out.println(customerList.size());
				for(CustomerVO customer:customerList){
					if(customer.getName().equals(name)){
						return customer.getId();
					}
				}				
				return 0;
			}
			
		});
	}


	private void makeComponents() {
		JPanel topPanel=new JPanel();
		itemBtn=new JButton("输入条目清单");
		topPanel.add(itemBtn);
		
		model=new DefaultTableModel(cells,ColumnsConstants.PAYMENT_COLUMNS_CREATE);
		table=new JTable(model);
		scrollPane=new JScrollPane();
		scrollPane.setViewportView(table);
		
		JPanel bottomPanel=new JPanel();
		bottomPanel.setLayout(new GridBagLayout());
		String accounts[]=initComboBoxs(0);
		accountCB=new JComboBox(accounts);
		JLabel accountL=new JLabel("账户：");
		String []customers=initComboBoxs(1);
		customerCB=new JComboBox(customers);
		JLabel customerL=new JLabel("客户：");
		userTF=new JTextField(10);
		userTF.setText(username);
		userTF.setEditable(false);
		JLabel userL=new JLabel("操作员：");
		sumTF=new JTextField(10);
		sumTF.setText("0");
		sumTF.setEditable(false);
		JLabel sumL=new JLabel("总额：");
		submitBtn=new JButton("提交");
		bottomPanel.add(accountL,new GBC(0,0).setInsets(10,0,10,0));
		bottomPanel.add(accountCB,new GBC(1,0).setInsets(10,0,10,10).setFill(GridBagConstraints.BOTH));
		bottomPanel.add(customerL,new GBC(2,0).setInsets(10,10,10,0));
		bottomPanel.add(customerCB,new GBC(3,0).setInsets(10,0,10,0).setInsets(10,0,10,0).setFill(GridBagConstraints.BOTH));
		bottomPanel.add(userL,new GBC(0,1).setInsets(10,0,10,10));
		bottomPanel.add(userTF,new GBC(1,1).setInsets(10,0,10,0).setFill(GridBagConstraints.BOTH));
		bottomPanel.add(sumL,new GBC(2,1).setInsets(10,10,10,0));
		bottomPanel.add(sumTF,new GBC(3,1).setInsets(10,0,10,0).setFill(GridBagConstraints.BOTH));
		bottomPanel.add(submitBtn,new GBC(1,2,2,1).setInsets(10,0,10,0).setFill(GridBagConstraints.BOTH));
		
		this.setLayout(new BorderLayout());
		this.add(topPanel,BorderLayout.NORTH);
		this.add(scrollPane,BorderLayout.CENTER);
		this.add(bottomPanel,BorderLayout.SOUTH);
	}


	private String[] initComboBoxs(int tag) {
		String []temp=null;
		switch(tag){
		case 0:
			temp=new String[accountList.size()];
			for(int i=0;i<accountList.size();i++){
				temp[i]=accountList.get(i).getName();
			}
			break;
		case 1:
			temp=new String[customerList.size()];
			for(int i=0;i<customerList.size();i++){
				temp[i]=customerList.get(i).getName();
			}
			break;
		default:
			return null;
		}
		
		return temp;
	}


	private class ItemChooser extends JPanel{
		private JTextField nameTF;
		private JTextField moneyTF;
		private JTextField remarksTF;
		
		private JButton okBtn;
		private JButton cancelBtn;
		private JDialog dialog;
		private boolean ok;
		public ItemChooser(){
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
					
					if("".equals(remarksTF.getText())||"".equals(nameTF.getText())){
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
			JLabel nameL=new JLabel("条目名：");
			nameTF=new JTextField(10);
			JLabel moneyL=new JLabel("金额：");
			moneyTF=new JTextField("0");
			JLabel remarksL=new JLabel("备注：");
			remarksTF=new JTextField();
			GridLayout layout=new GridLayout(3,2);
			layout.setVgap(10);
			panel.setLayout(layout);
			panel.add(new JLabel());
			panel.add(nameL);
			panel.add(nameTF);
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
		
		public PaymentItemVO getItem(){
			return new PaymentItemVO(nameTF.getText(),Integer.parseInt(moneyTF.getText()),remarksTF.getText());
		}
	}
}
