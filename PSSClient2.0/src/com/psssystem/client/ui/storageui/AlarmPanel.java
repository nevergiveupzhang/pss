package com.psssystem.client.ui.storageui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.text.DocumentFilter;
import javax.swing.text.InternationalFormatter;

import com.psssystem.client.controller.storagecontroller.IAlarmOrderController;
import com.psssystem.client.controller.storagecontroller.ICommodityController;
import com.psssystem.client.controllerimpl.storagecontrollerimpl.AlarmOrderControllerImpl;
import com.psssystem.client.controllerimpl.storagecontrollerimpl.CommodityControllerImpl;
import com.psssystem.client.data.ColumnsConstants;
import com.psssystem.client.ui.filter.IntFilter;
import com.psssystem.connection.vo.AlarmOrderVO;
import com.psssystem.connection.vo.CommodityVO;

public class AlarmPanel extends JTabbedPane {
	/*设置警戒线面板组件*/
	private JPanel setPanel;
	private JScrollPane warningLineScrollPane;
	private JTable warningLineTable;
	private TableModel wlModel;
	private JButton setBtn;
	private Object[][]wlCells=new Object[][]{};
	
	/*添加报警单面板组件*/
	private JPanel alarmPanel;
	private JTextField commIDTF;
	private JTextField commNameTF;
	private JTextField amountTF;
	private JTextField warninglineTF;
	private JButton addToListBtn;
	private JScrollPane alarmScrollPane;
	private JTable alarmTable;
	private TableModel alarmModel;
	private JButton addBtn;
	private Object[][]alarmCells=new Object[][]{};
	
	private DocumentFilter filter=new IntFilter();
	
	private ICommodityController commodityController;
	private IAlarmOrderController alarmOrderController;
	
	private List<CommodityVO> commList;
	public AlarmPanel(){
		this.commodityController=new CommodityControllerImpl();
		this.alarmOrderController=new AlarmOrderControllerImpl();
		this.commList=commodityController.getAllCommodities();
		init();
	}
	private void init() {
		makeComponent();
		addListener();
	}
	private void addListener() {
		/*alarmModel.addTableModelListener(new TableModelListener(){

			@Override
			public void tableChanged(TableModelEvent e) {
				alarmTable.setValueAt(alarmModel.getValueAt(alarmTable.getEditingRow(), alarmTable.getEditingColumn()), row, column);
			}
			
		});*/
		setBtn.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				for(int i=0;i<wlCells.length;i++){
					if((boolean) warningLineTable.getValueAt(i, 5)){
						int warningLine=(int) warningLineTable.getValueAt(i, 4);
						commList.get(i).setWarningLine(warningLine);
					}
				}
				if(commodityController.setWarningLines(commList))JOptionPane.showMessageDialog(null, "设置成功！");;
				
				makeTables();
				Thread t=new Thread(new Runnable(){

					@Override
					public void run() {
						warningLineScrollPane.setViewportView(warningLineTable);
						warningLineScrollPane.validate();
					}
					
				});
				t.start();
			}
			
		});
		
		addToListBtn.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				/*判断用户输入是否合法*/
				if(!Pattern.compile("^[0-9]+$").matcher(commIDTF.getText()).find()){
					commIDTF.setText("0");
				}
				
				if(!Pattern.compile("^[0-9]+$").matcher(amountTF.getText()).find()){
					amountTF.setText("0");
				}
				if(!Pattern.compile("^[0-9]+$").matcher(warninglineTF.getText()).find()){
					warninglineTF.setText("0");
				}
				
				if("".equals(commNameTF.getText())){
					JOptionPane.showMessageDialog(null, "请输入商品名称！");
					return;
				}
				if(Integer.parseInt(amountTF.getText())>Integer.parseInt(warninglineTF.getText())){
					JOptionPane.showMessageDialog(null, "库存数量大于警戒线，无需担忧哦！");
					return;
				}
				
				final int commID=Integer.parseInt(commIDTF.getText());
				final String commName=commNameTF.getText();
				final int amount=Integer.parseInt(amountTF.getText());
				final int warningline=Integer.parseInt(warninglineTF.getText());
				
				/*判断商品是否存在*/
				if(!commodityController.isCommodityIDAndNameExists(new CommodityVO(commID,commName))){
					JOptionPane.showMessageDialog(null, "商品信息有误！");
					return;
				}
				/*判断商品是否已经在刚添加的报价单列表中*/
				for(int i=0;i<alarmCells.length;i++){
					if(commID==(int)alarmCells[i][0]){
						JOptionPane.showMessageDialog(null, "此商品已存在报警单列表中！");
						return;
					}
				}
				
				/*增加报警单数据*/
				alarmCells=increaseAlarmCells(commID,commName,amount,warningline);
				
				/*更新报警单表格视图*/
				Thread t=new Thread(new Runnable(){
					
					@Override
					public void run() {
						alarmModel=new DefaultTableModel(alarmCells,ColumnsConstants.ALARM_COLUMNS_CREATE);
						alarmTable=new JTable(alarmModel);
						alarmScrollPane.setViewportView(alarmTable);
						alarmScrollPane.validate();
					}
				});
				
				t.start();
						
			}

			private Object[][] increaseAlarmCells(int commID, String commName, int amount, int warningline) {
				Object[][]tempCells=null;
				if(alarmCells.length==0){
					tempCells=new Object[][]{{commID,commName,amount,warningline}};
				}else{
					tempCells=new Object[alarmCells.length+1][];
					for(int i=0;i<alarmCells.length;i++){
						tempCells[i]=alarmCells[i];
					}
					tempCells[alarmCells.length]=new Object[]{commID,commName,amount,warningline};
				}
				return tempCells;
			}
			
		});
		
		addBtn.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				List<AlarmOrderVO> orderList=new ArrayList<AlarmOrderVO>();
				if(alarmCells.length==0){
					JOptionPane.showMessageDialog(null, "请输入商品！");
					return;
				}
				for(int i=0;i<alarmCells.length;i++){
					Object[] orderArr=alarmCells[i];
						int commID=(int) orderArr[0];
						String commName=(String) orderArr[1];
						int amount=(int) orderArr[2];
						int warningline=(int) orderArr[3];
						AlarmOrderVO order=new AlarmOrderVO(commID,commName,amount,warningline);
						orderList.add(order);
				}
				if(alarmOrderController.addOrder(orderList)){
					JOptionPane.showMessageDialog(null, "报警单添加成功！");
				}else{
					JOptionPane.showMessageDialog(null, "报警单添加失败！");
				}
				
				alarmCells=new Object[][]{};
				Thread t=new Thread(new Runnable(){

					@Override
					public void run() {
						alarmModel=new DefaultTableModel(alarmCells,ColumnsConstants.ALARM_COLUMNS_CREATE);
						alarmTable=new JTable(alarmModel);
						alarmScrollPane.setViewportView(alarmTable);
						alarmScrollPane.validate();
					}
					
				});
				t.start();
			}
			
		});
	}
	
	private void makeComponent() {
		makeTables();
		makeWarningLinePanel();
		makeAlarmPanel();
		
		
		this.setTabPlacement(JTabbedPane.LEFT);
		this.add("设置警戒线",setPanel);
		this.add("添加报警单",alarmPanel);		
	}
	
	private void makeAlarmPanel() {
		/*添加报警单面板组件初始化*/
		alarmPanel=new JPanel();
		JPanel commPanel=new JPanel();
		JLabel commIDL=new JLabel("   商品编号：");
		JLabel commNameL=new JLabel("   商品名称：");
		JLabel amountL=new JLabel ("   库存数量：");
		JLabel warninglineL=new JLabel ("    警戒线：");
		commIDTF=new JTextField(10);
		commNameTF=new JTextField(10);
		amountTF=new JTextField(10);
		warninglineTF=new JTextField(10);
		addToListBtn=new JButton("加入列表");
		
		commPanel.setLayout(new GridLayout(1,13));
		commPanel.add(new JLabel());
		commPanel.add(new JLabel());
		commPanel.add(commIDL);
		commPanel.add(commIDTF);
		commPanel.add(commNameL);
		commPanel.add(commNameTF);
		commPanel.add(amountL);
		commPanel.add(amountTF);
		commPanel.add(warninglineL);
		commPanel.add(warninglineTF);
		commPanel.add(addToListBtn);
		commPanel.add(new JLabel());
		commPanel.add(new JLabel());
		
		alarmScrollPane=new JScrollPane();
		alarmScrollPane.setViewportView(alarmTable);
		JPanel bottomPanel=new JPanel();
		addBtn=new JButton("添加报警单");
		bottomPanel.add(addBtn);
		alarmPanel.setLayout(new BorderLayout());
		alarmPanel.add(commPanel,BorderLayout.NORTH);
		alarmPanel.add(alarmScrollPane,BorderLayout.CENTER);
		alarmPanel.add(bottomPanel,BorderLayout.SOUTH);
	}
	private void makeWarningLinePanel() {
		/*设置警戒线面板组件初始化*/
		setPanel=new JPanel();
		warningLineScrollPane=new JScrollPane();
		warningLineScrollPane.setViewportView(warningLineTable);
		JPanel bottomPanel=new JPanel();
		setBtn=new JButton("设置");
		bottomPanel.add(setBtn);
		setPanel.setLayout(new BorderLayout());
		setPanel.add(warningLineScrollPane,BorderLayout.CENTER);
		setPanel.add(bottomPanel,BorderLayout.SOUTH);
				
	}
	private void makeTables() {
		wlCells=new Object[commList.size()][];
		for(int i=0;i<commList.size();i++){
			CommodityVO comm=commList.get(i);
			int commID=comm.getId();
			String commName=comm.getName();
			String commType=comm.getType();
			int amount=comm.getStockAmount();
			int warningline=comm.getWarningLine();
			wlCells[i]=new Object[]{commID,commName,commType,amount,warningline,false};
		}
		wlModel=new DefaultTableModel(wlCells,ColumnsConstants.WARINGLINE_COLUMNS_CREATE){
			public Class<?> getColumnClass(int c){
				return wlCells[0][c].getClass();
			}
			
			public boolean isCellEditable(int r,int c){
				return c==4||c==5;
			}
			
		};
		warningLineTable=new JTable(wlModel);
		
		
		
		alarmModel=new DefaultTableModel(alarmCells,ColumnsConstants.ALARM_COLUMNS_CREATE){
			public Class<?> getColumnClass(int c){
				return alarmCells[0][c].getClass();
			}
		};
		alarmTable=new JTable(alarmModel);
	}
}
