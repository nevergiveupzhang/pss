package com.psssystem.client.ui.adminui;

import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.net.MalformedURLException;
import java.rmi.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.*;

import com.psssystem.client.controller.admincontroller.IUserController;
import com.psssystem.client.controllerimpl.admincontrollerimpl.UserControllerImpl;
import com.psssystem.client.data.ColumnsConstants;
import com.psssystem.client.ui.mainui.GBC;
import com.psssystem.client.ui.renderer.ColorRenderer;
import com.psssystem.connection.vo.ResultMessage;
import com.psssystem.connection.vo.UserVO;

public class ContentPane extends JTabbedPane {
	/*	用户列表面板组件*/
	private JScrollPane scrollPane;
	private JTable userTable;
	private TableModel model;
	private JButton updateBtn;
	
	/*用户管理面板组件*/
	private JTextField usernameTF;
	private JTextField passwordTF;
	private JButton addBtn;
	private JButton delBtn;
	
	/*表格数据*/
	private Object[][]cells;

	private String userType;
	private IUserController userController;
	
	public ContentPane(String userType) {
		this.userType = userType;
		this.userController = new UserControllerImpl();
		init();
	}

	private void init() {
		/*创建用户列表和用户管理面板组件*/
		makeManageUserPanel();
		makeShowUserPanel();
		
		/*设置选项卡的显示方向*/
		setTabPlacement(JTabbedPane.LEFT);
		
		/*按钮添加监听器*/
		addListener();
	}

	private void addListener() {
		/*选项卡每次切换到user表格时，都重新从数据库中加载数据并重新绘制user表格*/		
		this.addChangeListener(new ChangeListener(){
			
			@Override
			public void stateChanged(ChangeEvent e) {
				Thread t=new Thread(new Runnable(){

					@Override
					public void run() {
						makeTable();
						scrollPane.setViewportView(userTable);
						scrollPane.validate();
					}
				});
				t.start();
			}
		});
			
		
		/*监听更新按钮事件*/
		updateBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				List<UserVO> userList =new ArrayList<UserVO>();
				int []temp=new int[cells.length];
				int j=0;
				for(int i=0;i<cells.length;i++){
					if((boolean) userTable.getValueAt(i, 4)){
						int id= (int) userTable.getValueAt(i, 0);
						String userName=(String) userTable.getValueAt(i, 1);
						String password=(String) userTable.getValueAt(i, 2);
						UserVO user=new UserVO(id,userName,password,userType);
						userList.add(user);
						temp[j]=i;
						j++;
					}
				}
				int []rows=new int[userList.size()];
				for(int i=0;i<rows.length;i++){
					rows[i]=temp[i];
				}
				ResultMessage rm=userController.updateUsers(userList);
				if(rm.getStatus()){
					JOptionPane.showMessageDialog(null, "更新成功！");
				}else{
					boolean[]r=rm.getFailRows();
					System.out.println(r.length);
					for(int i=0;i<r.length;i++){
						System.out.println(rows[i]);
						if(r[i]){
							JOptionPane.showMessageDialog(null, "用户名有重复！");
//							((ColorRenderer)userTable.getCellRenderer(rows[i], 1)).setColor(rows[i], Color.red);	
						}
					}
				}
			}
		});
		
		/*监听添加用户按钮事件*/
		addBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String username = usernameTF.getText();
				String password = passwordTF.getText();
				if(username.equals("")||password.equals("")){
					JOptionPane.showMessageDialog(null, "用户名和密码不能为空！");
				}else{
					int status=userController.createUser(new UserVO(username, password,
							userType));
					if(status!=ResultMessage.SUCCESS){
						JOptionPane.showMessageDialog(null, "用户已存在！");
					}
					usernameTF.setText("");
					passwordTF.setText("");
				}
			}

		});

		/*监听删除用户按钮事件*/
		delBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String username = usernameTF.getText();
				String password = passwordTF.getText();
				if(username.equals("")||password.equals("")){
					JOptionPane.showMessageDialog(null, "用户名和密码不能为空！");
				}else{
					if(userController.deleteUser(new UserVO(username, password,userType))){
						usernameTF.setText("");
						passwordTF.setText("");
					}else{
						JOptionPane.showMessageDialog(null, "用户不存在！");
					};
				}
					
			}
		});
	}

	private void makeShowUserPanel() {
		JPanel userPanel=new JPanel();
		userPanel.setLayout(new BorderLayout());
		
		scrollPane=new JScrollPane();
		/*新建user表格*/
		makeTable();
		scrollPane.setViewportView(userTable);
		JPanel bottomPanel=new JPanel();
		updateBtn = new JButton("更新");
		bottomPanel.add(updateBtn);
		/*添加组件到面板userPanel*/
		userPanel.add(scrollPane,BorderLayout.CENTER);
		userPanel.add(bottomPanel,BorderLayout.SOUTH);
		this.add("所有用户", userPanel);
	}

	private void makeTable() {
		List<UserVO> users = userController.getUsers(userType);
		cells=getCells(users);
		model=new DefaultTableModel(cells,ColumnsConstants.USER_COLUMNS){
			public Class<?> getColumnClass(int c){
				return cells[0][c].getClass();
			}
			
			public boolean isCellEditable(int r,int c){
				return c==1||c==2||c==4;
			}
		};
		
		userTable = new JTable(model);
	}

	private Object[][] getCells(List<UserVO> users) {
		Object[][]tempCells=new Object[users.size()][];
		for(int i=0;i<users.size();i++){
			UserVO user=users.get(i);
			int id=user.getId();
			String userName=user.getUsername();
			String password=user.getPassword();
			Object []row=new Object[]{id,userName,password,userType,false};
			tempCells[i]=row;
		}
		return tempCells;
	}

	private void makeManageUserPanel() {
		JPanel manageUserPanel = new JPanel();
		JLabel usernameL = new JLabel("用户名:");
		usernameTF = new JTextField(20);
		JLabel passwordL = new JLabel("密码:");
		passwordTF = new JTextField(20);
		addBtn = new JButton("添加用户");
		delBtn = new JButton("删除用户");
		manageUserPanel.setLayout(new GridBagLayout());
		manageUserPanel.add(usernameL,
				new GBC(0, 0).setWeight(0, 0).setInsets(10));
		manageUserPanel.add(usernameTF, new GBC(1, 0).setWeight(0, 0)
				.setInsets(10).setFill(GBC.HORIZONTAL));
		manageUserPanel.add(passwordL,
				new GBC(0, 1).setWeight(0, 0).setInsets(10));
		manageUserPanel.add(passwordTF, new GBC(1, 1).setWeight(0, 0)
				.setInsets(10).setFill(GBC.HORIZONTAL));
		manageUserPanel.add(addBtn,
				new GBC(0, 3).setFill(GBC.BOTH).setInsets(10, 0, 30, 0));
		manageUserPanel.add(delBtn,
				new GBC(1, 3).setFill(GBC.BOTH).setInsets(10, 100, 30, 20));
		this.add("用户管理",manageUserPanel);
	}

}
