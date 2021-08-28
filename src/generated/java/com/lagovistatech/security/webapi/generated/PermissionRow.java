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
public interface PermissionRow extends Row, Versioned {
	static final String TABLE_NAME = "Permissions";
	static final String SECURABLE = "d08ccf52b4cdd08e41cfb99ec42e0b29";

	/* COLUMNS */
	
	static final String VERSION = "Version";
	java.lang.Long getVersion();
	void setVersion(java.lang.Long value);
	
	static final String GROUPS_GUID = "Groups GUID";
	java.util.UUID getGroupsGuid();
	void setGroupsGuid(java.util.UUID value);
	
	static final String GUID = "GUID";
	java.util.UUID getGuid();
	void setGuid(java.util.UUID value);
	
	static final String SECURABLE_ACTIONS_GUID = "Securable Actions GUID";
	java.util.UUID getSecurableActionsGuid();
	void setSecurableActionsGuid(java.util.UUID value);
	
	
	/* CHILDREN */
		

	/* PARENTS */
	
	<R extends SecurableActionRow> R loadSecurableActionByMySecurableActionsGuid(Connection conn, SecurableActionRowFactory<R> factory) throws Exception;	
	
	<R extends GroupRow> R loadGroupByMyGroupsGuid(Connection conn, GroupRowFactory<R> factory) throws Exception;	
		

}
