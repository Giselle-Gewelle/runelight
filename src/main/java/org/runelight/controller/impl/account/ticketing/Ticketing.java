package org.runelight.controller.impl.account.ticketing;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.runelight.controller.Controller;
import org.runelight.db.dao.account.TicketingDAO;
import org.runelight.http.HttpRequestType;
import org.runelight.util.StringUtil;
import org.runelight.util.URLUtil;
import org.runelight.view.dto.account.ticketing.InboxDTO;
import org.runelight.view.dto.account.ticketing.MessageViewDTO;
import org.runelight.view.dto.account.ticketing.ThreadDTO;

public final class Ticketing extends Controller {

	private static final Logger LOG = Logger.getLogger(Ticketing.class);
	
	private static final String 
		ERROR_NOT_FOUND = "Unable to load dialogue.",
		ERROR_UNAUTHORIZED = "You are not authorized to access this ticket.",
		ERROR_GENERIC = "An error has occurred.";
	
	private TicketingDAO mainDao;
	
	@Override
	public void init() {
		mainDao = new TicketingDAO(getDbConnection(), getLoginSession().getUser());
		
		if(getRequest().getParameterMap().size() == 1 || getRequestType().equals(HttpRequestType.POST)) {
			int viewId = URLUtil.getIntParam(getRequest(), "viewid");
			if(viewId > 0) {
				if(prepareThread(viewId)) return;
			}
			
			int replyId = URLUtil.getIntParam(getRequest(), "replyid");
			if(replyId > 0) {
				if(prepareReply(replyId)) return;
			}
			
			int deleteId = URLUtil.getIntParam(getRequest(), "deleteid");
			if(deleteId > 0) {
				if(prepareDelete(deleteId)) return;
			}
		}
		
		prepareInbox();
	}
	
	private boolean prepareReply(int messageId) {
		if(!checkMessage(messageId)) {
			return false;
		}
		
		ThreadDTO thread = mainDao.getThread(messageId);
		if(thread == null) {
			setError(ERROR_NOT_FOUND);
			return true;
		}
		
		if(!thread.getCanReply()) {
			return false;
		}
		
		HttpServletRequest request = getRequest();
		
		request.setAttribute("replyId", messageId);
		
		String currentTitle = thread.getTitle();
		request.setAttribute("currentTitle", currentTitle);
		
		if(getRequestType().equals(HttpRequestType.POST)) {
			String message = request.getParameter("inputMessage");
			if(message == null) {
				request.setAttribute("errorCode", 0);
				return true;
			}
			
			message = message.trim();
			if(message.equals("")) {
				request.setAttribute("errorCode", 0);
				return true;
			}
			
			int charLimit = 1000;
			if(getLoginSession().getUser().isStaff()) {
				charLimit = 50000;
			}
			
			if(message.length() > charLimit) {
				request.setAttribute("errorCode", 1);
				return true;
			}
			
			boolean canReply = true;
			if(getLoginSession().getUser().isStaff()) {
				String canReplyStr = request.getParameter("inputCanReply");
				if(canReplyStr == null || !canReplyStr.equals("yes")) {
					canReply = false;
				}
			}
			
			MessageViewDTO lastMessage = thread.getMessageList().get(thread.getMessageList().size() - 1);
			if(!mainDao.sendMessage(true, thread.getId(), currentTitle, thread.getMessageCount() + 1, message, StringUtil.deFormatUsername(lastMessage.getAuthorName()), canReply, false)) {
				request.setAttribute("errorCode", 2);
			} else {
				request.setAttribute("sentTo", lastMessage.getAuthorName());
			}
		}
		
		return true;
	}
	
	private boolean prepareDelete(int messageId) {
		if(!checkMessage(messageId)) {
			return false;
		}
		
		HttpServletRequest request = getRequest();
		
		request.setAttribute("deleteId", messageId);
		
		if(getRequestType().equals(HttpRequestType.POST)) {
			if(request.getParameter("no") != null) {
				setRedirecting(true);
				
				try {
					getResponse().sendRedirect(URLUtil.getUrl("ticketing", "inbox.ws", true));
				} catch(IOException e) {
					LOG.error("IOException occurred while attempting to redirect the user from a cancelled ticket deletion.", e);
				}
				
				return false;
			}
			
			if(request.getParameter("yes") != null) {
				if(mainDao.deleteMessage(messageId)) {
					setRedirecting(true);
					
					try {
						getResponse().sendRedirect(URLUtil.getUrl("ticketing", "inbox.ws", true));
					} catch(IOException e) {
						LOG.error("IOException occurred while attempting to redirect the user to their inbox after a successful ticket deletion.", e);
					}
					
					return false;
				} else {
					setError(ERROR_GENERIC);
					return true;
				}
			}
		}
		
		return false;
	}
	
	private boolean prepareThread(int messageId) {
		if(!checkMessage(messageId)) {
			return false;
		}
		
		ThreadDTO thread = mainDao.getThread(messageId);
		if(thread == null) {
			setError(ERROR_NOT_FOUND);
			return true;
		}
		
		getRequest().setAttribute("thread", thread);
		
		return true;
	}
	
	/**
	 * Checks to see if a message exists, and is viewable by the user. 
	 * If the message is not found or can not be viewed, an error message is set that will be sent back to the view.
	 * @param messageId The ID of the message to look for.
	 * @return True if the message was found and can be viewed, false if it can not be viewed.
	 */
	private boolean checkMessage(int messageId) {
		int messageReturnCode = mainDao.messageExists(messageId);
		switch(messageReturnCode) {
			default:
			case TicketingDAO.MESSAGE_NOT_FOUND:
				setError(ERROR_NOT_FOUND);
				return false;
			case TicketingDAO.MESSAGE_UNAUTHORIZED:
				setError(ERROR_UNAUTHORIZED);
				return false;
			case TicketingDAO.MESSAGE_FOUND:
				return true;
		}
	}
	
	private void setError(String errorMsg) {
		getRequest().setAttribute("error", errorMsg);
	}
	
	private void prepareInbox() {
		InboxDTO inbox = mainDao.getInbox();
		
		if(inbox.getReceivedMessageList().size() > 0) {
			getRequest().setAttribute("receivedList", inbox.getReceivedMessageList());
		}
		if(inbox.getReadMessageList().size() > 0) {
			getRequest().setAttribute("readList", inbox.getReadMessageList());
		}
		if(inbox.getSentMessageList().size() > 0) {
			getRequest().setAttribute("sentList", inbox.getSentMessageList());
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
