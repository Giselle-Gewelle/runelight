package org.runelight.view.dto.account.ticketing;

import java.util.Date;

import org.runelight.util.DateUtil;
import org.runelight.util.StringUtil;

public final class MessageViewDTO {
	
	private final int messageId;
	private final String title;
	private final String date;
	private final String message;
	private final String authorName;
	private final boolean authorStaff;
	private final boolean read;
	private final boolean includeTitleInMsg;
	
	public MessageViewDTO(int messageId, String title, Date date, String message, String authorName, boolean authorStaff, Date readOn, boolean includeTitleInMsg) {
		this.messageId = messageId;
		this.title = title;
		this.date = DateUtil.MSG_CENTER_FORMAT.format(date);
		this.message = message;
		this.authorName = StringUtil.formatUsername(authorName);
		this.authorStaff = authorStaff;
		this.read = (readOn != null);
		this.includeTitleInMsg = includeTitleInMsg;
	}

	public int getMessageId() {
		return messageId;
	}

	public String getDate() {
		return date;
	}

	public String getMessage() {
		return message;
	}

	public String getAuthorName() {
		return authorName;
	}

	public boolean isAuthorStaff() {
		return authorStaff;
	}

	public boolean isRead() {
		return read;
	}

	public boolean isIncludeTitleInMsg() {
		return includeTitleInMsg;
	}

	public String getTitle() {
		return title;
	}
	
}
