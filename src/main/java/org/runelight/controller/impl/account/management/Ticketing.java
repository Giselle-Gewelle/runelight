package org.runelight.controller.impl.account.management;

import java.util.List;

import org.runelight.controller.Controller;
import org.runelight.db.dao.account.TicketingDAO;
import org.runelight.util.URLUtil;
import org.runelight.view.dto.account.ticketing.MessageQueueDTO;
import org.runelight.view.dto.account.ticketing.ThreadDTO;

public final class Ticketing extends Controller {

	private TicketingDAO mainDao;
	
	@Override
	public void init() {
		mainDao = new TicketingDAO(getDbConnection());
		
		if(getRequest().getParameterMap().size() == 1) {
			int viewId = URLUtil.getIntParam(getRequest(), "viewId");
			if(viewId > 0) {
				if(prepareThread(viewId)) return;
			}
		}
		
		prepareInbox();
	}
	
	private boolean prepareThread(int lastMessageId) {
		ThreadDTO thread = mainDao.getThread(getLoginSession().getUser(), lastMessageId);
		if(thread == null) {
			return false;
		}
		
		getRequest().setAttribute("thread", thread);
		return true;
	}
	
	private void prepareInbox() {
		List<MessageQueueDTO> receivedMessages = mainDao.getMessages(getLoginSession().getUser(), TicketingDAO.TYPE_RECEIVED);
		List<MessageQueueDTO> readMessages = mainDao.getMessages(getLoginSession().getUser(), TicketingDAO.TYPE_READ);
		List<MessageQueueDTO> sentMessages = mainDao.getMessages(getLoginSession().getUser(), TicketingDAO.TYPE_SENT);
		
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
