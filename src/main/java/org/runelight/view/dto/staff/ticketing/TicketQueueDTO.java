package org.runelight.view.dto.staff.ticketing;

import java.util.Date;

import org.runelight.util.DateUtil;

public final class TicketQueueDTO {
	
	private final int id;
	private final String title;
	private final int authorId;
	private final String authorName;
	private final String date;
	
	public TicketQueueDTO(int id, String title, int authorId, String authorName, Date date) {
		this.id = id;
		this.title = title;
		this.authorId = authorId;
		this.authorName = authorName;
		this.date = DateUtil.SHORT_TIME_FORMAT.format(date);
	}

	public int getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public int getAuthorId() {
		return authorId;
	}

	public String getAuthorName() {
		return authorName;
	}

	public String getDate() {
		return date;
	}
	
}
