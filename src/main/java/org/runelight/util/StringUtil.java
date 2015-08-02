package org.runelight.util;

public final class StringUtil {
	
	public static String deFormatUsername(String name) {
		name = name.replace(" ", "_");
		char[] charArray = name.toCharArray();
		
		char lastChar = ' ';
		for(int i = 0; i < charArray.length; i++) {
			char c = charArray[i];
			if(lastChar == ' ') {
				c = Character.toLowerCase(c);
			}
			
			lastChar = c;
			charArray[i] = c;
		}
		
		return new String(charArray);
	}
	
	public static String formatUsername(String name) {
		name = name.replace("_", " ");
		char[] charArray = name.toCharArray();
		
		char lastChar = ' ';
		for(int i = 0; i < charArray.length; i++) {
			char c = charArray[i];
			if(lastChar == ' ') {
				c = Character.toUpperCase(c);
			}
			
			lastChar = c;
			charArray[i] = c;
		}
		
		return new String(charArray);
	}
	
}
