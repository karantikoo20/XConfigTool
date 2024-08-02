package com.config.tool.login;

import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.util.HashMap;
import java.util.Map;

//import static org.bouncycastle.cms.RecipientId.password;

@RequiredArgsConstructor
public class LoginPresenter implements ILoginPresenter {

	private static final Logger logger = LogManager.getLogger(LoginPresenter.class);


	private final ILoginView loginView;
	private final Map<String, String> userCredentials;

	// Constructor to initialize userCredentials map
	public LoginPresenter(ILoginView loginView) {
		this.loginView = loginView;
		// Initialize userCredentials with hardcoded values or retrieve from database/API
		userCredentials = new HashMap<>();
		userCredentials.put("admin", "admin123");
		userCredentials.put("local", "local123");
		userCredentials.put("user", "user123");
		// Add more credentials as needed
	}

	@Override
	public void login(String username, String password) {
		logger.info("Attempting login for username: {}", username);
			// Check if the entered username exists in the userCredentials map
			if (userCredentials.containsKey(username)) {
				// If the username exists, check if the entered password matches
				String storedPassword = userCredentials.get(username);
				if (password.equals(storedPassword)) {
					// If username and password match, show main menu screen
					logger.info("Login successful for username: {}", username);
					loginView.showMainMenuScreen();
				} else {
					// If password doesn't match, show login failed message
					logger.warn("Login failed for username: {}", username);
					loginView.showLoginFailed();
				}
			} else {
				// If username doesn't exist, show login failed message
				logger.warn("Username not found: {}", username);
				loginView.showLoginFailed();
			}



		// Check the username and password (for simplicity, use hardcoded values)
		//if ("admin".equals(username) && "admin123".equals(password)) {
		//	loginView.showMainMenuScreen();
//
		//} else {
		//	loginView.showLoginFailed();
		//}

	}

	@Override
	public void cancelClicked() {
		logger.info("Cancel button clicked");
		loginView.showNonCancelableBox();
		
	}

}
