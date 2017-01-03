package com.psssystem.client.ui.finaceui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import com.eltima.components.ui.DatePicker;
import com.psssystem.client.controller.financecontroller.IManageProcessController;
import com.psssystem.client.controllerimpl.financecontrollerimpl.ManageProcessControllerImpl;
import com.psssystem.client.data.ColumnsConstants;
import com.psssystem.client.data.OrderInfoConstants;
import com.psssystem.client.ui.mainui.GBC;
import com.psssystem.connection.vo.GiftOrderVO;
import com.psssystem.connection.vo.LossOverflowOrderVO;
import com.psssystem.connection.vo.PaymentOrderVO;
import com.psssystem.connection.vo.PurchasingOrderVO;
import com.psssystem.connection.vo.ReceiptsOrderVO;
import com.psssystem.connection.vo.SalesOrderVO;

public class ManageProcessPanel extends JPanel {
	/* 时间区间，单据类型，客户，业务员 */
	private DatePicker startDate;
	private DatePicker endDate;
	private JComboBox orderTypeCB = new JComboBox(OrderInfoConstants.ORDERS);
	private JTextField customerTF;
	private JTextField salesmanTF;

	private JButton okBtn;
	private JScrollPane scrollPane;
	private JTable table;
	private TableModel model;
	private JButton exportBtn;
	private String username;
	private IManageProcessController controller;

	public ManageProcessPanel(String username) {
		this.username = username;
		this.controller = new ManageProcessControllerImpl();
		init();
	}

	private void init() {
		makeComponents();
		addListeners();
	}

	private void addListeners() {
		okBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String orderType=orderTypeCB.getSelectedItem().toString();
				Set set=getOrderSet(orderType);
				Object[][]cells=getCells(set,orderType);
				freshTable(cells,orderType);
			}

			

		});

		orderTypeCB.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				String orderType=orderTypeCB.getSelectedItem().toString();
				if(orderType.equals(OrderInfoConstants.GIFT)||orderType.equals(OrderInfoConstants.LOSS)||orderType.equals(OrderInfoConstants.OVERFLOW)){
					customerTF.setEditable(false);
					salesmanTF.setEditable(false);
				}else if(orderType.equals(OrderInfoConstants.PAYMENT)||orderType.equals(OrderInfoConstants.RECEIPTS)){
					customerTF.setEditable(true);
				}else if(orderType.equals(OrderInfoConstants.PURCHASING)||orderType.equals(OrderInfoConstants.PURCHASINGRETURN)||orderType.equals(OrderInfoConstants.SALES)||orderType.equals(OrderInfoConstants.SALESRETURN)){
					customerTF.setEditable(true);
					salesmanTF.setEditable(true);
				}
			}

		});
		
		
		exportBtn.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser=new JFileChooser();
				FileNameExtensionFilter filter=new FileNameExtensionFilter("('.xls .xlsx')","xls","xlsx");
				chooser.setFileFilter(filter);
				chooser.setCurrentDirectory(new File("."));
				int result=chooser.showSaveDialog(ManageProcessPanel.this);
				if(result==JFileChooser.APPROVE_OPTION){
					String name=chooser.getSelectedFile().getPath();
					if(!"".equals(name)){
						File file=new File(name+".xls");
						WritableWorkbook book=null;
						WritableSheet sheet=null;
						try {
							book=Workbook.createWorkbook(file);
							sheet=book.createSheet("第一页", 0);
							for(int i=0;i<model.getColumnCount();i++){
								sheet.addCell(new Label(i,0,model.getColumnName(i)));
							}
							for(int i=0;i<table.getRowCount();i++){
								for(int j=0;j<model.getColumnCount();j++){
									sheet.addCell(new Label(j,i+1,table.getValueAt(i, j).toString()));	
								}
							}
							book.write();
							book.close();
						} catch (IOException | WriteException e1) {
							e1.printStackTrace();
						}
						
					}
				}
			}
			
		});
	}

	private Object[][] getCells(Set set, String orderType) {
		Object[][]cells=new Object[set.size()][];
		int i=0;
		switch(orderType){
		case OrderInfoConstants.GIFT:
			Iterator<GiftOrderVO> giftItr=set.iterator();
			while(giftItr.hasNext()){
				GiftOrderVO vo=giftItr.next();
				cells[i]=new Object[]{vo.getId(),vo.getCommID(),vo.getCommName(),vo.getAmount(),vo.getCreatedDate(),vo.getStatus()};
				i++;
			}
			return cells;
		case OrderInfoConstants.LOSS:
			Iterator<LossOverflowOrderVO> lossItr=set.iterator();
			while(lossItr.hasNext()){
				LossOverflowOrderVO vo=lossItr.next();
				cells[i]=new Object[]{vo.getId(),vo.getCommID(),vo.getCommName(),vo.getAmount(),vo.getCreatedDate(),vo.getStatus()};
				i++;
			}
			return cells;
		case OrderInfoConstants.OVERFLOW:
			Iterator<LossOverflowOrderVO> overflowItr=set.iterator();
			while(overflowItr.hasNext()){
				LossOverflowOrderVO vo=overflowItr.next();
				cells[i]=new Object[]{vo.getId(),vo.getCommID(),vo.getCommName(),vo.getAmount(),vo.getCreatedDate(),vo.getStatus()};
				i++;
			}
			return cells;
		case OrderInfoConstants.PAYMENT:
			Iterator<PaymentOrderVO> paymentItr=set.iterator();
			while(paymentItr.hasNext()){
				/*"ID", "客户ID","操作员ID", "账户ID", "总额", "创建日期", "状态"*/
				PaymentOrderVO vo=paymentItr.next();
				cells[i]=new Object[]{vo.getId(),vo.getCustomerID(),vo.getCustomerName(),vo.getUserID(),vo.getUserName(),vo.getAccountName(),vo.getSum(),vo.getCreatedDate().toString(),vo.getStatus()};
				i++;
			}
			return cells;
		case OrderInfoConstants.PURCHASING:
			/*"ID", "客户ID","操作员ID", "业务员", "备注", "总额", "创建日期", "状态"*/
			Iterator<PurchasingOrderVO> purchasingItr=set.iterator();
			while(purchasingItr.hasNext()){
				PurchasingOrderVO vo=purchasingItr.next();
				cells[i]=new Object[]{vo.getId(),vo.getCustomerID(),vo.getCustomerName(),vo.getUserID(),vo.getUsername(),vo.getSalesman(),vo.getRemarks(),vo.getSum(),vo.getCreatedDate().toString(),vo.getStatus()};
				i++;
			}
			return cells;
		case OrderInfoConstants.PURCHASINGRETURN:
			Iterator<PurchasingOrderVO> purchasingReturnItr=set.iterator();
			while(purchasingReturnItr.hasNext()){
				PurchasingOrderVO vo=purchasingReturnItr.next();
				cells[i]=new Object[]{vo.getId(),vo.getCustomerID(),vo.getCustomerName(),vo.getUserID(),vo.getUsername(),vo.getSalesman(),vo.getRemarks(),vo.getSum(),vo.getCreatedDate(),vo.getStatus()};
				i++;
			}
			return cells;
		case OrderInfoConstants.RECEIPTS:
			/*"ID", "客户ID","操作员ID", "总额", "创建日期", "状态"*/
			Iterator<ReceiptsOrderVO> receiptsItr=set.iterator();
			while(receiptsItr.hasNext()){
				ReceiptsOrderVO vo=receiptsItr.next();
				cells[i]=new Object[]{vo.getId(),vo.getCustomerID(),vo.getCustomerName(),vo.getUserID(),vo.getUsername(),vo.getSum(),vo.getCreatedDate(),vo.getStatus()};
				i++;
			}
			return cells;
		case OrderInfoConstants.SALES:
/*					"ID", "客户ID", "操作员ID",
			"业务员", "备注", "折算前", "折算后", "折让", "代金券", "创建日期", "状态"*/
			Iterator<SalesOrderVO> salesItr=set.iterator();
			while(salesItr.hasNext()){
				SalesOrderVO vo=salesItr.next();
				cells[i]=new Object[]{vo.getId(),vo.getCustomerID(),vo.getCustomerName(),vo.getUserID(),vo.getUsername(),vo.getSalesman(),vo.getRemarks(),vo.getSumBeforeDiscount(),vo.getSumAfterDiscount(),vo.getDiscount(),vo.getVoucher(),vo.getCreatedDate(),vo.getStatus()};
				i++;
			}
			return cells;
		case OrderInfoConstants.SALESRETURN:
			Iterator<SalesOrderVO> salesReturnItr=set.iterator();
			while(salesReturnItr.hasNext()){
				SalesOrderVO vo=salesReturnItr.next();
				cells[i]=new Object[]{vo.getId(),vo.getCustomerID(),vo.getCustomerName(),vo.getUserID(),vo.getUsername(),vo.getSalesman(),vo.getRemarks(),vo.getSumBeforeDiscount(),vo.getSumAfterDiscount(),vo.getDiscount(),vo.getVoucher(),vo.getCreatedDate(),vo.getStatus()};
				i++;
			}
			return cells;
		}
		return cells;
	}

	private void freshTable(final Object[][] cells, final String orderType) {
		Thread t=new Thread(new Runnable(){

			@Override
			public void run() {
				String columns[];
				switch(orderType){
				case OrderInfoConstants.GIFT:
					columns=ColumnsConstants.GIFT_LOSS_OVERFLOW_COLUMNS;
					break;
				case OrderInfoConstants.LOSS:
					columns=ColumnsConstants.GIFT_LOSS_OVERFLOW_COLUMNS;
					break;
				case OrderInfoConstants.OVERFLOW:
					columns=ColumnsConstants.GIFT_LOSS_OVERFLOW_COLUMNS;
					break;
				case OrderInfoConstants.PAYMENT:
					columns=ColumnsConstants.PAYMENT_COLUMNS;
					break;
				case OrderInfoConstants.PURCHASING:
					columns=ColumnsConstants.PURCHASING_COLUMNS;
					break;
				case OrderInfoConstants.PURCHASINGRETURN:
					columns=ColumnsConstants.PURCHASING_COLUMNS;
					break;
				case OrderInfoConstants.RECEIPTS:
					columns=ColumnsConstants.RECEIPTS_COLUMNS;
					break;
				case OrderInfoConstants.SALES:
					columns=ColumnsConstants.SALES_COLUMNS;
					break;
				case OrderInfoConstants.SALESRETURN:
					columns=ColumnsConstants.SALES_COLUMNS;
					break;
				default:
					columns=null;
				}
				model=new DefaultTableModel(cells,columns);
				table=new JTable(model);
				scrollPane.setViewportView(table);
				scrollPane.validate();
			}
			
		});
		t.start();
	}

	private Set getOrderSet(String orderType) {
		Set set=null;
		switch(orderType){
		case OrderInfoConstants.GIFT:
			set=controller.getGiftOrder(new String[] { startDate.getText(),
					endDate.getText() });
			return set;
		case OrderInfoConstants.LOSS:
			set=controller.getLossOverflowOrder(OrderInfoConstants.LOSS,new String[]{startDate.getText(),endDate.getText()});
			return set;
		case OrderInfoConstants.OVERFLOW:
			set=controller.getLossOverflowOrder(OrderInfoConstants.OVERFLOW,new String[]{startDate.getText(),endDate.getText()});
			return set;
		case OrderInfoConstants.PAYMENT:
			set=controller.getPaymentOrder(new String[]{startDate.getText(),endDate.getText(),customerTF.getText()});
			return set;
		case OrderInfoConstants.PURCHASING:
			System.out.println(OrderInfoConstants.PURCHASING+"getOrderSet()");
			set=controller.getPurchasingOrder(new String []{startDate.getText(),endDate.getText(),customerTF.getText(),salesmanTF.getText()});
			return set;
		case OrderInfoConstants.PURCHASINGRETURN:
			set=controller.getPurchasingReturnOrder(new String []{startDate.getText(),endDate.getText(),customerTF.getText(),salesmanTF.getText()});
			return set;
		case OrderInfoConstants.RECEIPTS:
			set=controller.getReceiptsOrder(new String[]{startDate.getText(),endDate.getText(),customerTF.getText()});
			return set;
		case OrderInfoConstants.SALES:
			set=controller.getSalesOrder(new String []{startDate.getText(),endDate.getText(),customerTF.getText(),salesmanTF.getText()});
			return set;
		case OrderInfoConstants.SALESRETURN:
			set=controller.getSalesReturnOrder(new String []{startDate.getText(),endDate.getText(),customerTF.getText(),salesmanTF.getText()});
			return set;
		}
		return set;
	}
	private void makeComponents() {
		JPanel topPanel = new JPanel();
		JLabel startDateL = new JLabel("起始时间：");
		JLabel endDateL = new JLabel("截止时间：");
		JLabel customerL = new JLabel("客户名称：");
		JLabel salesmanL = new JLabel("业务员：");

		// DatePicker初始化
		Date initDate = new Date();
		String dateFormat = "yyyy-MM-dd HH:mm:ss";
		Font font = new Font("Times New Rom", Font.BOLD, 14);
		Dimension dimension = new Dimension(177, 30);
		startDate = new DatePicker(initDate, dateFormat, font, dimension);
		endDate = new DatePicker(initDate, dateFormat, font, dimension);

		customerTF = new JTextField(10);
		salesmanTF = new JTextField(10);
		okBtn = new JButton("确定");
		topPanel.setLayout(new GridBagLayout());
		topPanel.add(startDateL, new GBC(0, 0).tipicalLeftInsets());
		topPanel.add(startDate, new GBC(1, 0).tipicalRightInsets());
		topPanel.add(endDateL, new GBC(2, 0).tipicalLeftInsets());
		topPanel.add(endDate, new GBC(3, 0).tipicalRightInsets());
		topPanel.add(orderTypeCB, new GBC(4, 0).tipicalRightInsets());
		topPanel.add(customerL, new GBC(0, 1).tipicalLeftInsets());
		topPanel.add(customerTF, new GBC(1, 1).tipicalRightInsets().setFill(GridBagConstraints.BOTH));
		topPanel.add(salesmanL, new GBC(2, 1).tipicalLeftInsets());
		topPanel.add(salesmanTF, new GBC(3, 1).tipicalRightInsets().setFill(GridBagConstraints.BOTH));
		topPanel.add(okBtn, new GBC(4, 1).tipicalRightInsets().setFill(GridBagConstraints.BOTH));
		scrollPane = new JScrollPane();
		
		JPanel bottomPanel=new JPanel();
		exportBtn=new JButton("导出");
		bottomPanel.add(exportBtn);
		
		this.setLayout(new BorderLayout());
		this.add(topPanel, BorderLayout.NORTH);
		this.add(scrollPane, BorderLayout.CENTER);
		this.add(bottomPanel,BorderLayout.SOUTH);
	}

}
