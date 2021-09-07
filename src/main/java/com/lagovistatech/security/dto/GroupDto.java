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

import com.lagovistatech.security.webapi.entities.Group;

public class GroupDto implements DtoCopier<Group> {
	
	private java.lang.String myDisplayName;
	public java.lang.String getDisplayName() { return myDisplayName; } 
	public void setDisplayName(java.lang.String value) { myDisplayName = value; }
	
	private java.lang.Long myVersion;
	public java.lang.Long getVersion() { return myVersion; } 
	public void setVersion(java.lang.Long value) { myVersion = value; }
	
	private java.util.UUID myGuid;
	public java.util.UUID getGuid() { return myGuid; } 
	public void setGuid(java.util.UUID value) { myGuid = value; }
	
	private java.lang.Boolean myDisabled;
	public java.lang.Boolean getDisabled() { return myDisabled; } 
	public void setDisabled(java.lang.Boolean value) { myDisabled = value; }
	
	private java.lang.String myEmailAddress;
	public java.lang.String getEmailAddress() { return myEmailAddress; } 
	public void setEmailAddress(java.lang.String value) { myEmailAddress = value; }
		
	public static final GroupDtoCopier copier = new GroupDtoCopier();
	public void copyFrom(Group source) { copier.copy(source, this); }
	public void copyTo(Group destination) { copier.copy(this, destination); }
}
