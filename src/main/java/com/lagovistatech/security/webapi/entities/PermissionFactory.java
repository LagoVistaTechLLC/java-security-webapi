package com.lagovistatech.security.webapi.entities;

import java.util.HashMap;

import com.lagovistatech.security.webapi.generated.PermissionRowFactoryImp;

public class PermissionFactory extends PermissionRowFactoryImp<Permission> {
	private PermissionFactory() {}
	public static final PermissionFactory instance = new PermissionFactory();
	public Permission create() { return new PermissionImp(new HashMap<String, Object>()); }
}