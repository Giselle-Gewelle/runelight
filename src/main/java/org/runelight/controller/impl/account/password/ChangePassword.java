package org.runelight.controller.impl.account.password;

import org.runelight.controller.Controller;

public final class ChangePassword extends Controller {

	@Override
	public void init() {
		
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
