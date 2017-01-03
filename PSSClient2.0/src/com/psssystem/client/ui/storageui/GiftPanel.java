package com.psssystem.client.ui.storageui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Frame;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import com.psssystem.client.controller.storagecontroller.ICategoryController;
import com.psssystem.client.controller.storagecontroller.ICommodityController;
import com.psssystem.client.controller.storagecontroller.IGiftOrderController;
import com.psssystem.client.controllerimpl.storagecontrollerimpl.CategoryControllerImpl;
import com.psssystem.client.controllerimpl.storagecontrollerimpl.CommodityControllerImpl;
import com.psssystem.client.controllerimpl.storagecontrollerimpl.GiftOrderControllerImpl;
import com.psssystem.client.data.ColumnsConstants;
import com.psssystem.client.data.OperationInfoConstants;
import com.psssystem.client.ui.commodity.CommodityTreePanel;
import com.psssystem.client.ui.mainui.GBC;
import com.psssystem.connection.vo.CategoryVO;
import com.psssystem.connection.vo.CommodityVO;
import com.psssystem.connection.vo.GiftOrderVO;

public class GiftPanel extends JSplitPane {
	private CommodityTreePanel treeScrollPane;
	private JPanel opPanel;
	private JButton addToGiftListBtn;
	private JButton chooseBtn;
	private GiftChooser chooser;
	private JScrollPane tableScrollPane;
	private JTable table;
	private TableModel tableModel;
	private Object[][]cells=new Object[][]{};
	
	private JButton submitBtn;
	private IGiftOrderController giftOrderController;
	public GiftPanel(){
		this.giftOrderController=new GiftOrderControllerImpl();
		init();
	}

	private void init() {
		makeComponents();
		addListeners();
	}

	private void addListeners() {
		addToGiftListBtn.addActionListener(new GiftListener(OperationInfoConstants.ADD));
		
		chooseBtn.addActionListener(new GiftListener(OperationInfoConstants.CHOOSE));	
	
		submitBtn.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				if(cells.length==0){
					JOptionPane.showMessageDialog(null, "请输入商品信息！");
					return;
				}
				
				List<GiftOrderVO> giftList=new ArrayList<GiftOrderVO>();
				for(int i=0;i<cells.length;i++){
					Object[]row=cells[i];
					int commID=(int) row[0];
					String commName=(String) row[1];
					int amount=(int) row[2];
					GiftOrderVO gift=new GiftOrderVO(commID,commName,amount);
					giftList.add(gift);
				}
				if(!giftOrderController.addGiftOrder(giftList)){
					JOptionPane.showMessageDialog(null, "商品信息有误！");
				}else{
					JOptionPane.showMessageDialog(null, "赠送单添加成功！");
				}
				cells=new Object[][]{};
				
				Thread t=new Thread(new Runnable(){

					@Override
					public void run() {
						tableModel=new DefaultTableModel(cells,ColumnsConstants.GIFT_COLUMNS_CREATE);
						table=new JTable(tableModel);
						tableScrollPane.setViewportView(table);
						tableScrollPane.validate();
					}
					
				});
				t.start();
			}
			
		});
	}

	
	private void makeComponents() {
		treeScrollPane=new CommodityTreePanel();
		
		opPanel=new JPanel();
		JPanel topPanel=new JPanel();
		addToGiftListBtn=new JButton("加入列表");
		chooseBtn=new JButton("手动输入");
		topPanel.add(addToGiftListBtn);
		topPanel.add(chooseBtn);
		tableScrollPane=new JScrollPane();
		tableModel=new DefaultTableModel(cells,ColumnsConstants.GIFT_COLUMNS_CREATE);
		table=new JTable(tableModel);
		tableScrollPane.setViewportView(table);
		
		submitBtn=new JButton("提交");
		opPanel.setLayout(new BorderLayout());
		opPanel.add(topPanel,BorderLayout.NORTH);
		opPanel.add(tableScrollPane,BorderLayout.CENTER);
		
		JPanel bottomPanel=new JPanel();
		bottomPanel.add(submitBtn);
		opPanel.add(bottomPanel,BorderLayout.SOUTH);
		this.setLeftComponent(treeScrollPane);
		this.setRightComponent(opPanel);
	}

	private class GiftChooser extends JPanel{
		private JLabel idL;
		private JLabel nameL;
		private JLabel amountL;
		private JTextField idTF;
		private JTextField nameTF;
		private JTextField amountTF;
		private JButton okBtn;
		private JButton cancelBtn;
		private JDialog dialog;
		private boolean ok;
		public GiftChooser(){
			this.init();
		}
		private void init() {
			this.makeComponents();
			this.addListeners();
			
		}
		
		private void makeComponents() {
			idL=new JLabel("商品编号:");
			nameL=new JLabel("商品名称:");
			amountL=new JLabel("赠送数量:");
			nameTF=new JTextField(10);
			idTF=new JTextField(10);
			amountTF=new JTextField(10);
			
			JPanel panel=new JPanel();
			panel.setLayout(new GridBagLayout());
			panel.add(idL,new GBC(0,0).tipicalLeftInsets());
			panel.add(idTF,new GBC(1,0).tipicalRightInsets());
			panel.add(nameL,new GBC(0,1).tipicalLeftInsets());
			panel.add(nameTF,new GBC(1,1).tipicalRightInsets());
			panel.add(amountL,new GBC(0,2).tipicalLeftInsets());
			panel.add(amountTF,new GBC(1,2).tipicalRightInsets());
			
			okBtn=new JButton("确定");
			cancelBtn=new JButton("取消");
			JPanel buttonPanel=new JPanel();
			buttonPanel.add(okBtn);
			buttonPanel.add(cancelBtn);
			
			this.setLayout(new BorderLayout());
			this.add(panel,BorderLayout.CENTER);
			this.add(buttonPanel,BorderLayout.SOUTH);
		}
		
		private void addListeners() {
			okBtn.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent e) {
					if("".equals(nameTF.getText())||"".equals(idTF.getText())||"".equals(amountTF.getText())){
						idTF.setText("0");
						amountTF.setText("0");
						return;
					}
					
					if(!Pattern.compile("^[0-9]+$").matcher(idTF.getText()).find()){idTF.setText("0");return;}
					if(!Pattern.compile("^[0-9]+$").matcher(amountTF.getText()).find()){amountTF.setText("0");return;}
					
					if(Integer.parseInt(idTF.getText())==0||Integer.parseInt(amountTF.getText())==0){return;}
					ok=true;
					dialog.setVisible(false);
				}
				
			});
			
			cancelBtn.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent e) {
					ok=false;
					dialog.setVisible(false);
				}
				
			});
		}
		
		public void setGiftOrder(GiftOrderVO selectedGift) {
			nameTF.setText(selectedGift.getCommName());
			idTF.setText(selectedGift.getCommID()+"");;
			amountTF.setText(selectedGift.getAmount()+"");;
		}

		public GiftOrderVO getGiftOrder(){
			String name=nameTF.getText();
			int id=Integer.parseInt(idTF.getText());
			int amount=Integer.parseInt(amountTF.getText());
			GiftOrderVO giftOrder=new GiftOrderVO(id,name,amount);
			return giftOrder;
		}
		
		public boolean showDialog(Component parent, String title) {
			Frame owner=null;
			if(parent instanceof Frame) owner=(Frame) parent;
			else owner=(Frame) SwingUtilities.getAncestorOfClass(Frame.class, parent);
			
			if(dialog==null||dialog.getOwner()!=owner){
				dialog=new JDialog(owner,true);
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
	
	private class GiftListener implements ActionListener{
		private String op;
		public GiftListener(String op){
			this.op=op;
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			int commID = 0;
			String commName = null;
			int amount=0;
			if(op.equals(OperationInfoConstants.ADD)){
				DefaultMutableTreeNode selectedNode=(DefaultMutableTreeNode) treeScrollPane.getTree().getLastSelectedPathComponent();
				if(selectedNode==null||!treeScrollPane.isCommodityNode(selectedNode)){
					JOptionPane.showMessageDialog(null, "请选择商品！");
					return;
				}
				CommodityVO commodity=treeScrollPane.getSelectedCommodity(selectedNode.toString());
				commID=commodity.getId();
				commName=commodity.getName();
				String rs="0";
				if(Pattern.compile("^[0-9]+$").matcher(rs=JOptionPane.showInputDialog("请输入赠送数量：")).find()){
					amount=Integer.parseInt(rs);
				}else{
					JOptionPane.showMessageDialog(null, "不是数字！");
					return;
				};
			}else if(op.equals(OperationInfoConstants.CHOOSE)){
				GiftOrderVO gift=null;
				if(chooser==null) chooser=new GiftChooser();
				if(chooser.showDialog(GiftPanel.this, "Connect")){
					gift=chooser.getGiftOrder();
				}
				if(gift==null) return;
				commID=gift.getCommID();
				commName=gift.getCommName();
				amount=gift.getAmount();
			}
			boolean flag=true;
			for(CommodityVO vo:treeScrollPane.getCommodityList()){
				if(vo.getId()==commID&&vo.getName().equals(commName)){
					flag=false;
					break;
				}
			}
			if(flag){
				JOptionPane.showMessageDialog(null, "商品不存在！");
				return;
			}
			
			Object[] row=new Object[]{commID,commName,amount};
			cells=addToCells(row);
			
			Thread t=new Thread(new Runnable(){

				@Override
				public void run() {
					tableModel=new DefaultTableModel(cells,ColumnsConstants.GIFT_COLUMNS_CREATE);
					table=new JTable(tableModel);
					tableScrollPane.setViewportView(table);
					tableScrollPane.validate();
				}

				
			});
			
			t.start();
		}
		
		private Object[][] addToCells(Object[] row) {
			Object[][]temp=new Object[cells.length+1][];
			for(int i=0;i<cells.length;i++){
				temp[i]=cells[i];
			}
			temp[cells.length]=row;
			return temp;
		}
		
	}
}
