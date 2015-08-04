package org.runelight.util;

import java.text.SimpleDateFormat;

public final class DateUtil {
	
	public static final SimpleDateFormat 
		SHORT_NEWS_FORMAT = new SimpleDateFormat("dd-MMM-yyyy"),
		LONG_NEWS_FORMAT = new SimpleDateFormat("d MMMM yyyy"),
		SQL_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd"),
		SQL_DATETIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"),
		SHORT_TIME_FORMAT = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss"),
		LONG_TIME_FORMAT = new SimpleDateFormat("d MMMM yyyy HH:mm:ss");
	
}
