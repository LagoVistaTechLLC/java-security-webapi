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

public class SessionImp implements Session {
	private static final String AUTHENTICATION_FAILED = "Login failed!";

	protected SessionImp() {}

	private Connection connection;
	private Timestamp created;
	private User user;
	private Map<UUID, Group> groupsByGuid;
	private Set<UUID> groupMemberships;
	private Map<UUID, Action> actionsByGuid;
	private Map<UUID, Securable> securablesByGuid;
	private Map<UUID, Set<UUID>> allowedSecurableActions;
	private Map<String, Setting> settingsByKey;

	@Override
	public Connection getConnection() { return connection; }
	@Override
	public User getUser() { return user; }
	@Override
	public Group getGroup(UUID groupsGuid) throws RecordNotFoundException {
		if(groupsByGuid != null && groupsByGuid.containsKey(groupsGuid))
			return groupsByGuid.get(groupsGuid);
		
		throw new RecordNotFoundException("Could not locate group by GUID '" + groupsGuid.toString() + "'!");
	}
	@Override
	public Action getAction(UUID actionsGuid) throws RecordNotFoundException {
		if(actionsByGuid != null && actionsByGuid.containsKey(actionsGuid))
			return actionsByGuid.get(actionsGuid);
		
		throw new RecordNotFoundException("Could not locate action by GUID '" + actionsGuid.toString() + "'!");
	}
	@Override
	public Securable getSecurable(UUID securablesGuid) throws RecordNotFoundException {
		if(securablesByGuid != null && securablesByGuid.containsKey(securablesGuid))
			return securablesByGuid.get(securablesGuid);
		
		throw new RecordNotFoundException("Could not locate securable by GUID '" + securablesGuid.toString() + "'!");
	}
	@Override
	public Setting getSetting(String key) { return settingsByKey.get(key); }
	@Override
	public long getSecondsTillExpiration() {
		long timeoutInSeconds = Long.parseLong(settingsByKey.get(SETTING_SESSION_TIMEOUT_IN_SECONDS).getValue()) * 1000;
		long sessionDurationInMs = Instant.now().toEpochMilli() - created.toInstant().toEpochMilli();
		return (timeoutInSeconds - sessionDurationInMs) / 1000;
	}

	@Override
	public boolean isMemberOfGroup(UUID groupsGuid) { return groupMemberships.contains(groupsGuid); }	
	@Override
	public boolean isAdministration() {
		if(this.user.getGuid().equals(User.ADMINISTRATORS_GUID))
			return true;
		if(this.isMemberOfGroup(Group.ADMINISTRATORS_GUID))
			return true;

		return false;
	}
	@Override
	public boolean isAllowed(UUID securablesGuid, UUID actionsGuid) {
		if(isAdministration())
			return true;
		
		if(allowedSecurableActions.containsKey(securablesGuid))
			return allowedSecurableActions.get(securablesGuid).contains(actionsGuid);
	
		return false;
	}
	@Override
	public boolean isPasswordExpired() {
		int maxDays = Integer.parseInt(this.getSetting(User.SETTING_MAXIMUM_PASSWORD_AGE_IN_DAYS).getValue());
		Calendar minPasswordDate = Calendar.getInstance();
		minPasswordDate.add(Calendar.DATE, maxDays * -1);
		return user.getPasswordDate().before(minPasswordDate.getTime());
	}	
	@Override
	public boolean isExpired() {
		long timeoutInSeconds = Long.parseLong(settingsByKey.get(SETTING_SESSION_TIMEOUT_IN_SECONDS).getValue()) * 1000;
		long sessionDurationInMs = Instant.now().toEpochMilli() - created.toInstant().toEpochMilli();
		return sessionDurationInMs > timeoutInSeconds;
	}

	@Override
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
		
		loadGroups();
		loadActions();
		loadSecurables();
		loadMemberships();
		loadSecurableActions();
		
		created = Timestamp.from(java.time.Instant.now());
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
		
		Table<Membership> memberships = getUser().loadMembershipsByUsersGuidEqualsMyGuid(connection, MembershipFactory.instance);
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
}
