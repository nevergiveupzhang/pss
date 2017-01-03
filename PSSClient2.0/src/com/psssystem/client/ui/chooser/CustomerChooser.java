package com.psssystem.client.ui.chooser;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.text.DefaultFormatter;
import javax.swing.text.DocumentFilter;
import javax.swing.text.InternationalFormatter;
import javax.swing.text.MaskFormatter;

import org.jb2011.lnf.beautyeye.widget.border.*;

import com.psssystem.client.ui.filter.EmailFilter;
import com.psssystem.client.ui.filter.IntFilter;
import com.psssystem.client.ui.mainui.GBC;
import com.psssystem.connection.vo.CustomerVO;

public class CustomerChooser extends JPanel {
	/* "姓名","分类","级别","电话","地址","邮编","电子邮箱","默认业务员" */
	private JComboBox typeCB;
	private JComboBox levelCB;
	private JTextField nameTF;
	private JTextField phoneTF;
	private JTextField addrTF;
	private JTextField postcodeTF;
	private JTextField emailTF;
	private JTextField salesmanTF;
	private JButton okBtn;
	private JButton cancelBtn;
	private boolean ok;
	private JDialog dialog;

	public CustomerChooser() {
		this.init();
	}

	private void init() {
		this.makeComponents();
		addListeners();
	}

	private void addListeners() {
		okBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(!Pattern.compile("(^[0-9]{3}-[0-9]{4}-[0-9]{4})||(^[0-9]{3}-[0-9]{8})").matcher(phoneTF.getText()).find()){
					phoneTF.setText("188-8888-8888|010-88888888");
				}
				if(!Pattern.compile("[0-9]{6}").matcher(postcodeTF.getText()).find()){
					postcodeTF.setText("000000");
				}
				if(!Pattern.compile("^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$").matcher(emailTF.getText()).find()){
					emailTF.setText("xxx@xxx.com");
				}
				if ("".equals(nameTF.getText())
						|| "".equals(addrTF.getText())
						|| "".equals(salesmanTF.getText())
						|| "".equals(emailTF.getText()))
					return;
				
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

	private void makeComponents() {
		JLabel typeL = new JLabel("分类：");
		JLabel levelL = new JLabel("级别：");
		JLabel nameL = new JLabel("姓名：");
		JLabel phoneL = new JLabel("电话：");
		JLabel addrL = new JLabel("地址：");
		JLabel postcodeL = new JLabel("邮编：");
		JLabel emailL = new JLabel("电子邮箱：");
		JLabel salesmanL = new JLabel("默认业务员：");

		typeCB = new JComboBox(new String[] { "进货商", "销售商" });
		nameTF = new JTextField(10);
		levelCB = new JComboBox(new String[]{"1","2","3","4","5"});
		phoneTF = new JTextField(10);
		phoneTF.setText("188-8888-8888|010-88888888");
		postcodeTF = new JTextField(10);
		postcodeTF.setText("000000");
		addrTF = new JTextField(10);
		emailTF = new JTextField(10);
		emailTF.setText("xxx@xxx.com");
		salesmanTF = new JTextField(10);
		JPanel topPanel = new JPanel();

		GridLayout layout = new GridLayout(8, 4);
		layout.setHgap(0);
		layout.setVgap(10);
		topPanel.setLayout(layout);
		topPanel.add(new JLabel());
		topPanel.add(nameL);
		topPanel.add(nameTF);
		topPanel.add(new JLabel());
		topPanel.add(new JLabel());
		topPanel.add(typeL);
		topPanel.add(typeCB);
		topPanel.add(new JLabel());
		topPanel.add(new JLabel());
		topPanel.add(levelL);
		topPanel.add(levelCB);
		topPanel.add(new JLabel());
		topPanel.add(new JLabel());
		topPanel.add(phoneL);
		topPanel.add(phoneTF);
		topPanel.add(new JLabel());
		topPanel.add(new JLabel());
		topPanel.add(addrL);
		topPanel.add(addrTF);
		topPanel.add(new JLabel());
		topPanel.add(new JLabel());
		topPanel.add(postcodeL);
		topPanel.add(postcodeTF);
		topPanel.add(new JLabel());
		topPanel.add(new JLabel());
		topPanel.add(emailL);
		topPanel.add(emailTF);
		topPanel.add(new JLabel());
		topPanel.add(new JLabel());
		topPanel.add(salesmanL);
		topPanel.add(salesmanTF);
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

	public void setCustomer(CustomerVO customer) {

	}

	/* "姓名","分类","级别","电话","地址","邮编","电子邮箱","默认业务员" */
	public CustomerVO getCustomer() {
		return new CustomerVO.Builder(nameTF.getText(), (String) typeCB
				.getSelectedItem().toString()).level(Integer.parseInt(levelCB.getSelectedItem().toString()))
				.phoneNumber((String) phoneTF.getText())
				.addr(addrTF.getText().toString())
				.postcode(Integer.parseInt(postcodeTF.getText()))
				.email(emailTF.getText())
				.defaultSalesman(salesmanTF.getText().toString()).build();
	}

	public boolean showDialog(Component parent, String title) {
		JFrame owner = null;
		if (parent instanceof JFrame) {
			owner = (JFrame) parent;
		} else {
			owner = (JFrame) SwingUtilities.getAncestorOfClass(JFrame.class,
					parent);
		}

		if (dialog == null || dialog.getOwner() == null) {
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
