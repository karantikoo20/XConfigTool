package com.config.tool.mainScreen;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.config.tool.login.LoginScreenUI;

public class LogoutButtonClickListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			//dispose();
			new LoginScreenUI();
			// JOptionPane.showMessageDialog(ButtonTableUI.this, "Logout button clicked");
		}
	}