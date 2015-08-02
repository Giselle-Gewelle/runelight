package org.runelight.controller.impl.account;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.runelight.Config;
import org.runelight.controller.Controller;
import org.runelight.db.dao.account.CreateAccountDAO;
import org.runelight.http.HttpRequestType;
import org.runelight.security.Password;
import org.runelight.util.CountryUtil;
import org.runelight.util.StringUtil;
import org.runelight.view.dto.account.CreateAccountDTO;

public final class CreateAccount extends Controller {

	private static final Logger LOG = Logger.getLogger(CreateAccount.class);
	
	private static final int MAX_ATTEMPTS_PER_HALF_HOUR = 3;
	
	private static final Map<String, String> AGE_RANGE_MAP = new LinkedHashMap<String, String>() {

		private static final long serialVersionUID = 1L;
		
		{
			put("0", "12 and under");
			put("1", "13-17");
			put("2", "18-24");
			put("3", "25-29");
			put("4", "30-39");
			put("5", "40-49");
			put("6", "50-59");
			put("7", "60 plus");
		}
		
	};
	
	private CreateAccountDTO data = new CreateAccountDTO();
	
	@Override
	public void init() {
		if(flooding()) {
			tooManyAttempts();
			return;
		}
		
		if(getDest().equals("index.html")) {
			setMaps();
		} else {
			if(!getRequestType().equals(HttpRequestType.POST) && !getDest().equals("chooseagerange.ws")) {
				LOG.warn("User attempting to access an account creation section using the wrong request method.");
				sendHome();
				return;
			}
			
			switch(getDest()) {
				case "chooseagerange.ws":
					setMaps();
					validateAgeAndCountry();
					break;
				case "chooseusername.ws":
					if(!validateAgeAndCountry()) {
						sendHome();
						return;
					}
					
					int usernameReturnCode = validateUsername();
					if(usernameReturnCode > -1) {
						getRequest().setAttribute("usernameError", usernameReturnCode);
					}
					break;
				case "choosepassword.ws":
					if(!validateAgeAndCountry() || validateUsername() != -1) {
						sendHome();
						return;
					}
					
					if(!validateTerms()) {
						getRequest().setAttribute("termsError", true);
					}
					break;
				case "createaccount.ws":
					if(!validateAgeAndCountry() || validateUsername() != -1 || !validateTerms()) {
						sendHome();
						return;
					}
					
					int passwordReturnCode = validatePasswords();
					if(passwordReturnCode > -1) {
						getRequest().setAttribute("passwordError", passwordReturnCode);
					} else {
						getRequest().getSession().setAttribute("latestCreation", Calendar.getInstance());
					}
					break;
			}
			
			getRequest().setAttribute("createData", data);
		}
	}
	
	/**
	 * Checks to see if the requester is creating too many user accounts in quick succession. 
	 * @return True if they are detected to be creating too many, false if not.
	 */
	private boolean flooding() {
		Calendar smallCal = Calendar.getInstance();
		Calendar largeCal = Calendar.getInstance();
		smallCal.add(Calendar.MINUTE, -5);
		largeCal.add(Calendar.MINUTE, -30);
		
		try {
			Calendar latestCreation = (Calendar) getRequest().getSession().getAttribute("latestCreation");
			if(latestCreation.after(smallCal)) {
				return true;
			}
		} catch(Throwable t) {}
		
		if(CreateAccountDAO.tooManyAttempts(getDbConnection(), getRequestIP(), smallCal.getTime(), largeCal.getTime(), MAX_ATTEMPTS_PER_HALF_HOUR)) {
			return true;
		}
		
		return false;
	}
	
	/**
	 * Submits the data entered by the user to create a new user account. 
	 * @param passwordStr The password to assign to the account.
	 * @return -1 on success, 6 on failure (generally means a SQL error).
	 */
	private int submitAccountCreation(String passwordStr) {
		Date date = new Date();
		Password password = new Password(passwordStr);
		
		try {
			if(!CreateAccountDAO.createAccount(getDbConnection(), data.getUsername(), password.getHash(), password.getSalt(), Integer.parseInt(data.getAgeRange()), 
					Integer.parseInt(data.getCountryCode()), date, getRequestIP())) {
				return 6;
			}
		} catch(NumberFormatException e) {
			LOG.error("NumberFormatException while attempting to create user account.", e);
			return 6;
		}
		
		return -1;
	}
	
	/**
	 * Validates the password fields and privacy policy agreement.
	 * @return -1 if the passwords are valid, or something greater than -1 if invalid 
	 * (used to determine which error string to pull, which are all stored in the FTL).
	 */
	protected int validatePasswords() {
		String password1 = getRequest().getParameter("password1");
		if(password1 == null || password1.equals("")) {
			return 0;
		}
		
		int password1Length = password1.length();
		if(password1Length < 5 || password1Length > 20) {
			return 1;
		}
		
		String password2 = getRequest().getParameter("password2");
		if(password2 == null || !password2.equals(password1)) {
			return 2;
		}
		
		String regex = "^[a-zA-Z0-9]{5,20}$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(password1);
		if(!matcher.find()) {
			return 3;
		}
		
		if(Password.passwordContainsUsername(data.getUsername(), password1)) {
			return 4;
		}
		
		String privacyStr = getRequest().getParameter("agree_privacy");
		if(privacyStr == null || !privacyStr.equals("on")) {
			return 5;
		}
		
		return submitAccountCreation(password1);
	}
	
	/**
	 * Validates the terms+conditions agreement.
	 * @return True if the user has agreed, false if not.
	 */
	protected boolean validateTerms() {
		String termsStr = getRequest().getParameter("agree_terms");
		boolean success = true;
		
		if(termsStr == null || !termsStr.equals("on")) {
			success = false;
		}
		
		return success;
	}
	
	/**
	 * Validates the username field.
	 * @return -1 if the username is valid, or something greater than -1 if invalid 
	 * (used to determine which error string to pull, which are all stored in the FTL).
	 */
	protected int validateUsername() {
		String username = getRequest().getParameter("username");
		if(username == null) {
			return 0;
		}

		/*
		 * Replace any leading and trailing spaces (underscores).
		 */
		username = username.replace("_", " ").trim();
		if(username.equals("")) {
			return 0;
		}
		
		username = username.toLowerCase();
		int length = username.length();
		
		if(length < 1) {
			return 0;
		}
		
		if(length > 12) {
			return 1;
		}
		
		/*
		 * Replace all spaces with underscores, and make sure there are no double-underscores.
		 */
		username = username.replace(" ", "_");
		while(username.contains("__")) {
			username = username.replace("__", "_");
		}
		
		/*
		 * Make sure the username is using valid characters.
		 */
		String regex = "^[a-z0-9_]{1,12}$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(username);
		if(!matcher.find()) {
			return 1;
		}
		
		// TODO check if username violates T+C. Check for offensive names, etc...
		
		/*
		 * Now make sure the username isn't already in use by another user.
		 */
		int usernameCheckCode = CreateAccountDAO.usernameExists(getDbConnection(), username);
		if(usernameCheckCode == -1) {
			// SQL Error
			return 3;
		}
		if(usernameCheckCode == 1) {
			// Username already exists!
			// TODO username suggestions
			return 2;
		}
		
		data.setUsername(username);
		data.setFormattedUsername(StringUtil.formatUsername(username));
		return -1;
	}
	
	/**
	 * Validates the age range and country of residence fields, setting an error code for each if they are invalid.
	 * @return True if both are valid, false if one or both are invalid.
	 */
	protected boolean validateAgeAndCountry() {
		String ageStr = getRequest().getParameter("age");
		String countryStr = getRequest().getParameter("country");
		
		boolean ageError = false;
		boolean countryError = false;
		
		if(ageStr == null) {
			ageError = true;
		}
		
		if(countryStr == null) {
			countryError = true;
		}
		
		if(!ageError && !countryError) {
			if(!AGE_RANGE_MAP.containsKey(ageStr)) {
				ageError = true;
			}
			
			if(!CountryUtil.validCountry(countryStr)) {
				countryError = true;
			}
		}
		
		if(!ageError) {
			data.setAgeRange(ageStr);
		}
		if(!countryError) {
			data.setCountryCode(countryStr);
		}
		
		getRequest().setAttribute("ageError", ageError);
		getRequest().setAttribute("countryError", countryError);
		return (!ageError && !countryError);
	}
	
	/**
	 * Sends a redirect back to the client for the account creation index.
	 */
	private void sendHome() {
		try {
			getResponse().sendRedirect((Config.isSslEnabled() ? "https" : "http") + "://create." + Config.getHostName() + "/index.html");
		} catch(IOException e) {
			LOG.error("IOException while attempting to send the user back to the account creation index.", e);
		}
	}
	
	/**
	 * Sends the user off to the "too many recent account creation attempts" page.
	 */
	private void tooManyAttempts() {
		try {
			getResponse().sendRedirect((Config.isSslEnabled() ? "https" : "http") + "://create." + Config.getHostName() + "/toomanyattempts.ws");
		} catch(IOException e) {
			LOG.error("IOException while attempting to send the user to the 'too many account creation attempts' page.", e);
		}
	}
	
	/**
	 * Sets the age range and country / country code maps for the initial two pages (index.html and chooseagerange.ws).
	 */
	private void setMaps() {
		getRequest().setAttribute("ageRangeMap", AGE_RANGE_MAP);
		getRequest().setAttribute("countryMap", CountryUtil.COUNTRY_MAP);
	}
	
	protected CreateAccountDTO getData() {
		return data;
	}
	
	@Override
	public boolean isSecure() {
		return true;
	}

}
