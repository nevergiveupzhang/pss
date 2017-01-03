package com.psssystem.client.ui.storageui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.psssystem.client.data.OrderInfoConstants;
import com.psssystem.client.data.ScreenSizeConstants;
import com.psssystem.client.ui.mainui.LoginFrame;

//库存管理人员只能使用商品管理和库存管理功能
public class StorageManagerFrame extends JFrame {
	private LoginFrame loginFrame;
	private JTabbedPane tabbedPane;
	private JSplitPane commodityAndCategoryPanel;
	private JPanel storageViewPanel;
	private JPanel storageInventoryPanel;
	private JSplitPane giftPanel;
	private JPanel overflowPanel;
	private JPanel lossPanel;
	private JTabbedPane alarmPanel;

	public StorageManagerFrame(JFrame lFrame) {
		init();
	}

	private void init() {
		makePanel();
		addTab();
		basicalSetup();
		addListeners();
	}

	private void addListeners() {
		tabbedPane.addChangeListener(new ChangeListener(){

			@Override
			public void stateChanged(ChangeEvent e) {
				if(tabbedPane.getSelectedIndex()==3){
					Thread t=new Thread(new Runnable(){

						@Override
						public void run() {
							giftPanel= new GiftPanel();
							tabbedPane.setComponentAt(3, giftPanel);
							tabbedPane.validate();	
							
						}
					});
					t.start();
				}
				
				
			}
			
		});
	}

	private void addTab() {
		tabbedPane.addTab("商品与分类", commodityAndCategoryPanel);
		tabbedPane.addTab("库存查看", storageViewPanel);
		tabbedPane.addTab("库存盘点", storageInventoryPanel);
		tabbedPane.addTab("赠送单", giftPanel);
		tabbedPane.addTab("报溢单", overflowPanel);
		tabbedPane.addTab("报损单", lossPanel);
		tabbedPane.addTab("报警单", alarmPanel);
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
		commodityAndCategoryPanel = new CommodityAndCategoryPanel();
		storageViewPanel = new StorageViewPanel();
		storageInventoryPanel = new StorageInventoryPanel();
		giftPanel = new GiftPanel();
		overflowPanel = new LossOverflowPanel(OrderInfoConstants.OVERFLOW);
		lossPanel = new LossOverflowPanel(OrderInfoConstants.LOSS);
		alarmPanel = new AlarmPanel();
		tabbedPane = new JTabbedPane();
	}

	private void basicalSetup() {
		this.setResizable(false);
		this.setLocation(ScreenSizeConstants.LEFT, ScreenSizeConstants.TOP);
		this.setSize(ScreenSizeConstants.WIDTH, ScreenSizeConstants.HEIGHT);
		this.setVisible(true);
		this.setTitle("欢迎您，库存管理员！");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
