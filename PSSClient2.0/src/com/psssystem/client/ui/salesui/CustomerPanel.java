package com.psssystem.client.ui.salesui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import com.psssystem.client.controller.salescontroller.ICustomerController;
import com.psssystem.client.controllerimpl.salescontrollerimpl.CustomerControllerImpl;
import com.psssystem.client.data.ColumnsConstants;
import com.psssystem.client.ui.chooser.CustomerChooser;
import com.psssystem.connection.vo.CustomerVO;

public class CustomerPanel extends JTabbedPane {
	private JPanel addedPanel;
	private JButton chooseBtn;
	private JScrollPane addedScrollPane;
	private TableModel addedModel;
	private JTable addedTable;
	private JButton addedBtn;
	private CustomerChooser chooser;

	private JPanel deletePanel;
	private JScrollPane deleteScrollPane;
	private TableModel deleteModel;
	private JTable deleteTable;
	private JButton deleteBtn;

	private JPanel updatePanel;
	private JScrollPane updateScrollPane;
	private TableModel updateModel;
	private JTable updateTable;
	private JButton updateBtn;

	private JPanel searchPanel;
	private JTextField infoTF;
	private JButton searchBtn;
	private JScrollPane searchScrollPane;
	private DefaultTableModel searchModel;
	private JTable searchTable;

	private ICustomerController controller;
	/* 存储当前客户列表 */
	private List<CustomerVO> customerList = null;

	/* 添加和搜索表格信息 */
	private Object[][] addedCells = new Object[][] {};
	private Object[][] searchCells = new Object[][] {};

	/* 更新和删除表格信息 */
	private Object[][] cells = new Object[][] {};

	public CustomerPanel() {
		this.controller = new CustomerControllerImpl();
		this.customerList = controller.getAllCustomers();
		init();
	}

	private void init() {
		makeComponents();
		addListeners();
	}

	private void addListeners() {
		if(customerList.size()==0){
			chooseBtn.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent e) {
					JOptionPane.showMessageDialog(null, "您还没进行期初建账！");					
				}
				
			});
			addedBtn.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent e) {
					JOptionPane.showMessageDialog(null, "您还没进行期初建账！");					
				}
				
			});
			return;
		}
		chooseBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Thread t = new Thread(new Runnable() {

					@Override
					public void run() {
						CustomerVO customer = null;
						if (chooser == null)
							chooser = new CustomerChooser();
						if (!chooser.showDialog(CustomerPanel.this, "connect")) {
							return;
						}
						customer = chooser.getCustomer();
						addedCells = getCells(customer);
						addedModel = new DefaultTableModel(addedCells,
								ColumnsConstants.CUSTOMER_COLUMNS_CREATE);
						addedTable = new JTable(addedModel);
						addedScrollPane.setViewportView(addedTable);
						addedScrollPane.validate();
					}

				});
				t.start();
			}

			private Object[][] getCells(CustomerVO customer) {
				Object[][] temp = new Object[addedCells.length + 1][];
				for (int i = 0; i < addedCells.length; i++) {
					temp[i] = addedCells[i];
				}
				temp[addedCells.length] = new Object[] { customer.getName(),
						customer.getType(), customer.getLevel(),
						customer.getPhoneNumber(), customer.getAddr(),
						customer.getPostcode(), customer.getEmail(),
						customer.getDefaultSalesman() };
				return temp;
			}

		});

		addedBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				/* "姓名","分类","级别","电话","地址","邮编","电子邮箱","默认业务员" */
				Set<CustomerVO> customerList = new HashSet<CustomerVO>();
				for (Object[] o : addedCells) {
					CustomerVO customer = new CustomerVO.Builder((String) o[0],
							(String) o[1]).level((int) o[2])
							.phoneNumber((String) o[3]).addr((String) o[4])
							.postcode((int) o[5]).email((String) o[6])
							.defaultSalesman((String) o[7]).build();
					customerList.add(customer);
				}
				if(!controller.addCustomers(customerList)){
					JOptionPane.showMessageDialog(null, "客户信息有误！");
				};

				Thread t = new Thread(new Runnable() {

					@Override
					public void run() {
						addedCells = new Object[][] {};
						addedModel = new DefaultTableModel(addedCells,
								ColumnsConstants.CUSTOMER_COLUMNS_CREATE);
						addedTable = new JTable(addedModel);
						addedScrollPane.setViewportView(addedTable);
						addedScrollPane.validate();
					}

				});
				t.start();
			}

		});

		deleteBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				List<CustomerVO> deletedList = new ArrayList<CustomerVO>();
				for (int i = 0; i < deleteTable.getRowCount(); i++) {
					if ((boolean) deleteTable.getValueAt(i, 8)) {
						deletedList.add(customerList.get(i));
					}
				}
				if(!controller.deleteCustomers(deletedList))return;
				freshTable();
				addListeners();
			}

		});

		this.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				freshTable();
			}

		});

		updateBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				/* "姓名","分类","级别","电话","地址","邮编","电子邮箱","默认业务员" */
				List<CustomerVO> updateList = new ArrayList<CustomerVO>();
				for (int i = 0; i < updateTable.getRowCount(); i++) {
					if ((boolean) updateTable.getValueAt(i, 8)) {
						updateList.add(new CustomerVO.Builder(
								(String) updateTable.getValueAt(i, 0),
								(String) updateTable.getValueAt(i, 1))
								.id(customerList.get(i).getId())
								.level((int) updateTable.getValueAt(i, 2))
								.phoneNumber(
										(String) updateTable.getValueAt(i, 3))
								.addr((String) updateTable.getValueAt(i, 4))
								.postcode((int) updateTable.getValueAt(i, 5))
								.email((String) updateTable.getValueAt(i, 6))
								.defaultSalesman(
										(String) updateTable.getValueAt(i, 7))
								.build());
					}
				}

				controller.updateCustomers(updateList);
				freshTable();
			}

		});

		searchBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Thread t = new Thread(new Runnable() {

					@Override
					public void run() {
						searchCells = getCells(controller.searchCustomer(infoTF
								.getText()));
						searchModel = new DefaultTableModel(searchCells,
								ColumnsConstants.CUSTOMER_COLUMNS_CREATE);
						searchTable = new JTable(searchModel);
						searchScrollPane.setViewportView(searchTable);
						searchScrollPane.validate();
					}

				});
				t.start();

			}

			private Object[][] getCells(List<CustomerVO> searchCustomer) {
				System.out.println(searchCustomer.size());
				Object[][] temp = new Object[searchCustomer.size()][];
				for (int i = 0; i < searchCustomer.size(); i++) {
					CustomerVO vo = searchCustomer.get(i);
					temp[i] = new Object[] { vo.getName(), vo.getType(),
							vo.getLevel(), vo.getPhoneNumber(), vo.getAddr(),
							vo.getPostcode(), vo.getEmail(),
							vo.getDefaultSalesman() };
				}
				return temp;
			}

		});
	}

	private void freshTable() {
		Thread t = new Thread(new Runnable() {

			@Override
			public void run() {
				cells = initCells();
				deleteModel = new DefaultTableModel(cells,
						ColumnsConstants.CUSTOMER_COLUMNS) {
					public Class<?> getColumnClass(int c) {
						return cells[0][c].getClass();
					}

					public boolean isCellEditable(int r, int c) {
						return c == 8;
					}
				};
				deleteTable = new JTable(deleteModel);
				deleteScrollPane.setViewportView(deleteTable);
				deleteScrollPane.validate();

				updateModel = new DefaultTableModel(cells,
						ColumnsConstants.CUSTOMER_COLUMNS) {
					public Class<?> getColumnClass(int c) {
						return cells[0][c].getClass();
					}
				};
				updateTable = new JTable(updateModel);
				updateScrollPane.setViewportView(updateTable);
				updateScrollPane.validate();

			}

		});
		t.start();
	}

	private void makeComponents() {
		makeAddedPanel();
		makeDeletePanel();
		makeUpdatePanel();
		makeSearchPanel();

		this.setTabPlacement(JTabbedPane.LEFT);
		this.add("添加客户", addedPanel);
		this.add("删除客户", deletePanel);
		this.add("更新客户", updatePanel);
		this.add("查找客户", searchPanel);
	}

	private void makeSearchPanel() {
		searchPanel = new JPanel();

		infoTF = new JTextField(10);
		searchBtn = new JButton("搜索");
		JPanel searchTop = new JPanel();
		searchTop.add(infoTF);
		searchTop.add(searchBtn);

		searchScrollPane = new JScrollPane();
		searchModel = new DefaultTableModel(searchCells,
				ColumnsConstants.CUSTOMER_COLUMNS_CREATE);
		searchTable = new JTable(searchModel);
		searchScrollPane.setViewportView(searchTable);

		searchPanel.setLayout(new BorderLayout());
		searchPanel.add(searchTop, BorderLayout.NORTH);
		searchPanel.add(searchScrollPane, BorderLayout.CENTER);
	}

	private void makeUpdatePanel() {
		updatePanel = new JPanel();
		updateScrollPane = new JScrollPane();
		cells = initCells();
		updateModel = new DefaultTableModel(cells, ColumnsConstants.CUSTOMER_COLUMNS) {
			public Class<?> getColumnClass(int c) {
				return cells[0][c].getClass();
			}
			@Override
			public boolean isCellEditable(int r, int c) {
				return c==2||c==3||c==4||c==5||c==6||c==7||c==8;
			}
		};
		updateTable = new JTable(updateModel);
		updateScrollPane.setViewportView(updateTable);
		JPanel updateBottomPanel = new JPanel();
		updateBtn = new JButton("提交");
		updateBottomPanel.add(updateBtn);
		updatePanel.setLayout(new BorderLayout());
		updatePanel.add(updateScrollPane, BorderLayout.CENTER);
		updatePanel.add(updateBottomPanel, BorderLayout.SOUTH);
	}

	private void makeDeletePanel() {
		deletePanel = new JPanel();
		deleteScrollPane = new JScrollPane();
		cells = initCells();
		deleteModel = new DefaultTableModel(cells, ColumnsConstants.CUSTOMER_COLUMNS) {
			public Class<?> getColumnClass(int c) {
				return cells[0][c].getClass();
			}

			public boolean isCellEditable(int r, int c) {
				return c == 8;
			}
		};
		deleteTable = new JTable(deleteModel);
		deleteScrollPane.setViewportView(deleteTable);
		JPanel deleteBottomPanel = new JPanel();
		deleteBtn = new JButton("提交");
		deleteBottomPanel.add(deleteBtn);
		deletePanel.setLayout(new BorderLayout());
		deletePanel.add(deleteScrollPane, BorderLayout.CENTER);
		deletePanel.add(deleteBottomPanel, BorderLayout.SOUTH);
	}

	private void makeAddedPanel() {
		addedPanel = new JPanel();
		JPanel addedTop = new JPanel();
		chooseBtn = new JButton("手动输入");
		addedTop.add(chooseBtn);
		addedScrollPane = new JScrollPane();
		addedModel = new DefaultTableModel(addedCells,
				ColumnsConstants.CUSTOMER_COLUMNS_CREATE);
		addedTable = new JTable(addedModel);
		addedScrollPane.setViewportView(addedTable);
		JPanel addedBottomPanel = new JPanel();
		addedBtn = new JButton("提交");
		addedBottomPanel.add(addedBtn);
		addedPanel.setLayout(new BorderLayout());
		addedPanel.add(addedTop, BorderLayout.NORTH);
		addedPanel.add(addedScrollPane, BorderLayout.CENTER);
		addedPanel.add(addedBottomPanel, BorderLayout.SOUTH);
	}

	private Object[][] initCells() {
		customerList=controller.getAllCustomers();
		/* "姓名","分类","级别","电话","地址","邮编","电子邮箱","默认业务员" */
		Object[][] temp = new Object[customerList.size()][];
		for (int i = 0; i < customerList.size(); i++) {
			CustomerVO vo = customerList.get(i);
			temp[i] = new Object[] { vo.getName(), vo.getType(), vo.getLevel(),
					vo.getPhoneNumber(), vo.getAddr(), vo.getPostcode(),
					vo.getEmail(), vo.getDefaultSalesman(), false };
		}
		return temp;
	}

}
