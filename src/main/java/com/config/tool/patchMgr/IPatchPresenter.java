package com.config.tool.patchMgr;

import javax.swing.DefaultListModel;

public interface IPatchPresenter {
	void getPatchList(DefaultListModel<PatchStatusCheckBox> patchListModel);

	boolean checkXstoreRunning();

	void movePatches(DefaultListModel<PatchStatusCheckBox> patchListModel);

	void killStore();

	void handlePatchFiles(PatchListUI patchListUI, DefaultListModel<PatchStatusCheckBox> patchListModel);

}
