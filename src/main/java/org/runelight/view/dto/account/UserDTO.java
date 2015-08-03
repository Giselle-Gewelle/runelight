package org.runelight.view.dto.account;

public final class UserDTO {
	
	private int accountId;
	private String username;
	private String formattedUsername;
	
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
	
}
