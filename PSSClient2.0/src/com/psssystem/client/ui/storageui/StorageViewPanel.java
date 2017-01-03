package com.psssystem.client.ui.storageui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.swing.*;
import javax.swing.table.*;

import com.eltima.components.ui.DatePicker;
import com.psssystem.client.controller.storagecontroller.IStorageController;
import com.psssystem.client.controllerimpl.storagecontrollerimpl.StorageControllerImpl;
import com.psssystem.client.data.ColumnsConstants;
import com.psssystem.client.data.OperationInfoConstants;
import com.psssystem.connection.vo.StorageVO;


public class StorageViewPanel extends JPanel {
	/*操作面板组件定义*/
	private JPanel opPanel;
	private JLabel startL;
	private JLabel endL;
	private JButton storageInBtn;
	private JButton storageOutBtn;
	private JButton purBtn;
	private JButton salesBtn;
	private DatePicker startTime;
	private DatePicker endTime;

	/*表格面板组件定义*/
	private JScrollPane scrollPane;
	private JTable table;
	private TableModel model;
	/*表格数据定义*/
	private Object[][]cells=new Object[][]{};
	
	private IStorageController controller;
	public StorageViewPanel(){
		this.controller=new StorageControllerImpl();
		init();
	}

	private void init() {
		makeComponent();
		addListener();
	}

	private void addListener() {
		storageInBtn.addActionListener(new StorageViewListener(OperationInfoConstants.IMPORT_INFO));
		storageOutBtn.addActionListener(new StorageViewListener(OperationInfoConstants.EXPORT_INFO));
		purBtn.addActionListener(new StorageViewListener( OperationInfoConstants.PURCHASING_INFO));
		salesBtn.addActionListener(new StorageViewListener(OperationInfoConstants.SALES_INFO));
	}

	private void makeComponent() {
		/*操作面板组件初始化*/
		opPanel=new JPanel();
		startL=new JLabel("开始时间:");
		endL=new JLabel("结束时间:");
		//DatePicker初始化
		Font font = new Font("Times New Rom",Font.BOLD,14);
        Dimension dimension = new Dimension(177,30);
        String dateFormat="yyyy-MM-dd HH:mm:ss";
        Date initDate=new Date();
		startTime=new DatePicker(initDate,dateFormat,font,dimension);
		endTime=new DatePicker(initDate,dateFormat,font,dimension);
		
		storageInBtn=new JButton("入库信息");
		storageOutBtn=new JButton("出库信息");
		purBtn=new JButton("进货信息");
		salesBtn=new JButton("销售信息");
		
		opPanel.add(startL);
		opPanel.add(startTime);
		opPanel.add(endL);
		opPanel.add(endTime);
		opPanel.add(storageInBtn);
		opPanel.add(storageOutBtn);
		opPanel.add(purBtn);
		opPanel.add(salesBtn);
		
		/*表格面板组件初始化*/
		model=new DefaultTableModel(cells,ColumnsConstants.STORAGE_COLUMNS);
		table=new JTable(model);
		scrollPane=new JScrollPane();
		scrollPane.setViewportView(table);
		
		
		this.setLayout(new BorderLayout());
		this.add(opPanel,BorderLayout.NORTH);
		this.add(scrollPane,BorderLayout.CENTER);		
	}

	
	class StorageViewListener implements ActionListener{
		private String type;
		public StorageViewListener(String type) {
			this.type=type;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			Thread t=new Thread(new Runnable(){

				@Override
				public void run() {
					String startDate = startTime.getText();
					String endDate=endTime.getText();
					Set<StorageVO> storageList=controller.getStorageInfo(startDate,endDate,type);
					cells=new Object[storageList.size()][];
					int i=0;
					Iterator<StorageVO> itr=storageList.iterator();
					while(itr.hasNext()){
						StorageVO vo=itr.next();
						int commID=vo.getCommID();
						String commName=vo.getCommName();
						int amount=vo.getAmount();
						int sum=vo.getSum();
						String date=vo.getDate();
						String storageType=vo.getType();
						cells[i]=new Object[]{commID,commName,amount,sum,date,storageType};
						i++;
					}
					
					model=new DefaultTableModel(cells,ColumnsConstants.STORAGE_COLUMNS);
					table=new JTable(model);
					scrollPane.setViewportView(table);
					scrollPane.validate();					
				}
				
			});
			t.start();
		}
		
	}
}
