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
public class GroupRowImp extends VersionedRow implements GroupRow {
	public static final String TABLE_NAME = GroupRow.TABLE_NAME;
	public static final String SECURABLE = GroupRow.SECURABLE;

	protected GroupRowImp(HashMap<String, Object> values) { super(values); }	
	
	/* COLUMNS */
	
	public java.lang.String getDisplayName() {
		Object ret = this.get(DISPLAY_NAME);
		return ret == null ? null : (java.lang.String) ret;		
	}
	public void setDisplayName(java.lang.String value) { this.set(DISPLAY_NAME, value); }
	
	public java.lang.Long getVersion() {
		Object ret = this.get(VERSION);
		return ret == null ? null : (java.lang.Long) ret;		
	}
	public void setVersion(java.lang.Long value) { this.set(VERSION, value); }
	
	public java.util.UUID getGuid() {
		Object ret = this.get(GUID);
		return ret == null ? null : (java.util.UUID) ret;		
	}
	public void setGuid(java.util.UUID value) { this.set(GUID, value); }
	
	public java.lang.Boolean getDisabled() {
		Object ret = this.get(DISABLED);
		return ret == null ? null : (java.lang.Boolean) ret;		
	}
	public void setDisabled(java.lang.Boolean value) { this.set(DISABLED, value); }
	
	public java.lang.String getEmailAddress() {
		Object ret = this.get(EMAIL_ADDRESS);
		return ret == null ? null : (java.lang.String) ret;		
	}
	public void setEmailAddress(java.lang.String value) { this.set(EMAIL_ADDRESS, value); }
	
		
	/* CHILDREN */
	
	public <R extends MembershipRow> Table<R> loadMemberships(Connection conn, MembershipRowFactory<R> factory) throws Exception {
		String sql = 
			"SELECT * " + 
			"FROM " + conn.getAdapter().quoteIdentifier("Memberships") + " " + 
			"WHERE " + conn.getAdapter().quoteIdentifier("Groups GUID") + "=@Value";
		
		Parameters params = new Parameters();
		params.put("@Value", this.getGuid());
		
		return conn.fill(factory, sql, params);
	}
	
	public <R extends PermissionRow> Table<R> loadPermissions(Connection conn, PermissionRowFactory<R> factory) throws Exception {
		String sql = 
			"SELECT * " + 
			"FROM " + conn.getAdapter().quoteIdentifier("Permissions") + " " + 
			"WHERE " + conn.getAdapter().quoteIdentifier("Groups GUID") + "=@Value";
		
		Parameters params = new Parameters();
		params.put("@Value", this.getGuid());
		
		return conn.fill(factory, sql, params);
	}
	
	public <R extends SettingRow> Table<R> loadSettings(Connection conn, SettingRowFactory<R> factory) throws Exception {
		String sql = 
			"SELECT * " + 
			"FROM " + conn.getAdapter().quoteIdentifier("Settings") + " " + 
			"WHERE " + conn.getAdapter().quoteIdentifier("Groups GUID") + "=@Value";
		
		Parameters params = new Parameters();
		params.put("@Value", this.getGuid());
		
		return conn.fill(factory, sql, params);
	}
	
		
	/* PARENTS */
	
	
}
