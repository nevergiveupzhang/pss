package com.psssystem.client.ui.salesui;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.psssystem.client.data.OperationInfoConstants;
import com.psssystem.client.data.ScreenSizeConstants;
import com.psssystem.client.ui.mainui.LoginFrame;

public class SalesManagerFrame extends JFrame {
	private JTabbedPane tabbedPane;
	private JTabbedPane customerPanel;
	private JSplitPane purPanel;
	private JSplitPane purReturnPanel;
	private JSplitPane salePanel;
	private JSplitPane saleReturnPanel;
	
	private String username;
	public SalesManagerFrame(LoginFrame loginFrame, String username) {
		this.username=username;
		init();
	}

	private void init() {
		makeComponents();
		basicalSetup();
		tabbedPane.addChangeListener(new ChangeListener(){

			@Override
			public void stateChanged(ChangeEvent e) {
				Thread t=new Thread(new Runnable(){

					@Override
					public void run() {
						int index=tabbedPane.getSelectedIndex();
						switch(index){
						case 1:
							tabbedPane.setComponentAt(1,new PurchasingSalePanel(OperationInfoConstants.OP_PURCHASING,username));
							break;
						case 2:
							tabbedPane.setComponentAt(2,new PurchasingSalePanel(OperationInfoConstants.OP_PURCHASINGRETURN,username));
							break;
						case 3:
							tabbedPane.setComponentAt(3,new PurchasingSalePanel(OperationInfoConstants.OP_SALE,username));
							break;
						case 4:
							tabbedPane.setComponentAt(4,new PurchasingSalePanel(OperationInfoConstants.OP_SALERETURN,username));
							break;
						default:
							break;
						}
						tabbedPane.validate();	
					}
					
				});
				t.start();
			}
			
		});
	}

	private void basicalSetup() {
		this.setResizable(false);
		this.setLocation(ScreenSizeConstants.LEFT, ScreenSizeConstants.TOP);
		this.setSize(ScreenSizeConstants.WIDTH, ScreenSizeConstants.HEIGHT);
		this.setVisible(true);
		this.setTitle("欢迎您，进货销售人员！");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	private void makeComponents() {
		tabbedPane = new JTabbedPane();
		customerPanel = new CustomerPanel();
		purPanel = new PurchasingSalePanel(OperationInfoConstants.OP_PURCHASING,username);
		purReturnPanel = new PurchasingSalePanel(OperationInfoConstants.OP_PURCHASINGRETURN,username);
		salePanel = new PurchasingSalePanel(OperationInfoConstants.OP_SALE,username);
		saleReturnPanel = new PurchasingSalePanel(OperationInfoConstants.OP_SALERETURN,username);
		tabbedPane.add("客户管理", customerPanel);
		tabbedPane.add("进货", purPanel);
		tabbedPane.add("进货退货", purReturnPanel);
		tabbedPane.add("销售", salePanel);
		tabbedPane.add("销售退货", saleReturnPanel);
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
