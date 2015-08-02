package org.runelight.view.dto.account;

public final class CreateAccountDTO {
	
	private String ageRange;
	private String countryCode;
	private String username;
	private String formattedUsername;
	
	public String getAgeRange() {
		return ageRange;
	}
	public void setAgeRange(String ageRange) {
		this.ageRange = ageRange;
	}
	
	public String getCountryCode() {
		return countryCode;
	}
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
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
