package com.config.tool.mainScreen;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
@Getter
//@RequiredArgsConstructor
public enum MainScreenButtons {
	XSTORE_RESTART("xstoreRestart", "Xstore Restart"),
	XENV_RESTART("XenvRestart", "Xenv Restart"),
	XMOBILE_RESTART("XMobileRestart", "XMobile Restart"),
	EFTLink_RESTART("notepad", "EFTLink Restart"),
	RESTART_SYSTEM("systemRestart", "System Restart"),
	RUN_DATA_LOADER("runDataLoader", "Run Data Loader"),
	CHECK_CONNECTIVITY("checkConnectivity", "Check Connectivity"),
	CHECK_ORPHAN_TRANS("checkOrphanTrans", "Check Orphan Trans"),
	XSTORE_CONFIG_MGR("xstoreConfigMgr", "Xstore Config Manager"),
	TRANS_REPLICATION("Transreplication", "Transaction Replication"),
	DELETE_MARKER_FILES("deleteMarkerFiles", "Delete Marker Files"),
	PATCH_MGR("notepad", "Patch Manager"),
	XSTORE_USER_GUIDE("xstUserGuide", "Xstore User Guide"),
	TMP_PRIVILEGES("tmpPrivileges", "Add TMP Privileges"),
	DATABASE_BACKUP_RESTORE("databasebckrstr", "Database Operations"),
	BUTTON_16(null, null),
	BUTTON_17(null, null),
	BUTTON_18(null, null),
	BUTTON_19(null, null),
	BUTTON_20(null, null),
	BUTTON_21(null, null),
	BUTTON_22(null, null),
	BUTTON_23(null, null),
	BUTTON_24(null, null),
	BUTTON_25(null, null),
	BUTTON_26(null, null),
	BUTTON_27(null, null),
	BUTTON_28(null, null),
	BUTTON_29(null, null),
	BUTTON_30(null, null),
	BUTTON_31(null, null),
	BUTTON_32(null, null),
	BUTTON_33(null, null),
	BUTTON_34(null, null),
	BUTTON_35(null, null),
	BUTTON_36(null, null),
	BUTTON_37(null, null),
	BUTTON_38(null, null),
	BUTTON_39(null, null),
	BUTTON_40(null, null),
	BUTTON_41(null, null),
	//BUTTON_35(null, null),
	LOGOUT("logout", "Logout");
	private final String id;
	private final String text;
	MainScreenButtons(String id, String text) {
		this.id = id;
		this.text = text;
	}
	public String getId() {
		return id;
	}
	public String getText() {
		return text;
	}
}