package com.psssystem.client.ui.managerui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import com.psssystem.client.data.ScreenSizeConstants;
import com.psssystem.client.ui.finaceui.ManageProcessPanel;
import com.psssystem.client.ui.finaceui.ManageSituationPanel;
import com.psssystem.client.ui.finaceui.SalesDetailPanel;
import com.psssystem.client.ui.mainui.LoginFrame;

public class ManagerFrame extends JFrame {
	private JTabbedPane tabbedPane;
	private JTabbedPane approvalPanel;
	private JPanel salesDetailPanel;
	private JPanel manageProcessPanel;
	private JPanel manageSituationPanel;
	private JPanel promotionPanel;
	
	private String username;
	public ManagerFrame(LoginFrame loginFrame, String username) {
		this.username=username;
		init();
	}
	private void init() {
		makeComponents();
		addListeners();
		basicalSetup();
	}
	private void basicalSetup() {
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(ScreenSizeConstants.WIDTH,ScreenSizeConstants.HEIGHT);
		this.setLocation(ScreenSizeConstants.LEFT,ScreenSizeConstants.TOP);
		this.setTitle("欢迎总经理！");
	}
	private void addListeners() {
		
	}
	private void makeComponents() {
		tabbedPane=new JTabbedPane();
		approvalPanel=new ApprovalPanel();
		salesDetailPanel=new SalesDetailPanel(username);
		manageProcessPanel=new ManageProcessPanel(username);
		manageSituationPanel=new ManageSituationPanel(username);
		promotionPanel=new PromotionPanel();
		
		tabbedPane.add("单据审批",approvalPanel);
		tabbedPane.add("销售明细",salesDetailPanel);
		tabbedPane.add("销售历程",manageProcessPanel);
		tabbedPane.add("销售情况",manageSituationPanel);
		tabbedPane.add("促销策略",promotionPanel);
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

}
