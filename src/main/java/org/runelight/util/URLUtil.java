package org.runelight.util;

import javax.servlet.http.HttpServletRequest;

import org.runelight.Config;

public final class URLUtil {
	
	public static String getUrl(String mod, String dest) {
		return getUrl(mod, dest, false);
	}
	
	public static String getUrl(String mod, String dest, boolean secure) {
		return (secure && Config.isSslEnabled() ? "https" : "http") + "://" + ModUtil.modToSubdomain(mod) + "." + Config.getHostName() + "/" + dest;
	}
	
	public static int getIntParam(HttpServletRequest request, String name) {
		String paramStr = request.getParameter(name);
		if(paramStr == null || paramStr.length() < 1) {
			return -1;
		}
		
		if(paramStr.contains(".")) {
			return -1;
		}
		
		try {
			int param = Integer.parseInt(paramStr);
			if(param < 0) {
				return -1;
			}
			
			return param;
		} catch(NumberFormatException e) {
			return -1;
		}
	}
	
}
