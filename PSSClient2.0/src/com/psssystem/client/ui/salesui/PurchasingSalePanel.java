package com.psssystem.client.ui.salesui;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

import javax.swing.*;
import javax.swing.table.*;
import javax.swing.tree.DefaultMutableTreeNode;

import com.psssystem.client.controller.admincontroller.IUserController;
import com.psssystem.client.controller.salescontroller.*;
import com.psssystem.client.controllerimpl.admincontrollerimpl.UserControllerImpl;
import com.psssystem.client.controllerimpl.salescontrollerimpl.*;
import com.psssystem.client.ui.commodity.CommodityTreePanel;
import com.psssystem.client.ui.mainui.LoginFrame;
import com.psssystem.connection.vo.*;
import com.psssystem.client.data.ColumnsConstants;
import com.psssystem.client.data.IdentityInfoConstants;
import com.psssystem.client.data.OperationInfoConstants;
//单据编号（格式为：JHD-yyyyMMdd-xxxxx），供应商，仓库，操作员，
//入库商品列表，备注，总额合计。
//其中入库商品列表包含的信息有：商品编号，名称（从商品选择界面进行选择），型号，数量（手动输入），单价（默认为商品信息中的进价），金额，备注（手动输入）

public class PurchasingSalePanel extends JSplitPane {
	private CommodityTreePanel treeScrollPane;
	private JPanel opPanel;
	private JScrollPane tableScrollPane;
	private JTable table;
	private TableModel tableModel;

	private JButton inputBtn;

	private JTextField operatorTF;
	private JComboBox customerCB;
	private JTextField remarksTF;
	/* 销售以及退货面板 */
	private JTextField salesmanTF = null;
	private JTextField sumBeforeDiscountTF = null;
	private JTextField discountTF = null;
	private JTextField sumAfterDiscountTF = null;
	private JTextField voucherTF = null;
	/* 进货以及退货面板 */
	private JTextField sumTF = null;

	private JButton submitBtn;

	private PurchasingOrderChooser chooser;

	private Object[][] cells = new Object[][] {};
	private ICustomerController customerController;
	private IUserController userController;
	private IPurchasingOrderController purchasingOrderController;
	private ISalesOrderController salesOrderController;
	private List<CommodityItemVO> commodityList = new ArrayList<CommodityItemVO>();
	private List<CustomerVO> customerList = null;
	private List<UserVO> userList = null;
	private String operation;
	private String username;
	private int userID;

	public PurchasingSalePanel(String operation, String username) {
		this.operation = operation;
		this.username = username;
		this.userController = new UserControllerImpl();
		this.userList = userController.getUsers(IdentityInfoConstants.SALES);
		this.customerController = new CustomerControllerImpl();
		this.purchasingOrderController = new PurchasingOrderControllerImpl();
		this.salesOrderController = new SalesOrderControllerImpl();
		userID = getUserIDByName(username);
		init();
	}

	private void init() {
		makeComponents();
		addListeners();
	}

	private void addListeners() {
		if (getCustomers().length == 0) {
			doNotInitListener();
			return;
		} else {
			doInitListener();
		}

		if (operation.equals(OperationInfoConstants.OP_SALE)
				|| operation.equals(OperationInfoConstants.OP_SALERETURN)) {
			doSalesExtraListener();
		}
	}

	private void doSalesExtraListener() {
		voucherTF.addFocusListener(new FocusListener() {

			@Override
			public void focusGained(FocusEvent e) {
			}

			@Override
			public void focusLost(FocusEvent e) {
				if (!Pattern.compile("^[0-9]+$").matcher(voucherTF.getText())
						.find()) {
					voucherTF.setText("0");
				}

				try {
					sumBeforeDiscountTF.setText((getSum() - Integer
							.parseInt(voucherTF.getText())) + "");
					sumAfterDiscountTF.setText((getSum()
							- Integer.parseInt(discountTF.getText()) - Integer
								.parseInt(voucherTF.getText())) + "");
				} catch (NumberFormatException ex) {

				}
			}

		});

		discountTF.addFocusListener(new FocusListener() {

			@Override
			public void focusGained(FocusEvent e) {
			}

			@Override
			public void focusLost(FocusEvent e) {
				if (!Pattern.compile("^[0-9]+$").matcher(discountTF.getText())
						.find()) {
					discountTF.setText("0");
				}
				sumAfterDiscountTF.setText((getSum()
						- Integer.parseInt(discountTF.getText()) - Integer
							.parseInt(voucherTF.getText())) + "");
			}

		});

	}

	private void doInitListener() {
		inputBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				/* 判断是否选择了商品 */
				DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) treeScrollPane
						.getTree().getLastSelectedPathComponent();
				if (selectedNode == null || selectedNode.getAllowsChildren()) {
					JOptionPane.showMessageDialog(null, "请先选择商品！");
					return;
				}

				/* 获取用户选择的商品对象 */
				CommodityVO selectedCommodity = treeScrollPane
						.getSelectedCommodity(selectedNode.toString());

				/* 取得用户输入的商品信息 ，并传递用户选择的商品构造完整的商品对象 */
				if (chooser == null)
					chooser = new PurchasingOrderChooser(selectedCommodity
							.getId(), selectedCommodity.getType(),
							selectedCommodity.getName());
				if (!chooser.showDialog(PurchasingSalePanel.this, "connect")) {
					return;
				}
				final CommodityItemVO storageIn = new CommodityItemVO.Builder()
						.copyProperties(chooser.getCommodityItemVO())
						.commID(selectedCommodity.getId())
						.commName(selectedCommodity.getName())
						.commType(selectedCommodity.getType()).build();

				/* 更新商品列表commodityList中的商品 */
				for (CommodityItemVO vo : commodityList) {
					if (vo.getCommID() == storageIn.getCommID()) {
						JOptionPane.showMessageDialog(null, "该商品已存在进货单中！");
						return;
					}
				}
				commodityList.add(storageIn);

				/* 更新表格视图 */
				cells = increaseCells(storageIn);
				updateView();
			}
		});

		submitBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				/* 判断用户是否已经添加商品 */
				if (commodityList == null || commodityList.size() == 0) {
					JOptionPane.showMessageDialog(null, "请输入商品信息！");
					return;
				}
				if ("".equals(remarksTF.getText()) || "".equals(salesmanTF.getText())) {
					JOptionPane.showMessageDialog(null, "请输入" + operation + "单附加信息！");
					return;
				}
				switch (operation) {
				case OperationInfoConstants.OP_PURCHASING:
					doPurchasing();
					break;
				case OperationInfoConstants.OP_PURCHASINGRETURN:
					doPurchasingReturn();
					break;
				case OperationInfoConstants.OP_SALE:
					ensureNumFieldLegal();
					doSale();
					break;
				case OperationInfoConstants.OP_SALERETURN:
					ensureNumFieldLegal();
					daoSaleReturn();
					break;
				}
				/* 清空商品信息，同时更新视图 */
				cells = new Object[][] {};
				commodityList.removeAll(commodityList);
				updateTable();
			}

		});
	}

	protected void ensureNumFieldLegal() {
		if (!Pattern.compile("^[0-9]+$").matcher(voucherTF.getText())
				.find()) {
			voucherTF.setText("0");
		}
		if (!Pattern.compile("^[0-9]+$").matcher(discountTF.getText())
				.find()) {
			discountTF.setText("0");
		}
		
		sumBeforeDiscountTF.setText((getSum() - Integer
				.parseInt(voucherTF.getText())) + "");
		sumAfterDiscountTF.setText((getSum()
				- Integer.parseInt(discountTF.getText()) - Integer
					.parseInt(voucherTF.getText())) + "");
				
	}

	private Object[][] increaseCells(CommodityItemVO storageIn) {
		/* "编号","名称","型号","数量","单价","金额","备注" */
		Object[][] temp = new Object[cells.length + 1][];
		for (int i = 0; i < cells.length; i++) {
			temp[i] = cells[i];
		}
		temp[cells.length] = new Object[] { storageIn.getCommID(),
				storageIn.getCommName(), storageIn.getCommType(),
				storageIn.getAmount(), storageIn.getPrice(),
				storageIn.getSum(), storageIn.getRemarks() };
		return temp;
	}

	private void updateView() {
		/*更新表格视图*/
		updateTable();
		/* 更新总额视图 */
		if (operation.equals(OperationInfoConstants.OP_PURCHASING)
				|| operation.equals(OperationInfoConstants.OP_PURCHASINGRETURN)) {
			sumTF.setText(getSum() + "");
		} else {
			sumBeforeDiscountTF.setText((getSum() - Integer
					.parseInt(voucherTF.getText())) + "");
			sumAfterDiscountTF.setText((getSum()
					- Integer.parseInt(discountTF.getText()) - Integer
						.parseInt(voucherTF.getText())) + "");
					
		}

	}

	private void doNotInitListener() {
		inputBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showMessageDialog(null, "请先创建客户！");
			}

		});
		submitBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showMessageDialog(null, "请先创建客户！");
			}

		});
	}

	private void updateTable() {
		Thread t = new Thread(new Runnable() {

			@Override
			public void run() {
				tableModel = new DefaultTableModel(cells,
						ColumnsConstants.PURCHASING_SALES_COLUMNS_CREATE);
				table = new JTable(tableModel);
				tableScrollPane.setViewportView(table);
				tableScrollPane.validate();
			}

		});
		t.start();
	}

	private void daoSaleReturn() {
		boolean status = salesOrderController
				.addSalesReturnOrder(new SalesOrderVO.Builder(
						getCustomerIDByName(customerCB.getSelectedItem()
								.toString()), userID)
						.customerName(customerCB.getSelectedItem().toString())
						.salesman(salesmanTF.getText())
						.storageList(commodityList)
						.sumBeforeDiscount(
								Integer.parseInt(sumBeforeDiscountTF.getText()))
						.discount(Integer.parseInt(discountTF.getText()))
						.voucher(Integer.parseInt(voucherTF.getText()))
						.sumAfterDiscount(
								Integer.parseInt(sumAfterDiscountTF.getText()))
						.remarks(remarksTF.getText()).build());
		if (status)
			JOptionPane.showMessageDialog(null, "销售退货单添加成功！");
		else {
			JOptionPane.showMessageDialog(null, "销售退货单添加失败！");
		}
		remarksTF.setText("");
		salesmanTF.setText("");
		voucherTF.setText("");
		discountTF.setText("");
		sumBeforeDiscountTF.setText("0");
		sumAfterDiscountTF.setText("0");
	}

	private void doSale() {
		boolean status = salesOrderController
				.addSalesOrder(new SalesOrderVO.Builder(
						getCustomerIDByName(customerCB.getSelectedItem()
								.toString()), userID)
						.customerName(customerCB.getSelectedItem().toString())
						.salesman(salesmanTF.getText())
						.storageList(commodityList)
						.sumBeforeDiscount(
								Integer.parseInt(sumBeforeDiscountTF.getText()))
						.discount(Integer.parseInt(discountTF.getText()))
						.voucher(Integer.parseInt(voucherTF.getText()))
						.sumAfterDiscount(
								Integer.parseInt(sumAfterDiscountTF.getText()))
						.remarks(remarksTF.getText()).build());
		if (status)
			JOptionPane.showMessageDialog(null, "销售单添加成功！");
		else {
			JOptionPane.showMessageDialog(null, "销售单添加失败！");
		}
		remarksTF.setText("");
		salesmanTF.setText("");
		voucherTF.setText("0");
		discountTF.setText("0");
		sumBeforeDiscountTF.setText("0");
		sumAfterDiscountTF.setText("0");
	}
	
	

	private void doPurchasingReturn() {
		boolean status = purchasingOrderController
				.addPurchasingReturnOrder(new PurchasingOrderVO.Builder(
						getCustomerIDByName((String) customerCB
								.getSelectedItem()), userID)
						.salesman(salesmanTF.getText())
						.customerName(customerCB.getSelectedItem().toString())
						.storageList(commodityList)
						.remarks(remarksTF.getText())
						.sum(Integer.parseInt(sumTF.getText())).build());
		if (status)
			JOptionPane.showMessageDialog(null, "进货退货单添加成功！");
		else {
			JOptionPane.showMessageDialog(null, "进货退货单添加失败！");
		}
		remarksTF.setText("");
		sumTF.setText("0");
		salesmanTF.setText("");
	}

	private void doPurchasing() {
		boolean status = purchasingOrderController
				.addPurchasingOrder(new PurchasingOrderVO.Builder(
						getCustomerIDByName(customerCB.getSelectedItem()
								.toString()), userID)
						.customerName(customerCB.getSelectedItem().toString())
						.salesman(salesmanTF.getText())
						.storageList(commodityList)
						.remarks(remarksTF.getText())
						.sum(Integer.parseInt(sumTF.getText())).build());
		if (status){
			JOptionPane.showMessageDialog(null, "进货单添加成功！");
		}else {
			JOptionPane.showMessageDialog(null, "进货单添加失败！");
		}
		remarksTF.setText("");
		sumTF.setText("0");
		salesmanTF.setText("");
	}

	private int getSum() {
		int sum = 0;
		Iterator<CommodityItemVO> itr=commodityList.iterator();
		while(itr.hasNext()){
			sum += itr.next().getSum();
		}
		return sum;
	}

	private void makeComponents() {
		treeScrollPane = new CommodityTreePanel();

		opPanel = new JPanel();

		tableScrollPane = new JScrollPane();
		tableModel = new DefaultTableModel(cells,
				ColumnsConstants.PURCHASING_SALES_COLUMNS_CREATE);
		table = new JTable(tableModel);
		tableScrollPane.setViewportView(table);

		JPanel orderInfoPanel = new JPanel();
		String customers[] = getCustomers();
		operatorTF = new JTextField(username);
		operatorTF.setEditable(false);
		customerCB = new JComboBox(customers);
		remarksTF = new JTextField(10);
		salesmanTF = new JTextField();

		JLabel operatorL = new JLabel("操作员：");
		JLabel remarksL = new JLabel("备注：");
		JLabel salesmanL = new JLabel("业务员：");
		if (operation.equals(OperationInfoConstants.OP_PURCHASING)
				|| operation.equals(OperationInfoConstants.OP_PURCHASINGRETURN)) {
			orderInfoPanel.setLayout(new GridLayout(3, 6));
			JLabel customerL = new JLabel("进货商：");
			JLabel sumL = new JLabel("总额：");

			sumTF = new JTextField("0");
			sumTF.setEditable(false);
			orderInfoPanel.add(new JLabel());
			orderInfoPanel.add(operatorL);
			orderInfoPanel.add(operatorTF);
			orderInfoPanel.add(salesmanL);
			orderInfoPanel.add(salesmanTF);
			orderInfoPanel.add(new JLabel());
			orderInfoPanel.add(new JLabel());
			orderInfoPanel.add(customerL);
			orderInfoPanel.add(customerCB);
			orderInfoPanel.add(remarksL);
			orderInfoPanel.add(remarksTF);
			orderInfoPanel.add(new JLabel());
			orderInfoPanel.add(new JLabel());
			orderInfoPanel.add(sumL);
			orderInfoPanel.add(sumTF);
			orderInfoPanel.add(new JLabel());
			orderInfoPanel.add(new JLabel());
			orderInfoPanel.add(new JLabel());
		} else {
			orderInfoPanel.setLayout(new GridLayout(4, 6));
			sumBeforeDiscountTF = new JTextField("0");
			discountTF = new JTextField("0");
			sumAfterDiscountTF = new JTextField("0");
			voucherTF = new JTextField("0");
			sumBeforeDiscountTF.setEditable(false);
			sumAfterDiscountTF.setEditable(false);
			JLabel customerL = new JLabel("销售商：");
			JLabel sumBeforeDiscountL = new JLabel("折让前总额：");
			JLabel discountL = new JLabel("折让：");
			JLabel sumAfterDiscountL = new JLabel("折让后总额：");
			JLabel voucherL = new JLabel("代金券：");
			orderInfoPanel.add(new JLabel());
			orderInfoPanel.add(operatorL);
			orderInfoPanel.add(operatorTF);
			orderInfoPanel.add(customerL);
			orderInfoPanel.add(customerCB);
			orderInfoPanel.add(new JLabel());
			orderInfoPanel.add(new JLabel());
			orderInfoPanel.add(remarksL);
			orderInfoPanel.add(remarksTF);
			orderInfoPanel.add(salesmanL);
			orderInfoPanel.add(salesmanTF);
			orderInfoPanel.add(new JLabel());
			orderInfoPanel.add(new JLabel());
			orderInfoPanel.add(voucherL);
			orderInfoPanel.add(voucherTF);
			orderInfoPanel.add(discountL);
			orderInfoPanel.add(discountTF);
			orderInfoPanel.add(new JLabel());
			orderInfoPanel.add(new JLabel());
			orderInfoPanel.add(sumBeforeDiscountL);
			orderInfoPanel.add(sumBeforeDiscountTF);
			orderInfoPanel.add(sumAfterDiscountL);
			orderInfoPanel.add(sumAfterDiscountTF);
		}

		JPanel tp = new JPanel();
		inputBtn = new JButton("输入商品信息");
		tp.add(inputBtn);
		JPanel bp = new JPanel();
		submitBtn = new JButton("提交");
		bp.add(submitBtn);
		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(new BorderLayout());
		bottomPanel.add(tp, BorderLayout.NORTH);
		bottomPanel.add(orderInfoPanel, BorderLayout.CENTER);
		bottomPanel.add(bp, BorderLayout.SOUTH);

		opPanel.setLayout(new BorderLayout());
		opPanel.add(tableScrollPane, BorderLayout.CENTER);
		opPanel.add(bottomPanel, BorderLayout.SOUTH);
		this.setLeftComponent(treeScrollPane);
		this.setRightComponent(opPanel);
	}

	private String[] getCustomers() {
		customerList = customerController.getAllCustomers();
		Iterator<CustomerVO> itr = customerList.iterator();
		while (itr.hasNext()) {
			if (operation.equals(OperationInfoConstants.OP_PURCHASING)
					|| operation
							.equals(OperationInfoConstants.OP_PURCHASINGRETURN)) {
				if (!itr.next().getType()
						.equals(OperationInfoConstants.PURCHASING_BUSINNES))
					itr.remove();
			} else if (operation.equals(OperationInfoConstants.OP_SALE)
					|| operation.equals(OperationInfoConstants.OP_SALERETURN)) {
				if (!itr.next().getType().equals(OperationInfoConstants.SALE_BUSINNES))
					itr.remove();
			}
		}

		String[] customers = new String[customerList.size()];
		for (int i = 0; i < customerList.size(); i++) {
			customers[i] = customerList.get(i).getName();
		}
		return customers;
	}

	private int getCustomerIDByName(String name) {
		for (CustomerVO vo : customerList) {
			if (vo.getName().equals(name)) {
				return vo.getId();
			}
		}
		return -1;
	}

	private int getUserIDByName(String name) {
		for (UserVO vo : userList) {
			if (vo.getUsername().equals(name)) {
				return vo.getId();
			}
		}
		return -1;

	}

	private class PurchasingOrderChooser extends JPanel {
		private JTextField amountTF;
		private JTextField priceTF;
		private JTextField remarksTF;
		private JButton okBtn;
		private JButton cancelBtn;
		private boolean ok;
		private JDialog dialog;
		private int commID;
		private String commType, commName;

		public PurchasingOrderChooser(int commID, String commType,
				String commName) {
			this.commID = commID;
			this.commType = commType;
			this.commName = commName;
			this.init();
			this.addListeners();
		}

		private void addListeners() {
			okBtn.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					if (!Pattern.compile("^[0-9]+$")
							.matcher(amountTF.getText()).find()) {
						amountTF.setText("0");
						return;
					}

					if (!Pattern.compile("^[0-9]+$").matcher(priceTF.getText())
							.find()) {
						priceTF.setText("0");
						return;
					}
					if ("".equals(remarksTF.getText())) {
						return;
					}
					try {
						if (Integer.parseInt(amountTF.getText()) == 0
								|| Integer.parseInt(priceTF.getText()) == 0) {
							return;
						}
					} catch (NumberFormatException ex) {
						JOptionPane.showMessageDialog(null, "土豪啊！");
						return;
					}

					ok = true;
					dialog.setVisible(false);
				}

			});

			cancelBtn.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					ok = false;
					dialog.setVisible(false);
				}

			});
		}

		private void init() {
			JPanel topPanel = new JPanel();
			GridLayout layout = new GridLayout(3, 4);
			layout.setVgap(20);
			topPanel.setLayout(layout);
			JLabel amountL = new JLabel("数量：");
			JLabel priceL = new JLabel("进价：");
			JLabel remarksL = new JLabel("备注：");
			amountTF = new JTextField(10);
			priceTF = new JTextField(10);
			remarksTF = new JTextField(10);
			topPanel.add(new JLabel());
			topPanel.add(amountL);
			topPanel.add(amountTF);
			topPanel.add(new JLabel());
			topPanel.add(new JLabel());
			topPanel.add(priceL);
			topPanel.add(priceTF);
			topPanel.add(new JLabel());
			topPanel.add(new JLabel());
			topPanel.add(remarksL);
			topPanel.add(remarksTF);
			topPanel.add(new JLabel());

			JPanel bottomPanel = new JPanel();
			okBtn = new JButton("确定");
			cancelBtn = new JButton("取消");
			bottomPanel.add(okBtn);
			bottomPanel.add(cancelBtn);
			this.setLayout(new BorderLayout());
			this.add(topPanel, BorderLayout.CENTER);
			this.add(bottomPanel, BorderLayout.SOUTH);
		}

		public void setStorageInList() {

		}

		public CommodityItemVO getCommodityItemVO() {
			CommodityItemVO vo = new CommodityItemVO.Builder().commID(commID)
					.commName(commName).commType(commType)
					.amount(Integer.parseInt(amountTF.getText()))
					.price(Integer.parseInt(priceTF.getText())).sum(Integer.parseInt(amountTF.getText())*Integer.parseInt(priceTF.getText()))
					.remarks(remarksTF.getText()).build();
			return vo;

		}

		public boolean showDialog(Component parent, String title) {
			JFrame owner = null;
			if (parent instanceof JFrame) {
				owner = (JFrame) parent;
			} else {
				owner = (JFrame) SwingUtilities.getAncestorOfClass(
						JFrame.class, parent);
			}

			if (dialog == null || dialog.getOwner() != owner) {
				dialog = new JDialog(owner, true);
				dialog.add(this);
				dialog.getRootPane().setDefaultButton(okBtn);
				dialog.pack();
			}
			dialog.setTitle(title);
			dialog.setLocation(owner.getMousePosition());
			dialog.setVisible(true);
			return ok;
		}
	}

	public static void main(String[] args) {
	}
}
