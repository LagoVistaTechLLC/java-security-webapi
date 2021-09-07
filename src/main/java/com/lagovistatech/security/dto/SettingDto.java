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
package com.lagovistatech.security.dto;

import com.lagovistatech.security.webapi.entities.Setting;

public class SettingDto implements DtoCopier<Setting> {
	
	private java.lang.Long myVersion;
	public java.lang.Long getVersion() { return myVersion; } 
	public void setVersion(java.lang.Long value) { myVersion = value; }
	
	private java.lang.Boolean myServerSideOnly;
	public java.lang.Boolean getServerSideOnly() { return myServerSideOnly; } 
	public void setServerSideOnly(java.lang.Boolean value) { myServerSideOnly = value; }
	
	private java.util.UUID myGuid;
	public java.util.UUID getGuid() { return myGuid; } 
	public void setGuid(java.util.UUID value) { myGuid = value; }
	
	private java.lang.String myValue;
	public java.lang.String getValue() { return myValue; } 
	public void setValue(java.lang.String value) { myValue = value; }
	
	private java.util.UUID myUsersGuid;
	public java.util.UUID getUsersGuid() { return myUsersGuid; } 
	public void setUsersGuid(java.util.UUID value) { myUsersGuid = value; }
	
	private java.lang.String myKey;
	public java.lang.String getKey() { return myKey; } 
	public void setKey(java.lang.String value) { myKey = value; }

	public static final SettingDtoCopier copier = new SettingDtoCopier();
	public void copyFrom(Setting source) { copier.copy(source, this); }
	public void copyTo(Setting destination) { copier.copy(this, destination); }
}
