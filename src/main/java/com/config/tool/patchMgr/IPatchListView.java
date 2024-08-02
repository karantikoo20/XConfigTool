package com.config.tool.patchMgr;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;

public interface IPatchListView {

	int showXstoreRestartWarning(JFrame parent, String message, String title);

	void getPatchList(DefaultListModel<PatchStatusCheckBox> patchListModel);

	void showPatchesDeployed();
	
	void showProgressBar();
}
