package org.runelight.controller.impl.account.sessions;

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
			toMod = "main1";
			toDest = "title.ws";
		}
		
		String toQuery = "";
		if(toDest.indexOf('?') != -1) {
			toQuery = toDest.substring(toDest.indexOf('?'));
			toDest = toDest.substring(0, toDest.indexOf('?'));
		}
		
		if(!RequestHandler.CONTROLLER_MAP.containsKey(toMod + " " + toDest)) {
			toMod = "main1";
			toDest = "title.ws";
		}
		
		if(toDest.equals("login.ws") || toDest.equals("loginform.ws") || toDest.equals("logout.ws")) {
			toMod = "main1";
			toDest = "title.ws";
		}
		
		String toSubdomain = ModUtil.modToSubdomain(toMod);
		String toFullDest = (RequestHandler.SECURE_MODS.contains(toMod) && Config.isSslEnabled() ? "https" : "http") + "://" + toSubdomain + "." + Config.getHostName() + "/" + toDest + toQuery;

		LOG.info("mod = " + toMod + ", dest = " + toDest + ", query = " + toQuery);
		LOG.info("fullDest = " + toFullDest);
		
		request.setAttribute("toMod", toMod);
		request.setAttribute("toDest", toDest);
		request.setAttribute("toSubdomain", toSubdomain);
		request.setAttribute("toFullDest", toFullDest);
	}
	
	@Override
	public boolean isSecure() {
		return true;
	}

}
