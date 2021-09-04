package com.lagovistatech.security.webapi.entities;

import java.util.HashMap;

import com.lagovistatech.security.webapi.generated.UserRowFactoryImp;

public class UserFactory extends UserRowFactoryImp<User> {
	private static final int SALT_LENGTH = 32;
	private static int ITERATIONS = 10000;
	private static final int KEY_LENGTH = 512;
	private static PasswordHasher HASHER = new PasswordHasher(SALT_LENGTH, ITERATIONS, KEY_LENGTH);
	
	public static void setIterations(int value) {
		ITERATIONS = value;
		HASHER = new PasswordHasher(SALT_LENGTH, ITERATIONS, KEY_LENGTH);
	}
	
	private UserFactory() {}
	public static final UserFactory instance = new UserFactory();
	public User create() { return new UserImp(new HashMap<String, Object>(), HASHER); }
}