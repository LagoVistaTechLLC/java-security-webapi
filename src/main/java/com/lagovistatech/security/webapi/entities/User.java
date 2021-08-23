package com.lagovistatech.security.webapi.entities;

import java.util.UUID;

import com.lagovistatech.Helpers;
import com.lagovistatech.database.Connection;
import com.lagovistatech.database.Table;
import com.lagovistatech.security.webapi.generated.GroupRow;
import com.lagovistatech.security.webapi.generated.GroupRowFactory;
import com.lagovistatech.security.webapi.generated.UserRow;

public interface User extends UserRow { 
	public static UUID ADMINISTRATORS_GUID = Helpers.uuidFromString("07e938003af84c6a99015528dc8ecdf0");
	public static String SETTING_MINIMUM_PASSWORD_LENGTH = "Minimum Password Length";
	public static String SETTING_MAXIMUM_PASSWORD_AGE_IN_DAYS = "Maximum Password Age in Days";
	public static String SETTING_MINIMUM_PASSWORD_COMPLEXITY = "Minimum Password Complexity";
	
	void resetPassword(Session session, String newPassword, String confirmPassword) throws Exception;
	boolean validatePassword(String password) throws Exception;
	<R extends GroupRow> Table<R> loadMyGroups(Connection conn, GroupRowFactory<R> factory) throws Exception;
}
