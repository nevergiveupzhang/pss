package com.psssystem.client.ui.finaceui;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.psssystem.client.data.ScreenSizeConstants;
import com.psssystem.client.ui.mainui.LoginFrame;
import com.psssystem.client.ui.salesui.CustomerPanel;
import com.psssystem.client.ui.salesui.PurchasingSalePanel;
import com.psssystem.client.ui.salesui.SalesManagerFrame;

public class FinanceManagerFrame extends JFrame {
	private JTabbedPane tabbedPane;
	private JTabbedPane accountPanel;
	private JPanel receiptsPanel;
	private JPanel paymentPanel;
	private JPanel salesDetailPanel;
	private JPanel processPanel;
	private JPanel situationPanel;
	private JSplitPane beginAccountPanel;
	
	private String username;
	public FinanceManagerFrame(LoginFrame loginFrame, String username) {
		this.username=username;
		init();
	}

	private void init() {
		makeComponents();
		basicalSetup();
	}

	private void basicalSetup() {
		this.setResizable(false);
		this.setLocation(ScreenSizeConstants.TOP,ScreenSizeConstants.LEFT);
		this.setSize(ScreenSizeConstants.WIDTH, ScreenSizeConstants.HEIGHT);
		this.setVisible(true);
		this.setTitle("欢迎您，财务人员！");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	private void makeComponents() {
		tabbedPane = new JTabbedPane();
		accountPanel=new AccountManagePanel(username);
		receiptsPanel=new ReceiptsPanel(username);
		paymentPanel=new PaymentPanel(username);
		salesDetailPanel=new SalesDetailPanel(username);
		processPanel=new ManageProcessPanel(username);
		situationPanel=new ManageSituationPanel(username);
		beginAccountPanel=new BeginAccountPanel(username);
		tabbedPane.add("账户管理", accountPanel);
		tabbedPane.add("收款", receiptsPanel);
		tabbedPane.add("付款", paymentPanel);
		tabbedPane.add("销售明细", salesDetailPanel);
		tabbedPane.add("经营历程", processPanel);
		tabbedPane.add("经营情况",situationPanel);
		tabbedPane.add("期初建账",beginAccountPanel);
		tabbedPane.setOpaque(true);
		JButton btn=new JButton("退出");
		this.getRootPane().getLayeredPane().add(btn, JLayeredPane.DEFAULT_LAYER);
		btn.setBounds(ScreenSizeConstants.WIDTH-200, 30, 100, 30);
		btn.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
			
		});
		this.add(tabbedPane);
	}
	
	
	public static void main(String []args){
		new FinanceManagerFrame(null,"fm1");
	}
}
