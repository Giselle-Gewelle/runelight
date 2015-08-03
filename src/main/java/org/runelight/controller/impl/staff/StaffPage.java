package org.runelight.controller.impl.staff;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.runelight.controller.Controller;
import org.runelight.util.URLUtil;

public class StaffPage extends Controller {

	private static final Logger LOG = Logger.getLogger(StaffPage.class);
	
	private boolean authorized;
	
	@Override
	public void init() {
		if(!getLoginSession().getUser().isStaff()) {
			setRedirecting(true);
			
			try {
				getResponse().sendRedirect(URLUtil.getUrl("staff", "notallowed.ws"));
			} catch(IOException e) {
				LOG.error("IOException occurred while attempting to redirect an unauthorized user away from a staff-only page.", e);
			}
			
			authorized = false;
		} else {
			authorized = true;
		}
	}
	
	protected final boolean isAuthorized() {
		return authorized;
	}
	
	@Override
	public boolean isSecure() {
		return true;
	}
	
	@Override
	public boolean loginRequired() {
		return true;
	}

}
