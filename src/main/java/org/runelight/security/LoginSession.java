package org.runelight.security;

import java.sql.Connection;

import org.runelight.view.dto.account.UserDTO;

public final class LoginSession {
	
	private Connection dbConnection;
	private UserDTO user;
	private boolean loggedIn;
	
	public LoginSession(Connection dbConnection) {
		this.dbConnection = dbConnection;
	}
	
	private void checkLoginSession() {
		
	}
	
	private UserDTO getUser() {
		return user;
	}
	
	public boolean isLoggedIn() {
		return loggedIn;
	}
	
}
