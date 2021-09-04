package com.lagovistatech.security.webapi.entities;

import java.security.SecureRandom;
import java.security.spec.KeySpec;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class PasswordHasher {
	public PasswordHasher(int saltLength, int iterations, int keyLength) {
		this.iterations = iterations;
		this.keyLength = keyLength;
		
		SecureRandom random = new SecureRandom();
		salt = new byte[saltLength];
		random.nextBytes(salt);
	}
	
	private int keyLength;
	public void setKeyLength(int value) { keyLength = value; }
	public int getKeyLength() { return keyLength; }
	
	private int iterations;
	public void setIterations(int value) { iterations = value; }
	public int getItterations() { return iterations; }
	
	private byte[] salt;
	public void setSalt(byte[] salt) { this.salt = salt; }
	public byte[] getSalt() { return this.salt; }
	
	public byte[] calculate(String secret) throws Exception {		
		KeySpec spec = new PBEKeySpec((secret).toCharArray(), salt, iterations, keyLength);
		return SecretKeyFactory
			//.getInstance("PBKDF2WithHmacSHA512")
			.getInstance("PBKDF2WithHmacSHA1")
			.generateSecret(spec)
			.getEncoded();

	}
	
	public static int calculateComplexity(String value) {
		int hasLower = 0;
		int hasUpper = 0;
		int hasNumber = 0;
		int hasOther = 0;
		
		for(char c : value.toCharArray()) {
			if(Character.isLowerCase(c))
				hasLower = 1;
			else if(Character.isUpperCase(c))
				hasUpper = 1;
			else if(Character.isDigit(c))
				hasNumber = 1;
			else
				hasOther = 1;
		}
		
		return hasLower + hasNumber + hasOther + hasUpper;
	}
}
