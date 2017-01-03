package com.psssystem.client.ui.storageui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import javax.swing.*;
import javax.swing.table.*;
import javax.swing.text.DocumentFilter;
import javax.swing.text.InternationalFormatter;

import com.psssystem.client.controller.storagecontroller.ICommodityController;
import com.psssystem.client.controller.storagecontroller.ILossOverflowController;
import com.psssystem.client.controllerimpl.storagecontrollerimpl.CommodityControllerImpl;
import com.psssystem.client.controllerimpl.storagecontrollerimpl.LossOverflowControllerImpl;
import com.psssystem.client.data.ColumnsConstants;
import com.psssystem.client.data.OrderInfoConstants;
import com.psssystem.client.ui.filter.IntFilter;
import com.psssystem.connection.vo.CommodityVO;
import com.psssystem.connection.vo.LossOverflowOrderVO;

public class LossOverflowPanel extends JPanel {
	/* 商品信息输入面板组件 */
	private JTextField idTF;
	private JTextField nameTF;
	private JTextField numTF;
	private JButton addBtn;

	/* 待添加的商品列表 */
	private JScrollPane scrollPane;
	private JTable table;
	private TableModel model;
	/* 提交按钮 */
	private JButton submitBtn;

	private DocumentFilter filter = new IntFilter();
	// 待添加商品列表数据
	private Object[][] cells = new Object[][] {};

	private List<LossOverflowOrderVO> orderList = new ArrayList<LossOverflowOrderVO>();
	private ILossOverflowController controller;
	private ICommodityController commodityController;
	private String type;

	public LossOverflowPanel(String type) {
		this.type = type;
		controller = new LossOverflowControllerImpl();
		commodityController = new CommodityControllerImpl();
		init();
	}

	private void init() {
		makeComponent();
		addListener();
	}

	private void addListener() {
		addBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				/*判断用户输入是否合法*/
				if (!Pattern.compile("^[0-9]+$").matcher(idTF.getText()).find()) {
					idTF.setText("0");
				}
				if (!Pattern.compile("^[0-9]+$").matcher(numTF.getText())
						.find()) {
					numTF.setText("0");
				}
				if ("".equals(nameTF.getText())) {
					JOptionPane.showMessageDialog(null, "请输入商品名称！");
					return;
				}
				
				final int commID=Integer.parseInt(idTF.getText());
				final String commName=nameTF.getText();
				final int commNum=Integer.parseInt(numTF.getText());
				
				if(commNum==0){
					JOptionPane.showMessageDialog(nameTF, "数量不能为空哦！");
					return;
				}
				/*判断商品是否存在*/
				if (!commodityController.isCommodityIDAndNameExists(new CommodityVO(
						commID, commName))) {
					JOptionPane.showMessageDialog(null, "商品信息有误！");
					return;
				}
				
				/*判断商品是否已经添加到报溢报损列表中*/
				for(LossOverflowOrderVO vo:orderList){
					if(vo.getCommID()==commID){
						JOptionPane.showMessageDialog(nameTF, "已添加到列表当中！");
						return;
					}
				}
				LossOverflowOrderVO lossOverflowOrderVO = new LossOverflowOrderVO(
						commID, commName, commNum, type);
				orderList.add(lossOverflowOrderVO);
				
				/*刷新赠送列表视图*/
				Thread t = new Thread(new Runnable() {

					@Override
					public void run() {
						cells = makeCells();
						model = new DefaultTableModel(cells,
								ColumnsConstants.LOSS_OVERFLOW_COLUMNS_CREATE);
						table = new JTable(model);
						scrollPane.setViewportView(table);
						scrollPane.validate();
					}

					private Object[][] makeCells() {
						Object[][] tempCells = new Object[cells.length + 1][];

						if (cells.length == 0) {
							tempCells[0] = new Object[] { commID, commName,
									commNum };
						} else {
							for (int i = 0; i < cells.length; i++) {
								tempCells[i] = cells[i];
							}
							tempCells[cells.length] = new Object[] { commID,
									commName, commNum };
						}

						return tempCells;
					}

				});

				t.start();
			}

		});

		submitBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (orderList.size() == 0) {
					JOptionPane.showMessageDialog(nameTF, "请输入商品信息！");
					return;
				}
				if (controller.addOrder(orderList)) {
					JOptionPane.showMessageDialog(null, "添加成功！");
				} else {
					JOptionPane.showMessageDialog(null, "添加失败！");
				}
				orderList.clear();
				cells = new Object[][] {};
				Thread t = new Thread(new Runnable() {

					@Override
					public void run() {
						model = new DefaultTableModel(cells,
								ColumnsConstants.LOSS_OVERFLOW_COLUMNS_CREATE);
						table = new JTable(model);
						scrollPane.setViewportView(table);
						scrollPane.validate();
					}

				});
				t.start();
			}

		});
	}

	private void makeComponent() {
		/* 商品信息输入面板组件初始化 */
		JPanel commPanel = new JPanel();
		commPanel.setLayout(new GridLayout(1, 17));
		JLabel idL = new JLabel("商品编号：");
		idTF = new JTextField(10);
		JLabel nameL = new JLabel("商品名称：");
		nameTF = new JTextField(10);
		JLabel numL = new JLabel(type.equals(OrderInfoConstants.LOSS)?"溢出数量：":"报损数量：");
		numTF = new JTextField();
		addBtn = new JButton("加入列表");

		commPanel.add(new JLabel());
		commPanel.add(new JLabel());
		commPanel.add(new JLabel());
		commPanel.add(new JLabel());
		commPanel.add(idL);
		commPanel.add(idTF);
		commPanel.add(nameL);
		commPanel.add(nameTF);
		commPanel.add(numL);
		commPanel.add(numTF);
		commPanel.add(addBtn);
		commPanel.add(new JLabel());
		commPanel.add(new JLabel());
		commPanel.add(new JLabel());
		commPanel.add(new JLabel());

		/* 待添加的商品列表组件初始化 */
		scrollPane = new JScrollPane();
		model = new DefaultTableModel(cells,
				ColumnsConstants.LOSS_OVERFLOW_COLUMNS_CREATE);
		table = new JTable(model);
		scrollPane.setViewportView(table);

		/* 提交按钮初始化 */
		JPanel bottomPanel = new JPanel();
		submitBtn = new JButton("提交");
		bottomPanel.add(submitBtn);

		this.setLayout(new BorderLayout());
		this.add(commPanel, BorderLayout.NORTH);
		this.add(scrollPane, BorderLayout.CENTER);
		this.add(bottomPanel, BorderLayout.SOUTH);
	}
}
