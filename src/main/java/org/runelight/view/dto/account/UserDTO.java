package org.runelight.view.dto.account;

import org.runelight.security.Password;

public final class UserDTO {
	
	private int accountId;
	private String username;
	private String formattedUsername;
	private Password password;
	private String currentIP;
	
	public int getAccountId() {
		return accountId;
	}
	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getFormattedUsername() {
		return formattedUsername;
	}
	public void setFormattedUsername(String formattedUsername) {
		this.formattedUsername = formattedUsername;
	}
	
	public Password getPassword() {
		return password;
	}
	public void setPassword(Password password) {
		this.password = password;
	}
	
	public String getCurrentIP() {
		return currentIP;
	}
	public void setCurrentIP(String currentIP) {
		this.currentIP = currentIP;
	}
	
}
