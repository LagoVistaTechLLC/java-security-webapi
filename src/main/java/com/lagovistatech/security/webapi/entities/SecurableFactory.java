package com.lagovistatech.security.webapi.entities;

import java.util.HashMap;

import com.lagovistatech.database.Connection;
import com.lagovistatech.database.Table;
import com.lagovistatech.security.webapi.generated.SecurableRowFactoryImp;

public class SecurableFactory extends SecurableRowFactoryImp<Securable> {
	private SecurableFactory() {}
	public static final SecurableFactory instance = new SecurableFactory();
	public Securable create() { return new SecurableImp(new HashMap<String, Object>()); }

	public Table<Securable> loadAll(Connection connection) throws Exception {
		String sql = "SELECT * FROM " + connection.getAdapter().quoteIdentifier(Securable.TABLE_NAME);
		return connection.fill(this, sql);
	}
}