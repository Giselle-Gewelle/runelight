package org.runelight.controller.impl.account.management;

import org.runelight.controller.Controller;

public final class Ticketing extends Controller {

	@Override
	public void init() {
		
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
