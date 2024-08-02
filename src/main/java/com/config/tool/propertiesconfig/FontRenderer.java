package com.config.tool.propertiesconfig;

import java.awt.Component;
import java.awt.Font;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class FontRenderer extends DefaultTableCellRenderer {

	private Font font;

	public FontRenderer(Font font) {
		this.font = font;
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		component.setFont(font);
		return component;
	}
}