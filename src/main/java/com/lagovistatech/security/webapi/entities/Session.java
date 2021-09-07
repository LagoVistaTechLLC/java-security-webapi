package com.lagovistatech.security.webapi.entities;

import java.util.UUID;

import com.lagovistatech.database.Connection;
import com.lagovistatech.database.RecordNotFoundException;

public interface Session {
	static final String SETTING_SESSION_TIMEOUT_IN_SECONDS = "Session Timeout in Seconds";

	Connection getConnection();
	User getUser();
	Group getGroup(UUID groupsGuid) throws RecordNotFoundException;
	Action getAction(UUID actionsGuid) throws RecordNotFoundException;
	Securable getSecurable(UUID securablesGuid) throws RecordNotFoundException;
	Setting getSetting(String key);
	long getSecondsTillExpiration();

	boolean isMemberOfGroup(UUID groupsGuid);
	boolean isAdministration();
	boolean isAllowed(UUID securablesGuid, UUID actionsGuid);
	boolean isPasswordExpired();
	boolean isExpired();

	void login(Connection connection, String userName, String password) throws Exception;
}