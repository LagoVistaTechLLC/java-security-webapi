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

import com.lagovistatech.security.webapi.entities.Action;

public class ActionDto implements DtoCopier<Action> {
	
	private java.lang.String myDisplayName;
	public java.lang.String getDisplayName() { return myDisplayName; } 
	public void setDisplayName(java.lang.String value) { myDisplayName = value; }
	
	private java.lang.String myAbbreviation;
	public java.lang.String getAbbreviation() { return myAbbreviation; } 
	public void setAbbreviation(java.lang.String value) { myAbbreviation = value; }
	
	private java.lang.Long myVersion;
	public java.lang.Long getVersion() { return myVersion; } 
	public void setVersion(java.lang.Long value) { myVersion = value; }
	
	private java.util.UUID myGuid;
	public java.util.UUID getGuid() { return myGuid; } 
	public void setGuid(java.util.UUID value) { myGuid = value; }

	public static final ActionDtoCopier copier = new ActionDtoCopier();
	public void copyFrom(Action source) { copier.copy(source, this); }
	public void copyTo(Action destination) { copier.copy(this, destination); }

}
