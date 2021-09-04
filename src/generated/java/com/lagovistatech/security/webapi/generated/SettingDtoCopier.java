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

public class SettingDtoCopier {
	public void copy(SettingDto source, Setting destination) {
		
		destination.setVersion(source.getVersion());
		
		destination.setServerSideOnly(source.getServerSideOnly());
		
		destination.setGuid(source.getGuid());
		
		destination.setValue(source.getValue());
		
		destination.setUsersGuid(source.getUsersGuid());
		
		destination.setKey(source.getKey());
		
	}
	public void copy(Setting source, SettingDto destination) {
		
		destination.setVersion(source.getVersion());
		
		destination.setServerSideOnly(source.getServerSideOnly());
		
		destination.setGuid(source.getGuid());
		
		destination.setValue(source.getValue());
		
		destination.setUsersGuid(source.getUsersGuid());
		
		destination.setKey(source.getKey());
		
	}
}
