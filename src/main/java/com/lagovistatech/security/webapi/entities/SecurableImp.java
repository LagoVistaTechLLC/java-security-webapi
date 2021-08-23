package com.lagovistatech.security.webapi.entities;

import java.util.HashMap;

import com.lagovistatech.security.webapi.generated.SecurableRowImp;

public class SecurableImp extends SecurableRowImp implements Securable {
	protected SecurableImp(HashMap<String, Object> values) { super(values); }
}
