package com.psssystem.client.ui.storageui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import com.psssystem.client.controller.storagecontroller.IInventoryController;
import com.psssystem.client.controllerimpl.storagecontrollerimpl.InventoryControllerImpl;
import com.psssystem.client.data.ColumnsConstants;
import com.psssystem.connection.vo.InventoryVO;

public class StorageInventoryPanel extends JPanel {
	private JScrollPane scrollPane;
	private JTable table;
	private TableModel model;
	private Object[][]cells=new Object[][]{};
	
	private JButton exportExcelBtn;
	private JButton beginBtn;
	
	private IInventoryController controller;
	
	public StorageInventoryPanel(){
		this.controller=new InventoryControllerImpl();
		makeComponent();
		addListener();
	}

	private void addListener() {
		beginBtn.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				Thread t=new Thread(new Runnable(){

					@Override
					public void run() {
						cells=getCells();
						model=new DefaultTableModel(cells,ColumnsConstants.INVENTORY_COLUMNS);
						table=new JTable(model);
						scrollPane.setViewportView(table);
						scrollPane.validate();
					}
				
				});

				t.start();
			}
			
		});
		exportExcelBtn.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				/*创建file chooser*/
				JFileChooser chooser=new JFileChooser();
				/*文件过滤器安装*/
				FileNameExtensionFilter filter=new FileNameExtensionFilter("('.xls .xlsx')","xls","xlsx");
				chooser.setFileFilter(filter);
				/*默认的文件路径*/
				chooser.setCurrentDirectory(new File("."));
				/*打开文件保存对话框*/
				int result=chooser.showSaveDialog(StorageInventoryPanel.this);
				
				if(result==JFileChooser.APPROVE_OPTION){
					String name=chooser.getSelectedFile().getPath();
					if(!name.equals("")){
						File file=new File(name+".xls");
						WritableWorkbook book=null;
						WritableSheet sheet=null;
						try {
							book=Workbook.createWorkbook(file);
							sheet=book.createSheet("第一页", 0);
							for(int i=0;i<model.getColumnCount();i++){
								sheet.addCell(new Label(i,0,model.getColumnName(i)));	
							}
							/*将盘点表格当中的数据添加到Excel文件中*/
							for(int i=0;i<table.getRowCount();i++){
								for(int j=0;j<model.getColumnCount();j++){
									sheet.addCell(new Label(j,i+1,table.getValueAt(i, j).toString()));
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
			}
			
		});
	}

	private void makeComponent() {
		JPanel topPanel=new JPanel();
		beginBtn=new JButton("开始盘点");
		topPanel.add(beginBtn);
		scrollPane=new JScrollPane();
		model=new DefaultTableModel(cells,ColumnsConstants.INVENTORY_COLUMNS);
		table=new JTable(model);
		scrollPane.setViewportView(table);
		
		JPanel bottomPanel=new JPanel();
		exportExcelBtn=new JButton("导出Excel");
		bottomPanel.add(exportExcelBtn);
		
		this.setLayout(new BorderLayout());
		this.add(topPanel,BorderLayout.NORTH);
		this.add(scrollPane,BorderLayout.CENTER);
		this.add(bottomPanel,BorderLayout.SOUTH);
	}

	private Object[][] getCells() {
		Object[][]tempCells=null;
		InventoryVO vo=controller.getInventory();
		int id=vo.getId();
		String batch=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(vo.getBatch());
		byte[] commInfo=vo.getCommInfo();
		Workbook book=null;
		Sheet sheet =null;
		File file=new File("resource/inventory.xls");
		try {
			if(!file.exists()){
				file.createNewFile();
			}
			BufferedOutputStream bos=new BufferedOutputStream(new FileOutputStream(file));
			bos.write(commInfo);
            book= Workbook.getWorkbook(file);
            sheet= book.getSheet(0);
            tempCells=new Object[sheet.getRows()-1][];
            for(int i=1;i<sheet.getRows();i++){
            	Cell commNamecell = sheet.getCell(0, i);
                String commName = commNamecell.getContents();
                Cell commTypecell = sheet.getCell(1, i);
                String commType = commTypecell.getContents();
                Cell amountCell=sheet.getCell(2,i);
                String amount=amountCell.getContents();
                tempCells[i-1]=new Object[]{i,id,batch,commName,commType,amount};
            }
            book.close();
        } catch (Exception e) {
        	e.printStackTrace();
        }
		return tempCells;
	}
}
