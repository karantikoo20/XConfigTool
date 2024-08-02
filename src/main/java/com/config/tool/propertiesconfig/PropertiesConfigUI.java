package com.config.tool.propertiesconfig;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import com.config.tool.constent.IConfigToolConstents;
import com.config.tool.mainScreen.MainScreenUI;
import com.config.tool.properties.PropertiesFileReader;
import com.config.tool.properties.PropertiesHolder;

public class PropertiesConfigUI extends JFrame implements IConfigView {

	private DefaultTableModel tableModel;
	private JTable configTable;
	ConfigPresenter configPresenter = new ConfigPresenter(this);
	Properties properties;
	// String filePath = "C:/Accen_Emp_13059506/configWiz/system.properties";
	String filePath = IConfigToolConstents.XSTORE_SYSTEM_PROPERTIES;

	public PropertiesConfigUI() {
		try {
		setTitle(PropertiesFileReader.readProperties(IConfigToolConstents.CONFIG_WIZ_SCREEN_TITLE));
		
		setSize(600, 400);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Get the screen size
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

		// Set the frame size to the screen size
		setSize(screenSize);

		// Set the frame state to maximized
		setExtendedState(JFrame.MAXIMIZED_BOTH);

		tableModel = new DefaultTableModel();

		tableModel.addColumn(PropertiesFileReader.readProperties(IConfigToolConstents.CONFIG_DESCRIPTION_TITLE));
		tableModel.addColumn(PropertiesFileReader.readProperties(IConfigToolConstents.PROPERTY_KEY_TITLE));
		tableModel.addColumn(PropertiesFileReader.readProperties(IConfigToolConstents.PROPERTY_VALUE_TITLE));

		configTable = new JTable(tableModel);

		configTable.getColumnModel().getColumn(0).setCellEditor(new EditableCellEditor());
		configTable.getColumnModel().getColumn(1).setCellEditor(new EditableCellEditor());
		setColumnWidth(2, 220);
		setColumnWidth(1, 550);

		setCellRenderer(0, new FontRenderer(new Font("Arial", Font.PLAIN, 16)));
		setCellRenderer(1, new FontRenderer(new Font("Arial", Font.PLAIN, 16)));
		setCellRenderer(2, new FontRenderer(new Font("Arial", Font.PLAIN, 16)));

		JScrollPane scrollPane = new JScrollPane(configTable);

		JButton saveButton = new JButton(PropertiesFileReader.readProperties(IConfigToolConstents.SAVE_BTN_LABEL));
		// saveButton.setBounds(-10, -10, 0, 0);;
		saveButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				saveConfig();
			}
		});

		JButton backButton = new JButton(PropertiesFileReader.readProperties(IConfigToolConstents.BACK_BTN_LABEL));
		backButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				MainScreenUI.launchMainUi();;

			}
		});

		/*
		 * JButton xstoreRestartButton = new JButton("Xstore Restart");
		 * xstoreRestartButton.addActionListener(new ActionListener() {
		 * 
		 * @Override public void actionPerformed(ActionEvent e) {
		 * deleteXstoreAnchorFile(); try { Thread.sleep(10000); } catch
		 * (InterruptedException e1) { // TODO Auto-generated catch block
		 * e1.printStackTrace(); } startXstoreIfNotStarted(); } });
		 */
		JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		bottomPanel.add(saveButton);
		bottomPanel.add(backButton);
		//bottomPanel.add(xstoreRestartButton);

		add(scrollPane, BorderLayout.CENTER);
		add(bottomPanel, BorderLayout.SOUTH);

		setUndecorated(true);

		setVisible(true);

		configPresenter.initProperties();
		
		}
		catch (IOException e) {
			// TODO: handle exception
		}
	}

	private void addConfigRow(String labelText, String defaultValue, String infoText) {
		tableModel.addRow(new Object[] { labelText, defaultValue, infoText });
	}

	private void saveConfig() {
		Map<String, String> updatedKeyValuePair = new HashMap<>();

		for (int row = 0; row < tableModel.getRowCount(); row++) {
			configTable.getSelectedRow();
			tableModel.fireTableDataChanged();
			String configKey = (String) tableModel.getValueAt(row, 1);
			String configUpdatedValue = (String) tableModel.getValueAt(row, 2);
			updatedKeyValuePair.put(configKey, configUpdatedValue);
		}
		configPresenter.saveButtonClicked(updatedKeyValuePair);
		showFileUpdatedDialog();
	}



	public void configWizardLaunch() {
		SwingUtilities.invokeLater(() -> new PropertiesConfigUI());
	}

	/*
	 * protected void readSystemPropertiesFile() { // String filePath =
	 * "C:/Accen_Emp_13059506/configWiz/system.properties"; String filePath =
	 * "C:/xstore/system.properties";
	 * 
	 * properties = new Properties(); try (FileReader fileReader = new
	 * FileReader(filePath)) { properties.load(fileReader); } catch (IOException e)
	 * { e.printStackTrace(); }
	 * 
	 * }
	 */

	private void setColumnWidth(int columnIndex, int width) {
		TableColumn column = configTable.getColumnModel().getColumn(columnIndex);
		column.setMinWidth(width);
		column.setMaxWidth(width);
		column.setPreferredWidth(width);
	}



	private void setCellRenderer(int columnIndex, DefaultTableCellRenderer renderer) {
		configTable.getColumnModel().getColumn(columnIndex).setCellRenderer(renderer);
	}


	private void showFileUpdatedDialog() {
		try {
		JOptionPane.showMessageDialog(this, PropertiesFileReader.readProperties(IConfigToolConstents.CONFIG_SAVED_MESSAGE),
				"Configs Updated", JOptionPane.OK_OPTION);
		// dispose(); // Close the login screen after successful login
		}
		catch (IOException e) {
			// TODO: handle exception
		}
	}


	@Override
	public void showProperties(List<PropertiesHolder> propertiesList) {
		for (PropertiesHolder property : propertiesList) {
			addConfigRow(property.getDescription(), property.getPropKey(), property.getPropValue());
		}

	}

}
