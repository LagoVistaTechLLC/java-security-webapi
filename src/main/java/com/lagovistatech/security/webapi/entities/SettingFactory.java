package com.lagovistatech.security.webapi.entities;

import java.util.HashMap;

import com.lagovistatech.database.Connection;
import com.lagovistatech.database.Table;
import com.lagovistatech.security.webapi.generated.SettingRowFactoryImp;

public class SettingFactory extends SettingRowFactoryImp<Setting> {
	private SettingFactory() {}
	public static final SettingFactory instance = new SettingFactory();
	public Setting create() { return new SettingImp(new HashMap<String, Object>()); }

	public Table<Setting> loadAll(Connection connection) throws Exception {
		String sql = "SELECT * FROM " + connection.getAdapter().quoteIdentifier(Setting.TABLE_NAME);
		return connection.fill(SettingFactory.instance, sql);
	}
}