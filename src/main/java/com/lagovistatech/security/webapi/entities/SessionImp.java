package com.lagovistatech.security.webapi.entities;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import com.lagovistatech.Helpers;
import com.lagovistatech.database.Connection;
import com.lagovistatech.database.Parameters;
import com.lagovistatech.database.Table;

public class SessionImp implements Session {
	protected SessionImp() { }
	
	private Connection connection;
	public Connection getConnection() { return connection; }

	private Timestamp expires;
	public Timestamp getExpires() { return expires; }

	private User user;
	public User getUser() { return user; }

	private Map<String, Setting> settingsByKey;
	public Setting getSettingByKey(String key) { return settingsByKey.get(key); }

	private Map<UUID, Action> actions;
	public Map<UUID, Action> getActions() { return actions; }

	private Map<UUID, Securable> securables;
	public Map<UUID, Securable> getSecurables() { return securables; }

	private Map<UUID, Set<UUID>> securablesAllowedActions;
	public Map<UUID, Set<UUID>> getSecurablesAllowedActions() { return securablesAllowedActions; }

	private List<Group> groups;
	public List<Group> getGroups() { return groups; }
	
	private Set<UUID> groupUuids;
	public boolean isGroupMember(UUID uuid) { return groupUuids.contains(uuid); }
	
	public static final String AUTHENTICATION_FAILED = "Login failed!"; 
	public void login(Connection connection, String userName, String password) throws Exception {
		this.connection = connection;
		loadUserForLogin(userName);
		
		if(user == null)
			throw new InvalidLoginException(AUTHENTICATION_FAILED);
		
		if(!user.validatePassword(password))
			throw new InvalidLoginException(AUTHENTICATION_FAILED);

		loadSettings();
		
		int maxDays = Integer.parseInt(this.getSettingByKey(User.SETTING_MAXIMUM_PASSWORD_AGE_IN_DAYS).getValue());
		Calendar minPasswordDate = Calendar.getInstance();
		minPasswordDate.add(Calendar.DATE, maxDays * -1);
		if(user.getPasswordDate().before(minPasswordDate.getTime()))
			throw new PasswordExpiredException("Password has expired!");
		
		loadGroups();
		loadActions();
		loadSecurables();
		loadPermissions();
	}
	private void loadActions() throws Exception {
		this.actions = new HashMap<UUID, Action>();
		
		Table<Action> table = ActionFactory.instance.loadAll(connection);
		for(Action action : table) 
			this.actions.put(action.getGuid(), action);
	}
	private void loadSecurables() throws Exception {
		this.securables = new HashMap<UUID, Securable>();
		
		Table<Securable> table = SecurableFactory.instance.loadAll(connection);
		for(Securable securable : table)
			this.securables.put(securable.getGuid(), securable);
	}
	private void loadPermissions() throws Exception {
		String sql = Helpers.readResourceAsString(getClass(), "/SessionImp.loadPermissions.sql");
		Parameters params = new Parameters();
		params.put("@UsersGuid", user.getGuid());
		params.put("@EveryonesGuid", Group.EVERYONES_GUID);
		
		Table<SecurableAction> table = connection.fill(SecurableActionFactory.instance, sql, params);
		
		this.securablesAllowedActions = new HashMap<UUID, Set<UUID>>();
		for(SecurableAction row : table) {
			if(!this.securablesAllowedActions.containsKey(row.getSecurablesGuid()))
				this.securablesAllowedActions.put(row.getSecurablesGuid(), new HashSet<UUID>());
			
			if(!this.securablesAllowedActions.get(row.getSecurablesGuid()).contains(row.getActionsGuid()))
				this.securablesAllowedActions.get(row.getSecurablesGuid()).add(row.getActionsGuid());
		}
	}
	private void loadGroups() throws Exception {
		Table<Group> table = user.loadMyGroups(connection, GroupFactory.instance);
		groupUuids = new HashSet<UUID>();
		groups = new ArrayList<Group>(table.size());
		for(Group group : table) {
			this.groups.add(group);
			this.groupUuids.add(group.getGuid());
		}
	}
	private void loadSettings() throws Exception {
		Table<Setting> settings = SettingFactory.instance.loadAll(connection);
		
		settingsByKey = new HashMap<String, Setting>();
		for(Setting setting : settings)
			settingsByKey.put(setting.getKey(), setting);
	}
	private void loadUserForLogin(String userName) throws Exception {
		try {
			user = UserFactory.instance.loadByUserName(connection, userName);
		}
		catch(Exception ex) {
			if(ex.toString().contains("Could not load unique row for 'User Name'"))
				throw new InvalidLoginException(AUTHENTICATION_FAILED);
			
			throw ex;
		}
	}
	
	public boolean isAllowed(UUID securable, UUID action) {
		if(this.user.getGuid().equals(User.ADMINISTRATORS_GUID))
			return true;
		if(this.isGroupMember(Group.ADMINISTRATORS_GUID))
			return true;
		
		if(!this.securablesAllowedActions.containsKey(securable))
			return false;
		if(!this.securablesAllowedActions.get(securable).contains(action))
			return false;
		
		return true;
	}
}
