package org.runelight.controller.impl.staff.accounts;

import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;
import org.runelight.controller.impl.staff.StaffPage;
import org.runelight.db.dao.account.TicketingDAO;
import org.runelight.dto.TicketDTO;
import org.runelight.dto.TicketQueueDTO;
import org.runelight.http.HttpRequestType;
import org.runelight.util.StringUtil;
import org.runelight.util.URLUtil;

/**
 * Controller for the Ticketing section of the Staff Center.
 * @author Giselle
 */
public final class StaffTicketing extends StaffPage {

	private static final Logger LOG = Logger.getLogger(StaffTicketing.class);
	
	TicketingDAO dao;
	
	@Override
	public void init() {
		super.init();
		
		if(!isAuthorized()) {
			return;
		}
		
		dao = new TicketingDAO(getDbConnection(), getLoginSession().getUser());
		
		switch(getDest()) {
			case "accounts/tickets/queue.ws":
				setupTicketQueue();
				break;
			case "accounts/tickets/details.ws":
				setupTicketDetails();
				break;
			case "accounts/tickets/reply.ws":
				setupReply();
				break;
			case "accounts/tickets/delete.ws":
				setupDelete();
				break;
		}
	}
	
	private void setupDelete() {
		TicketDTO ticket = setupTicketDetails();
		if(ticket == null) {
			return;
		}
		
		if(getRequestType().equals(HttpRequestType.POST)) {
			String cancel = getRequest().getParameter("cancel");
			if(cancel != null) {
				setRedirecting(true);
				
				try {
					getResponse().sendRedirect(URLUtil.getUrl("staff", "accounts/tickets/details.ws?id=" + ticket.getId(), true));
				} catch(IOException e) {
					LOG.error("IOException occurred while attempting to redirect a staff member from the ticket deletion page back to the ticket details page.", e);
				}
				
				return;
			}
			
			String submit = getRequest().getParameter("submit");
			if(submit == null) {
				getRequest().setAttribute("error", true);
				return;
			}
			
			dao.setTicketActioned(ticket.getId());
			
			try {
				getResponse().sendRedirect(URLUtil.getUrl("staff", "accounts/tickets/queue.ws?delete=true", true));
			} catch(IOException e) {
				LOG.error("IOException occurred while attempting to redirect a staff member from the ticket deletion page back to the ticket queue page.", e);
			}
		}
	}
	
	private void setupReply() {
		TicketDTO ticket = setupTicketDetails();
		if(ticket == null) {
			return;
		}
		
		if(getRequestType().equals(HttpRequestType.POST)) {
			getRequest().setAttribute("submitted", true);
			
			String message = getRequest().getParameter("inputMessage");
			
			if(message == null) {
				return;
			}
			
			message = message.trim();
			
			if(message.equals("")) {
				return;
			}
			
			if(message.length() > 50000) {
				return;
			}
			
			String canReplyStr = getRequest().getParameter("inputCanReply");
			boolean canReply = false;
			if(canReplyStr != null && canReplyStr.equals("yes")) {
				canReply = true;
			}
			
			TicketingDAO ticketingDAO = new TicketingDAO(getDbConnection(), getLoginSession().getUser());
			if(ticketingDAO.sendMessage(true, ticket.getTopicId(), "Re: " + ticket.getTitle(), 2, message, StringUtil.deFormatUsername(ticket.getAuthorName()), canReply, false)) {
				ticketingDAO.setTicketActioned(ticket.getId());
				getRequest().setAttribute("successful", true);
			}
		}
	}
	
	private TicketDTO setupTicketDetails() {
		int id = URLUtil.getIntParam(getRequest(), "id");
		if(id < 1) {
			return null;
		}
		
		TicketDTO ticket = dao.getTicket(id);
		if(ticket != null) {
			getRequest().setAttribute("ticket", ticket);
		}
		
		return ticket;
	}
	
	private void setupTicketQueue() {
		List<TicketQueueDTO> tickets = dao.getOpenTickets();
		if(tickets.size() > 0) {
			getRequest().setAttribute("ticketList", tickets);
		}
		
		if(getRequest().getParameter("delete") != null && getRequest().getParameter("delete").equals("true")) {
			getRequest().setAttribute("ticketDeleted", true);
		}
	}

}
