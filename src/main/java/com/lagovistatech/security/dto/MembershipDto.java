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

import com.lagovistatech.security.webapi.entities.Membership;

public class MembershipDto implements DtoCopier<Membership> {
	
	private java.lang.Long myVersion;
	public java.lang.Long getVersion() { return myVersion; } 
	public void setVersion(java.lang.Long value) { myVersion = value; }
	
	private java.util.UUID myGroupsGuid;
	public java.util.UUID getGroupsGuid() { return myGroupsGuid; } 
	public void setGroupsGuid(java.util.UUID value) { myGroupsGuid = value; }
	
	private java.lang.Boolean myIncluded;
	public java.lang.Boolean getIncluded() { return myIncluded; } 
	public void setIncluded(java.lang.Boolean value) { myIncluded = value; }
	
	private java.util.UUID myGuid;
	public java.util.UUID getGuid() { return myGuid; } 
	public void setGuid(java.util.UUID value) { myGuid = value; }
	
	private java.util.UUID myUsersGuid;
	public java.util.UUID getUsersGuid() { return myUsersGuid; } 
	public void setUsersGuid(java.util.UUID value) { myUsersGuid = value; }
		
	public static final MembershipDtoCopier copier = new MembershipDtoCopier();
	public void copyFrom(Membership source) { copier.copy(source, this); }
	public void copyTo(Membership destination) { copier.copy(this, destination); }
}
