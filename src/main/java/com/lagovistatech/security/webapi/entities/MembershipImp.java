package com.lagovistatech.security.webapi.entities;

import java.util.HashMap;

import com.lagovistatech.security.webapi.generated.MembershipRowImp;

public class MembershipImp extends MembershipRowImp implements Membership {
	protected MembershipImp(HashMap<String, Object> values) { super(values); }
}
