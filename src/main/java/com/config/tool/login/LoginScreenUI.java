package com.config.tool.login;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import com.config.tool.constent.IConfigToolConstents;
import com.config.tool.mainScreen.MainScreenUI;
import com.config.tool.mainScreen.WatermarkPanel;
import com.config.tool.properties.PropertiesFileReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoginScreenUI extends JFrame implements ILoginView {

	// Initialize the logger
	private static final Logger logger = LogManager.getLogger(LoginScreenUI.class);


	private JTextField usernameField;
	private JPasswordField passwordField;
	private ILoginPresenter loginPresenter = new LoginPresenter(this);


	public LoginScreenUI() {

		try {
			//WatermarkPanel watermarkPanel = new WatermarkPanel("/resources/Accenture_logo.png"); // Create the watermark panel

			setTitle(PropertiesFileReader.readProperties(IConfigToolConstents.LOGIN_SCREEN_TITLE));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Create components
		JLabel headerLabel = new JLabel(PropertiesFileReader.readProperties(IConfigToolConstents.LOGIN_SCREEN_HEADER));
		headerLabel.setFont(new Font("Arial", Font.BOLD, 30)); // Increased font size
		headerLabel.setHorizontalAlignment(JLabel.CENTER);

		JLabel usernameLabel = new JLabel(PropertiesFileReader.readProperties(IConfigToolConstents.USERNAME_LABEL));
		JLabel passwordLabel = new JLabel(PropertiesFileReader.readProperties(IConfigToolConstents.PASSWORD_LABEL));

		usernameField = new JTextField(20);
		passwordField = new JPasswordField(20);

		JButton loginButton = new JButton(PropertiesFileReader.readProperties(IConfigToolConstents.LOGIN_BTN_LABEL));
		JButton cancelButton = new JButton(PropertiesFileReader.readProperties(IConfigToolConstents.CANCEL_BTN_LABEL));

		// Set layout with GridBagLayout for better centering
		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gbc.insets = new Insets(10, 10, 10, 10); // Add padding

		// Add components to the panel
		panel.add(headerLabel, gbc);
		panel.add(usernameLabel, gbc);
		panel.add(usernameField, gbc);
		panel.add(passwordLabel, gbc);
		panel.add(passwordField, gbc);
		panel.add(loginButton, gbc);
		panel.add(cancelButton, gbc);

		// Style components
		Font labelFont = new Font("Arial", Font.BOLD, 14);
		usernameLabel.setFont(labelFont);
		passwordLabel.setFont(labelFont);
		loginButton.setFont(labelFont);
		cancelButton.setFont(labelFont);

		// Set background colors
		panel.setBackground(new Color(240, 240, 240));
		loginButton.setBackground(new Color(50, 150, 50));
		loginButton.setForeground(Color.WHITE);
		cancelButton.setBackground(new Color(200, 50, 50));
		cancelButton.setForeground(Color.WHITE);

		// Add rounded corners to buttons
		styleButton(loginButton);
		styleButton(cancelButton);

		// Add action listeners
		loginButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				loginPresenter.login(usernameField.getText(),String.valueOf(passwordField.getPassword()));
			}
		});

		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				loginPresenter.cancelClicked();

			}
		});

		// Use BorderLayout for the frame's content pane
		setLayout(new BorderLayout());
		add(panel, BorderLayout.CENTER);
			//getContentPane().add(watermarkPanel, BorderLayout.CENTER);

		setExtendedState(JFrame.MAXIMIZED_BOTH); // Launch in full-screen mode
		setUndecorated(true); // Remove window decorations (optional)
		setVisible(true);
		}
		catch (IOException e) {
			e.getMessage();
		}
	}

	private void styleButton(JButton button) {
		button.setFocusPainted(false);
		button.setBorderPainted(false);
		button.setContentAreaFilled(true);
		button.setOpaque(true);
		button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
	}

	/*
	 * public static void main(String[] args) { StyledLoginScreen loginScreen= new
	 * StyledLoginScreen(); AppPresenter presenter = new AppPresenter(loginScreen);
	 * 
	 * }
	 */

	
	public static void showLoginScreen() {
		SwingUtilities.invokeLater(() -> new LoginScreenUI());
	}

	@Override
	public void showMainMenuScreen() {
		logger.info("Showing main menu screen");
		dispose();
		//ButtonTableUI mainUILauncher = new ButtonTableUI();
		MainScreenUI.launchMainUi();
		// ConfigWizardUI configScreenLauncher = new ConfigWizardUI();
		// onfigScreenLauncher.configWizardLaunch();

	}

	@Override
	public void showLoginFailed() {
		logger.error("Login failed");
		JOptionPane.showMessageDialog(this, IConfigToolConstents.LOGIN_FAILED_MESSAGE, IConfigToolConstents.ERROR, JOptionPane.ERROR_MESSAGE);
		// Clear the password field after a failed login attempt
		passwordField.setText("");

	}

	@Override
	public void showNonCancelableBox() {
		logger.warn("Showing non-cancelable box");
		JOptionPane.showMessageDialog(this, IConfigToolConstents.NON_CANCELABLE_MESSAGE, IConfigToolConstents.NON_CANCELABLE_MESSAGE, JOptionPane.ERROR_MESSAGE);
		
	}
}
