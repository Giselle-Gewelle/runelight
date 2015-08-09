package org.runelight.controller;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.runelight.http.HttpRequestType;
import org.runelight.security.LoginSession;
import org.runelight.util.URLUtil;

public abstract class Controller {

	private static final Logger LOG = Logger.getLogger(Controller.class);
	
	private HttpServletRequest request;
	private HttpServletResponse response;
	private HttpRequestType requestType;
	private String requestIP;
	private long requestTime;
	private Connection dbConnection;
	private String mod;
	private String dest;
	
	private JSONObject jsonData;
	
	private LoginSession loginSession;
	
	private boolean redirecting;
	
	public void setup(HttpServletRequest request, HttpServletResponse response, HttpRequestType requestType, String requestIP, long requestTime, 
			Connection dbConnection, String mod, String dest) {
		this.request = request;
		this.response = response;
		this.requestType = requestType;
		this.requestIP = requestIP;
		this.requestTime = requestTime;
		this.dbConnection = dbConnection;
		this.mod = mod;
		this.dest = dest;
		this.redirecting = false;
		
		String queryString = request.getQueryString();
		if(queryString == null) {
			queryString = "";
		} else {
			queryString = "?" + queryString;
		}
		
		/*
		 * Check for an active login session.
		 */
		this.loginSession = new LoginSession(dbConnection, this, (String) request.getSession().getAttribute("sessionHash"));
		if(this.loginSession.isLoggedIn()) {
			request.getSession().setAttribute("sessionHash", this.loginSession.getUser().getSessionHash());
		} else {
			if(this.loginRequired()) {
				this.redirecting = true;
				
				try {
					response.sendRedirect(URLUtil.getUrl("main1", "loginform.ws?mod=" + mod + "&dest=" + dest + queryString, true));
				} catch(IOException e) {
					LOG.error("IOException occurred while attempting to redirect user to the loginform.", e);
				}
			}
		}
		
		request.setAttribute("loginSession", loginSession);
		request.setAttribute("currentMod", mod);
		request.setAttribute("currentDest", dest);
		request.setAttribute("currentQuery", queryString);
		
		this.jsonData = null;
	}
	
	public abstract void init();
	
	protected final void setJsonData(JSONObject jsonData) {
		this.jsonData = jsonData;
	}
	
	protected final void setRedirecting(boolean redirecting) {
		this.redirecting = redirecting;
	}
	
	/**
	 * Whether or not this page must be access through HTTPS (if SSL is enabled in the configuration file).
	 * @return True if it is secure, false if not.
	 */
	public boolean isSecure() {
		return false;
	}
	
	/**
	 * Whether or not to update/refresh a secure (HTTPS) login session.
	 * @return True if the session should be refreshed, false if it should expire after the given time runs out.
	 */
	public boolean holdSecureSession() {
		return true;
	}
	
	/**
	 * Whether or not the user must be logged in to view the current page.
	 * @return True if they must be logged in, false if not.
	 */
	public boolean loginRequired() {
		return false;
	}
	
	protected final HttpServletRequest getRequest() {
		return request;
	}
	
	protected final HttpServletResponse getResponse() {
		return response;
	}
	
	protected final HttpRequestType getRequestType() {
		return requestType;
	}
	
	public final String getRequestIP() {
		return requestIP;
	}
	
	public final long getRequestTime() {
		return requestTime;
	}
	
	protected final Connection getDbConnection() {
		return dbConnection;
	}
	
	public final String getMod() {
		return mod;
	}
	
	public final String getDest() {
		return dest;
	}
	
	public final LoginSession getLoginSession() {
		return loginSession;
	}
	
	public final boolean isRedirecting() {
		return redirecting;
	}
	
	public final JSONObject getJsonData() {
		return jsonData;
	}
	
}
