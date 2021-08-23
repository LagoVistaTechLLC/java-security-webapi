package com.lagovistatech.security.webapi.entities;

import java.util.HashMap;

import com.lagovistatech.security.webapi.generated.MembershipRowFactoryImp;

public class MembershipFactory extends MembershipRowFactoryImp<Membership> {
	private MembershipFactory() {}
	public static final MembershipFactory instance = new MembershipFactory();
	public Membership create() { return new MembershipImp(new HashMap<String, Object>()); }
}