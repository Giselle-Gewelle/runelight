package org.runelight.controller.impl.account.sessions;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.runelight.controller.Controller;
import org.runelight.db.dao.account.LoginSessionDAO;
import org.runelight.util.URLUtil;

public final class LogoutAttempt extends Controller {

	private static final Logger LOG = Logger.getLogger(LogoutAttempt.class);
	
	@Override
	public void init() {
		if(getLoginSession().isLoggedIn()) {
			LoginSessionDAO.killSession(getDbConnection(), getLoginSession().getUser().getSessionId());
		}
		
		setRedirecting(true);
		
		try {
			getResponse().sendRedirect(URLUtil.getUrl("main1", "title.ws"));
		} catch(IOException e) {
			LOG.error("IOException occurred while attempting to redirect the user to the title page after logging out.", e);
		}
	}
	
	@Override
	public boolean isSecure() {
		return true;
	}

}
