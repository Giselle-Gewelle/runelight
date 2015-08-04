package org.runelight.view.dto.staff.accountDetails;

import java.util.Date;

import org.runelight.util.DateUtil;

public final class SessionDetailsDTO {
	
	private final String ip;
	private final String startDate;
	private final String endDate;
	private final String startMod;
	private final String currentMod;
	private final String startDest;
	private final String currentDest;
	private final boolean secure;
	
	public SessionDetailsDTO(String ip, Date startDate, Date endDate, String startMod, String currentMod, String startDest, String currentDest, boolean secure) {
		this.ip = ip;
		this.startDate = DateUtil.SHORT_TIME_FORMAT.format(startDate);
		this.endDate = DateUtil.SHORT_TIME_FORMAT.format(endDate);
		this.startMod = startMod;
		this.currentMod = currentMod;
		this.startDest = startDest;
		this.currentDest = currentDest;
		this.secure = secure;
	}

	public String getIp() {
		return ip;
	}

	public String getStartDate() {
		return startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public String getStartMod() {
		return startMod;
	}

	public String getCurrentMod() {
		return currentMod;
	}

	public String getStartDest() {
		return startDest;
	}

	public String getCurrentDest() {
		return currentDest;
	}

	public boolean isSecure() {
		return secure;
	}
	
}
