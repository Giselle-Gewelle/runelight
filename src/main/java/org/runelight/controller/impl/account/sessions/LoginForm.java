package org.runelight.controller.impl.account.sessions;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.runelight.Config;
import org.runelight.controller.Controller;
import org.runelight.http.RequestHandler;
import org.runelight.util.ModUtil;

public final class LoginForm extends Controller {

	private static final Logger LOG = Logger.getLogger(LoginForm.class);
	
	@Override
	public void init() {
		HttpServletRequest request = getRequest();
		
		String toMod = request.getParameter("mod");
		String toDest = request.getParameter("dest");
		
		if(toMod == null || toDest == null) {
			sendHome();
			return;
		}
		
		String toQuery = "";
		if(toDest.indexOf('?') != -1) {
			toQuery = toDest.substring(toDest.indexOf('?'));
			toDest = toDest.substring(0, toDest.indexOf('?'));
		}
		
		if(!RequestHandler.CONTROLLER_MAP.containsKey(toMod + " " + toDest)) {
			sendHome();
			return;
		}
		
		if(toDest.equals("login.ws") || toDest.equals("loginform.ws") || toDest.equals("logout.ws")) {
			sendHome();
			return;
		}
		
		String toSubdomain = ModUtil.modToSubdomain(toMod);
		String toFullDest = (RequestHandler.SECURE_MODS.contains(toMod) && Config.isSslEnabled() ? "https" : "http") + "://" + toSubdomain + "." + Config.getHostName() + "/" + toDest + toQuery;
		
		request.setAttribute("toMod", toMod);
		request.setAttribute("toDest", toDest);
		request.setAttribute("toSubdomain", toSubdomain);
		request.setAttribute("toFullDest", toFullDest);
	}
	
	private void sendHome() {
		setRedirecting(true);
		
		try {
			getResponse().sendRedirect("http://www." + Config.getHostName() + "/title.ws");
		} catch(IOException e) {
			LOG.error("IOException while attempting to send the user back to the main page from an invalid loginform mod/dest.", e);
		}
	}
	
	@Override
	public boolean isSecure() {
		return true;
	}

}
