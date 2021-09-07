package com.lagovistatech.security.webapi.entities;

import java.util.HashMap;

import com.lagovistatech.database.Connection;
import com.lagovistatech.database.Table;
import com.lagovistatech.security.webapi.generated.GroupRowFactoryImp;

public class GroupFactory extends GroupRowFactoryImp<Group> {
	private GroupFactory() {}
	public static final GroupFactory instance = new GroupFactory();
	public Group create() { return new GroupImp(new HashMap<>()); }

	public Table<Group> loadAll(Connection connection) throws Exception {
		String sql = "SELECT * FROM " + connection.getAdapter().quoteIdentifier(Group.TABLE_NAME) + 
			" ORDER BY " + connection.getAdapter().quoteIdentifier(Group.DISPLAY_NAME);
		return connection.fill(this, sql);
	}
}