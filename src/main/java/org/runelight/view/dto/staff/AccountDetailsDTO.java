package org.runelight.view.dto.staff;

import java.util.Date;
import java.util.List;

import org.runelight.controller.impl.account.CreateAccount;
import org.runelight.util.CountryUtil;
import org.runelight.util.DateUtil;
import org.runelight.util.StringUtil;
import org.runelight.view.dto.staff.accountDetails.IPDateDTO;
import org.runelight.view.dto.staff.accountDetails.SessionDetailsDTO;

public final class AccountDetailsDTO {
	
	private final int accountId;
	private final String username;
	private final String formattedUsername;
	private final String ageRange;
	private final String country;
	private final String creationDate;
	private final String creationIP;
	private final String lastLoginDate;
	private final String currentIP;
	private final boolean staff;
	private final boolean fmod;
	private final boolean pmod;
	private final List<IPDateDTO> passwordChangeList;
	private final List<SessionDetailsDTO> sessionList;
	private final List<IPDateDTO> loginAttemptList;
	
	public AccountDetailsDTO(int accountId, String username, String ageRange, String countryCode, Date creationDate, String creationIP, Date lastLoginDate, String currentIP, 
			boolean staff, boolean fmod, boolean pmod, List<IPDateDTO> passwordChangeList, List<SessionDetailsDTO> sessionList, List<IPDateDTO> loginAttemptList) {
		this.accountId = accountId;
		this.username = username;
		this.formattedUsername = StringUtil.formatUsername(username);
		this.ageRange = CreateAccount.AGE_RANGE_MAP.get(ageRange);
		this.country = CountryUtil.COUNTRY_MAP.get(countryCode);
		this.creationDate = DateUtil.LONG_TIME_FORMAT.format(creationDate);
		this.creationIP = creationIP;
		
		if(lastLoginDate == null) {
			this.lastLoginDate = null;
		} else {
			this.lastLoginDate = DateUtil.LONG_TIME_FORMAT.format(lastLoginDate);
		}
		
		this.currentIP = currentIP;
		this.staff = staff;
		this.fmod = fmod;
		this.pmod = pmod;
		this.passwordChangeList = passwordChangeList;
		this.sessionList = sessionList;
		this.loginAttemptList = loginAttemptList;
	}
	
	public List<SessionDetailsDTO> getSessionList() {
		return sessionList;
	}

	public int getAccountId() {
		return accountId;
	}

	public String getUsername() {
		return username;
	}

	public String getFormattedUsername() {
		return formattedUsername;
	}

	public String getCountry() {
		return country;
	}

	public String getAgeRange() {
		return ageRange;
	}

	public String getCreationDate() {
		return creationDate;
	}

	public String getCreationIP() {
		return creationIP;
	}

	public String getLastLoginDate() {
		return lastLoginDate;
	}

	public String getCurrentIP() {
		return currentIP;
	}

	public boolean isStaff() {
		return staff;
	}

	public boolean isFmod() {
		return fmod;
	}

	public boolean isPmod() {
		return pmod;
	}

	public List<IPDateDTO> getPasswordChangeList() {
		return passwordChangeList;
	}

	public List<IPDateDTO> getLoginAttemptList() {
		return loginAttemptList;
	}
	
}
