package com.psssystem.client.ui.managerui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import com.psssystem.client.controller.financecontroller.IPaymentOrderController;
import com.psssystem.client.controller.financecontroller.IReceiptsOrderController;
import com.psssystem.client.controller.salescontroller.IPurchasingOrderController;
import com.psssystem.client.controller.salescontroller.ISalesOrderController;
import com.psssystem.client.controller.storagecontroller.IAlarmOrderController;
import com.psssystem.client.controller.storagecontroller.IGiftOrderController;
import com.psssystem.client.controller.storagecontroller.ILossOverflowController;
import com.psssystem.client.controllerimpl.financecontrollerimpl.PaymentOrderControllerImpl;
import com.psssystem.client.controllerimpl.financecontrollerimpl.ReceiptsOrderControllerImpl;
import com.psssystem.client.controllerimpl.salescontrollerimpl.PurchasingOrderControllerImpl;
import com.psssystem.client.controllerimpl.salescontrollerimpl.SalesOrderControllerImpl;
import com.psssystem.client.controllerimpl.storagecontrollerimpl.AlarmOrderControllerImpl;
import com.psssystem.client.controllerimpl.storagecontrollerimpl.GiftOrderControllerImpl;
import com.psssystem.client.controllerimpl.storagecontrollerimpl.LossOverflowControllerImpl;
import com.psssystem.client.data.ColumnsConstants;
import com.psssystem.client.data.OrderInfoConstants;
import com.psssystem.connection.vo.AlarmOrderVO;
import com.psssystem.connection.vo.GiftOrderVO;
import com.psssystem.connection.vo.LossOverflowOrderVO;
import com.psssystem.connection.vo.PaymentOrderVO;
import com.psssystem.connection.vo.PurchasingOrderVO;
import com.psssystem.connection.vo.ReceiptsOrderVO;
import com.psssystem.connection.vo.SalesOrderVO;

public class ApprovalPanel extends JTabbedPane {
	private JScrollPane alarmScrollPane;
	private JScrollPane giftScrollPane;
	private JScrollPane lossScrollPane;
	private JScrollPane overflowScrollPane;
	private JScrollPane paymentScrollPane;
	private JScrollPane receiptsScrollPane;
	private JScrollPane purchasingScrollPane;
	private JScrollPane purchasingReturnScrollPane;
	private JScrollPane salesScrollPane;
	private JScrollPane salesReturnScrollPane;

	private JTable alarmTable;
	private JTable giftTable;
	private JTable lossTable;
	private JTable overflowTable;
	private JTable paymentTable;
	private JTable receiptsTable;
	private JTable purchasingTable;
	private JTable purchasingReturnTable;
	private JTable salesTable;
	private JTable salesReturnTable;

	private TableModel alarmModel;
	private TableModel giftModel;
	private TableModel lossModel;
	private TableModel overflowModel;
	private TableModel paymentModel;
	private TableModel receiptsModel;
	private TableModel purchasingModel;
	private TableModel purchasingReturnModel;
	private TableModel salesModel;
	private TableModel salesReturnModel;

	private JButton alarmBtn;
	private JButton giftBtn;
	private JButton lossBtn;
	private JButton overflowBtn;
	private JButton paymentBtn;
	private JButton receiptsBtn;
	private JButton purchasingBtn;
	private JButton purchasingReturnBtn;
	private JButton salesBtn;
	private JButton salesReturnBtn;

	private Object[][] alarmCells = new Object[][] {};
	private Object[][] giftCells = new Object[][] {};
	private Object[][] lossCells = new Object[][] {};
	private Object[][] overflowCells = new Object[][] {};
	private Object[][] paymentCells = new Object[][] {};
	private Object[][] receiptsCells = new Object[][] {};
	private Object[][] purchasingCells = new Object[][] {};
	private Object[][] purchasingReturnCells = new Object[][] {};
	private Object[][] salesCells = new Object[][] {};
	private Object[][] salesReturnCells = new Object[][] {};

	private IAlarmOrderController alarmController;
	private IGiftOrderController giftController;
	private ILossOverflowController lossOverflowController;
	private IPaymentOrderController paymentController;
	private IReceiptsOrderController receiptsController;
	private IPurchasingOrderController purchasingController;
	private ISalesOrderController salesController;

	private Set<AlarmOrderVO> alarmSet;
	private Set<GiftOrderVO> giftSet;
	private Set<LossOverflowOrderVO> lossSet;
	private Set<LossOverflowOrderVO> overflowSet;
	private Set<PaymentOrderVO> paymentSet;
	private Set<ReceiptsOrderVO> receiptsSet;
	private Set<PurchasingOrderVO> purchasingSet;
	private Set<PurchasingOrderVO> purchasingReturnSet;
	private Set<SalesOrderVO> salesSet;
	private Set<SalesOrderVO> salesReturnSet;

	public ApprovalPanel() {
		initController();
		initSet();
		initComponent();
	}

	private void initSet() {
		this.alarmSet = this.alarmController.getNotPassed();
		this.giftSet = this.giftController.getNotPassed();
		this.lossSet = this.lossOverflowController.getNotPassedByType(OrderInfoConstants.LOSS);
		this.overflowSet = this.lossOverflowController
				.getNotPassedByType(OrderInfoConstants.OVERFLOW);
		this.paymentSet = this.paymentController.getNotPassed();
		this.receiptsSet = this.receiptsController.getNotPassed();
		this.salesSet = this.salesController.getNotPassedByType(OrderInfoConstants.SALES);
		this.salesReturnSet = this.salesController
				.getNotPassedByType(OrderInfoConstants.SALESRETURN);
		this.purchasingSet = this.purchasingController
				.getNotPassedByType(OrderInfoConstants.PURCHASING);
		this.purchasingReturnSet = this.purchasingController
				.getNotPassedByType(OrderInfoConstants.PURCHASINGRETURN);		
	}

	private void initController() {
		alarmController = new AlarmOrderControllerImpl();
		giftController = new GiftOrderControllerImpl();
		lossOverflowController = new LossOverflowControllerImpl();
		paymentController = new PaymentOrderControllerImpl();
		receiptsController = new ReceiptsOrderControllerImpl();
		purchasingController = new PurchasingOrderControllerImpl();
		salesController = new SalesOrderControllerImpl();
	}

	private void initComponent() {
		initCells();
		makeComponents();
		addListeners();
	}

	private void initCells() {
		initAlarmCells();
		initGiftCells();
		initLossCells();
		initOverflowCells();
		initPaymentCells();
		initReceiptsCells();
		initPurchasingCells();
		initPurchasingReturnCells();
		initSalesCells();
		initSalesReturnCells();
	}

	private void initSalesReturnCells() {
		salesReturnCells = new Object[salesReturnSet.size()][];	
		int i = 0;
		for (SalesOrderVO vo : salesReturnSet) {
			salesReturnCells[i] = new Object[] { vo.getId(), vo.getCustomerID(),
					vo.getUserID(), vo.getSalesman(), vo.getRemarks(),
					vo.getSumBeforeDiscount(),vo.getSumAfterDiscount(),vo.getDiscount(),vo.getVoucher(), vo.getCreatedDate(), vo.getStatus(),false };
			System.out.println(vo.getStatus());
			i++;
		}
	}

	private void initSalesCells() {
		salesCells = new Object[salesSet.size()][];	
		int i = 0;
		for (SalesOrderVO vo : salesSet) {
			salesCells[i] = new Object[] { vo.getId(), vo.getCustomerID(),
					vo.getUserID(), vo.getSalesman(), vo.getRemarks(),
					vo.getSumBeforeDiscount(),vo.getSumAfterDiscount(),vo.getDiscount(),vo.getVoucher(), vo.getCreatedDate(), vo.getStatus(),false };
			i++;
		}
	}

	private void initPurchasingReturnCells() {
		purchasingReturnCells = new Object[purchasingReturnSet.size()][];
		int i = 0;
		for (PurchasingOrderVO vo : purchasingReturnSet) {
			purchasingReturnCells[i] = new Object[] { vo.getId(),
					vo.getCustomerID(), vo.getUserID(), vo.getSalesman(),
					vo.getRemarks(), vo.getSum(), vo.getCreatedDate(),
					vo.getStatus(),false};
			i++;
		}
	}

	private void initPurchasingCells() {
		purchasingCells = new Object[purchasingSet.size()][];
		int i = 0;
		for (PurchasingOrderVO vo : purchasingSet) {
			purchasingCells[i] = new Object[] { vo.getId(), vo.getCustomerID(),
					vo.getUserID(), vo.getSalesman(), vo.getRemarks(),
					vo.getSum(), vo.getCreatedDate(), vo.getStatus(),false };
			i++;
		}		
	}

	private void initReceiptsCells() {
		receiptsCells = new Object[receiptsSet.size()][];
		int i = 0;
		for (ReceiptsOrderVO vo : receiptsSet) {
			receiptsCells[i] = new Object[] { vo.getId(), vo.getCustomerID(),
					vo.getUserID(), vo.getSum(), vo.getCreatedDate(),
					vo.getStatus(),false };
			i++;
		}		
	}

	private void initPaymentCells() {
		paymentCells = new Object[paymentSet.size()][];		
		int i = 0;
		for (PaymentOrderVO vo : paymentSet) {
			paymentCells[i] = new Object[] { vo.getId(), vo.getCustomerID(),
					vo.getUserID(), vo.getAccountName(), vo.getSum(),
					vo.getCreatedDate(), vo.getStatus(),false };
			i++;
		}
	}

	private void initOverflowCells() {
		overflowCells = new Object[overflowSet.size()][];	
		int i = 0;
		for (LossOverflowOrderVO vo : overflowSet) {
			overflowCells[i] = new Object[] { vo.getId(),vo.getCommID(),
					vo.getCommName(), vo.getAmount(), vo.getCreatedDate(),
					vo.getStatus() ,false};
			i++;
		}

	}

	private void initLossCells() {
		lossCells = new Object[lossSet.size()][];	
		int i = 0;
		for (LossOverflowOrderVO vo : lossSet) {
			lossCells[i] = new Object[] {vo.getId(), vo.getCommID(),
					vo.getCommName(), vo.getAmount(), vo.getCreatedDate(),
					vo.getStatus(),false };
			i++;
		}
	}

	private void initGiftCells() {
		giftCells = new Object[giftSet.size()][];
		int i = 0;
		for (GiftOrderVO vo : giftSet) {
			giftCells[i] = new Object[] {vo.getId(),vo.getCommID(),
					vo.getCommName(), vo.getAmount(), vo.getCreatedDate(),
					vo.getStatus() ,false};
			i++;
		}		
	}

	private void initAlarmCells() {
		alarmCells = new Object[alarmSet.size()][];		
		int i = 0;
		for (AlarmOrderVO vo : alarmSet) {
			alarmCells[i] = new Object[] { vo.getId(), vo.getCommID(),
					vo.getCommName(), vo.getCreatedDate(), vo.getStatus() ,false};
			i++;
		}
	}

	private void addListeners() {
		alarmBtn.addActionListener(new ApprovalListener(OrderInfoConstants.ALARM));
		giftBtn.addActionListener(new ApprovalListener(OrderInfoConstants.GIFT));
		lossBtn.addActionListener(new ApprovalListener(OrderInfoConstants.LOSS));
		overflowBtn.addActionListener(new ApprovalListener(OrderInfoConstants.OVERFLOW));
		paymentBtn.addActionListener(new ApprovalListener(OrderInfoConstants.PAYMENT));
		receiptsBtn.addActionListener(new ApprovalListener(OrderInfoConstants.RECEIPTS));
		purchasingBtn.addActionListener(new ApprovalListener(OrderInfoConstants.PURCHASING));
		purchasingReturnBtn.addActionListener(new ApprovalListener(OrderInfoConstants.PURCHASINGRETURN));
		salesBtn.addActionListener(new ApprovalListener(OrderInfoConstants.SALES));
		salesReturnBtn.addActionListener(new ApprovalListener(OrderInfoConstants.SALESRETURN));
		
		this.addChangeListener(new ChangeListener(){

			@Override
			public void stateChanged(ChangeEvent e) {
				int index=ApprovalPanel.this.getSelectedIndex();
				switch(index){
				case 0:
					Set<AlarmOrderVO> alarmTemp=alarmController.getNotPassed();
					if(alarmTemp.size()==alarmSet.size()){
						return;
					}else{
						alarmSet=alarmTemp;
						Thread t=new Thread(new ApprovalRunnable(OrderInfoConstants.ALARM));
						t.start();
					}
				case 1:
					Set<GiftOrderVO> giftTemp=giftController.getNotPassed();
					if(giftTemp.size()==giftSet.size()){
						return;
					}else{
						giftSet=giftTemp;
						Thread t=new Thread(new ApprovalRunnable(OrderInfoConstants.GIFT));
						t.start();
					}
				case 2:
					Set<LossOverflowOrderVO> lossTemp=lossOverflowController.getNotPassedByType(OrderInfoConstants.LOSS);
					if(lossTemp.size()==lossSet.size()){
						return;
					}else{
						lossSet=lossTemp;
						Thread t=new Thread(new ApprovalRunnable(OrderInfoConstants.LOSS));
						t.start();
					}
				case 3:
					Set<LossOverflowOrderVO> overflowTemp=lossOverflowController.getNotPassedByType(OrderInfoConstants.OVERFLOW);
					if(overflowTemp.size()==overflowSet.size()){
						return;
					}else{
						overflowSet=overflowTemp;
						Thread t=new Thread(new ApprovalRunnable(OrderInfoConstants.OVERFLOW));
						t.start();
					}
				case 4:
					Set<PaymentOrderVO> paymentTemp=paymentController.getNotPassed();
					if(paymentTemp.size()==paymentSet.size()){
						return;
					}else{
						paymentSet=paymentTemp;
						Thread t=new Thread(new ApprovalRunnable(OrderInfoConstants.PAYMENT));
						t.start();
					}
				case 5:
					Set<ReceiptsOrderVO> receiptsTemp=receiptsController.getNotPassed();
					if(receiptsTemp.size()==receiptsSet.size()){
						return;
					}else{
						receiptsSet=receiptsTemp;
						Thread t=new Thread(new ApprovalRunnable(OrderInfoConstants.RECEIPTS));
						t.start();
					}
				case 6:
					Set<PurchasingOrderVO> purchasingTemp=purchasingController.getNotPassedByType(OrderInfoConstants.PURCHASING);
					if(purchasingTemp.size()==purchasingSet.size()){
						return;
					}else{
						purchasingSet=purchasingTemp;
						Thread t=new Thread(new ApprovalRunnable(OrderInfoConstants.PURCHASING));
						t.start();
					}
				case 7:
					Set<PurchasingOrderVO> purchasingReturnTemp=purchasingController.getNotPassedByType(OrderInfoConstants.PURCHASINGRETURN);
					if(purchasingReturnTemp.size()==purchasingReturnSet.size()){
						return;
					}else{
						purchasingReturnSet=purchasingReturnTemp;
						Thread t=new Thread(new ApprovalRunnable(OrderInfoConstants.PURCHASINGRETURN));
						t.start();
					}
				case 8:
					Set<SalesOrderVO> salesTemp=salesController.getNotPassedByType(OrderInfoConstants.SALES);
					if(salesTemp.size()==salesSet.size()){
						return;
					}else{
						salesSet=salesTemp;
						Thread t=new Thread(new ApprovalRunnable(OrderInfoConstants.SALES));
						t.start();
					}
				case 9:
					Set<SalesOrderVO> salesReturnTemp=salesController.getNotPassedByType(OrderInfoConstants.SALESRETURN);
					if(salesReturnTemp.size()==salesReturnSet.size()){
						return;
					}else{
						salesReturnSet=salesReturnTemp;
						Thread t=new Thread(new ApprovalRunnable(OrderInfoConstants.SALESRETURN));
						t.start();
					}
				
				}
			}
			
		});
	}

	private void makeComponents() {
		makeAlarmPanel();
		makeGiftPanel();
		makeLossPanel();
		makeOverflowPanel();
		makePaymentPanel();
		makeReceiptsPanel();
		makePurchasingPanel();
		makePurchasingReturnPanel();
		makeSalesPanel();
		makeSalesReturnPanel();
		this.setTabPlacement(JTabbedPane.LEFT);
	}

	private void makeSalesReturnPanel() {
		JPanel salesReturnPanel = new JPanel();
		salesReturnScrollPane = new JScrollPane();
		makeSalesReturnTable();
		JPanel bottomPanel=new JPanel();
		salesReturnBtn=new JButton("提交");
		bottomPanel.add(salesReturnBtn);
		salesReturnPanel.setLayout(new BorderLayout());
		salesReturnPanel.add(salesReturnScrollPane,BorderLayout.CENTER);
		salesReturnPanel.add(bottomPanel,BorderLayout.SOUTH);
		this.add("销售退货单",salesReturnPanel);
	}

	private void makeSalesReturnTable() {
		salesReturnModel = new DefaultTableModel(salesReturnCells,ColumnsConstants.SALES_COLUMNS_APPROVAL){
			public Class<?> getColumnClass(int c){
				return salesReturnCells[0][c].getClass();
			}
			public boolean isCellEditable(int r,int c){
				return c==11;
			}
		};
		salesReturnTable=new JTable(salesReturnModel);
		salesReturnScrollPane.setViewportView(salesReturnTable);		
	}

	private void makeSalesPanel() {
		JPanel salesPanel = new JPanel();
		salesScrollPane = new JScrollPane();
		makeSalesTable();
		JPanel bottomPanel=new JPanel();
		salesBtn=new JButton("提交");
		bottomPanel.add(salesBtn);
		salesPanel.setLayout(new BorderLayout());
		salesPanel.add(salesScrollPane,BorderLayout.CENTER);
		salesPanel.add(bottomPanel,BorderLayout.SOUTH);
		this.add("销售单",salesPanel);
	}

	private void makeSalesTable() {
		salesModel = new DefaultTableModel(salesCells,ColumnsConstants.SALES_COLUMNS_APPROVAL){
			public Class<?> getColumnClass(int c){
				return salesCells[0][c].getClass();
			}
			public boolean isCellEditable(int r,int c){
				return c==11;
			}
		};
		salesTable=new JTable(salesModel);
		salesScrollPane.setViewportView(salesTable);		
	}

	private void makePurchasingReturnPanel() {
		JPanel purchasingReturnPanel = new JPanel();
		purchasingReturnScrollPane = new JScrollPane();
		makePurchasingReturnTable();
		JPanel bottomPanel=new JPanel();
		purchasingReturnBtn=new JButton("提交");
		bottomPanel.add(purchasingReturnBtn);
		purchasingReturnPanel.setLayout(new BorderLayout());
		purchasingReturnPanel.add(purchasingReturnScrollPane,BorderLayout.CENTER);
		purchasingReturnPanel.add(bottomPanel,BorderLayout.SOUTH);
		this.add("进货退货单",purchasingReturnPanel);
	}

	private void makePurchasingReturnTable() {
		purchasingReturnModel = new DefaultTableModel(purchasingReturnCells,ColumnsConstants.PURCHASING_COLUMNS_APPROVAL){
			public Class<?> getColumnClass(int c){
				return purchasingReturnCells[0][c].getClass();
			}
			public boolean isCellEditable(int r,int c){
				return c==8;
			}
		};
		purchasingReturnTable=new JTable(purchasingReturnModel);
		purchasingReturnScrollPane.setViewportView(purchasingReturnTable);
				
	}

	private void makePurchasingPanel() {
		JPanel purchasingPanel = new JPanel();
		purchasingScrollPane = new JScrollPane();
		makePurchasingTable();
		JPanel bottomPanel=new JPanel();
		purchasingBtn=new JButton("提交");
		bottomPanel.add(purchasingBtn);
		purchasingPanel.setLayout(new BorderLayout());
		purchasingPanel.add(purchasingScrollPane,BorderLayout.CENTER);
		purchasingPanel.add(bottomPanel,BorderLayout.SOUTH);
		this.add("进货单",purchasingPanel);
	}

	private void makePurchasingTable() {
		purchasingModel = new DefaultTableModel(purchasingCells,ColumnsConstants.PURCHASING_COLUMNS_APPROVAL){
			public Class<?> getColumnClass(int c){
				return purchasingCells[0][c].getClass();
			}
			public boolean isCellEditable(int r,int c){
				return c==8;
			}
		};
		purchasingTable=new JTable(purchasingModel);
		purchasingScrollPane.setViewportView(purchasingTable);
				
	}

	private void makeReceiptsPanel() {
		JPanel receiptsPanel = new JPanel();
		receiptsScrollPane = new JScrollPane();
		makeReceiptsTable();
		JPanel bottomPanel=new JPanel();
		receiptsBtn=new JButton("提交");
		bottomPanel.add(receiptsBtn);
		receiptsPanel.setLayout(new BorderLayout());
		receiptsPanel.add(receiptsScrollPane,BorderLayout.CENTER);
		receiptsPanel.add(bottomPanel,BorderLayout.SOUTH);
		this.add("收款单",receiptsPanel);
	}

	private void makeReceiptsTable() {
		receiptsModel = new DefaultTableModel(receiptsCells,ColumnsConstants.RECEIPTS_COLUMNS_APPROVAL){
			public Class<?> getColumnClass(int c){
				return receiptsCells[0][c].getClass();
			}
			public boolean isCellEditable(int r,int c){
				return c==6;
			}
		};
		receiptsTable=new JTable(receiptsModel);
		receiptsScrollPane.setViewportView(receiptsTable);
				
	}

	private void makePaymentPanel() {
		JPanel paymentPanel = new JPanel();
		paymentScrollPane = new JScrollPane();
		makePaymentTable();
		JPanel bottomPanel=new JPanel();
		paymentBtn=new JButton("提交");
		bottomPanel.add(paymentBtn);
		paymentPanel.setLayout(new BorderLayout());
		paymentPanel.add(paymentScrollPane,BorderLayout.CENTER);
		paymentPanel.add(bottomPanel,BorderLayout.SOUTH);
		this.add("付款单",paymentPanel);
	}

	private void makePaymentTable() {
		paymentModel = new DefaultTableModel(paymentCells,ColumnsConstants.PAYMENT_COLUMNS_APPROVAL){
			public Class<?> getColumnClass(int c){
				return paymentCells[0][c].getClass();
			}
			public boolean isCellEditable(int r,int c){
				return c==7;
			}
		};
		paymentTable=new JTable(paymentModel);
		paymentScrollPane.setViewportView(paymentTable);		
	}

	private void makeOverflowPanel() {
		JPanel overflowPanel = new JPanel();
		overflowScrollPane = new JScrollPane();
		makeOverflowTable();
		JPanel bottomPanel=new JPanel();
		overflowBtn=new JButton("提交");
		bottomPanel.add(overflowBtn);
		overflowPanel.setLayout(new BorderLayout());
		overflowPanel.add(overflowScrollPane,BorderLayout.CENTER);
		overflowPanel.add(bottomPanel,BorderLayout.SOUTH);
		this.add("报溢单",overflowPanel);
	}

	private void makeOverflowTable() {
		overflowModel = new DefaultTableModel(overflowCells,ColumnsConstants.GIFT_LOSS_OVERFLOW_COLUMNS_APPROVAL){
			public Class<?> getColumnClass(int c){
				return overflowCells[0][c].getClass();
			}
			public boolean isCellEditable(int r,int c){
				return c==6;
			}
		};
		overflowTable=new JTable(overflowModel);
		overflowScrollPane.setViewportView(overflowTable);		
	}

	private void makeLossPanel() {
		JPanel lossPanel = new JPanel();
		lossScrollPane = new JScrollPane();
		makeLossTable();
		JPanel bottomPanel=new JPanel();
		lossBtn=new JButton("提交");
		bottomPanel.add(lossBtn);
		lossPanel.setLayout(new BorderLayout());
		lossPanel.add(lossScrollPane,BorderLayout.CENTER);
		lossPanel.add(bottomPanel,BorderLayout.SOUTH);
		this.add("报损单",lossPanel);
	}

	private void makeLossTable() {
		lossModel = new DefaultTableModel(lossCells,ColumnsConstants.GIFT_LOSS_OVERFLOW_COLUMNS_APPROVAL){
			public Class<?> getColumnClass(int c){
				return lossCells[0][c].getClass();
			}
			public boolean isCellEditable(int r,int c){
				return c==6;
			}
		};
		lossTable=new JTable(lossModel);
		lossScrollPane.setViewportView(lossTable);		
	}

	private void makeGiftPanel() {
		JPanel giftPanel = new JPanel();
		giftScrollPane = new JScrollPane();
		makeGiftTable();
		JPanel bottomPanel=new JPanel();
		giftBtn=new JButton("提交");
		bottomPanel.add(giftBtn);
		giftPanel.setLayout(new BorderLayout());
		giftPanel.add(giftScrollPane,BorderLayout.CENTER);
		giftPanel.add(bottomPanel,BorderLayout.SOUTH);
		this.add("赠送单",giftPanel);
	}

	private void makeGiftTable() {
		giftModel = new DefaultTableModel(giftCells,ColumnsConstants.GIFT_LOSS_OVERFLOW_COLUMNS_APPROVAL){
			public Class<?> getColumnClass(int c){
				return giftCells[0][c].getClass();
			}
			public boolean isCellEditable(int r,int c){
				return c==6;
			}
		};
		giftTable=new JTable(giftModel);
		giftScrollPane.setViewportView(giftTable);		
	}

	private void makeAlarmPanel() {
		JPanel alarmPanel = new JPanel();
		alarmScrollPane = new JScrollPane();
		makeAlarmTable();
		JPanel bottomPanel=new JPanel();
		alarmBtn=new JButton("提交");
		bottomPanel.add(alarmBtn);
		alarmPanel.setLayout(new BorderLayout());
		alarmPanel.add(alarmScrollPane,BorderLayout.CENTER);
		alarmPanel.add(bottomPanel,BorderLayout.SOUTH);
		this.add("报警单",alarmPanel);
	}
	private void makeAlarmTable() {
		alarmModel = new DefaultTableModel(alarmCells,ColumnsConstants.ALARM_COLUMNS_APPROVAL){
			public Class<?> getColumnClass(int c){
				return alarmCells[0][c].getClass();
			}
			public boolean isCellEditable(int r,int c){
				return c==5;
			}
		};
		alarmTable=new JTable(alarmModel);
		alarmScrollPane.setViewportView(alarmTable);	
	}

	private class ApprovalListener implements ActionListener{
		private final String order;
		public ApprovalListener(String order) {
			this.order=order;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			switch(order){
			case OrderInfoConstants.ALARM:
				approveAlarm();
				break;
			case OrderInfoConstants.GIFT:
				approveGift();
				break;
			case OrderInfoConstants.LOSS:
				approveLoss();
				break;
			case OrderInfoConstants.OVERFLOW:
				approveOverflow();
				break;
			case OrderInfoConstants.PAYMENT:
				approvePayment();
				break;
			case OrderInfoConstants.RECEIPTS:
				approveReceipts();
				break;
			case OrderInfoConstants.PURCHASING:
				approvePurchasing();
				break;
			case OrderInfoConstants.PURCHASINGRETURN:
				approvePurchasingReturn();
				break;
			case OrderInfoConstants.SALES:
				approveSales();
				break;
			case OrderInfoConstants.SALESRETURN:
				approveSalesReturn();
				break;
			}
		}

		private void approveSalesReturn() {
			Set<SalesOrderVO> salesReturn=new HashSet<SalesOrderVO>();
			Iterator<SalesOrderVO> itr=salesReturnSet.iterator();
			int i=0;
			while(itr.hasNext()){
				SalesOrderVO vo=itr.next();
				if((boolean) salesReturnTable.getValueAt(i, 11)){
					salesReturn.add(vo);
				}
				i++;
			}	
			if(salesController.approveByType(salesReturn, OrderInfoConstants.SALESRETURN)){
				salesReturnSet.removeAll(salesReturn);
				JOptionPane.showMessageDialog(null, "审批成功！");
			}else{
				JOptionPane.showMessageDialog(null, "审批失败！");
			}
			Thread t=new Thread(new ApprovalRunnable(OrderInfoConstants.SALESRETURN));
			t.start();			
		}

		private void approveSales() {
			Set<SalesOrderVO> sales=new HashSet<SalesOrderVO>();
			Iterator<SalesOrderVO> itr=salesSet.iterator();
			int i=0;
			while(itr.hasNext()){
				SalesOrderVO vo=itr.next();
				if((boolean) salesTable.getValueAt(i, 11)){
					sales.add(vo);
				}
				i++;
			}	
			if(salesController.approveByType(sales, OrderInfoConstants.SALES)){
				salesSet.removeAll(sales);
				JOptionPane.showMessageDialog(null, "审批成功！");
			}else{
				JOptionPane.showMessageDialog(null, "审批失败！");
			}
			Thread t=new Thread(new ApprovalRunnable(OrderInfoConstants.SALES));
			t.start();					
		}

		private void approvePurchasingReturn() {
			Set<PurchasingOrderVO> purchasingReturn=new HashSet<PurchasingOrderVO>();
			Iterator<PurchasingOrderVO> itr=purchasingReturnSet.iterator();
			int i=0;
			while(itr.hasNext()){
				PurchasingOrderVO vo=itr.next();
				if((boolean) purchasingReturnTable.getValueAt(i, 8)){
					purchasingReturn.add(vo);
				}
				i++;
			}	
			if(purchasingController.approveByType(purchasingReturn, OrderInfoConstants.PURCHASINGRETURN)){
				purchasingReturnSet.removeAll(purchasingReturn);
				JOptionPane.showMessageDialog(null, "审批成功！");
			}else{
				JOptionPane.showMessageDialog(null, "审批失败！");
			}
			Thread t=new Thread(new ApprovalRunnable(OrderInfoConstants.PURCHASINGRETURN));
			t.start();				
		}

		private void approvePurchasing() {
			Set<PurchasingOrderVO> purchasing=new HashSet<PurchasingOrderVO>();
			Iterator<PurchasingOrderVO> itr=purchasingSet.iterator();
			int i=0;
			while(itr.hasNext()){
				PurchasingOrderVO vo=itr.next();
				if((boolean) purchasingTable.getValueAt(i, 8)){
					purchasing.add(vo);
				}
				i++;
			}
			if(purchasingController.approveByType(purchasing, OrderInfoConstants.PURCHASING)){
				purchasingSet.removeAll(purchasing);
				JOptionPane.showMessageDialog(null, "审批成功！");
			}else{
				JOptionPane.showMessageDialog(null, "审批失败！");
			}
			Thread t=new Thread(new ApprovalRunnable(OrderInfoConstants.PURCHASING));
			t.start();					
		}

		private void approveReceipts() {
			Set<ReceiptsOrderVO> receipts=new HashSet<ReceiptsOrderVO>();
			Iterator<ReceiptsOrderVO> itr=receiptsSet.iterator();
			int i=0;
			while(itr.hasNext()){
				ReceiptsOrderVO vo=itr.next();
				if((boolean) receiptsTable.getValueAt(i, 6)){
					receipts.add(vo);
				}
				i++;
			}	
			if(receiptsController.approve(receipts)){
				receiptsSet.removeAll(receipts);
				JOptionPane.showMessageDialog(null, "审批成功！");
			}else{
				JOptionPane.showMessageDialog(null, "审批失败！");
			}
			Thread t=new Thread(new ApprovalRunnable(OrderInfoConstants.RECEIPTS));
			t.start();			
		}

		private void approvePayment() {
			Set<PaymentOrderVO> payment=new HashSet<PaymentOrderVO>();
			Iterator<PaymentOrderVO> itr=paymentSet.iterator();
			int i=0;
			while(itr.hasNext()){
				PaymentOrderVO vo=itr.next();
				if((boolean) paymentTable.getValueAt(i, 7)){
					payment.add(vo);
				}
				i++;
			}	
			if(paymentController.approve(payment)){
				paymentSet.removeAll(payment);
				JOptionPane.showMessageDialog(null, "审批成功！");
			}else{
				JOptionPane.showMessageDialog(null, "审批失败！");
			}
			Thread t=new Thread(new ApprovalRunnable(OrderInfoConstants.PAYMENT));
			t.start();
		}

		private void approveOverflow() {
			Set<LossOverflowOrderVO> overflow=new HashSet<LossOverflowOrderVO>();
			Iterator<LossOverflowOrderVO> itr=overflowSet.iterator();
			int i=0;
			while(itr.hasNext()){
				LossOverflowOrderVO vo=itr.next();
				if((boolean) overflowTable.getValueAt(i, 6)){
					overflow.add(vo);
				}
				i++;
			}
			if(lossOverflowController.approveByType(overflow, OrderInfoConstants.OVERFLOW)){
				overflowSet.removeAll(overflow);
				JOptionPane.showMessageDialog(null, "审批成功！");
			}else{
				JOptionPane.showMessageDialog(null, "审批失败！");
			}
			Thread t=new Thread(new ApprovalRunnable(OrderInfoConstants.OVERFLOW));
			t.start();
			
		}

		private void approveLoss() {
			Set<LossOverflowOrderVO> loss=new HashSet<LossOverflowOrderVO>();
			Iterator<LossOverflowOrderVO> itr=lossSet.iterator();
			int i=0;
			while(itr.hasNext()){
				LossOverflowOrderVO vo=itr.next();
				if((boolean) lossTable.getValueAt(i, 6)){
					loss.add(vo);
				}
				i++;
			}
			if(lossOverflowController.approveByType(loss, OrderInfoConstants.LOSS)){
				lossSet.removeAll(loss);
				JOptionPane.showMessageDialog(null, "审批成功！");
			}else{
				JOptionPane.showMessageDialog(null, "审批失败！");
			}
			Thread t=new Thread(new ApprovalRunnable(OrderInfoConstants.LOSS));
			t.start();
		}

		private void approveGift() {
			Set<GiftOrderVO> gift=new HashSet<GiftOrderVO>();
			Iterator<GiftOrderVO> itr=giftSet.iterator();
			int i=0;
			while(itr.hasNext()){
				GiftOrderVO vo=itr.next();
				if((boolean) giftTable.getValueAt(i, 6)){
					gift.add(vo);
				}
				i++;
			}
			if(giftController.approve(gift)){
				giftSet.removeAll(gift);
				JOptionPane.showMessageDialog(null, "审批成功！");
			}else{
				JOptionPane.showMessageDialog(null, "审批失败！");
				return;
			}
			Thread t=new Thread(new ApprovalRunnable(OrderInfoConstants.GIFT));
			t.start();
		}

		private void approveAlarm() {
			Set<AlarmOrderVO> alarm=new HashSet<AlarmOrderVO>();
			Iterator<AlarmOrderVO> itr=alarmSet.iterator();
			int i=0;
			while(itr.hasNext()){
				AlarmOrderVO vo=itr.next();
				if((boolean) alarmTable.getValueAt(i, 5)){
					alarm.add(vo);
				}
				i++;
			}
			if(alarmController.approve(alarm)){
				alarmSet.removeAll(alarm);
				JOptionPane.showMessageDialog(null, "审批成功！");
			}else{
				JOptionPane.showMessageDialog(null, "审批失败！");
				return;
			}
			Thread t=new Thread(new ApprovalRunnable(OrderInfoConstants.ALARM));
			t.start();
		}
		
	}
	
	private class ApprovalRunnable implements Runnable{
		private final String order;
		public ApprovalRunnable(String order) {
			this.order=order;
		}

		@Override
		public void run() {
			switch(order){
			case OrderInfoConstants.ALARM:
				initAlarmCells();
				makeAlarmTable();
				alarmScrollPane.validate();
				break;
			case OrderInfoConstants.GIFT:
				initGiftCells();
				makeGiftTable();
				giftScrollPane.validate();
				break;
			case OrderInfoConstants.LOSS:
				initLossCells();
				makeLossTable();
				lossScrollPane.validate();
				break;
			case OrderInfoConstants.OVERFLOW:
				initOverflowCells();
				makeOverflowTable();
				overflowScrollPane.validate();
				break;
			case OrderInfoConstants.PAYMENT:
				initPaymentCells();
				makePaymentTable();
				paymentScrollPane.validate();
				break;
			case OrderInfoConstants.RECEIPTS:
				initReceiptsCells();
				makeReceiptsTable();
				receiptsScrollPane.validate();
				break;
			case OrderInfoConstants.PURCHASING:
				initPurchasingCells();
				makePurchasingTable();
				purchasingScrollPane.validate();
				break;
			case OrderInfoConstants.PURCHASINGRETURN:
				initPurchasingReturnCells();
				makePurchasingReturnTable();
				purchasingReturnScrollPane.validate();
				break;
			case OrderInfoConstants.SALES:
				initSalesCells();
				makeSalesTable();
				salesScrollPane.validate();
				break;
			case OrderInfoConstants.SALESRETURN:
				initSalesReturnCells();
				makeSalesReturnTable();
				salesReturnScrollPane.validate();
				break;
			}
		}

	}
}
