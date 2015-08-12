package org.runelight.view.dto.account.ticketing;

import java.util.List;

public final class ThreadDTO {
	
	private final int id;
	private final int mainMessageId;
	private final int messageCount;
	private final String title;
	private final boolean canReply;
	private final List<MessageViewDTO> messageList;
	
	public ThreadDTO(int id, int mainMessageId, int messageCount, String title, boolean replyFlag, String latestPoster, String currentUsername, List<MessageViewDTO> messageList) {
		this.id = id;
		this.mainMessageId = mainMessageId;
		this.messageCount = messageCount;
		this.title = title;
		
		if(!replyFlag) {
			this.canReply = false;
		} else {
			if(latestPoster.equals(currentUsername)) {
				this.canReply = false;
			} else {
				this.canReply = true;
			}
		}
		
		this.messageList = messageList;
	}

	public int getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public boolean getCanReply() {
		return canReply;
	}

	public List<MessageViewDTO> getMessageList() {
		return messageList;
	}

	public int getMainMessageId() {
		return mainMessageId;
	}

	public int getMessageCount() {
		return messageCount;
	}
	
}
