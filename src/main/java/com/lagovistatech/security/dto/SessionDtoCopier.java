package com.lagovistatech.security.dto;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.lagovistatech.security.webapi.entities.Action;
import com.lagovistatech.security.webapi.entities.ActionFactory;
import com.lagovistatech.security.webapi.entities.Group;
import com.lagovistatech.security.webapi.entities.GroupFactory;
import com.lagovistatech.security.webapi.entities.Securable;
import com.lagovistatech.security.webapi.entities.SecurableFactory;
import com.lagovistatech.security.webapi.entities.Session;
import com.lagovistatech.security.webapi.entities.Setting;
import com.lagovistatech.security.webapi.entities.SettingFactory;
import com.lagovistatech.security.webapi.entities.User;
import com.lagovistatech.security.webapi.entities.UserFactory;

public class SessionDtoCopier {

	public void copyTo(SessionDto source, Session destination) {
		Map<UUID, Action> actionsByGuid = new HashMap<>();
		for(UUID actionGuid : source.getActionsByGuid().keySet()) {
			Action action = ActionFactory.instance.create();
			source.getActionsByGuid().get(actionGuid).copyTo(action);
			actionsByGuid.put(actionGuid, action);
		}
		destination.setActionsByGuid(actionsByGuid);
		
		destination.setAllowedSecurableActions(source.getAllowedSecurableActions());
		destination.setCreated(source.getCreated());
		destination.setGroupMemberships(source.getGroupMemberships());
		
		Map<UUID, Group> groupsByGuid = new HashMap<>();
		for(UUID groupGuid : source.getGroupsByGuid().keySet()) {
			Group group = GroupFactory.instance.create();
			source.getGroupsByGuid().get(groupGuid).copyTo(group);
			groupsByGuid.put(groupGuid, group);
		}
		destination.setGroupsByGuid(groupsByGuid);
		
		Map<UUID, Securable> securablesByGuid = new HashMap<>();
		for(UUID securableGuid : source.getSecurablesByGuid().keySet()) {
			Securable securable = SecurableFactory.instance.create();
			source.getSecurablesByGuid().get(securableGuid).copyTo(securable);
			securablesByGuid.put(securableGuid, securable);
		}
		destination.setSecurablesByGuid(securablesByGuid);
				
		Map<String, Setting> settingsByKey = new HashMap<>();
		for(String key : source.getSettingsByKey().keySet()) {
			Setting setting = SettingFactory.instance.create();
			source.getSettingsByKey().get(key).copyTo(setting);
			settingsByKey.put(key, setting);
		}
		destination.setSettingsByKey(settingsByKey);
		
		User user = UserFactory.instance.create();
		source.getUser().copyTo(user);
		destination.setUser(user);
	}

	public void copyFrom(Session source, SessionDto destination) {
		Map<UUID, ActionDto> actionsByGuid = new HashMap<>();
		for(UUID actionGuid : source.getActionsByGuid().keySet()) {
			ActionDto action = new ActionDto();
			action.copyFrom(source.getActionsByGuid().get(actionGuid));
			actionsByGuid.put(actionGuid, action);
		}
		destination.setActionsByGuid(actionsByGuid);

		destination.setAllowedSecurableActions(source.getAllowedSecurableActions());
		destination.setCreated(source.getCreated());
		destination.setGroupMemberships(source.getGroupMemberships());

		Map<UUID, GroupDto> groupsByGuid = new HashMap<>();
		for(UUID groupGuid : source.getGroupsByGuid().keySet()) {
			GroupDto group = new GroupDto();
			group.copyFrom(source.getGroupsByGuid().get(groupGuid));
			groupsByGuid.put(groupGuid, group);
		}
		destination.setGroupsByGuid(groupsByGuid);

		Map<UUID, SecurableDto> securablesByGuid = new HashMap<>();
		for(UUID securableGuid : source.getSecurablesByGuid().keySet()) {
			SecurableDto securable = new SecurableDto();
			securable.copyFrom(source.getSecurablesByGuid().get(securableGuid));
			securablesByGuid.put(securableGuid, securable);
		}
		destination.setSecurablesByGuid(securablesByGuid);

		Map<String, SettingDto> settingsByKey = new HashMap<>();
		for(String key : source.getSettingsByKey().keySet()) {
			SettingDto setting = new SettingDto();
			setting.copyFrom(source.getSettingsByKey().get(key));
			settingsByKey.put(key, setting);
		}
		destination.setSettingsByKey(settingsByKey);

		UserDto user = new UserDto();
		user.copyFrom(source.getUser());
		destination.setUser(user);
	}

}
