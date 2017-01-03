package com.psssystem.client.ui.mainui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import com.psssystem.client.controller.maincontroller.ILoginController;
import com.psssystem.client.controllerimpl.maincontrollerimpl.LoginControllerImpl;
import com.psssystem.client.data.IdentityInfoConstants;
import com.psssystem.client.ui.adminui.AdminFrame;
import com.psssystem.client.ui.finaceui.FinanceManagerFrame;
import com.psssystem.client.ui.managerui.ManagerFrame;
import com.psssystem.client.ui.salesui.SalesManagerFrame;
import com.psssystem.client.ui.storageui.StorageManagerFrame;
import com.psssystem.connection.vo.UserVO;

public class LoginFrame extends JFrame {
	private JPanel mainPanel;
	private JLabel usernameL;
	private JLabel passwordL;
	private JLabel identityL;
	private JTextField usernameTF;
	private JPasswordField passwordTF;
	private JComboBox identityCB;
	private JButton loginB;

	private AWTEventListener listener;

	private ILoginController controller;

	public LoginFrame() {
		this.controller = new LoginControllerImpl();
		init();
	}

	private void init() {
		makeComponent();
		fillComponent();
		basicalSetup();
		addListener();
	}

	private void addListener() {
		loginB.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String username = usernameTF.getText();
				String password = passwordTF.getText();
				String identity = (String) identityCB.getSelectedItem();
				
				/*验证用户名和密码是否为空*/
				if ("".equals(password) || "".equals(username)) {
					JOptionPane.showMessageDialog(null, "用户名和密码不能为空！");
					return;
				}

				/*管理员直接通过客户端验证*/
				if (identity.equals(IdentityInfoConstants.ADMIN)){
					if (username.equals("admin") && password.equals("admin")) {
						AdminFrame adminFrame = new AdminFrame();
						Toolkit.getDefaultToolkit().removeAWTEventListener(
								listener);
						LoginFrame.this.setVisible(false);
					}
				}
				/*其它用户通过服务器端验证身份*/
				boolean success = controller.login(new UserVO(username,
						password, identity));
				if (!success) {
					usernameTF.setText("");
					passwordTF.setText("");
					return;
				}

				if (identity.equals(IdentityInfoConstants.STORAGE)) {
					StorageManagerFrame stockManaView = new StorageManagerFrame(
							LoginFrame.this);
					Toolkit.getDefaultToolkit()
							.removeAWTEventListener(listener);
					LoginFrame.this.setVisible(false);
					return;
				}
				if (identity.equals(IdentityInfoConstants.SALES)) {
					SalesManagerFrame salesFrame = new SalesManagerFrame(
							LoginFrame.this,username);
					Toolkit.getDefaultToolkit()
							.removeAWTEventListener(listener);
					LoginFrame.this.setVisible(false);
					return;
				}
				if (identity.equals(IdentityInfoConstants.FINANCE)) {
					FinanceManagerFrame financeManaView = new
					FinanceManagerFrame(LoginFrame.this,username);
					Toolkit.getDefaultToolkit()
							.removeAWTEventListener(listener);
					LoginFrame.this.setVisible(false);
					return;
				}
				if (identity.equals(IdentityInfoConstants.MANAGER)) {
					ManagerFrame managerFrame=new ManagerFrame(LoginFrame.this,username);
					Toolkit.getDefaultToolkit()
					.removeAWTEventListener(listener);
					LoginFrame.this.setVisible(false);
					return;
				}
			}
		});

		listener = new LoginFrameListener();
		Toolkit.getDefaultToolkit().addAWTEventListener(listener,
				AWTEvent.KEY_EVENT_MASK);
	}

	private void fillComponent() {
		mainPanel.setLayout(new GridBagLayout());
		mainPanel.add(usernameL, new GBC(0, 0).setWeight(0, 0).setInsets(10)
				.setAnchor(GBC.EAST));
		mainPanel.add(usernameTF, new GBC(1, 0).setWeight(0, 0).setInsets(10)
				.setFill(GBC.BOTH));
		mainPanel.add(passwordL, new GBC(0, 1).setWeight(0, 0).setInsets(10)
				.setAnchor(GBC.EAST));
		mainPanel.add(passwordTF, new GBC(1, 1).setWeight(0, 0).setInsets(10)
				.setFill(GBC.BOTH));
		mainPanel.add(identityL, new GBC(0, 2).setWeight(0, 0).setInsets(10)
				.setAnchor(GBC.EAST));
		mainPanel.add(identityCB, new GBC(1, 2).setWeight(0, 0).setInsets(10)
				.setFill(GBC.BOTH));
		mainPanel.add(loginB,
				new GBC(0, 3, 2, 1).setWeight(0, 1).setInsets(10, 0, 30, 0)
						.setFill(GBC.BOTH));
		this.add(mainPanel);
	}

	private void basicalSetup() {
		this.setSize(500, 300);
		this.setLocation(400, 200);
		this.setVisible(true);
		this.setTitle("进销存系统");
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	private void makeComponent() {
		mainPanel = new JPanel();

		usernameL = new JLabel("用户名:");
		passwordL = new JLabel("密码:");
		identityL = new JLabel("身份:");

		usernameTF = new JTextField(10);
		passwordTF = new JPasswordField();
		identityCB = new JComboBox(IdentityInfoConstants.IDENTITIES);
		loginB = new JButton("登陆");

		identityCB.setSelectedIndex(1);
		usernameTF.setText("j");
		passwordTF.setText("j");
	}

	class LoginFrameListener implements AWTEventListener {

		@Override
		public void eventDispatched(AWTEvent event) {
			if (((KeyEvent) event).getID() == KeyEvent.KEY_PRESSED) {
				if (((KeyEvent) event).getKeyCode() == KeyEvent.VK_ENTER) {
					String username = usernameTF.getText();
					String password = passwordTF.getText();
					String identity = (String) identityCB.getSelectedItem();
					
					/*验证用户名和密码是否为空*/
					if ("".equals(password) || "".equals(username)) {
						JOptionPane.showMessageDialog(null, "用户名和密码不能为空！");
						return;
					}

					/*管理员直接通过客户端验证*/
					if (identity.equals(IdentityInfoConstants.ADMIN)) {
						if (username.equals("admin") && password.equals("admin")) {
							AdminFrame adminFrame = new AdminFrame();
							Toolkit.getDefaultToolkit().removeAWTEventListener(
									listener);
							LoginFrame.this.setVisible(false);
						}
					}
					/*其它用户通过服务器端验证身份*/
					boolean success = controller.login(new UserVO(username,
							password, identity));
					if (!success) {
						usernameTF.setText("");
						passwordTF.setText("");
						return;
					}

					if (identity.equals(IdentityInfoConstants.STORAGE)) {
						StorageManagerFrame stockManaView = new StorageManagerFrame(
								LoginFrame.this);
						Toolkit.getDefaultToolkit()
								.removeAWTEventListener(listener);
						LoginFrame.this.setVisible(false);
						return;
					}
					if (identity.equals(IdentityInfoConstants.SALES)) {
						SalesManagerFrame salesFrame = new SalesManagerFrame(
								LoginFrame.this,username);
						Toolkit.getDefaultToolkit()
								.removeAWTEventListener(listener);
						LoginFrame.this.setVisible(false);
						return;
					}
					if (identity.equals(IdentityInfoConstants.FINANCE)) {
						 FinanceManagerFrame financeManaView = new
						 FinanceManagerFrame(LoginFrame.this,username);
						Toolkit.getDefaultToolkit()
								.removeAWTEventListener(listener);
						LoginFrame.this.setVisible(false);
						return;
					}
					if (identity.equals(IdentityInfoConstants.MANAGER)) {
						ManagerFrame managerFrame=new ManagerFrame(LoginFrame.this,username);
						Toolkit.getDefaultToolkit()
						.removeAWTEventListener(listener);
						LoginFrame.this.setVisible(false);
						return;
					}
				}
			}
		}

	}
}
