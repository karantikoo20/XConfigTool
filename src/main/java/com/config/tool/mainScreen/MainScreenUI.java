package com.config.tool.mainScreen;

import java.awt.*;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

import javax.swing.*;

import com.config.tool.constent.IConfigToolConstents;
import com.config.tool.login.LoginScreenUI;
import com.config.tool.properties.PropertiesFileReader;
import com.config.tool.propertiesconfig.PropertiesConfigUI;
import com.config.tool.xstuserguide.DisplayXstUserGuidepdf;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MainScreenUI extends JFrame implements IMainScreenView {

	public static final Logger logger = LogManager.getLogger(MainScreenUI.class);
	private static XstoreRestart xstoreRestart; // Singleton instance of XstoreRestart

	IMainPresenter mainPresenter = new MainPresenter(this);
	//MainPresenter mainpresenter = new MainPresenter(this);


	public MainScreenUI() {
		try {
			setTitle(PropertiesFileReader.readProperties(IConfigToolConstents.XST_CONFIG_TOOL_TITLE));
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setSize(600, 600);

			setExtendedState(JFrame.MAXIMIZED_BOTH); // Launch in full-screen mode
			setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
			setUndecorated(true); // Remove window decorations (optional)

			try {
				UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
			} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
					 | UnsupportedLookAndFeelException e) {
				e.printStackTrace();
			}

			initializeUI();
			xstoreRestart = new XstoreRestart(); // Initialize the singleton instance
		} catch (IOException e) {
			logger.error("Error initializing UI: " + e.getMessage());
		}
	}

	private void initializeUI() {
		WatermarkPanel watermarkPanel = new WatermarkPanel("/resources/Accenture_logo.png"); // Create the watermark panel

		//create button panel
		JPanel panel = new JPanel();
		int gridSize = 6; // Set the desired grid size
		panel.setLayout(new GridLayout(gridSize, gridSize, 10, 10)); // GridLayout with 10-pixel gaps

		int buttonSize = 60; // Set the desired button size

		// Add buttons with watermark background
		Arrays.asList(MainScreenButtons.values()).forEach(appButton -> {
			JButton button = createButton(appButton.getId(), appButton.getText(), watermarkPanel);
			panel.add(button);
		});

		// Add the watermark panel to the JFrame
		getContentPane().add(watermarkPanel, BorderLayout.CENTER);

		// Add the button panel to the JFrame
		getContentPane().add(panel, BorderLayout.SOUTH);

		setVisible(true);
	}

	public static void launchMainUi() {
		SwingUtilities.invokeLater(() -> new MainScreenUI());
	}

	private JButton createButton(String id, String buttonLabel, WatermarkPanel watermarkPanel) {
		JButton panelButton = new JButton(buttonLabel);
		if (id == null && buttonLabel == null) {
			panelButton.setEnabled(false);
		}
		if (id != null && id.equals("logout")) {
			panelButton.setPreferredSize(new Dimension(80, 95));
			panelButton.setBorder(new RoundBorder(10)); // Set corner radius to 10 pixels
			panelButton.setForeground(Color.WHITE);
			panelButton.setBackground(new Color(204, 0, 0)); // Red background color
			panelButton.setHorizontalTextPosition(SwingConstants.RIGHT); // Place text to the right of the icon
			panelButton.setFont(panelButton.getFont().deriveFont(Font.BOLD, 16)); // Increase font size to 16
		}

		panelButton.addActionListener((e) -> {
			// Log the button click event
			logger.info("Button clicked: " + buttonLabel);
			if (id != null) {
				showWaitPrompt("Operation in progress...");
				new Thread(() -> {
					try {
						switch (id) {
							case "xstoreRestart":
								executeXstoreRestart();
								break;
							case "XMobileRestart":
								executeXstoreMobileRestart();
								break;
							case "XenvRestart":
								executeXenvRestart();
								break;
							case "systemRestart":
								executeSystemRestart();
								break;

							case "notepad":
								openNotepad();
								break;
							case "Transreplication":
								runTransReplicationUtility();
								break;
							case "runDataLoader":
								runDataLoader();
								break;
							case "checkConnectivity":
								checkConnectivity();
								break;
							case "checkOrphanTrans":
								checkOrphanTrans();
								break;
							case "xstoreConfigMgr":
								launchConfigWizard();
								break;
							case "deleteMarkerFiles":
								deleteMarkerFiles();
								break;
							case "xstUserGuide":
								showXstoreUserGuide();
								break;
							case "logout":
								logoutFromMainScreen();
								break;
							case "tmpPrivileges": // New case
								handleTmpPrivileges();
								break;
							case "databasebckrstr": // New case
								handledatabaseBackupRestore();
								break;
							default:
								logger.warn("Unhandled button ID: " + id);
						}
					} catch (Exception ex) {
						logger.error("Error performing action: " + ex.getMessage());
					}
				}).start();
			}
		});

		return panelButton;
	}

	// new method added
	private void handleTmpPrivileges() {
		logger.info("Handling TMP Privileges...");

		// Prompt the user to enter an employee ID
		String employeeId = JOptionPane.showInputDialog(this,
				"Enter Employee ID:", "TMP Privileges",
				JOptionPane.PLAIN_MESSAGE);

		// Check if the input is not null (if the user didn't cancel the dialog)
		if (employeeId != null) {
			// Log the entered employee ID
			logger.info("Employee ID entered: " + employeeId);

			// Handle the TMP Privileges action using the entered employee ID
			// Replace this comment with the actual implementation of TMP Privileges using the employeeId

			// Show a confirmation message
			JOptionPane.showMessageDialog(this, "TMP Privileges action executed for Employee ID: " + employeeId,
					"TMP Privileges", JOptionPane.INFORMATION_MESSAGE);
		} else {
			logger.info("TMP Privileges action cancelled by the user.");
		}
	}


	private void openNotepad() {
		try {
			Runtime.getRuntime().exec("notepad.exe");
		} catch (IOException ex) {
			logger.error("Error opening Notepad: " + ex.getMessage());
		}
	}

	private void executeXstoreRestart() {
		logger.info("Restarting Xstore...");
		try {
			xstoreRestart.handleXstoreAnchorFile();

			mainPresenter.executeBatchFile(IConfigToolConstents.XSTORE_BATCH_FILE_PATH);
		} catch (Exception e) {
			logger.error("Error restarting Xstore: " + e.getMessage());
		}
	}

	private void executeXstoreMobileRestart() {
		logger.info("Restarting XstoreMobile...");
		try {
			XstoreMobileRestart xstoreMobRestart = new XstoreMobileRestart();
			xstoreMobRestart.handleXstoreMobileAnchorFile();

			mainPresenter.executeBatchFile(IConfigToolConstents.XSTORE_MOB_BATCH_FILE_PATH);
		} catch (Exception e) {
			logger.error("Error restarting XstoreMobile: " + e.getMessage());
		}
	}


	private void executeXenvRestart() {
		logger.info("Restarting XstoreMobile...");
		try {
			xstoreRestart.handleXstoreAnchorFile();
			logger.info("xstore anchor file deleted...");

			XenvRestart xenvRestart= new  XenvRestart();
			xenvRestart.handleXenvAnchorFile();
			logger.info("xenv anchor file deleted...");

			mainPresenter.executeBatchFile(IConfigToolConstents.Xenv_BATCH_FILE_PATH);
		} catch (Exception e) {
			logger.error("Error restarting Xenv: " + e.getMessage());
		}
	}

	private void executeSystemRestart() {
		logger.info("Restarting system...");
		try {
			String os = System.getProperty("os.name").toLowerCase();
			if (os.contains("win")) {
				Runtime.getRuntime().exec("shutdown -r -t 0");
			} else if (os.contains("mac")) {
				Runtime.getRuntime().exec("sudo shutdown -r now");
			} else if (os.contains("nix") || os.contains("nux")) {
				Runtime.getRuntime().exec("sudo shutdown -r now");
			} else {
				logger.error("Unsupported operating system: " + os);
			}
		} catch (IOException ex) {
			logger.error("Error restarting system: " + ex.getMessage());
		}
	}


	private void showWaitPrompt(String msg) {
		logger.info("Showing wait prompt: " + msg);

		JDialog dialog = new JDialog(this, "Please wait", Dialog.ModalityType.MODELESS);
		JLabel label = new JLabel(msg, SwingConstants.CENTER);
		label.setFont(new Font("Arial", Font.PLAIN, 15));
		dialog.add(label);
		dialog.setSize(250, 150);
		dialog.setLocationRelativeTo(this);
		dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		dialog.setVisible(true);

		Timer timer = new Timer(2000, (e) -> dialog.dispose());
		timer.setRepeats(false); // Execute only once
		timer.start();
	}



	private void runDataLoader() {
		logger.info("Running Data Loader...");
		// Implementation of running the data loader
		mainPresenter.loadMNTFiles();

	}

	private void checkConnectivity() {
		logger.info("Checking Connectivity...");
		// Implementation of checking connectivity
		mainPresenter.checkSystemConnectivity();
		//showInternetConnectivityStatus("Connected");
	}

	private void checkOrphanTrans() {
		logger.info("Checking Orphan Transactions...");
		// Implementation of checking orphan transactions
		mainPresenter.checkForOrphanTrans();
		//showIsAnyOrphanTransPresent("Present");
	}

	private void deleteMarkerFiles() {
		logger.info("Deleting Marker Files...");
		// Implementation of deleting marker files
		mainPresenter.delMarkerFiles();

	}

	@Override
	public void launchConfigWizard() {
		try {
			logger.info("Launching the configuration wizard");
			dispose();
			new PropertiesConfigUI();
		} catch (Exception e) {
			logger.error("Error launching the configuration wizard: " + e.getMessage(), e);
			JOptionPane.showMessageDialog(this, "An error occurred while launching the configuration wizard.",
					"Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	@Override
	public void logoutFromMainScreen() {
		try {
			logger.info("Logging out from the main screen");
			dispose();
			new LoginScreenUI();
		} catch (Exception e) {
			logger.error("Error logging out from the main screen: " + e.getMessage(), e);
			JOptionPane.showMessageDialog(this, "An error occurred while logging out from the main screen.",
					"Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	@Override
	public void showErrorMarkerFileDeletedConfirmation(String message) {
		try {
			logger.info("Showing error marker file deleted confirmation");
			JOptionPane.showMessageDialog(this, message, "Err Marker Files Deleted", JOptionPane.OK_OPTION);
		} catch (HeadlessException e) {
			logger.error("Error showing error marker file deleted confirmation: " + e.getMessage(), e);
			JOptionPane.showMessageDialog(this, "An error occurred while showing error marker file deleted confirmation.",
					"Error", JOptionPane.ERROR_MESSAGE);
		}
	}


	@Override
	public void showInternetConnectivityStatus(String status) {
		try {
			logger.info("Displaying internet connectivity status: " + status);
			JOptionPane.showMessageDialog(this, "Your System is " + status,
					"Internet Status", JOptionPane.OK_OPTION);
		} catch (HeadlessException e) {
			logger.error("Headless exception occurred while displaying internet connectivity status: " + e.getMessage(), e);
			JOptionPane.showMessageDialog(this, "An error occurred while displaying internet connectivity status.",
					"Error", JOptionPane.ERROR_MESSAGE);
		} catch (Exception e) {
			logger.error("Error occurred while displaying internet connectivity status: " + e.getMessage(), e);
			JOptionPane.showMessageDialog(this, "An error occurred while displaying internet connectivity status.",
					"Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	@Override
	public void showMNTsLoaded() {
		try {
			logger.info("Data loader executed successfully");
			JOptionPane.showMessageDialog(this, "Data Loader Executed",
					"Data Loader Execution", JOptionPane.OK_OPTION);
		} catch (Exception e) {
			logger.error("Error occurred while executing data loader: " + e.getMessage(), e);
			JOptionPane.showMessageDialog(this, "An error occurred while executing the data loader.",
					"Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	public void runTransReplicationUtility(){
		try {
			mainPresenter.runTransReplication();
			logger.info("Trans replication utility executed successfully");
			JOptionPane.showMessageDialog(this, "Trans replication utility",
					"Trans replication utility Execution", JOptionPane.OK_OPTION);
		} catch (Exception e) {
			logger.error("Error occurred while executing Trans replication utility: " + e.getMessage(), e);
			JOptionPane.showMessageDialog(this, "An error occurred while executing the Trans replication utility.",
					"Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	@Override
	public void showIsAnyOrphanTransPresent(String orphanTransStatus) {
		try {
			logger.info("Checking for orphan transactions: " + orphanTransStatus);
			JOptionPane.showMessageDialog(this, "Orphan Transaction Status: " + orphanTransStatus,
					"Orphan Transactions Status", JOptionPane.OK_OPTION);
		} catch (HeadlessException e) {
			logger.error("Headless exception occurred while checking orphan transactions: " + e.getMessage(), e);
			JOptionPane.showMessageDialog(this, "An error occurred while checking orphan transactions.",
					"Error", JOptionPane.ERROR_MESSAGE);
		} catch (Exception e) {
			logger.error("Error occurred while checking orphan transactions: " + e.getMessage(), e);
			JOptionPane.showMessageDialog(this, "An error occurred while checking orphan transactions.",
					"Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	@Override
	public void showPatchesList() {
		try {
			logger.info("Showing patches deployed confirmation");
			JOptionPane.showMessageDialog(this, "Patches have been deployed successfully.",
					"Patches Deployed", JOptionPane.INFORMATION_MESSAGE);
		} catch (HeadlessException e) {
			logger.error("Headless exception occurred while showing patches deployed confirmation: " + e.getMessage(), e);
			JOptionPane.showMessageDialog(this, "An error occurred while showing patches deployed confirmation.",
					"Error", JOptionPane.ERROR_MESSAGE);
		} catch (Exception e) {
			logger.error("Error occurred while showing patches deployed confirmation: " + e.getMessage(), e);
			JOptionPane.showMessageDialog(this, "An error occurred while showing patches deployed confirmation.",
					"Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	@Override
	public void showXstoreUserGuide() {
		logger.info("Displaying PDF...");
		try {
			DisplayXstUserGuidepdf displayPDF = new DisplayXstUserGuidepdf();
			displayPDF.showXstUserGuide();

		} catch (Exception e) {
			logger.error("Error displaying pdf: " + e.getMessage());
			JOptionPane.showMessageDialog(this, "An error occurred while Displaying the PDF.",
					"Error", JOptionPane.ERROR_MESSAGE);
		}
	}


	private void handledatabaseBackupRestore() {
		SwingUtilities.invokeLater(() -> {
			// Create a dialog to choose the operation
			String[] options = {"Backup Employees Table", "Restore Employees Table"};
			int choice = JOptionPane.showOptionDialog(this, "Choose an operation:",
					"Database Backup/Restore", JOptionPane.DEFAULT_OPTION,
					JOptionPane.PLAIN_MESSAGE, null, options, options[0]);

			switch (choice) {
				case 0:
					handleBackupOperation();
					break;
				case 1:
					handleRestoreOperation();
					break;
				default:
					logger.info("No valid option selected for Database Backup/Restore.");
			}
		});
	}

	private void handleBackupOperation() {
		String backupFilePath = JOptionPane.showInputDialog(this,
				"Enter the backup file path:", "Backup Employees Table",
				JOptionPane.PLAIN_MESSAGE);

		if (backupFilePath != null && !backupFilePath.trim().isEmpty()) {
			DatabaseRestore.backupEmployeesTable(backupFilePath);
			JOptionPane.showMessageDialog(this, "Backup completed successfully.",
					"Backup Status", JOptionPane.INFORMATION_MESSAGE);
			logger.info("Database backup completed successfully");
		} else {
			logger.info("Backup operation canceled or invalid input.");
		}
	}

	private void handleRestoreOperation() {
		String restoreFilePath = JOptionPane.showInputDialog(this,
				"Enter the restore file path:", "Restore Employees Table",
				JOptionPane.PLAIN_MESSAGE);
		logger.info("Database restore completed successfully");

		if (restoreFilePath != null && !restoreFilePath.trim().isEmpty()) {
			DatabaseRestore.restoreEmployeesTable(restoreFilePath);
			JOptionPane.showMessageDialog(this, "Restore completed successfully.",
					"Restore Status", JOptionPane.INFORMATION_MESSAGE);
		} else {
			logger.info("Restore operation canceled or invalid input.");
		}
	}

    /*@Override
    public void showErrorMessage(String errorMessage) {
       logger.error("Displaying error message: " + errorMessage);
       JOptionPane.showMessageDialog(this, errorMessage,
             "Error", JOptionPane.ERROR_MESSAGE);
    }*/
}