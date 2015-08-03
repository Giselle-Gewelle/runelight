package org.runelight.security;

import java.sql.Connection;
import java.util.Calendar;

import org.apache.log4j.Logger;
import org.runelight.controller.Controller;
import org.runelight.db.dao.account.LoginSessionDAO;
import org.runelight.util.DateUtil;
import org.runelight.view.dto.account.SessionCheckDTO;
import org.runelight.view.dto.account.UserSessionDTO;

public final class LoginSession {
	
	private static final Logger LOG = Logger.getLogger(LoginSession.class);
	
	/**
	 * The length of time a login session will wait before becoming inactive, in MINUTES.
	 */
	public static final int IDLE_TIME = 60;
	
	private UserSessionDTO user;
	private boolean loggedIn;
	
	public LoginSession(Connection dbConnection, Controller viewController, String hash) {
		this.user = null;
		this.loggedIn = false;
		
		if(hash == null || hash.length() != 128) {
			return;
		}
		
		Calendar minCal = Calendar.getInstance();
		minCal.add(Calendar.MINUTE, -IDLE_TIME);
		
		SessionCheckDTO sessionCheck = LoginSessionDAO.findSession(dbConnection, hash, viewController.getRequestIP(), minCal.getTime());
		if(sessionCheck == null) {
			return;
		}
		
		if(viewController.isSecure() && !sessionCheck.isSecure()) {
			LOG.info("User attempting to access a secure website section using an insecure login session. Killing their session...");
			LoginSessionDAO.killSession(dbConnection, sessionCheck.getSessionId());
			return;
		}
		
		String endDate = sessionCheck.getEndDate();
		if(!sessionCheck.isSecure()) {
			Calendar newEndCal = Calendar.getInstance();
			newEndCal.add(Calendar.MINUTE, IDLE_TIME);
			endDate = DateUtil.SQL_DATETIME_FORMAT.format(newEndCal.getTime());
		}
		
		this.user = LoginSessionDAO.getLoginSessionDetails(dbConnection, sessionCheck.getSessionId(), viewController.isSecure(), viewController.getMod(), viewController.getDest(), endDate);
		if(this.user != null) {
			this.loggedIn = true;
		}
	}
	
	public UserSessionDTO getUser() {
		return user;
	}
	
	public boolean isLoggedIn() {
		return loggedIn;
	}
	
}
