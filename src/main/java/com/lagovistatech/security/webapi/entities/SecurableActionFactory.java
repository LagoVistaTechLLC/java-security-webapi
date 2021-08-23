package com.lagovistatech.security.webapi.entities;

import java.util.HashMap;

import com.lagovistatech.security.webapi.generated.SecurableActionRowFactoryImp;

public class SecurableActionFactory extends SecurableActionRowFactoryImp<SecurableAction> {
	private SecurableActionFactory() {}
	public static final SecurableActionFactory instance = new SecurableActionFactory();
	public SecurableAction create() { return new SecurableActionImp(new HashMap<String, Object>()); }
}