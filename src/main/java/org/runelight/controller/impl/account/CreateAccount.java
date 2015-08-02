package org.runelight.controller.impl.account;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.runelight.Config;
import org.runelight.controller.Controller;
import org.runelight.http.HttpRequestType;
import org.runelight.util.CountryUtil;
import org.runelight.view.dto.account.CreateAccountDTO;

public final class CreateAccount extends Controller {

	private static final Logger LOG = Logger.getLogger(CreateAccount.class);
	
	private static Map<String, String> AGE_RANGE_MAP = new LinkedHashMap<String, String>() {

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
	
	private CreateAccountDTO data;
	
	@Override
	public void init() {
		// TODO flood/spam protection - triple layer:
		// 1. ip + date in mysql
		// 2. HttpSession
		// 3. Cookies
		
		if(getDest().equals("index.html")) {
			setMaps();
		} else {
			if(!getRequestType().equals(HttpRequestType.POST) && !getDest().equals("chooseagerange.ws")) {
				LOG.warn("User attempting to access an account creation section using the wrong request method.");
				
				try {
					sendHome();
				} catch (IOException e) {
					LOG.error("IOException occurred while attempting to redirect an invalid account creation request.", e);
				}
				
				return;
			}
			
			data = new CreateAccountDTO();
			
			switch(getDest()) {
				case "chooseagerange.ws":
					setMaps();
					validateAgeAndCountry();
					break;
			}
			
			getRequest().setAttribute("createData", data);
		}
	} 
	
	/**
	 * Validates the age range and country of residence fields, setting an error code for each if they are invalid.
	 * @return True if both are valid, false if one or both are invalid.
	 */
	private boolean validateAgeAndCountry() {
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
	 * @throws IOException
	 */
	private void sendHome() throws IOException {
		getResponse().sendRedirect((Config.isSslEnabled() ? "https" : "http") + "://create." + Config.getHostName() + "/index.html");
	}
	
	/**
	 * Sets the age range and country / country code maps for the initial two pages (index.html and chooseagerange.ws).
	 */
	private void setMaps() {
		getRequest().setAttribute("ageRangeMap", AGE_RANGE_MAP);
		getRequest().setAttribute("countryMap", CountryUtil.COUNTRY_MAP);
	}
	
	@Override
	public boolean isSecure() {
		return true;
	}

}
