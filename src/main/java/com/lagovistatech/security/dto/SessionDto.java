package com.lagovistatech.security.dto;

import java.util.List;

public class SessionDto {
	private UserDto user;
	public UserDto getUser() {
		return user;
	}
	public void setUser(UserDto user) {
		this.user = user;
	}

	private List<SettingDto> settings;
	public List<SettingDto> getSettings() {
		return settings;
	}
	public void setSettings(List<SettingDto> settings) {
		this.settings = settings;
	}

	private List<GroupDto> groups;
	public List<GroupDto> getGroups() {
		return groups;
	}
	public void setGroups(List<GroupDto> groups) {
		this.groups = groups;
	}

	private List<ActionDto> actions;
	public List<ActionDto> getActions() {
		return actions;
	}
	public void setActions(List<ActionDto> actions) {
		this.actions = actions;
	}

	private List<PermissionDto> permissions;
	public List<PermissionDto> getPermissions() {
		return permissions;
	}
	public void setPermissions(List<PermissionDto> permissions) {
		this.permissions = permissions;
	}

	private List<SecurableDto> securables;
	public List<SecurableDto> getSecurables() {
		return securables;
	}
	public void setSecurables(List<SecurableDto> securables) {
		this.securables = securables;
	}

	private List<MembershipDto> memberships;
	public List<MembershipDto> getMemberships() {
		return memberships;
	}
	public void setMemberships(List<MembershipDto> memberships) {
		this.memberships = memberships;
	}

	private java.sql.Timestamp expires;
	public java.sql.Timestamp getExpires() {
		return expires;
	}
	public void setExpires(java.sql.Timestamp expires) {
		this.expires = expires;
	}

	private byte[] signature;
	public byte[] getSignature() {
		return signature;
	}
	public void setSignature(byte[] signature) {
		this.signature = signature;
	}
}
