package com.lagovistatech.security.dto;

import java.sql.Timestamp;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import com.lagovistatech.security.webapi.entities.Session;

public class SessionDto implements DtoCopier<Session> {
	private UserDto user;
	public UserDto getUser() {
		return user;
	}
	public void setUser(UserDto user) {
		this.user = user;
	}

	private Timestamp created;
	public Timestamp getCreated() {
		return created;
	}
	public void setCreated(Timestamp created) {
		this.created = created;
	}

	private Map<UUID, GroupDto> groupsByGuid;
	public Map<UUID, GroupDto> getGroupsByGuid() {
		return groupsByGuid;
	}
	public void setGroupsByGuid(Map<UUID, GroupDto> groups) {
		this.groupsByGuid = groups;
	}

	private Map<UUID, ActionDto> actionsByGuid;
	public Map<UUID, ActionDto> getActionsByGuid() {
		return actionsByGuid;
	}
	public void setActionsByGuid(Map<UUID, ActionDto> actions) {
		this.actionsByGuid = actions;
	}

	private Map<UUID, SecurableDto> securablesByGuid;
	public Map<UUID, SecurableDto> getSecurablesByGuid() {
		return securablesByGuid;
	}
	public void setSecurablesByGuid(Map<UUID, SecurableDto> securables) {
		this.securablesByGuid = securables;
	}

	private Map<String, SettingDto> settingsByKey;
	public Map<String, SettingDto> getSettingsByKey() {
		return settingsByKey;
	}
	public void setSettingsByKey(Map<String, SettingDto> settings) {
		this.settingsByKey = settings;
	}

	private Map<UUID, Set<UUID>> allowedSecurableActions;
	public Map<UUID, Set<UUID>> getAllowedSecurableActions() {
		return allowedSecurableActions;
	}
	public void setAllowedSecurableActions(Map<UUID, Set<UUID>> allowedSecurableActions) {
		this.allowedSecurableActions = allowedSecurableActions;
	}

	private Set<UUID> groupMemberships;
	public Set<UUID> getGroupMemberships() {
		return groupMemberships;
	}
	public void setGroupMemberships(Set<UUID> groupMemberships) {
		this.groupMemberships = groupMemberships;
	}

	private SessionDtoCopier copier = new SessionDtoCopier();
	public void copyTo(Session destination) {
		copier.copyTo(this, destination);
	}
	public void copyFrom(Session source) {
		copier.copyFrom(source, this);
	}
}
