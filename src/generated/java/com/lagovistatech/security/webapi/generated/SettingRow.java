/*

	Copyright (C) 2021 Lago Vista Technologies LLC

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU Affero General Public License as
    published by the Free Software Foundation, either version 3 of the
    License, or (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Affero General Public License for more details.

    You should have received a copy of the GNU Affero General Public License
    along with this program.  If not, see <https://www.gnu.org/licenses/>.
	
*/
package com.lagovistatech.security.webapi.generated;

import com.lagovistatech.Factory;
import com.lagovistatech.database.Connection;
import com.lagovistatech.database.Row;
import com.lagovistatech.database.Table;
import com.lagovistatech.database.Versioned;

@SuppressWarnings("unused")
public interface SettingRow extends Row, Versioned {
	static final String TABLE_NAME = "Settings";
	static final String SECURABLE = "f4f70727dc34561dfde1a3c529b6205c";

	/* COLUMNS */
	
	static final String VERSION = "Version";
	java.lang.Long getVersion();
	void setVersion(java.lang.Long value);
	
	static final String SERVER_SIDE_ONLY = "Server Side Only";
	java.lang.Boolean getServerSideOnly();
	void setServerSideOnly(java.lang.Boolean value);
	
	static final String GROUPS_GUID = "Groups GUID";
	java.util.UUID getGroupsGuid();
	void setGroupsGuid(java.util.UUID value);
	
	static final String GUID = "GUID";
	java.util.UUID getGuid();
	void setGuid(java.util.UUID value);
	
	static final String VALUE = "Value";
	java.lang.String getValue();
	void setValue(java.lang.String value);
	
	static final String USERS_GUID = "Users GUID";
	java.util.UUID getUsersGuid();
	void setUsersGuid(java.util.UUID value);
	
	static final String KEY = "Key";
	java.lang.String getKey();
	void setKey(java.lang.String value);
	
	
	/* CHILDREN */
		

	/* PARENTS */
	
	<R extends UserRow> R loadUser(Connection conn, UserRowFactory<R> factory) throws Exception;	
	
	<R extends GroupRow> R loadGroup(Connection conn, GroupRowFactory<R> factory) throws Exception;	
		

}
