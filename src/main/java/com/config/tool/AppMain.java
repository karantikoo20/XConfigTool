package com.config.tool;

/*import com.config.tool.login.LoginScreenUI;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class AppMain {
	private static final Logger logger = LogManager.getLogger(AppMain.class);
	
	public static void main(String[] args) {
		LoginScreenUI.showLoginScreen();
	}

}*/
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.config.tool.login.LoginScreenUI;

public class AppMain {
	private static final Logger logger = LogManager.getLogger(AppMain.class);

	public static void main(String[] args) {
		// Initialize Log4j
		System.setProperty("log4j.configurationFile", "resources/log4j2.xml");

		logger.info("Application started.");

		try {
			// Show the login screen
			LoginScreenUI.showLoginScreen();
		} catch (Exception e) {
			logger.error("Error occurred while starting the application: " + e.getMessage());
		}
	}
}
