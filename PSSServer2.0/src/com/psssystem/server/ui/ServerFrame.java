package com.psssystem.server.ui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import com.psssystem.server.util.RegisterServer;

public class ServerFrame extends JFrame {
	private int SCREEN_WIDTH=Toolkit.getDefaultToolkit().getScreenSize().width;
	private int SCREEN_HEIGHT=Toolkit.getDefaultToolkit().getScreenSize().height;
	private int WIDTH=SCREEN_WIDTH*5/7;
	private int HEIGHT=SCREEN_HEIGHT*3/4;
	
	public ServerFrame(){
		init();
	}

	private void init() {
		basicalSetup();
		JButton btn=new JButton("启动服务器");
		btn.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				RegisterServer rs=new  RegisterServer();
				rs.registryServer();
			}
		});
		
		add(btn,BorderLayout.SOUTH);
	}
	
	private void basicalSetup() {
		this.setResizable(false);
		this.setLocation(SCREEN_WIDTH/7,SCREEN_HEIGHT/8);
		this.setSize(WIDTH,HEIGHT);
		this.setVisible(true);
		this.setTitle("服务器！");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
	}
}
