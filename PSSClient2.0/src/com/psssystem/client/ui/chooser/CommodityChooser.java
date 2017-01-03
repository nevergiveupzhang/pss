package com.psssystem.client.ui.chooser;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Frame;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.text.DocumentFilter;
import javax.swing.text.InternationalFormatter;

import com.psssystem.client.ui.filter.IntFilter;
import com.psssystem.client.ui.mainui.GBC;
import com.psssystem.connection.vo.CommodityVO;

public class CommodityChooser extends JPanel {
	private DocumentFilter filter=new IntFilter();
	
	private JTextField nameTF;
	private JTextField typeTF;
	private JTextField stockAmountTF;
	private JTextField purchasingPriceTF;
	private JTextField sellingPriceTF;
	
	private JButton okBtn;
	private JButton cancelBtn;
	private JDialog dialog;
	private boolean ok;
	public CommodityChooser(){
		this.init();
	}

	public void setCommodity(CommodityVO selectedCommodity) {
		nameTF.setText(selectedCommodity.getName());
		typeTF.setText(selectedCommodity.getType());;
		stockAmountTF.setText(selectedCommodity.getStockAmount()+"");;
		purchasingPriceTF.setText(selectedCommodity.getPurchasingPrice()+"");;
		sellingPriceTF.setText(selectedCommodity.getSellingPrice()+"");;
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

	private void init() {
		this.makeComponents();
		this.addListeners();
		
	}

	private void addListeners() {
		okBtn.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				if("".equals(nameTF.getText())||"".equals(typeTF.getText())||"".equals(stockAmountTF.getText())||"".equals(purchasingPriceTF.getText())||"".equals(sellingPriceTF.getText())){
					return;
				}
				if(!Pattern.compile("^[0-9]+$").matcher(stockAmountTF.getText()).find()){
					stockAmountTF.setText("0");
				}
				
				if(!Pattern.compile("^[0-9]+$").matcher(purchasingPriceTF.getText()).find()){
					purchasingPriceTF.setText("0");
				}
				if(!Pattern.compile("^[0-9]+$").matcher(sellingPriceTF.getText()).find()){
					sellingPriceTF.setText("0");
				}
				
				if(Integer.parseInt(stockAmountTF.getText())==0||Integer.parseInt(purchasingPriceTF.getText())==0||Integer.parseInt(sellingPriceTF.getText())==0)return;
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

	private void makeComponents() {
		JLabel nameL=new JLabel("商品名称:");
		JLabel typeL=new JLabel("商品型号:");
		JLabel stockAmountL=new JLabel("库存数量:");
		JLabel purchasingPriceL=new JLabel("进价:");
		JLabel sellingPriceL=new JLabel("售价:");
		
		nameTF=new JTextField(10);
		typeTF=new JTextField(10);
		stockAmountTF=new JTextField(10);
		stockAmountTF.setText("0");
		purchasingPriceTF=new JTextField(10);
		purchasingPriceTF.setText("0");
		sellingPriceTF=new JTextField(10);
		sellingPriceTF.setText("0");
		
		JPanel panel=new JPanel();
		GridLayout layout=new GridLayout(5,4);
		layout.setVgap(20);
		panel.setLayout(layout);
		panel.add(new JLabel());
		panel.add(nameL);
		panel.add(nameTF);
		panel.add(new JLabel());
		panel.add(new JLabel());
		panel.add(typeL);
		panel.add(typeTF);
		panel.add(new JLabel());
		panel.add(new JLabel());
		panel.add(stockAmountL);
		panel.add(stockAmountTF);
		panel.add(new JLabel());
		panel.add(new JLabel());
		panel.add(purchasingPriceL);
		panel.add(purchasingPriceTF);
		panel.add(new JLabel());
		panel.add(new JLabel());
		panel.add(sellingPriceL);
		panel.add(sellingPriceTF);
		panel.add(new JLabel());
		
		
		/*panel.add(nameL,new GBC(0,0).tipicalLeftInsets());
		panel.add(nameTF,new GBC(1,0).tipicalRightInsets());
		panel.add(typeL,new GBC(0,1).tipicalLeftInsets());
		panel.add(typeTF,new GBC(1,1).tipicalRightInsets());
		panel.add(stockAmountL,new GBC(0,2).tipicalLeftInsets());
		panel.add(stockAmountTF,new GBC(1,2).tipicalRightInsets());
		panel.add(purchasingPriceL,new GBC(0,3).tipicalLeftInsets());
		panel.add(purchasingPriceTF,new GBC(1,3).tipicalRightInsets());
		panel.add(sellingPriceL,new GBC(0,4).tipicalLeftInsets());
		panel.add(sellingPriceTF,new GBC(1,4).tipicalRightInsets());*/
		
		okBtn=new JButton("确定");
		cancelBtn=new JButton("取消");
		JPanel buttonPanel=new JPanel();
		buttonPanel.add(okBtn);
		buttonPanel.add(cancelBtn);
		
		this.setLayout(new BorderLayout());
		this.add(panel,BorderLayout.CENTER);
		this.add(buttonPanel,BorderLayout.SOUTH);
	}
	
	
	public CommodityVO getCommodity(){
		String name=nameTF.getText();
		String type=typeTF.getText();
		int amount=Integer.parseInt(stockAmountTF.getText());
		int purchasingPrice=Integer.parseInt(purchasingPriceTF.getText());
		int sellingPrice=Integer.parseInt(sellingPriceTF.getText());
		CommodityVO commodity=new CommodityVO(name,type,amount,purchasingPrice,sellingPrice,purchasingPrice,sellingPrice);
		return commodity;
	}
}
