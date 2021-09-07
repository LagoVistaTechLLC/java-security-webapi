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

import com.lagovistatech.security.webapi.entities.Securable;

public class SecurableDto implements DtoCopier<Securable> {
	
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

	public static final SecurableDtoCopier copier = new SecurableDtoCopier();
	public void copyFrom(Securable source) { copier.copy(source, this); }
	public void copyTo(Securable destination) { copier.copy(this, destination); }
}
