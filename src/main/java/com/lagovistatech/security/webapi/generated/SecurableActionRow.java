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
public interface SecurableActionRow extends Row, Versioned {
	static final String TABLE_NAME = "Securable Actions";
	static final String SECURABLE = "aa0c7b2d207247f0a962926a112327a7";

	/* COLUMNS */
	
	static final String VERSION = "Version";
	java.lang.Long getVersion();
	void setVersion(java.lang.Long value);
	
	static final String GUID = "GUID";
	java.util.UUID getGuid();
	void setGuid(java.util.UUID value);
	
	static final String SECURABLES_GUID = "Securables GUID";
	java.util.UUID getSecurablesGuid();
	void setSecurablesGuid(java.util.UUID value);
	
	static final String ACTIONS_GUID = "Actions GUID";
	java.util.UUID getActionsGuid();
	void setActionsGuid(java.util.UUID value);
	
	
	/* CHILDREN */
	
	<R extends PermissionRow> Table<R> loadPermissionsBySecurableActionsGuidEqualsMyGuid(Connection conn, PermissionRowFactory<R> factory) throws Exception;	
		

	/* PARENTS */
	
	<R extends SecurableRow> R loadSecurableByMySecurablesGuid(Connection conn, SecurableRowFactory<R> factory) throws Exception;	
	
	<R extends ActionRow> R loadActionByMyActionsGuid(Connection conn, ActionRowFactory<R> factory) throws Exception;	
		

}
