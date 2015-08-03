package org.runelight.controller.impl.account.password;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.runelight.controller.Controller;
import org.runelight.http.HttpRequestType;
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
			
			
		}
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
