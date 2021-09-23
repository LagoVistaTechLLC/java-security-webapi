package com.lagovistatech.security.webapi.entities;

import java.util.Calendar;
import java.util.HashMap;

import com.lagovistatech.Helpers;
import com.lagovistatech.crypto.PasswordHasher;
import com.lagovistatech.database.Connection;
import com.lagovistatech.database.Parameters;
import com.lagovistatech.database.Table;
import com.lagovistatech.security.dto.UserDto;
import com.lagovistatech.security.dto.UserDtoCopier;
import com.lagovistatech.security.webapi.generated.GroupRow;
import com.lagovistatech.security.webapi.generated.GroupRowFactory;
import com.lagovistatech.security.webapi.generated.SecurableActionRowFactory;
import com.lagovistatech.security.webapi.generated.UserRowImp;

public class UserImp extends UserRowImp implements User {	

	private PasswordHasher hasher;
	protected UserImp(HashMap<String, Object> values, PasswordHasher hasher) {
		super(values);
		
		this.hasher = hasher;
	}

	public void resetPassword(Session session, String newPassword, String confirmPassword) throws Exception {
		if(!session.isAdministration())
			throw new AccessDeniedException("Only administrators can reset a password!");
		
		unsecuredResetPassword(session, newPassword, confirmPassword);
	}
	private void unsecuredResetPassword(Session session, String newPassword, String confirmPassword) throws Exception { 
		if(newPassword == null || confirmPassword == null)
			throw new Exception("You must specify a new and confirm password");
		if(!newPassword.equals(confirmPassword))
			throw new PasswordMismatchException("The new and confirm passwords do not match!");
		
		this.setPasswordHash(hasher.calculate(newPassword));
		this.setPasswordIterations(hasher.getItterations());
		this.setPasswordSalt(hasher.getSalt());
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
		if(this.getPasswordIterations() == null || this.getPasswordHash() == null || this.getPasswordSalt() == null)
			return false;
		
		hasher.setSalt(this.getPasswordSalt());
		hasher.setIterations(getPasswordIterations());
		byte[] potential = hasher.calculate(password);
		byte[] actual = this.getPasswordHash();
		
		if(potential.length != actual.length)
			return false;
		
		for(int cnt = 0; cnt < potential.length; cnt++)
			if(potential[cnt] != actual[cnt])
				return false;
		
		return true;
	}
	public void changePassword(Session session, String currentPassword, String newPassword, String confirmPassword) throws Exception {
		if(newPassword == null || confirmPassword == null)
			throw new Exception("You must provide a new password!");
		if(!newPassword.equals(confirmPassword))
			throw new Exception("The new password and confirm password do not match!");
		if(newPassword.length() < 1)
			throw new Exception("You must provide a new password!");

		int minLength = Integer.parseInt(session.getSettingsByKey().get(User.SETTING_MINIMUM_PASSWORD_LENGTH).getValue());
		if(newPassword.length() < minLength)
			throw new Exception("The password does not meet the length requirements of at least " + minLength + " charaters long!");
		
		int minComplexity = Integer.parseInt(session.getSettingsByKey().get(User.SETTING_MINIMUM_PASSWORD_COMPLEXITY).getValue());
		if(PasswordHasher.calculateComplexity(newPassword) < minComplexity)
			throw new Exception("The password does not meet complexity requirements of at least " + minComplexity + " charater catagories (lower case, upper case, numbers, and symbols)!");

		if(!validatePassword(currentPassword))
			throw new InvalidLoginException("Could not validate current password!");	
		
		unsecuredResetPassword(session, newPassword, confirmPassword);
	}
	
	public <R extends GroupRow> Table<R> loadMyGroups(Connection conn, GroupRowFactory<R> factory) throws Exception {
		String sql = Helpers.readResourceAsString(getClass(), "/UserImp.loadMyGroups.sql");
		
		Parameters params = new Parameters();
		params.put("@UsersGuid", this.getGuid());
		
		return conn.fill(factory, sql, params);
	}
	public <R extends SecurableAction> Table<R> loadSecurableActions(Connection connection, SecurableActionRowFactory<R> factory) throws Exception {
		String sql = Helpers.readResourceAsString(getClass(), "/UserImp.loadSecurableActions.sql");
		
		Parameters params = new Parameters();
		params.put("@UsersGuid", this.getGuid());
		params.put("@EveryonesGuid", Group.EVERYONES_GUID);
		
		return connection.fill(factory, sql, params);
	}

	private static final UserDtoCopier copier = new UserDtoCopier();
	public void copyFrom(UserDto source) { copier.copy(source, this); }
	public void copyTo(UserDto destination) { copier.copy(this, destination); }
}
