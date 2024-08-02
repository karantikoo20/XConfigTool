package com.config.tool.propertiesconfig;

import java.util.EventObject;

import javax.swing.DefaultCellEditor;
import javax.swing.JTextField;

class EditableCellEditor extends DefaultCellEditor {
	public EditableCellEditor() {
		super(new JTextField());
	}

	@Override
	public boolean isCellEditable(EventObject e) {
		return false;
	}
}