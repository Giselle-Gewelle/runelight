package org.runelight.view.dto.staff.accountDetails;

import java.util.Date;

import org.runelight.util.DateUtil;

public final class IPDateDTO {
	
	private final String ip;
	private final String date;
	
	public IPDateDTO(String ip, Date date) {
		this.ip = ip;
		this.date = DateUtil.SHORT_TIME_FORMAT.format(date);
	}

	public String getIp() {
		return ip;
	}

	public String getDate() {
		return date;
	}
	
}
