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

import java.util.HashMap;

import com.lagovistatech.Factory;
import com.lagovistatech.database.Connection;
import com.lagovistatech.database.Parameters;
import com.lagovistatech.database.RecordNotFoundException;
import com.lagovistatech.database.Table;
import com.lagovistatech.database.VersionedRow;

@SuppressWarnings("unused")
public class MembershipRowImp extends VersionedRow implements MembershipRow {
	public static final String TABLE_NAME = MembershipRow.TABLE_NAME;
	public static final String SECURABLE = MembershipRow.SECURABLE;

	protected MembershipRowImp(HashMap<String, Object> values) { super(values); }	
	
	/* COLUMNS */
	
	public java.lang.Long getVersion() {
		Object ret = this.get(VERSION);
		return ret == null ? null : (java.lang.Long) ret;		
	}
	public void setVersion(java.lang.Long value) { this.set(VERSION, value); }
	
	public java.util.UUID getGroupsGuid() {
		Object ret = this.get(GROUPS_GUID);
		return ret == null ? null : (java.util.UUID) ret;		
	}
	public void setGroupsGuid(java.util.UUID value) { this.set(GROUPS_GUID, value); }
	
	public java.util.UUID getGuid() {
		Object ret = this.get(GUID);
		return ret == null ? null : (java.util.UUID) ret;		
	}
	public void setGuid(java.util.UUID value) { this.set(GUID, value); }
	
	public java.util.UUID getUsersGuid() {
		Object ret = this.get(USERS_GUID);
		return ret == null ? null : (java.util.UUID) ret;		
	}
	public void setUsersGuid(java.util.UUID value) { this.set(USERS_GUID, value); }
	
		
	/* CHILDREN */
	
		
	/* PARENTS */
	
	public <R extends UserRow> R loadUser(Connection conn, UserRowFactory<R> factory) throws Exception {
		String sql = 
			"SELECT * " + 
			"FROM " + conn.getAdapter().quoteIdentifier("Users") + " " + 
			"WHERE " + conn.getAdapter().quoteIdentifier("GUID") + "=@Value";
		
		Parameters params = new Parameters();
		params.put("@Value", this.getUsersGuid());
		
		Table<R> table = conn.fill(factory, sql, params);
		if(table.size() != 1)
			throw new RecordNotFoundException("Could not load unique row for 'Users'.'GUID' having a value of " + this.getUsersGuid().toString() + "!");
		
		return table.get(0);
	}
	
	public <R extends GroupRow> R loadGroup(Connection conn, GroupRowFactory<R> factory) throws Exception {
		String sql = 
			"SELECT * " + 
			"FROM " + conn.getAdapter().quoteIdentifier("Groups") + " " + 
			"WHERE " + conn.getAdapter().quoteIdentifier("GUID") + "=@Value";
		
		Parameters params = new Parameters();
		params.put("@Value", this.getGroupsGuid());
		
		Table<R> table = conn.fill(factory, sql, params);
		if(table.size() != 1)
			throw new RecordNotFoundException("Could not load unique row for 'Groups'.'GUID' having a value of " + this.getGroupsGuid().toString() + "!");
		
		return table.get(0);
	}
	
	
}
