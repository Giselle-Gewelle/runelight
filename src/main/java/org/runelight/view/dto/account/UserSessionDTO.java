package org.runelight.view.dto.account;

import org.runelight.util.StringUtil;

public final class UserSessionDTO {
	
	private final int accountId;
	private final String username;
	private final String formattedUsername;
	private final boolean staff;
	private final boolean fmod;
	private final boolean pmod;
	private final String ip;
	
	private final int sessionId;
	private final String sessionHash;
	private final boolean secure;
	private final String mod;
	private final String dest;
	
	private final int unreadMessages;
	private final int supportQueries;
	
	public UserSessionDTO(int accountId, String username, boolean staff, boolean fmod, boolean pmod, String ip, int sessionId, String sessionHash, boolean secure, String mod, String dest, 
			int unreadMessages, int supportQueries) {
		this.accountId = accountId;
		this.username = username;
		this.formattedUsername = StringUtil.formatUsername(username);
		this.staff = staff;
		this.fmod = fmod;
		this.pmod = pmod;
		this.ip = ip;
		
		this.sessionId = sessionId;
		this.sessionHash = sessionHash;
		this.secure = secure;
		this.mod = mod;
		this.dest = dest;
		
		this.unreadMessages = unreadMessages;
		this.supportQueries = supportQueries;
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
	public boolean isStaff() {
		return staff;
	}
	public boolean isFmod() {
		return fmod;
	}
	public boolean isPmod() {
		return pmod;
	}
	public String getIP() {
		return ip;
	}
	
	public int getSessionId() {
		return sessionId;
	}
	public String getSessionHash() {
		return sessionHash;
	}
	public boolean isSecure() {
		return secure;
	}
	public String getMod() {
		return mod;
	}
	public String getDest() {
		return dest;
	}

	public int getUnreadMessages() {
		return unreadMessages;
	}

	public int getSupportQueries() {
		return supportQueries;
	}
	
}
