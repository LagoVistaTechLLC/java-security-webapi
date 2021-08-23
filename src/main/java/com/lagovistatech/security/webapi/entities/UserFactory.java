package com.lagovistatech.security.webapi.entities;

import java.util.HashMap;

import com.lagovistatech.security.webapi.generated.UserRowFactoryImp;

public class UserFactory extends UserRowFactoryImp<User> {
	private UserFactory() {}
	public static final UserFactory instance = new UserFactory();
	public User create() { return new UserImp(new HashMap<String, Object>()); }
}