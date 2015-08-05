package org.runelight;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;

public final class Config {
	
	private static final Logger LOG = Logger.getLogger(Config.class);
	
	private static boolean initialized = false;
	
	private static String hostName;
	private static boolean sslEnabled;
	private static String gameName;
	private static String companyName;
	private static String formattedHostName;
	
	public static void init() {
		if(initialized) {
			return;
		}
		
		initialized = true;
		
		Properties properties = new Properties();
		File propertiesFile = new File(System.getenv("TOMCAT_HOME") + File.separatorChar + "conf" + File.separatorChar + "runelight.properties");
		
		if(!propertiesFile.exists()) {
			LOG.error("Properties file not found!");
			return;
		}
		
		FileInputStream stream = null;
		
		try {
			stream = new FileInputStream(propertiesFile);
			properties.load(stream);
			
			hostName = properties.getProperty("hostName");
			sslEnabled = Boolean.valueOf(properties.getProperty("sslEnabled"));
			gameName = properties.getProperty("gameName");
			companyName = properties.getProperty("companyName");
			formattedHostName = properties.getProperty("formattedHostName");
		} catch(Exception e) {
			LOG.error("Exception occured while attempting to open the properties file input stream.", e);
		} finally {
			if(stream == null) {
				return;
			}
			
			try {
				stream.close();
			} catch(IOException e) {
				LOG.error("IOException occured while attempting to close the properties file input stream.", e);
			}
		}
	}
	
	public static String getHostName() {
		return hostName;
	}
	
	public static boolean isSslEnabled() {
		return sslEnabled;
	}
	
	public static String getGameName() {
		return gameName;
	}
	
	public static String getCompanyName() {
		return companyName;
	}
	
	public static String getFormattedHostName() {
		return formattedHostName;
	}
	
}
