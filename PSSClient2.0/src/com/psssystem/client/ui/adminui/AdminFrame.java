package com.psssystem.client.ui.adminui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import com.psssystem.client.data.ScreenSizeConstants;

public class AdminFrame extends JFrame {
	private JTabbedPane tabbedPane;
	private JTabbedPane storagePane;
	private JTabbedPane pursalesPane;
	private JTabbedPane financePane;
	private JTabbedPane managerPane;
	public AdminFrame(){
		init();
	}
	
	private void init() {
		basicalSetup();
		makePanel();
		
		tabbedPane.add("库存管理人员",storagePane);
		tabbedPane.add("进货销售人员",pursalesPane);
		tabbedPane.add("财务人员",financePane);
		tabbedPane.add("总经理",managerPane);
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
	
	

	private void makePanel() {
		tabbedPane=new JTabbedPane();
		storagePane=new ContentPane("库存管理人员");
		pursalesPane=new ContentPane("进货销售人员");
		financePane=new ContentPane("财务人员");
		managerPane=new ContentPane("总经理");		
	}
	
	private void basicalSetup() {
		this.setResizable(false);
		this.setLocation(ScreenSizeConstants.LEFT,ScreenSizeConstants.TOP);
		this.setSize(ScreenSizeConstants.WIDTH,ScreenSizeConstants.HEIGHT);
		this.setVisible(true);
		this.setTitle("欢迎您，管理员！");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
	}


}
