package org.runelight.controller;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.runelight.http.HttpRequestType;

public abstract class Controller {

	private HttpServletRequest request;
	private HttpServletResponse response;
	private HttpRequestType requestType;
	private String requestIP;
	private long requestTime;
	private Connection dbConnection;
	private String mod;
	private String dest;
	
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
	}
	
	public abstract void init();
	
	protected final void setRedirecting(boolean redirecting) {
		this.redirecting = redirecting;
	}
	
	public boolean isSecure() {
		return false;
	}
	
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
	
	protected final String getRequestIP() {
		return requestIP;
	}
	
	protected final long getRequestTime() {
		return requestTime;
	}
	
	protected final Connection getDbConnection() {
		return dbConnection;
	}
	
	protected final String getMod() {
		return mod;
	}
	
	protected final String getDest() {
		return dest;
	}
	
	public final boolean isRedirecting() {
		return redirecting;
	}
	
}
