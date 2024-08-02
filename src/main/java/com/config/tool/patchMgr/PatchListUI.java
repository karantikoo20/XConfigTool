package com.config.tool.patchMgr;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;

public class PatchListUI extends JFrame implements IPatchListView {

	private JList<PatchStatusCheckBox> patchFileList;
	private DefaultListModel<PatchStatusCheckBox> patchListModel;
	// private PatchListPresenter patchPresenter = new PatchListPresenter();
	private IPatchPresenter patchPresenter = new PatchListPresenter(this);

	public PatchListUI() {

		initPatchListUI();

	}

	private void initPatchListUI() {
		setTitle("Patches List");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(325, 400);
		setLocationRelativeTo(null);

		patchListModel = new DefaultListModel<PatchStatusCheckBox>();
		patchFileList = new JList<>(patchListModel);
		patchFileList.setCellRenderer(new PatchStatusCheckBoxListCellRenderer());
		patchFileList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

		// Add a mouse listener to handle checkbox clicks

		patchFileList.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				int index = patchFileList.locationToIndex(e.getPoint());
				if (index != -1) {
					PatchStatusCheckBox fileCheckBox = patchListModel.getElementAt(index);
					fileCheckBox.setSelected(!fileCheckBox.isSelected());
					patchListModel.setElementAt(fileCheckBox, index);
				}
			}
		});

		JScrollPane scrollPane = new JScrollPane(patchFileList);

		JPanel headerPanel = new JPanel(new BorderLayout());
		JLabel enableHeaderLabel = new JLabel("Enable");
		enableHeaderLabel.setFont(enableHeaderLabel.getFont().deriveFont(Font.BOLD, 14f));
		headerPanel.add(enableHeaderLabel, BorderLayout.WEST);

		JLabel patchNameHeaderLabel = new JLabel("               Patch Name");
		patchNameHeaderLabel.setFont(patchNameHeaderLabel.getFont().deriveFont(Font.BOLD, 14f));
		headerPanel.add(patchNameHeaderLabel, BorderLayout.CENTER);

		JPanel buttonPanel = new JPanel();
		JButton listButton = new JButton("Deploy Patches");
		listButton.addActionListener(e -> patchPresenter.handlePatchFiles(this, patchListModel));
		buttonPanel.add(listButton);

		JPanel mainPanel = new JPanel(new BorderLayout());
		mainPanel.add(headerPanel, BorderLayout.NORTH);
		mainPanel.add(scrollPane, BorderLayout.CENTER);

		add(mainPanel, BorderLayout.CENTER);
		add(buttonPanel, BorderLayout.SOUTH);

		patchPresenter.getPatchList(patchListModel);

		setVisible(true);

	}

	public void showPatchesList() {
		SwingUtilities.invokeLater(() -> new PatchListUI().setVisible(true));
	}

	@Override
	public int showXstoreRestartWarning(JFrame parent, String message, String title) {
		return JOptionPane.showConfirmDialog(parent, message, title, JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE);

	}

	@Override
	public void getPatchList(DefaultListModel<PatchStatusCheckBox> patchListModel) {
		// TODO Auto-generated method stub
		// patchListModel.
	}

	@Override
	public void showPatchesDeployed() {
		dispose();
		JOptionPane.showMessageDialog(this, "Patches Deployed Successfully", "Patches Deployed Successfully",
				JOptionPane.OK_OPTION);

	}

	@Override
	public void showProgressBar() {

	}

}
