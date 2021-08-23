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
	private int iterations;
	
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
}
