package com.psssystem.client.ui.finaceui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

import javax.swing.JButton;
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
import com.psssystem.client.controller.financecontroller.ISalesDetailController;
import com.psssystem.client.controllerimpl.financecontrollerimpl.SalesDetailControllerImpl;
import com.psssystem.client.data.ColumnsConstants;
import com.psssystem.client.ui.mainui.GBC;
import com.psssystem.connection.vo.SalesDetailVO;

public class SalesDetailPanel extends JPanel {
	/* 时间区间，商品名，客户，业务员 */
	private DatePicker startDate;
	private DatePicker endDate;
	private JTextField commNameTF;
	private JTextField customerTF;
	private JTextField salesmanTF;
	private JButton okBtn;

	private JTable table;
	private TableModel model;
	private JScrollPane scrollPane;
	private JButton exportBtn;
	
	private Object[][] cells = new Object[][] {};

	private String username;
	private ISalesDetailController controller;

	public SalesDetailPanel(String username) {
		this.username = username;
		this.controller = new SalesDetailControllerImpl();
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
				String[] condition = new String[] { startDate.getText(),
						endDate.getText(), commNameTF.getText(),
						customerTF.getText(), salesmanTF.getText() };
				Set<SalesDetailVO> list = controller.getSalesDetail(condition);
				cells = getCells(list);
				Thread t = new Thread(new Runnable() {

					@Override
					public void run() {
						model = new DefaultTableModel(cells, ColumnsConstants.SALESDETAIL_COLUMNS);
						table = new JTable(model);
						scrollPane.setViewportView(table);
						scrollPane.validate();
					}

				});
				t.start();
			}

			private Object[][] getCells(Set<SalesDetailVO> list) {
				Object[][] temp = new Object[list.size()][];
				Iterator<SalesDetailVO> itr = list.iterator();
				int i = 0;
				while (itr.hasNext()) {
					SalesDetailVO vo = itr.next();
					temp[i] = new Object[] { vo.getDate(), vo.getCommName(),
							vo.getCommType(), vo.getAmount(), vo.getPrice(),
							vo.getSum() };
					i++;
				}
				return temp;
			}

		});
		
		
		exportBtn.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser=new JFileChooser();
				FileNameExtensionFilter filter=new FileNameExtensionFilter("('.xls .xlsx')","xls","xlsx");
				chooser.setSelectedFile(new File("."));
				int result=chooser.showOpenDialog(SalesDetailPanel.this);
				if(result==JFileChooser.APPROVE_OPTION){
					String name=chooser.getSelectedFile().getPath();
					File file=new File(name+".xls");
					WritableWorkbook book=null;
					WritableSheet sheet=null;
					try {
						book=Workbook.createWorkbook(file);
						sheet=book.createSheet("第一页", 0);
						for(int i=0;i<model.getColumnCount();i++){
							sheet.addCell(new Label(i,0,model.getColumnName(i)));
						}
						for(int i=0;i<model.getColumnCount();i++){
							for(int j=0;j<table.getRowCount();j++){
								sheet.addCell(new Label(i,j+1,table.getValueAt(i, j).toString()));
							}
						}
						book.write();
						book.close();
					} catch (IOException e1) {
						e1.printStackTrace();
					} catch (RowsExceededException e1) {
						e1.printStackTrace();
					} catch (WriteException e1) {
						e1.printStackTrace();
					}
					
				}
			}
			
		});
	}

	private void makeComponents() {
		JPanel topPanel = new JPanel();
		topPanel.setLayout(new GridBagLayout());
		JLabel startL = new JLabel("开始日期：");
		JLabel endL = new JLabel("结束日期：");
		JLabel commNameL = new JLabel("商品名称：");
		JLabel customerL = new JLabel("客户名称：");
		JLabel salesmanL = new JLabel("业务员：");
		// DatePicker初始化
		Date initDate = new Date();
		String dateFormat = "yyyy-MM-dd HH:mm:ss";
		Font font = new Font("Times New Rom", Font.BOLD, 14);
		Dimension dimension = new Dimension(177, 30);
		startDate = new DatePicker(initDate, dateFormat, font, dimension);
		endDate = new DatePicker(initDate, dateFormat, font, dimension);
		commNameTF = new JTextField(10);
		customerTF = new JTextField(10);
		salesmanTF = new JTextField(10);
		okBtn = new JButton("确定");
		topPanel.add(startL, new GBC(0, 0).setInsets(5, 0, 5, 0));
		topPanel.add(startDate, new GBC(1, 0).setInsets(5, 0, 5, 10));
		topPanel.add(endL, new GBC(2, 0).setInsets(5, 10, 5, 0));
		topPanel.add(endDate, new GBC(3, 0).setInsets(5, 0, 5, 10));
		topPanel.add(commNameL, new GBC(4, 0).setInsets(5, 10, 5, 0));
		topPanel.add(commNameTF, new GBC(5, 0).setInsets(5, 0, 5, 0));
		topPanel.add(customerL, new GBC(0, 1).setInsets(5, 0, 5, 0));
		topPanel.add(customerTF, new GBC(1, 1).setInsets(5, 0, 5, 10));
		topPanel.add(salesmanL, new GBC(2, 1).setInsets(5, 10, 5, 0));
		topPanel.add(salesmanTF, new GBC(3, 1).setInsets(5, 0, 5, 10));
		topPanel.add(
				okBtn,
				new GBC(4, 1, 2, 1).setInsets(5, 10, 5, 0).setFill(
						GridBagConstraints.BOTH));

		model = new DefaultTableModel(cells, ColumnsConstants.SALESDETAIL_COLUMNS);
		table = new JTable(model);
		scrollPane = new JScrollPane();
		scrollPane.setViewportView(table);

		JPanel bottomPanel=new JPanel();
		exportBtn=new JButton("导出");
		bottomPanel.add(exportBtn);
		this.setLayout(new BorderLayout());
		this.add(topPanel, BorderLayout.NORTH);
		this.add(scrollPane, BorderLayout.CENTER);
		this.add(bottomPanel,BorderLayout.SOUTH);
	}

}
