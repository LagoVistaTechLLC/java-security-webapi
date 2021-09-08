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
public interface GroupRow extends Row, Versioned {
	static final String TABLE_NAME = "Groups";
	static final String SECURABLE = "a37ede293936e29279ed543129451ec3";

	/* COLUMNS */
	
	static final String DISPLAY_NAME = "Display Name";
	java.lang.String getDisplayName();
	void setDisplayName(java.lang.String value);
	
	static final String VERSION = "Version";
	java.lang.Long getVersion();
	void setVersion(java.lang.Long value);
	
	static final String GUID = "GUID";
	java.util.UUID getGuid();
	void setGuid(java.util.UUID value);
	
	static final String DISABLED = "Disabled";
	java.lang.Boolean getDisabled();
	void setDisabled(java.lang.Boolean value);
	
	static final String EMAIL_ADDRESS = "Email Address";
	java.lang.String getEmailAddress();
	void setEmailAddress(java.lang.String value);
	
	
	/* CHILDREN */
	
	<R extends MembershipRow> Table<R> loadMemberships(Connection conn, MembershipRowFactory<R> factory) throws Exception;	
	
	<R extends PermissionRow> Table<R> loadPermissions(Connection conn, PermissionRowFactory<R> factory) throws Exception;	
	
	<R extends SettingRow> Table<R> loadSettings(Connection conn, SettingRowFactory<R> factory) throws Exception;	
		

	/* PARENTS */
		

}
