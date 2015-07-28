package org.runelight.util;

public final class ModUtil {
	
	public static String subdomainToMod(String subdomain) {
		String mod = subdomain.replace("-", "_");
		if(mod.equals("www")) {
			mod = "main1";
		}
		
		return mod;
	}
	
	public static String modToSubdomain(String mod) {
		String subdomain = mod.replace("_", "-");
		if(subdomain.equals("main1")) {
			subdomain = "www";
		}
		
		return subdomain;
	}
	
}
