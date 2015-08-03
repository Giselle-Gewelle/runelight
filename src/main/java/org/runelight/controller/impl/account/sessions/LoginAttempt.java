package org.runelight.controller.impl.account.sessions;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.runelight.Config;
import org.runelight.controller.Controller;
import org.runelight.db.dao.account.LoginSessionDAO;
import org.runelight.http.HttpRequestType;
import org.runelight.http.RequestHandler;
import org.runelight.security.LoginSession;
import org.runelight.util.ModUtil;
import org.runelight.util.URLUtil;
import org.runelight.view.dto.account.UserDTO;

public final class LoginAttempt extends Controller {

	private static final Logger LOG = Logger.getLogger(LoginAttempt.class);
	
	private String toMod;
	private String toDest;
	private String toQuery;
	
	@Override
	public void init() {
		if(flooding()) {
			getRequest().setAttribute("flooding", true);
			return;
		}
		
		int loginCode = checkLogin();
		if(loginCode != -1) {
			getRequest().setAttribute("error", loginCode);
		} else {
			try {
				getResponse().sendRedirect(URLUtil.getUrl(toMod, toDest + toQuery, RequestHandler.SECURE_MODS.contains(toMod)));
			} catch (IOException e) {
				LOG.error("IOException occurred while attempting to redirect the newly logged-in user to their destination.", e);
			}	
		}
	}
	
	/**
	 * Checks to see if the user is making too many login attempts.
	 * @return True if too many login attempts are detected, false if not.
	 */
	protected boolean flooding() {
		Calendar shortCal = Calendar.getInstance();
		shortCal.add(Calendar.MINUTE, -5);
		int maxShort = 5;

		int shortAttempts = LoginSessionDAO.getLoginAttemptsForTimeFrame(getDbConnection(), getRequestIP(), shortCal.getTime(), maxShort);
		if(shortAttempts > maxShort) {
			return true;
		}
		
		Calendar longCal = Calendar.getInstance();
		longCal.add(Calendar.MINUTE, -30);
		int maxLong = 15;
		
		int longAttempts = LoginSessionDAO.getLoginAttemptsForTimeFrame(getDbConnection(), getRequestIP(), longCal.getTime(), maxLong);
		if(longAttempts > maxLong) {
			return true;
		}
		
		return false;
	}
	
	/**
	 * Checks if the user's login attempt is valid or not, and submits their session to the database if it is indeed valid.
	 * @return -1 on success, or an integer greater than -1 on failure (corresponding to the appropriate error code/message).
	 */
	protected int checkLogin() {
		HttpServletRequest request = getRequest();
		
		if(!getRequestType().equals(HttpRequestType.POST)) {
			return 0;
		}
		
		if(!validDest()) {
			return 0;
		}
		
		String username = request.getParameter("username");
		if(!validInput(username, "^[a-z0-9_]{1,12}$")) {
			return 1;
		}
		
		String password = request.getParameter("password");
		if(!validInput(password, "^[a-zA-Z0-9]{5,20}$")) {
			return 2;
		}
		
		UserDTO user = LoginSessionDAO.getUserInfo(getDbConnection(), username, new Date(getRequestTime()), getRequestIP());
		if(user == null) {
			return 2;
		}
		
		if(!user.getPassword().equals(password)) {
			return 2;
		}
		
		user.setCurrentIP(getRequestIP());
		
		Calendar startCal = Calendar.getInstance();
		startCal.setTimeInMillis(getRequestTime());
		Calendar endCal = Calendar.getInstance();
		endCal.setTimeInMillis(getRequestTime());
		endCal.add(Calendar.MINUTE, LoginSession.IDLE_TIME);
		
		String sessionHash = LoginSessionDAO.submitLoginSession(getDbConnection(), user, startCal.getTime(), endCal.getTime(), toMod, toDest);
		request.getSession().setAttribute("sessionHash", sessionHash);
		
		return -1;
	}
	
	/**
	 * Validates a login form input field.
	 * @param input The value of the field to check.
	 * @param regex The regex that the value must match.
	 * @return True if valid, false if not.
	 */
	protected boolean validInput(String input, String regex) {
		if(input == null || input.equals("")) {
			return false;
		}
		
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(input);
		if(!matcher.find()) {
			return false;
		}
		
		return true;
	}
	
	/**
	 * Validates the user's requested destination, breaking it back up into its core components.
	 * @return True if the requested destination is a valid one, false if not.
	 */
	protected boolean validDest() {
		HttpServletRequest request = getRequest();
		
		String fullDest = request.getParameter("dest");
		if(fullDest == null) {
			return false;
		}
		
		fullDest = fullDest.trim();
		if(fullDest.equals("")) {
			return false;
		}
		
		int protocolIdx = fullDest.indexOf("://");
		if(protocolIdx == -1) {
			return false;
		}
		
		String string = fullDest.substring(protocolIdx + 3);
		
		int subdomainIdx = string.indexOf('.');
		if(subdomainIdx == -1) {
			return false;
		}
		
		String subdomain = string.substring(0, subdomainIdx);
		string = string.substring(subdomainIdx + 1);
		
		int hostNameIdx = string.indexOf(Config.getHostName());
		if(hostNameIdx == -1) {
			return false;
		}
		
		string = string.substring(hostNameIdx + Config.getHostName().length());
		
		int destIdx = string.indexOf('/');
		if(destIdx != 0) {
			return false;
		}
		
		String dest = string.substring(1);

		String query = "";
		int queryIdx = dest.indexOf('?');
		if(queryIdx > -1) {
			query = dest.substring(queryIdx);
			dest = dest.substring(0, queryIdx);
		}
		
		String mod = ModUtil.subdomainToMod(subdomain);
		
		if(dest.equals("login.ws") || dest.equals("loginform.ws") || dest.equals("logout.ws")) {
			return false;
		}
		
		if(!RequestHandler.CONTROLLER_MAP.containsKey(mod + " " + dest)) {
			return false;
		}
		
		this.toMod = mod;
		this.toDest = dest;
		this.toQuery = query;
		
		request.setAttribute("requestedMod", mod);
		request.setAttribute("requestedDest", dest);
		return true;
	}
	
	@Override
	public boolean isSecure() {
		return true;
	}

}
