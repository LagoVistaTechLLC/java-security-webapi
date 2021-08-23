package com.lagovistatech.security.webapi.entities;

import java.util.HashMap;

import com.lagovistatech.security.webapi.generated.SecurableActionRowImp;

public class SecurableActionImp extends SecurableActionRowImp implements SecurableAction {
	protected SecurableActionImp(HashMap<String, Object> values) { super(values); }
}
