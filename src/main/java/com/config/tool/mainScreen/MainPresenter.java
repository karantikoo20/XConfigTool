package com.config.tool.mainScreen;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.config.tool.constent.IConfigToolConstents;

import lombok.RequiredArgsConstructor;

import static com.config.tool.mainScreen.MainScreenUI.logger;

//@RequiredArgsConstructor
public class MainPresenter implements IMainPresenter {

	private final IMainScreenView view;

	public MainPresenter(IMainScreenView view) {
		this.view = view;
	}

    /*@Override
    public void buttonClicked(String buttonId) {

       if (buttonId.equals(MainScreenButtons.XSTORE_RESTART.getId())) {
          xstoreRestart();
       } else if (buttonId.equals(MainScreenButtons.XENV_RESTART.getId())) {
          xenvRestart();
       } else if (buttonId.equals(MainScreenButtons.XSTORE_CONFIG_MGR.getId())) {
          view.launchConfigWizard();
       } else if (buttonId.equals(MainScreenButtons.LOGOUT.getId())) {
          view.logoutFromMainScreen();
       } else if (buttonId.equals(MainScreenButtons.TRANS_REPLICATION.getId())) {
          runTransReplicationUtility();
       } else if (buttonId.equals(MainScreenButtons.DELETE_MARKER_FILES.getId())) {
          deleteMarkerFiles();
       } else if (buttonId.equals(MainScreenButtons.CHECK_CONNECTIVITY.getId())) {
          checkSystemConnectivity();
       }
       else if (buttonId.equals(MainScreenButtons.RUN_DATA_LOADER.getId())) {
          loadMNTFiles();
       }
       else if (buttonId.equals(MainScreenButtons.CHECK_ORPHAN_TRANS.getId())) {
          checkForOrphanTrans();
       }
       else if (buttonId.equals(MainScreenButtons.PATCH_MGR.getId())) {
          handlePatches();
       }
       else if (buttonId.equals(MainScreenButtons.XSTORE_USER_GUIDE.getId())) {
          showXstoreUserGuide();
       }


    }
*/


	public void runTransReplication() {

		executeBatchFile(IConfigToolConstents.TRANS_REPLICATION_BATCH_FILE_PATH);

	}

	public void delMarkerFiles() {
		String markerErrorFilePath = IConfigToolConstents.ERR_MARKER_FILE_PATH;
		File folder = new File(markerErrorFilePath);

		// Check if the path exists and is a directory
		if (folder.exists() && folder.isDirectory()) {
			File[] files = folder.listFiles();

			if (files != null) {
				boolean fileDeleted = false;
				for (File file : files) {
					if (file.isFile() && file.getName().endsWith(".err")) {
						// Delete files with the specified extension
						if (file.delete()) {
							System.out.println("Deleted: " + file.getAbsolutePath());
							view.showErrorMarkerFileDeletedConfirmation("Error marker file deleted: " + file.getAbsolutePath());
							fileDeleted = true;
						} else {
							System.err.println("Failed to delete: " + file.getAbsolutePath());
						}
					}
				}
				if (!fileDeleted) {
					System.err.println("No .err files found to delete.");
					view.showErrorMarkerFileDeletedConfirmation("No .err files found to delete.");
				}
			} else {
				System.err.println("Error reading the directory contents.");
				view.showErrorMarkerFileDeletedConfirmation("Error reading the directory contents.");
			}
		} else {
			System.err.println("Directory not found: " + markerErrorFilePath);
			view.showErrorMarkerFileDeletedConfirmation("Directory not found: " + markerErrorFilePath);
		}
	}




	public void checkSystemConnectivity() {
		String internetStatus = null;
		try {
			// Try to reach Google's public DNS server (8.8.8.8)
			InetAddress address = InetAddress.getByName("8.8.8.8");
			internetStatus = address.isReachable(1000)?"connected":"disconnected"; // Timeout set to 1 second
		} catch (IOException e) {
			internetStatus = "disconnected";
		}
		view.showInternetConnectivityStatus(internetStatus);
	}

	public void loadMNTFiles() {
		executeBatchFile(IConfigToolConstents.DATALOADER_LOADER_BATCH_FILE_PATH);
		view.showMNTsLoaded();
	}


	public void checkForOrphanTrans() {
		String folderPath = IConfigToolConstents.ORPHAN_TRANS_PATH; // Replace with the path to your folder
		String fileExtension = ".trn";

		if (isOrphanTransExist(folderPath, fileExtension)) {
			view.showIsAnyOrphanTransPresent("present");
		} else {
			view.showIsAnyOrphanTransPresent("not present");
		}
	}

	public static boolean isOrphanTransExist(String folderPath, String fileExtension) {
		File folder = new File(folderPath);

		// Check if the path is a directory
		if (folder.isDirectory()) {
			File[] files = folder.listFiles();

			if (files != null) {
				for (File file : files) {
					if (file.isFile() && file.getName().endsWith(fileExtension)) {
						// File with the specified extension found
						return true;
					}
				}
			}
		}

		// No file with the specified extension found
		return false;
	}

	public void executeBatchFile(String filePath) {
		String batchFilePath = filePath;

		Path path = Paths.get(batchFilePath);

		// Check if the file exists
		boolean fileExists = Files.exists(path);

		if (!fileExists) {
			System.out.println("The file does not exists at: " + path.toAbsolutePath());
		} else {

			try {

				// Create a process builder with the command to execute the batch file
				ProcessBuilder processBuilder = new ProcessBuilder("cmd", "/c", "start", "cmd", "/c", batchFilePath);

				// Start the process
				Process process = processBuilder.start();

				// Wait for the process to complete (optional)
				int exitCode = process.waitFor();
			} catch (IOException | InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private void handlePatches() {

		view.showPatchesList();

	}





}