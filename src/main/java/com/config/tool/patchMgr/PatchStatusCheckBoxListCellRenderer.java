package com.config.tool.patchMgr;

import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;

public class PatchStatusCheckBoxListCellRenderer extends JPanel implements ListCellRenderer<PatchStatusCheckBox> {
        private final JCheckBox checkBox;
        private final JLabel label;

        public PatchStatusCheckBoxListCellRenderer() {
            setLayout(new BorderLayout());
            checkBox = new JCheckBox();
            label = new JLabel();
            add(checkBox, BorderLayout.WEST);
            add(label, BorderLayout.CENTER);
        }

        @Override
        public Component getListCellRendererComponent(JList<? extends PatchStatusCheckBox> list,
        		PatchStatusCheckBox value,
                                                      int index,
                                                      boolean isSelected,
                                                      boolean cellHasFocus) {
            checkBox.setSelected(value.isSelected());
            label.setText(value.getFile().getName());
            label.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 50)); // Adjust the left and right padding
            label.setForeground(isSelected ? list.getSelectionForeground() : list.getForeground());
            label.setBackground(isSelected ? list.getSelectionBackground() : list.getBackground());
            setEnabled(list.isEnabled());
            setFont(list.getFont());
            setBackground(list.getBackground());
            return this;
        }
    }