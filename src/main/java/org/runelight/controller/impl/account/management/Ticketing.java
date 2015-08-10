package org.runelight.controller.impl.account.management;

import java.util.List;

import org.runelight.controller.Controller;
import org.runelight.db.dao.account.TicketingDAO;
import org.runelight.util.URLUtil;
import org.runelight.view.dto.account.ticketing.MessageQueueDTO;
import org.runelight.view.dto.account.ticketing.ThreadDTO;

public final class Ticketing extends Controller {

	private static final String 
		ERROR_NOT_FOUND = "Unable to load dialogue.";
	
	private TicketingDAO mainDao;
	
	@Override
	public void init() {
		mainDao = new TicketingDAO(getDbConnection(), getLoginSession().getUser());
		
		if(getRequest().getParameterMap().size() == 1) {
			int viewId = URLUtil.getIntParam(getRequest(), "viewid");
			if(viewId > 0) {
				if(prepareThread(viewId)) return;
			}
			
			//reply here
			
			int deleteId = URLUtil.getIntParam(getRequest(), "deleteid");
			if(deleteId > 0) {
				if(prepareDelete(deleteId)) return;
			}
		}
		
		prepareInbox();
	}
	
	private boolean prepareDelete(int messageId) {
		if(!mainDao.messageExists(messageId)) {
			setError(ERROR_NOT_FOUND);
			return false;
		}
		
		return true;
	}
	
	private boolean prepareThread(int lastMessageId) {
		ThreadDTO thread = mainDao.getThread(lastMessageId);
		if(thread == null) {
			setError(ERROR_NOT_FOUND);
			return false;
		}
		
		getRequest().setAttribute("thread", thread);
		return true;
	}
	
	private void setError(String errorMsg) {
		getRequest().setAttribute("error", errorMsg);
	}
	
	private void prepareInbox() {
		List<MessageQueueDTO> receivedMessages = mainDao.getMessages(TicketingDAO.TYPE_RECEIVED);
		List<MessageQueueDTO> readMessages = mainDao.getMessages(TicketingDAO.TYPE_READ);
		List<MessageQueueDTO> sentMessages = mainDao.getMessages(TicketingDAO.TYPE_SENT);
		
		if(receivedMessages != null) {
			getRequest().setAttribute("receivedList", receivedMessages);
		}
		if(readMessages != null) {
			getRequest().setAttribute("readList", readMessages);
		}
		if(sentMessages != null) {
			getRequest().setAttribute("sentList", sentMessages);
		}
	}
	
	@Override
	public boolean isSecure() {
		return true;
	}
	
	@Override
	public boolean holdSecureSession() {
		return true;
	}
	
	@Override
	public boolean loginRequired() {
		return true;
	}

}
