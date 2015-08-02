package org.runelight.security;

import java.util.Random;

public final class Password {
	
private static final int SALT_LENGTH = 50;
	
	private static final char[] ALPHA = {
		'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 
		'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'
	};
	private static final char[] NUMERIC = {
		'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'
	};
	private static final char[] SPECIAL = {
		'!', '@', '#', '$', '%', '^', '&', '*', '(', ')', '-', '=', '_', '+', 
		'[', ']', '{', '}', '|', ';', ':', ',', '.', '/', '<', '>', '?'
	};
	
	
	private String hash;
	private String salt;
	
	/**
	 * Used to generate a brand new password hash and salt.
	 * @param password The password to hash.
	 */
	public Password(String password) {
		this.salt = generateSalt();
		this.hash = generateHash(password);
	}
	
	/**
	 * Used to store and compare an existing password hash/salt set.
	 * @param hash The existing password hash.
	 * @param salt The salt used to make the hash.
	 */
	public Password(String hash, String salt) {
		this.hash = hash;
		this.salt = salt;
	}
	
	/**
	 * Checks to see if the supplied password (when hashed) matches this password's hash.
	 * @param password The plain-text password to verify.
	 * @return boolean
	 */
	public boolean equals(String password) {
		return generateHash(password).equals(this.hash);
	}
	
	/**
	 * Fetches the SHA512 string of this password.
	 * @return The SHA512 string of this password.
	 */
	public String getHash() {
		return this.hash;
	}
	
	/**
	 * Fetches the salt used by this password.
	 * @return The salt used by this password.
	 */
	public String getSalt() {
		return this.salt;
	}
	
	/**
	 * Generates a SHA512 hash of the specified password.
	 * @param password The password to hash.
	 * @return The hashed string.
	 */
	private String generateHash(String password) {
		return Hashing.sha512(salt + password);
	}
	
	/**
	 * Generates a random salt string for use in password hashing.
	 * @return A randomly generated salt string.
	 */
	private String generateSalt() {
		String salt = "";
		Random random = new Random();
		
		int lastCharType = -1;
		while(salt.length() != SALT_LENGTH) {
			int charType = random.nextInt(4);
			if(lastCharType == charType) {
				continue;
			}
			
			int charIdx = 0;
			char newChar = ' ';
			switch(charType) {
				default:
				case 0:
					charIdx = random.nextInt(ALPHA.length);
					newChar = ALPHA[charIdx];
					break;
				case 1:
					charIdx = random.nextInt(ALPHA.length);
					newChar = Character.toUpperCase(ALPHA[charIdx]);
					break;
				case 2:
					charIdx = random.nextInt(NUMERIC.length);
					newChar = NUMERIC[charIdx];
					break;
				case 3:
					charIdx = random.nextInt(SPECIAL.length);
					newChar = SPECIAL[charIdx];
					break;
			}
			
			salt += newChar;
		}
		
		return salt;
	}
	
	/**
	 * Checks to see if the specified password contains the specified username.
	 * @param username The username to look for.
	 * @param password The password to look in.
	 * @return True if the username is found, false if it is not found or it is too short (less than 3 characters).
	 */
	public static boolean passwordContainsUsername(String username, String password) {
		/*
		 * Username is too short to really matter...
		 */
		if(username.length() < 3) {
			return false;
		}
		
		/*
		 * Lowercase the password since all usernames are forced into lowercase, while passwords are NOT.
		 * But here... We want to be case insensative in our validation.
		 */
		password = password.toLowerCase();
		
		if(password.contains(username)) {
			return true;
		}
		
		if(new StringBuilder(username).reverse().toString().equals(password)) {
			return true;
		}
		
		/*
		 * If the username has multiple words, look to see if the password contains any of those words as well.
		 */
		String[] usernameParts = username.split("_");
		for(int i = 0; i < usernameParts.length; i++) {
			String part = usernameParts[i];
			if(part.length() < 3) {
				continue;
			}
			
			if(password.contains(part)) {
				return true;
			}
		}
		
		return false;
	}
	
}
