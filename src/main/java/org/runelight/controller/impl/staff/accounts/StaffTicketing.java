package org.runelight.controller.impl.staff.accounts;

import java.util.List;

import org.runelight.controller.impl.staff.StaffPage;
import org.runelight.db.dao.account.TicketingDAO;
import org.runelight.dto.TicketDTO;
import org.runelight.dto.TicketQueueDTO;
import org.runelight.util.URLUtil;

/**
 * Controller for the Ticketing section of the Staff Center.
 * @author Giselle
 */
public final class StaffTicketing extends StaffPage {

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
		}
	}
	
	private void setupTicketDetails() {
		int id = URLUtil.getIntParam(getRequest(), "id");
		if(id < 1) {
			return;
		}
		
		TicketDTO ticket = dao.getTicket(id);
		if(ticket != null) {
			getRequest().setAttribute("ticket", ticket);
		}
	}
	
	private void setupTicketQueue() {
		List<TicketQueueDTO> tickets = dao.getOpenTickets();
		if(tickets != null) {
			getRequest().setAttribute("ticketList", tickets);
		}
	}

}
