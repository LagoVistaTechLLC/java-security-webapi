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

import com.lagovistatech.database.Connection;
import com.lagovistatech.database.Parameters;
import com.lagovistatech.database.RecordNotFoundException;
import com.lagovistatech.database.Table;

public class PermissionRowFactoryImp<R extends PermissionRow> implements PermissionRowFactory<R> {
	protected PermissionRowFactoryImp() {}
	
	@SuppressWarnings("unchecked")
	public R create() { return (R) new PermissionRowImp(new HashMap<String, Object>()); }

	/* UNIQUES */
	
	public R loadByGuid(Connection conn, java.util.UUID value) throws Exception {
		String sql = 
			"SELECT * " + 
			"FROM " + conn.getAdapter().quoteIdentifier("Permissions") + " " + 
			"WHERE " + conn.getAdapter().quoteIdentifier("GUID") + "=@Value";
		
		Parameters params = new Parameters();
		params.put("@Value", value);
		
		Table<R> table = conn.fill(this, sql, params);
		if(table.size() != 1)
			throw new RecordNotFoundException("Could not load unique row for 'GUID' having a value of " + value.toString() + "!");
		
		return table.get(0);
	}
	
	
}
