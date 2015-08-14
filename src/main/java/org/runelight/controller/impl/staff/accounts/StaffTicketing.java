package org.runelight.controller.impl.staff.accounts;

import java.util.List;

import org.runelight.controller.impl.staff.StaffPage;
import org.runelight.db.dao.account.TicketingDAO;
import org.runelight.view.dto.staff.ticketing.TicketQueueDTO;

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
			case "accounts/ticketqueue.ws":
				setupTicketQueue();
				break;
		}
	}
	
	private void setupTicketQueue() {
		List<TicketQueueDTO> tickets = dao.getOpenTickets();
		if(tickets != null) {
			getRequest().setAttribute("ticketList", tickets);
		}
	}

}
