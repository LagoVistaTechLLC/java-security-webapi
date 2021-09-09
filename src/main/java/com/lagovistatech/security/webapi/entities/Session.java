package com.lagovistatech.security.webapi.entities;

import java.sql.Timestamp;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import com.lagovistatech.database.Connection;
import com.lagovistatech.security.dto.SessionDto;

public interface Session {
	static String SETTING_SESSION_TIMEOUT_IN_SECONDS = "Session Timeout in Seconds";
	
	Connection getConnection();
	void setConnection(Connection connection);

	Timestamp getCreated();
	void setCreated(Timestamp created);

	User getUser();
	void setUser(User user);

	Map<UUID, Group> getGroupsByGuid();
	void setGroupsByGuid(Map<UUID, Group> groupsByGuid);

	Set<UUID> getGroupMemberships();
	void setGroupMemberships(Set<UUID> groupMemberships);

	Map<UUID, Action> getActionsByGuid();
	void setActionsByGuid(Map<UUID, Action> actionsByGuid);

	Map<UUID, Securable> getSecurablesByGuid();
	void setSecurablesByGuid(Map<UUID, Securable> securablesByGuid);

	Map<UUID, Set<UUID>> getAllowedSecurableActions();
	void setAllowedSecurableActions(Map<UUID, Set<UUID>> allowedSecurableActions);

	Map<String, Setting> getSettingsByKey();
	void setSettingsByKey(Map<String, Setting> settingsByKey);

	boolean isMemberOfGroup(UUID groupsGuid);
	boolean isAdministration();
	boolean isAllowed(UUID securablesGuid, UUID actionsGuid);
	boolean isPasswordExpired();
	boolean isExpired();

	void login(Connection connection, String userName, String password) throws Exception;
	
	SessionDto createDto();
}