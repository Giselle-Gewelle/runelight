package org.runelight.controller.impl.account.sessions;

import java.io.IOException;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.runelight.Config;
import org.runelight.controller.Controller;
import org.runelight.http.HttpRequestType;
import org.runelight.http.RequestHandler;
import org.runelight.security.LoginSession;
import org.runelight.util.ModUtil;

public final class LoginAttempt extends Controller {

	private static final Logger LOG = Logger.getLogger(LoginAttempt.class);
	
	private String toMod;
	private String toDest;
	private String toQuery;
	
	@Override
	public void init() {
		// TODO flood/brute-force checking - HttpSession, and SQL(?)
		
		int loginCode = checkLogin();
		if(loginCode != -1) {
			getRequest().setAttribute("error", loginCode);
		} else {
			try {
				getResponse().sendRedirect((Config.isSslEnabled() && RequestHandler.SECURE_MODS.contains(toMod) ? "https" : "http") + 
						"://" + ModUtil.modToSubdomain(toMod) + "." + Config.getHostName() + "/" + toDest + toQuery);
			} catch (IOException e) {
				LOG.error("IOException occurred while attempting to redirect the newly logged-in user to their destination.", e);
			}	
		}
	}
	
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
		
		Calendar startCal = Calendar.getInstance();
		startCal.setTimeInMillis(getRequestTime());
		Calendar endCal = Calendar.getInstance();
		endCal.setTimeInMillis(getRequestTime());
		endCal.add(Calendar.MINUTE, LoginSession.IDLE_TIME);
		
		// TODO submit the session! :D
		
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
