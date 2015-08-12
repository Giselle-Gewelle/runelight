package org.runelight.controller.impl.staff.accounts;

import org.runelight.controller.impl.staff.StaffPage;
import org.runelight.db.dao.staff.StaffAccountsDAO;
import org.runelight.util.URLUtil;
import org.runelight.view.dto.staff.AccountDetailsDTO;

public final class StaffMessaging extends StaffPage {

	@Override
	public void init() {
		super.init();
		
		if(!isAuthorized()) {
			return;
		}
		
		AccountDetailsDTO account = getAccount();
		if(account != null) {
			
		}
	}
	
	public AccountDetailsDTO getAccount() {
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
