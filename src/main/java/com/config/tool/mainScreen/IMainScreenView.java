package com.config.tool.mainScreen;

public interface IMainScreenView {

	void launchConfigWizard();

	void logoutFromMainScreen();



	void showErrorMarkerFileDeletedConfirmation(String message);

	void showInternetConnectivityStatus(String status);

	void showMNTsLoaded();

	void showIsAnyOrphanTransPresent(String orphanTransStatus);

	void showPatchesList();

	void showXstoreUserGuide();

//	void handleTmpPrivileges();



}