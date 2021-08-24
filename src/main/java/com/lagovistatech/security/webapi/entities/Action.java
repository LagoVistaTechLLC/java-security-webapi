package com.lagovistatech.security.webapi.entities;

import java.util.UUID;

import com.lagovistatech.Helpers;
import com.lagovistatech.security.webapi.generated.ActionRow;

public interface Action extends ActionRow { 
	public static UUID READ_GUID = Helpers.uuidFromString("b1f9efe539ec460e94d1880295799b3f");
	public static UUID CREATE_GUID = Helpers.uuidFromString("2681aeff2e4d4943a20748abd784a67e");
	public static UUID UPDATE_GUID = Helpers.uuidFromString("4cfaa8968bb24248afada78368d96fc0");
	public static UUID DELETE_GUID = Helpers.uuidFromString("1027f346e1074d8fbc4174520c51b7b5");
}
