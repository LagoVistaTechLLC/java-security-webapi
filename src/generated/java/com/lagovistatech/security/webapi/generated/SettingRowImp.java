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
public class SettingRowImp extends VersionedRow implements SettingRow {
	public static final String TABLE_NAME = SettingRow.TABLE_NAME;
	public static final String SECURABLE = SettingRow.SECURABLE;

	protected SettingRowImp(HashMap<String, Object> values) { super(values); }	
	
	/* COLUMNS */
	
	public java.lang.Long getVersion() {
		Object ret = this.get(VERSION);
		return ret == null ? null : (java.lang.Long) ret;		
	}
	public void setVersion(java.lang.Long value) { this.set(VERSION, value); }
	
	public java.lang.Boolean getServerSideOnly() {
		Object ret = this.get(SERVER_SIDE_ONLY);
		return ret == null ? null : (java.lang.Boolean) ret;		
	}
	public void setServerSideOnly(java.lang.Boolean value) { this.set(SERVER_SIDE_ONLY, value); }
	
	public java.util.UUID getGuid() {
		Object ret = this.get(GUID);
		return ret == null ? null : (java.util.UUID) ret;		
	}
	public void setGuid(java.util.UUID value) { this.set(GUID, value); }
	
	public java.lang.String getValue() {
		Object ret = this.get(VALUE);
		return ret == null ? null : (java.lang.String) ret;		
	}
	public void setValue(java.lang.String value) { this.set(VALUE, value); }
	
	public java.util.UUID getUsersGuid() {
		Object ret = this.get(USERS_GUID);
		return ret == null ? null : (java.util.UUID) ret;		
	}
	public void setUsersGuid(java.util.UUID value) { this.set(USERS_GUID, value); }
	
	public java.lang.String getKey() {
		Object ret = this.get(KEY);
		return ret == null ? null : (java.lang.String) ret;		
	}
	public void setKey(java.lang.String value) { this.set(KEY, value); }
	
		
	/* CHILDREN */
	
		
	/* PARENTS */
	
	
}
