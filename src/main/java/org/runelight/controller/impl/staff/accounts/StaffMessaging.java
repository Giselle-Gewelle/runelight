package org.runelight.controller.impl.staff.accounts;

import org.runelight.controller.impl.staff.StaffPage;
import org.runelight.db.dao.account.TicketingDAO;
import org.runelight.db.dao.staff.StaffAccountsDAO;
import org.runelight.dto.AccountDetailsDTO;
import org.runelight.http.HttpRequestType;
import org.runelight.util.URLUtil;

public final class StaffMessaging extends StaffPage {

	@Override
	public void init() {
		super.init();
		
		if(!isAuthorized()) {
			return;
		}
		
		AccountDetailsDTO account = getAccount();
		if(account != null) {
			if(getRequestType().equals(HttpRequestType.POST)) {
				getRequest().setAttribute("submitted", true);
				getRequest().setAttribute("successful", submit(account));
			}
		}
	}
	
	private boolean submit(AccountDetailsDTO receiver) {
		String title = getRequest().getParameter("inputTitle");
		String message = getRequest().getParameter("inputMessage");
		
		if(title == null || message == null) {
			return false;
		}
		
		title = title.trim();
		message = message.trim();
		
		if(title.equals("") || message.equals("")) {
			return false;
		}
		
		if(title.length() > 50 || message.length() > 50000) {
			return false;
		}
		
		String canReplyStr = getRequest().getParameter("inputCanReply");
		boolean canReply = false;
		if(canReplyStr != null && canReplyStr.equals("yes")) {
			canReply = true;
		}
		
		TicketingDAO ticketingDAO = new TicketingDAO(getDbConnection(), getLoginSession().getUser());
		return ticketingDAO.sendMessage(false, 0, title, 1, message, receiver.getUsername(), canReply, false);
	}
	
	private AccountDetailsDTO getAccount() {
		int accountId = URLUtil.getIntParam(getRequest(), "user");
		if(accountId < 1) {
			return null;
		}
		
		AccountDetailsDTO details = StaffAccountsDAO.getAccountDetails(getDbConnection(), accountId);
		if(details != null) {
			getRequest().setAttribute("account", details);
			return details;
		}
		
		return null;
	}
	
}
