package org.runelight.controller.impl.account.password;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.runelight.controller.Controller;
import org.runelight.db.dao.account.AccountManagementDAO;
import org.runelight.http.HttpRequestType;
import org.runelight.security.Password;
import org.runelight.util.URLUtil;

public final class ChangePassword extends Controller {

	private static final Logger LOG = Logger.getLogger(ChangePassword.class);
	
	@Override
	public void init() {
		if(getDest().equals("password.ws")) {
			if(!getRequestType().equals(HttpRequestType.POST)) {
				setRedirecting(true);
				
				try {
					getResponse().sendRedirect(URLUtil.getUrl("password_history", "passchange.html", true));
				} catch(IOException e) {
					LOG.error("IOException occurred while attempting to redirect the user back to the main password change form.", e);
				}
				
				return;
			}
			
			int returnCode = validate();
			if(returnCode > -1) {
				getRequest().setAttribute("errorCode", returnCode);
			}
		}
	}
	
	private int validate() {
		String current = getRequest().getParameter("current");
		String password1 = getRequest().getParameter("password1");
		String password2 = getRequest().getParameter("password2");
		
		if(current == null || password1 == null || password2 == null) {
			return 0;
		}
		if(current.trim().equals("") || password1.trim().equals("") || password2.trim().equals("")) {
			return 0;
		}
		
		if(password1.length() < 5 || password1.length() > 20) {
			return 2;
		}
		
		if(!password1.equals(password2)) {
			return 1;
		}
		
		String regex = "^[a-zA-Z0-9]{5,20}$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(password1);
		if(!matcher.find()) {
			return 3;
		}
		
		Password currentPassword = AccountManagementDAO.getUserPassword(getDbConnection(), getLoginSession().getUser().getAccountId());
		if(currentPassword == null) {
			return 7;
		}
		
		if(!currentPassword.equals(current)) {
			return 4;
		}
		
		if(currentPassword.equals(password1)) {
			return 5;
		}
		
		if(Password.passwordContainsUsername(getLoginSession().getUser().getUsername(), password1)) {
			return 6;
		}
		
		Password newPassword = new Password(password1);
		if(!AccountManagementDAO.changePassword(getDbConnection(), getLoginSession().getUser().getAccountId(), getRequestIP(), currentPassword, newPassword)) {
			return 7;
		}
		
		return -1;
	}
	
	@Override
	public boolean isSecure() {
		return true;
	}
	
	@Override
	public boolean holdSecureSession() {
		return false;
	}
	
	@Override
	public boolean loginRequired() {
		return true;
	}

}
