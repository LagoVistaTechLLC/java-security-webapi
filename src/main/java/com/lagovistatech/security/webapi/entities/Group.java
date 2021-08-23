package com.lagovistatech.security.webapi.entities;

import java.util.UUID;

import com.lagovistatech.Helpers;
import com.lagovistatech.security.webapi.generated.GroupRow;

public interface Group extends GroupRow { 
	public static UUID ADMINISTRATORS_GUID = Helpers.uuidFromString("38500a92c26f4491844ddbc84a485fe9");
	public static UUID EVERYONES_GUID = Helpers.uuidFromString("977f148f173f4b97aaf86268f052b984");
}
