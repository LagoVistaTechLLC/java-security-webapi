package com.lagovistatech.security.webapi.entities;

import java.util.HashMap;

import com.lagovistatech.security.webapi.generated.PermissionRowImp;

public class PermissionImp extends PermissionRowImp implements Permission {
	protected PermissionImp(HashMap<String, Object> values) { super(values); }
}
