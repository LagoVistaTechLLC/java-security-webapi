package com.lagovistatech.security.webapi.entities;

import java.util.HashMap;

import com.lagovistatech.database.Connection;
import com.lagovistatech.database.Table;
import com.lagovistatech.security.webapi.generated.ActionRowFactoryImp;

public class ActionFactory extends ActionRowFactoryImp<Action> {
	private ActionFactory() {}
	public static final ActionFactory instance = new ActionFactory();
	public Action create() { return new ActionImp(new HashMap<String, Object>()); }

	public Table<Action> loadAll(Connection connection) throws Exception {
		String sql = "SELECT * FROM " + connection.getAdapter().quoteIdentifier(Action.TABLE_NAME);
		
		return connection.fill(this, sql);
	}
}