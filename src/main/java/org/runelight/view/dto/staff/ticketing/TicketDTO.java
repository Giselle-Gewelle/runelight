package org.runelight.view.dto.staff.ticketing;

/**
 * Data Transfer Object for Staff Center Ticket Details.
 * @author Giselle
 * TODO: WSDL
 */
public final class TicketDTO {
	
	private final int id;
	private final String title;
	private final String date;
	private final String message;
	private final String authorName;
	private final String authorIP;
	private final int authorId;
	
	public TicketDTO(int id, String title, String date, String message, String authorName, String authorIP, int authorId) {
		this.id = id;
		this.title = title;
		this.date = date;
		this.message = message;
		this.authorName = authorName;
		this.authorIP = authorIP;
		this.authorId = authorId;
	}

	public int getId() {
		return id;
	}

	public String getTitle() {
		return title;
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

	public String getAuthorIP() {
		return authorIP;
	}

	public int getAuthorId() {
		return authorId;
	}
	
}
