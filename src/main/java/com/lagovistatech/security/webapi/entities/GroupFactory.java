package com.lagovistatech.security.webapi.entities;

import java.util.HashMap;

import com.lagovistatech.security.webapi.generated.GroupRowFactoryImp;

public class GroupFactory extends GroupRowFactoryImp<Group> {
	private GroupFactory() {}
	public static final GroupFactory instance = new GroupFactory();
	public Group create() { return new GroupImp(new HashMap<String, Object>()); }
}