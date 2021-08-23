package com.lagovistatech.security;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.lagovistatech.database.Table;
import com.lagovistatech.database.TableFactory;
import com.lagovistatech.security.webapi.entities.PasswordMismatchException;
import com.lagovistatech.security.webapi.entities.Session;
import com.lagovistatech.security.webapi.entities.SessionFactory;
import com.lagovistatech.security.webapi.entities.User;
import com.lagovistatech.security.webapi.entities.UserFactory;

class UserTest {
	@Test
	void ResetPassword_Success() throws Exception {
		TestDatabase.runTest(connection -> {
			Table<User> table = TableFactory.instanciate(UserFactory.instance);
			
			User user = table.createRow();
			user.setDisabled(false);
			user.setDisplayName("Test User");
			user.setEmailAddress("test@localhost");
			user.setUserName("test");
			
			connection.save(table);
			
			Session session = SessionFactory.instance.create();
			session.login(connection, "administrator", "Welcome123");
			
			user = UserFactory.instance.loadByUserName(connection, "test");
			user.resetPassword(session, "Welcome123", "Welcome123");
		});
	}
	@Test
	void ResetPassword_Mismatch() throws Exception {
		TestDatabase.runTest(connection -> {
			Table<User> table = TableFactory.instanciate(UserFactory.instance);
			
			User user = table.createRow();
			user.setDisabled(false);
			user.setDisplayName("Test User");
			user.setEmailAddress("test@localhost");
			user.setUserName("test");
			
			connection.save(table);
			
			Session session = SessionFactory.instance.create();
			session.login(connection, "administrator", "Welcome123");
			
			user = UserFactory.instance.loadByUserName(connection, "test");
			try {
				user.resetPassword(session, "Welcome123", "Welcome456");
			}
			catch(PasswordMismatchException ex) {
				assert(true);
			}
		});
	}
	@Test
	void ResetPassword_AdminGroup_Success() throws Exception {
//		TestDatabase.runTest(connection -> {
//			Table<User> table = TableFactory.instanciate(UserFactory.instance);
//			
//			User user = table.createRow();
//			user.setDisabled(false);
//			user.setDisplayName("Test User");
//			user.setEmailAddress("test@localhost");
//			user.setUserName("test");
//			
//			connection.save(table);
//			
//			Session session = SessionFactory.instance.create();
//			session.login(connection, "administrator", "Welcome123");
//			
//			user = UserFactory.instance.loadByUserName(connection, "test");
//			try {
//				user.resetPassword(session, "Welcome123", "Welcome456");
//			}
//			catch(PasswordMismatchException ex) {
//				assert(true);
//			}
//		});
	}
	@Test
	void ResetPassword_NotAdministrator() throws Exception {
//		TestDatabase.runTest(connection -> {
//			Table<User> table = TableFactory.instanciate(UserFactory.instance);
//			
//			User user = table.createRow();
//			user.setDisabled(false);
//			user.setDisplayName("Test User");
//			user.setEmailAddress("test@localhost");
//			user.setUserName("test");
//			
//			connection.save(table);
//			
//			Session session = SessionFactory.instance.create();
//			session.login(connection, "administrator", "Welcome123");
//			
//			user = UserFactory.instance.loadByUserName(connection, "test");
//			try {
//				user.resetPassword(session, "Welcome123", "Welcome456");
//			}
//			catch(PasswordMismatchException ex) {
//				assert(true);
//			}
//		});
	}
	@Test
	void ChangePassword_ComplexityFail() {
		fail("Not yet implemented");
	}
	@Test
	void ChangePassword_Success() {
		fail("Not yet implemented");
	}
	@Test
	void ChangePassword_LengthFail() {
		fail("Not yet implemented");
	}
	@Test
	void ChangePassword_Missmatch() {
		fail("Not yet implemented");
	}
	@Test
	void ChangePassword_Empty() {
		fail("Not yet implemented");
	}
	@Test
	void ChangePassword_LoginFail() {
		fail("Not yet implemented");
	}
}
