package org.runelight.view.dto.account;

public final class SessionCheckDTO {
	
	private final int sessionId;
	private final boolean secure;
	private final String endDate;
	
	public SessionCheckDTO(int sessionId, boolean secure, String endDate) {
		this.sessionId = sessionId;
		this.secure = secure;
		this.endDate = endDate;
	}
	
	public int getSessionId() {
		return sessionId;
	}
	
	public boolean isSecure() {
		return secure;
	}
	
	public String getEndDate() {
		return endDate;
	}
	
}
