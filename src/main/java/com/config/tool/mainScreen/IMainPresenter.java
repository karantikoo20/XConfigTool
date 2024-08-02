package com.config.tool.mainScreen;

public interface IMainPresenter {

	//void buttonClicked(String buttonId);


	void loadMNTFiles();
	void delMarkerFiles();

	void executeBatchFile(String xstoreBatchFilePath);
	void checkSystemConnectivity();

	void checkForOrphanTrans();

	void runTransReplication();
}
