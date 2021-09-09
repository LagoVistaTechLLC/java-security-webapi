package com.lagovistatech.security.webapi.entities;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import com.lagovistatech.database.Connection;
import com.lagovistatech.database.RecordNotFoundException;
import com.lagovistatech.database.Table;
import com.lagovistatech.security.dto.SessionDto;

public class SessionImp implements Session {
	private static final String AUTHENTICATION_FAILED = "Login failed!";

	protected SessionImp() {}

	private Connection connection;
	public Connection getConnection() {
		return connection;
	}
	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	private User user;
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}

	private Timestamp created;
	public Timestamp getCreated() {
		return created;
	}
	public void setCreated(Timestamp created) {
		this.created = created;
	}

	private Map<UUID, Group> groupsByGuid;
	public Map<UUID, Group> getGroupsByGuid() {
		return groupsByGuid;
	}
	public void setGroupsByGuid(Map<UUID, Group> groupsByGuid) {
		this.groupsByGuid = groupsByGuid;
	}

	private Set<UUID> groupMemberships;
	public Set<UUID> getGroupMemberships() {
		return groupMemberships;
	}
	public void setGroupMemberships(Set<UUID> groupMemberships) {
		this.groupMemberships = groupMemberships;
	}

	private Map<UUID, Action> actionsByGuid;
	public Map<UUID, Action> getActionsByGuid() {
		return actionsByGuid;
	}
	public void setActionsByGuid(Map<UUID, Action> actionsByGuid) {
		this.actionsByGuid = actionsByGuid;
	}
	
	private Map<UUID, Securable> securablesByGuid;
	public Map<UUID, Securable> getSecurablesByGuid() {
		return securablesByGuid;
	}
	public void setSecurablesByGuid(Map<UUID, Securable> securablesByGuid) {
		this.securablesByGuid = securablesByGuid;
	}

	private Map<UUID, Set<UUID>> allowedSecurableActions;
	public Map<UUID, Set<UUID>> getAllowedSecurableActions() {
		return allowedSecurableActions;
	}
	public void setAllowedSecurableActions(Map<UUID, Set<UUID>> allowedSecurableActions) {
		this.allowedSecurableActions = allowedSecurableActions;
	}

	private Map<String, Setting> settingsByKey;
	public Map<String, Setting> getSettingsByKey() {
		return settingsByKey;
	}
	public void setSettingsByKey(Map<String, Setting> settingsByKey) {
		this.settingsByKey = settingsByKey;
	}

	public boolean isMemberOfGroup(UUID groupsGuid) { return groupMemberships.contains(groupsGuid); }	
	public boolean isAdministration() {
		if(this.user.getGuid().equals(User.ADMINISTRATORS_GUID))
			return true;
		if(this.isMemberOfGroup(Group.ADMINISTRATORS_GUID))
			return true;

		return false;
	}
	public boolean isAllowed(UUID securablesGuid, UUID actionsGuid) {
		if(isAdministration())
			return true;
		
		if(allowedSecurableActions.containsKey(securablesGuid))
			return allowedSecurableActions.get(securablesGuid).contains(actionsGuid);
	
		return false;
	}
	public boolean isPasswordExpired() {
		int maxDays = Integer.parseInt(getSettingsByKey().get(User.SETTING_MAXIMUM_PASSWORD_AGE_IN_DAYS).getValue());
		Calendar minPasswordDate = Calendar.getInstance();
		minPasswordDate.add(Calendar.DATE, maxDays * -1);
		return user.getPasswordDate().before(minPasswordDate.getTime());
	}	
	public boolean isExpired() {
		long timeoutInSeconds = Long.parseLong(getSettingsByKey().get(SETTING_SESSION_TIMEOUT_IN_SECONDS).getValue()) * 1000;
		long sessionDurationInMs = Instant.now().toEpochMilli() - created.toInstant().toEpochMilli();
		return sessionDurationInMs > timeoutInSeconds;
	}

	public void login(Connection connection, String userName, String password) throws Exception {
		if(userName == null || userName.length() < 1)
			throw new InvalidLoginException("No user name provided!");
		if(password == null || password.length() < 1)
			throw new InvalidLoginException("No password provided!");
		
		this.connection = connection;
		loadUserForLogin(userName);
		
		if(user == null)
			throw new InvalidLoginException(AUTHENTICATION_FAILED);
		if(!user.validatePassword(password))
			throw new InvalidLoginException(AUTHENTICATION_FAILED);
		
		loadSettings();
		loadGroups();
		loadActions();
		loadSecurables();
		loadMemberships();
		loadSecurableActions();
		
		created = Timestamp.from(java.time.Instant.now());
	}
	private void loadSettings() throws Exception {
		settingsByKey = new HashMap<>();
		
		Table<Setting> settings = SettingFactory.instance.loadAll(connection);
		for(Setting setting : settings)
			settingsByKey.put(setting.getKey(), setting);
		
		settings = getUser().loadSettings(connection, SettingFactory.instance);
		for(Setting setting : settings)
			settingsByKey.put(setting.getKey(), setting);
	}
	private void loadSecurableActions() throws Exception {
		allowedSecurableActions = new HashMap<>();
		
		Table<SecurableAction> sas = getUser().loadSecurableActions(connection, SecurableActionFactory.instance);
		for(SecurableAction sa : sas) {
			if(!allowedSecurableActions.containsKey(sa.getSecurablesGuid()))
				allowedSecurableActions.put(sa.getSecurablesGuid(), new HashSet<>());
			
			allowedSecurableActions.get(sa.getSecurablesGuid()).add(sa.getActionsGuid());
		}
	}
	private void loadMemberships() throws Exception {
		groupMemberships = new HashSet<>();
		
		Table<Membership> memberships = getUser().loadMemberships(connection, MembershipFactory.instance);
		for(Membership membership : memberships) 
			groupMemberships.add(membership.getGroupsGuid());
		
		groupMemberships.add(Group.EVERYONES_GUID);
	}
	private void loadSecurables() throws Exception {
		securablesByGuid = new HashMap<>();
		
		Table<Securable> securables = SecurableFactory.instance.loadAll(connection);
		for(Securable securable : securables)
			securablesByGuid.put(securable.getGuid(), securable);
	}
	private void loadActions() throws Exception {
		actionsByGuid = new HashMap<>();
		
		Table<Action> actions = ActionFactory.instance.loadAll(connection);
		for(Action action : actions)
			actionsByGuid.put(action.getGuid(), action);
	}
	private void loadGroups() throws Exception {
		groupsByGuid = new HashMap<>();
		
		Table<Group> groups = GroupFactory.instance.loadAll(connection);
		for(Group group : groups)
			groupsByGuid.put(group.getGuid(), group);
	}
	private void loadUserForLogin(String userName) throws Exception {
		try { user = UserFactory.instance.loadByUserName(connection, userName); }
		catch(RecordNotFoundException ex) {
			throw new InvalidLoginException(AUTHENTICATION_FAILED);
		}
	}
	
	public SessionDto createDto() {
		SessionDto ret = new SessionDto();
		ret.copyFrom(this);
		return ret;
	}
}
