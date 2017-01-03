package com.psssystem.client.ui.chooser;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.text.DocumentFilter;
import javax.swing.text.InternationalFormatter;

import com.psssystem.client.ui.filter.IntFilter;
import com.psssystem.connection.vo.AccountVO;
import com.psssystem.connection.vo.CustomerVO;

public class AccountChooser extends JPanel {
	/* "姓名","分类","级别","电话","地址","邮编","电子邮箱","默认业务员" */
	private JTextField accountNameTF;
	private JFormattedTextField accountSumTF;
	private JButton okBtn;
	private JButton cancelBtn;
	private boolean ok;
	private JDialog dialog;
	private DocumentFilter filter=new IntFilter();
	public AccountChooser() {
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
				if(accountNameTF.getText()==null||"".equals(accountNameTF.getText()))return;
				ok = true;
				dialog.setVisible(false);
			}

		});

		cancelBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				ok=false;
				dialog.setVisible(false);
			}

		});
	}

	private void makeComponents() {
		JLabel nameL = new JLabel("账户名：");
		JLabel sumL = new JLabel("总额：");
		 

		accountNameTF = new JTextField(10);
		
		accountSumTF = new JFormattedTextField(new InternationalFormatter(NumberFormat.getIntegerInstance()){
			protected DocumentFilter getDocumentFilter(){
				return filter;
			}
		});
		accountSumTF.setValue(new Integer(0));
		JPanel topPanel = new JPanel();
		GridLayout layout=new GridLayout(2,4);
		layout.setVgap(10);
		topPanel.setLayout(layout);
		topPanel.add(new JLabel());
		topPanel.add(nameL);
		topPanel.add(accountNameTF);
		topPanel.add(new JLabel());
		topPanel.add(new JLabel());
		topPanel.add(sumL);
		topPanel.add(accountSumTF);
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

	public void setAccount(AccountVO account) {

	}

	/* "姓名","分类","级别","电话","地址","邮编","电子邮箱","默认业务员" */
	public AccountVO getAccount() {
		return new AccountVO(accountNameTF.getText(),((Long) accountSumTF.getValue()).intValue());
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
		dialog.setLocation(owner.getMousePosition());
		dialog.setTitle(title);
		dialog.setVisible(true);
		return ok;
	}
}
