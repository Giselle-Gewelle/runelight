package org.runelight.view.dto.account.ticketing;

import java.util.Date;

import org.runelight.util.DateUtil;

public final class MessageQueueDTO {
	
	private final int messageId;
	private final String title;
	private final String date;
	private final int messageNum;
	
	public MessageQueueDTO(int messageId, String title, Date date, int messageNum) {
		this.messageId = messageId;
		this.title = title;
		this.date = DateUtil.MSG_CENTER_FORMAT.format(date);
		this.messageNum = messageNum;
	}

	public int getMessageId() {
		return messageId;
	}

	public String getTitle() {
		return title;
	}

	public String getDate() {
		return date;
	}

	public int getMessageNum() {
		return messageNum;
	}
	
}
