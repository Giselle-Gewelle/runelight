package org.runelight.controller.impl.account.sessions;

import javax.servlet.http.HttpServletRequest;

import org.runelight.controller.Controller;

public final class LoginAttempt extends Controller {

	@Override
	public void init() {
		checkLogin();
	}
	
	private void checkLogin() {
		HttpServletRequest request = getRequest();
		
		String currentMod = getMod();
		
		String toDest = request.getParameter("dest");
	}
	
	@Override
	public boolean isSecure() {
		return true;
	}

}
