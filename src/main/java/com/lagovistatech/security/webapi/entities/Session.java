package com.lagovistatech.security.webapi.entities;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import com.lagovistatech.database.Connection;

/**
 * The session holds data about the user, groups they belong to, permissions,
 * and settings defined for the system.  The client will be provided two 
 * instances of this object - a client readable version, and a version that is
 * encrypted and readable by other web API end points.
 */
public interface Session {
	Connection getConnection();

	Timestamp getExpires();

	Setting getSettingByKey(String key);
	
	Map<UUID, Action> getActions();	
	Map<UUID, Securable> getSecurables();
	Map<UUID, Set<UUID>> getSecurablesAllowedActions();

	List<Group> getGroups();
	boolean isGroupMember(UUID group);

	User getUser();
	void login(Connection conn, String userName, String password) throws Exception;
	boolean isPasswordExpired();
	
	boolean isAllowed(UUID securable, UUID action);
	boolean isAdministration();
}
