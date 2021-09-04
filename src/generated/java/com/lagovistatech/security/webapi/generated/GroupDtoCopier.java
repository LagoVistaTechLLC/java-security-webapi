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

public class GroupDtoCopier {
	public void copy(GroupDto source, Group destination) {
		
		destination.setDisplayName(source.getDisplayName());
		
		destination.setVersion(source.getVersion());
		
		destination.setGuid(source.getGuid());
		
		destination.setDisabled(source.getDisabled());
		
		destination.setEmailAddress(source.getEmailAddress());
		
	}
	public void copy(Group source, GroupDto destination) {
		
		destination.setDisplayName(source.getDisplayName());
		
		destination.setVersion(source.getVersion());
		
		destination.setGuid(source.getGuid());
		
		destination.setDisabled(source.getDisabled());
		
		destination.setEmailAddress(source.getEmailAddress());
		
	}
}
