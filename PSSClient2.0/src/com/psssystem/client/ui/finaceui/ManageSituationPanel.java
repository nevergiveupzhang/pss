package com.psssystem.client.ui.finaceui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.eltima.components.ui.DatePicker;
import com.psssystem.client.controller.financecontroller.IManageSituationController;
import com.psssystem.client.controllerimpl.financecontrollerimpl.ManageSituationControllerImpl;
import com.psssystem.client.ui.mainui.GBC;
import com.psssystem.connection.vo.BalanceVO;

public class ManageSituationPanel extends JPanel {
	private DatePicker startDate;
	private DatePicker endDate;
	private JButton okBtn;
	
	private IManageSituationController controller;

	public ManageSituationPanel(String username) {
		controller = new ManageSituationControllerImpl();
		init();
	}

	private void init() {
		makeComponents();
		adddListeners();
	}

	private void adddListeners() {
		okBtn.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				Thread t=new Thread(new Runnable(){

					@Override
					public void run() {
						String start=startDate.getText();
						String end=endDate.getText();
						BalanceVO vo=controller.getManageSituationByDate(start, end);
						
						JPanel centerPanel=new JPanel();
						centerPanel.setLayout(new GridBagLayout());
						JLabel incomeL=new JLabel("总收入：");
						JLabel income=new JLabel(vo.getIncome()+"");
						JLabel discountL=new JLabel("总折让：");
						JLabel discount=new JLabel(vo.getDiscount()+"");
						JLabel outcomeL=new JLabel("总支出：");
						JLabel outcome=new JLabel(vo.getOutcome()+"");
						JLabel profitL=new JLabel("总利润：");
						JLabel profit=new JLabel(vo.getProfit()+"");
						
						incomeL.setFont(new Font("serif",Font.BOLD,39));
						income.setFont(new Font("serif",Font.BOLD,39));
						discountL.setFont(new Font("serif",Font.BOLD,39));
						discount.setFont(new Font("serif",Font.BOLD,39));
						outcomeL.setFont(new Font("serif",Font.BOLD,39));
						outcome.setFont(new Font("serif",Font.BOLD,39));
						profitL.setFont(new Font("serif",Font.BOLD,39));
						profit.setFont(new Font("serif",Font.BOLD,39));
						centerPanel.add(incomeL,new GBC(0,0).setFill(GridBagConstraints.BOTH));
						centerPanel.add(income,new GBC(1,0).setFill(GridBagConstraints.BOTH));
						centerPanel.add(outcomeL,new GBC(0,1).setFill(GridBagConstraints.BOTH));
						centerPanel.add(outcome,new GBC(1,1).setFill(GridBagConstraints.BOTH));
						centerPanel.add(discountL,new GBC(0,2).setFill(GridBagConstraints.BOTH));
						centerPanel.add(discount,new GBC(1,2).setFill(GridBagConstraints.BOTH));
						centerPanel.add(profitL,new GBC(0,3).setFill(GridBagConstraints.BOTH));
						centerPanel.add(profit,new GBC(1,3).setFill(GridBagConstraints.BOTH));
						ManageSituationPanel.this.add(centerPanel,BorderLayout.CENTER);
						ManageSituationPanel.this.validate();
					}
					
				});
				t.start();
			}
			
		});
	}

	private void makeComponents() {
		JPanel topPanel=new JPanel();
		JLabel startDateL=new JLabel("起始时间：");
		JLabel endDateL=new JLabel("截止时间：");
		// DatePicker初始化
		Date initDate = new Date();
		String dateFormat = "yyyy-MM-dd HH:mm:ss";
		Font font = new Font("Times New Rom", Font.BOLD, 14);
		Dimension dimension = new Dimension(177, 30);
		startDate = new DatePicker(initDate, dateFormat, font, dimension);
		endDate = new DatePicker(initDate, dateFormat, font, dimension);
		okBtn=new JButton("确定");
		topPanel.add(startDateL);
		topPanel.add(startDate);
		topPanel.add(endDateL);
		topPanel.add(endDate);
		topPanel.add(okBtn);
		
		JPanel centerPanel=new JPanel();
		centerPanel.setLayout(new GridBagLayout());
		this.setLayout(new BorderLayout());
		this.add(topPanel,BorderLayout.NORTH);
		this.add(centerPanel,BorderLayout.CENTER);
	}

}
