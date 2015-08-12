package org.runelight.view.dto.account.ticketing;

import java.util.List;

public final class InboxDTO {
	
	private final List<MessageQueueDTO> receivedMessageList;
	private final List<MessageQueueDTO> sentMessageList;
	private final List<MessageQueueDTO> readMessageList;
	
	public InboxDTO(List<MessageQueueDTO> receivedMessageList, List<MessageQueueDTO> sentMessageList, List<MessageQueueDTO> readMessageList) {
		if(receivedMessageList.size() < 1) {
			this.receivedMessageList = null;
		} else {
			this.receivedMessageList = receivedMessageList;
		}
		
		if(sentMessageList.size() < 1) {
			this.sentMessageList = null;
		} else {
			this.sentMessageList = sentMessageList;
		}
		
		if(readMessageList.size() < 1) {
			this.readMessageList = null;
		} else {
			this.readMessageList = readMessageList;
		}
	}

	public List<MessageQueueDTO> getReceivedMessageList() {
		return receivedMessageList;
	}

	public List<MessageQueueDTO> getSentMessageList() {
		return sentMessageList;
	}

	public List<MessageQueueDTO> getReadMessageList() {
		return readMessageList;
	}
	
}
