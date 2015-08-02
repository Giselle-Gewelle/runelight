package org.runelight.security;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.UUID;

import org.apache.log4j.Logger;

public final class Hashing {
	
	private static final Logger LOG = Logger.getLogger(Hashing.class);
	
	/**
	 * Generates a unique session hash string.
	 * @return The unique session hash string.
	 */
	public static String generateSessionHash() {
		long timestamp = new Date().getTime();
		UUID uuid = UUID.randomUUID();
		
		return sha512(uuid + "-" + timestamp);
	}
	
	/**
	 * Hashes a specified string using SHA512.
	 * @param string The string to hash.
	 * @return The SHA512'd string.
	 */
	public static String sha512(String string) {
	    try {
	    	MessageDigest md = MessageDigest.getInstance("SHA-512");
	    	
	        return convertToHex(md.digest(string.getBytes("UTF-8")));
	    } catch (NoSuchAlgorithmException e) {
	        LOG.error("NoSuchAlgorithmException while attempting to generate SHA512 hash.", e);
	    } catch (UnsupportedEncodingException e) {
	        LOG.error("UnsupportedEncodingException while attempting to generate SHA512 hash.", e);
	    }
	    
	    return null;
	}
	
	/**
	 * Converts a message digest byte array into the appropriate string.
	 * @param bytes The byte array retrieved from MessageDigest.digest.
	 * @return The converted string.
	 */
	private static String convertToHex(byte[] bytes) {
	    StringBuffer buffer = new StringBuffer();
	    
	    for(int i = 0; i < bytes.length; i++) {
	    	buffer.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
	    }
	    
	    return buffer.toString();
	}
	
}
