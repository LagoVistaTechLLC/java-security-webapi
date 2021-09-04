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

public class ActionDtoCopier {
	public void copy(ActionDto source, Action destination) {
		
		destination.setDisplayName(source.getDisplayName());
		
		destination.setAbbreviation(source.getAbbreviation());
		
		destination.setVersion(source.getVersion());
		
		destination.setGuid(source.getGuid());
		
	}
	public void copy(Action source, ActionDto destination) {
		
		destination.setDisplayName(source.getDisplayName());
		
		destination.setAbbreviation(source.getAbbreviation());
		
		destination.setVersion(source.getVersion());
		
		destination.setGuid(source.getGuid());
		
	}
}
