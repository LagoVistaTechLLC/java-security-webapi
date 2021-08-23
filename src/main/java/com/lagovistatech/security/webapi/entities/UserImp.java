package com.lagovistatech.security.webapi.entities;


import java.util.Calendar;
import java.util.HashMap;

import com.lagovistatech.Helpers;
import com.lagovistatech.database.Connection;
import com.lagovistatech.database.Parameters;
import com.lagovistatech.database.Table;
import com.lagovistatech.security.webapi.generated.GroupRow;
import com.lagovistatech.security.webapi.generated.GroupRowFactory;
import com.lagovistatech.security.webapi.generated.UserRowImp;

public class UserImp extends UserRowImp implements User {
	private static final int SALT_LENGTH = 32;
	private static final int ITERATIONS = 1000000;
	private static final int KEY_LENGTH = 512;
	
	protected UserImp(HashMap<String, Object> values) {
		super(values);
	}

	public void resetPassword(Session session, String newPassword, String confirmPassword) throws Exception { 
		if(newPassword == null || confirmPassword == null)
			throw new Exception("You must specify a new and confirm password");
		if(!newPassword.equals(confirmPassword))
			throw new PasswordMismatchException("The new and confirm passwords do not match!");
		
		PasswordHasher ph = new PasswordHasher(SALT_LENGTH, ITERATIONS, KEY_LENGTH);
		this.setPasswordHash(ph.calculate(newPassword));
		this.setPasswordIterations(ITERATIONS);
		this.setPasswordSalt(ph.getSalt());
		this.setPasswordDate(new java.sql.Date(Calendar.getInstance().getTimeInMillis()));
		
		session.getConnection().save(getTable());
		
		User reloaded = UserFactory.instance.loadByGuid(session.getConnection(), this.getGuid());
		this.setPasswordHash(reloaded.getPasswordHash());
		this.setPasswordIterations(reloaded.getPasswordIterations());
		this.setPasswordSalt(reloaded.getPasswordSalt());
		this.setPasswordDate(reloaded.getPasswordDate());
		this.setVersion(reloaded.getVersion());
	}
	public boolean validatePassword(String password) throws Exception {
		PasswordHasher ph = new PasswordHasher(SALT_LENGTH, this.getPasswordIterations(), KEY_LENGTH);
		ph.setSalt(this.getPasswordSalt());
		byte[] potential = ph.calculate(password);
		byte[] actual = this.getPasswordHash();
		
		if(potential.length != actual.length)
			return false;
		
		for(int cnt = 0; cnt < potential.length; cnt++)
			if(potential[cnt] != actual[cnt])
				return false;
		
		return true;
	}
	
	public <R extends GroupRow> Table<R> loadMyGroups(Connection conn, GroupRowFactory<R> factory) throws Exception {
		String sql = Helpers.readResourceAsString(getClass(), "/UserImp.loadMyGroups.sql");
		
		Parameters params = new Parameters();
		params.put("@UsersGuid", this.getGuid());
		
		return conn.fill(factory, sql, params);
	}
}
