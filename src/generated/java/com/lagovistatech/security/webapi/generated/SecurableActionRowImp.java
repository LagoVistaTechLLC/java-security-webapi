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
public class SecurableActionRowImp extends VersionedRow implements SecurableActionRow {
	public static final String TABLE_NAME = SecurableActionRow.TABLE_NAME;
	public static final String SECURABLE = SecurableActionRow.SECURABLE;

	protected SecurableActionRowImp(HashMap<String, Object> values) { super(values); }	
	
	/* COLUMNS */
	
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
	
	public java.util.UUID getSecurablesGuid() {
		Object ret = this.get(SECURABLES_GUID);
		return ret == null ? null : (java.util.UUID) ret;		
	}
	public void setSecurablesGuid(java.util.UUID value) { this.set(SECURABLES_GUID, value); }
	
	public java.util.UUID getActionsGuid() {
		Object ret = this.get(ACTIONS_GUID);
		return ret == null ? null : (java.util.UUID) ret;		
	}
	public void setActionsGuid(java.util.UUID value) { this.set(ACTIONS_GUID, value); }
	
		
	/* CHILDREN */
	
	public <R extends PermissionRow> Table<R> loadPermissionsBySecurableActionsGuidEqualsMyGuid(Connection conn, PermissionRowFactory<R> factory) throws Exception {
		String sql = 
			"SELECT * " + 
			"FROM " + conn.getAdapter().quoteIdentifier("Permissions") + " " + 
			"WHERE " + conn.getAdapter().quoteIdentifier("Securable Actions GUID") + "=@Value";
		
		Parameters params = new Parameters();
		params.put("@Value", this.getGuid());
		
		return conn.fill(factory, sql, params);
	}
	
		
	/* PARENTS */
	
	public <R extends SecurableRow> R loadSecurableByMySecurablesGuid(Connection conn, SecurableRowFactory<R> factory) throws Exception {
		String sql = 
			"SELECT * " + 
			"FROM " + conn.getAdapter().quoteIdentifier("Securables") + " " + 
			"WHERE " + conn.getAdapter().quoteIdentifier("GUID") + "=@Value";
		
		Parameters params = new Parameters();
		params.put("@Value", this.getSecurablesGuid());
		
		Table<R> table = conn.fill(factory, sql, params);
		if(table.size() != 1)
			throw new RecordNotFoundException("Could not load unique row for 'Securables'.'GUID' having a value of " + this.getSecurablesGuid().toString() + "!");
		
		return table.get(0);
	}
	
	public <R extends ActionRow> R loadActionByMyActionsGuid(Connection conn, ActionRowFactory<R> factory) throws Exception {
		String sql = 
			"SELECT * " + 
			"FROM " + conn.getAdapter().quoteIdentifier("Actions") + " " + 
			"WHERE " + conn.getAdapter().quoteIdentifier("GUID") + "=@Value";
		
		Parameters params = new Parameters();
		params.put("@Value", this.getActionsGuid());
		
		Table<R> table = conn.fill(factory, sql, params);
		if(table.size() != 1)
			throw new RecordNotFoundException("Could not load unique row for 'Actions'.'GUID' having a value of " + this.getActionsGuid().toString() + "!");
		
		return table.get(0);
	}
	
	
}
