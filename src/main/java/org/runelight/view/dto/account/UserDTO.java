package org.runelight.view.dto.account;

import org.runelight.security.Password;

public final class UserDTO {
	
	private int accountId;
	private String username;
	private String formattedUsername;
	private Password password;
	private String currentIP;
	private String creationDate;
	private String creationIP;
	private boolean staff;
	private boolean fmod;
	private boolean pmod;
	
	public UserDTO() {
		this.password = null;
	}
	
	public UserDTO(Password password) {
		this.password = password;
	}
	
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
	
	public String getCurrentIP() {
		return currentIP;
	}
	public void setCurrentIP(String currentIP) {
		this.currentIP = currentIP;
	}

	public String getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}

	public String getCreationIP() {
		return creationIP;
	}

	public void setCreationIP(String creationIP) {
		this.creationIP = creationIP;
	}

	public boolean isStaff() {
		return staff;
	}

	public void setStaff(boolean staff) {
		this.staff = staff;
	}

	public boolean isFmod() {
		return fmod;
	}

	public void setFmod(boolean fmod) {
		this.fmod = fmod;
	}

	public boolean isPmod() {
		return pmod;
	}

	public void setPmod(boolean pmod) {
		this.pmod = pmod;
	}
	
}
