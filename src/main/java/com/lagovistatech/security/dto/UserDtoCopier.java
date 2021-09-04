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

import com.lagovistatech.security.webapi.entities.User;

public class UserDtoCopier {
	public void copy(UserDto source, User destination) {
		
		destination.setDisplayName(source.getDisplayName());
		
		destination.setVersion(source.getVersion());
		
		destination.setUserName(source.getUserName());
		
		destination.setGuid(source.getGuid());
		
		destination.setDisabled(source.getDisabled());
		
		destination.setMobilePhone(source.getMobilePhone());
		
		destination.setEmailAddress(source.getEmailAddress());
		
		destination.setPasswordDate(source.getPasswordDate());
		
	}
	public void copy(User source, UserDto destination) {
		
		destination.setDisplayName(source.getDisplayName());
		
		destination.setVersion(source.getVersion());
		
		destination.setUserName(source.getUserName());
		
		destination.setGuid(source.getGuid());
		
		destination.setDisabled(source.getDisabled());
		
		destination.setMobilePhone(source.getMobilePhone());
		
		destination.setEmailAddress(source.getEmailAddress());
		
		destination.setPasswordDate(source.getPasswordDate());
		
	}
}
