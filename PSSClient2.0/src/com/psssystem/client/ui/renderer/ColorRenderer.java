package com.psssystem.client.ui.renderer;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import com.psssystem.client.data.ColumnsConstants;

public class ColorRenderer extends DefaultTableCellRenderer {
	int n=-1;
	public Component getTableCellRendererComponent(JTable table,
			Object value, boolean isSelected, boolean hasFocus, int row,
			int column) {
		Component com = super.getTableCellRendererComponent(table, value,
				isSelected, hasFocus, row, column);
		if (row == n)// 你要变色的行
			setBackground(Color.red);
		return this;
	}

	

	public void setColor(int row, Color color) {
		n = row;
	}
}