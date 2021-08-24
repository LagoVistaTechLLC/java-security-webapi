package com.lagovistatech.security;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Date;

import org.junit.jupiter.api.Test;

import com.lagovistatech.database.TableFactory;
import com.lagovistatech.security.webapi.entities.Action;
import com.lagovistatech.security.webapi.entities.ActionFactory;
import com.lagovistatech.security.webapi.entities.InvalidLoginException;
import com.lagovistatech.security.webapi.entities.PasswordExpiredException;
import com.lagovistatech.security.webapi.entities.Securable;
import com.lagovistatech.security.webapi.entities.SecurableFactory;
import com.lagovistatech.security.webapi.entities.Session;
import com.lagovistatech.security.webapi.entities.SessionFactory;
import com.lagovistatech.security.webapi.entities.User;
import com.lagovistatech.security.webapi.entities.UserFactory;

class SessionTest {	
	@Test
	void Login_Success() throws Exception {
		TestDatabase.runTest(connection -> {
			Session session = SessionFactory.instance.create();
			session.login(connection, "administrator", "Welcome123");
			
			assertTrue(session.getUser().getUserName().equals("administrator"));
			assertTrue(session.getGroups().size() == 2);
			assertTrue(session.getSettingByKey(User.SETTING_MAXIMUM_PASSWORD_AGE_IN_DAYS) != null);
		});
	}
	@Test
	void Login_BadUser() throws Exception {
		TestDatabase.runTest(connection -> {
			try {
				Session session = SessionFactory.instance.create();
				session.login(connection, "bad", "Welcome123");
				fail("No exception thrown for invalid login.");				
			}
			catch(InvalidLoginException ex) {
				assertTrue(true);
			}			
		});
	}
	@Test
	void Login_BadPassword() throws Exception {
		TestDatabase.runTest(connection -> {
			try {
				Session session = SessionFactory.instance.create();
				session.login(connection, "administrator", "bad");
				fail("No exception thrown for invalid login!");
			}
			catch(InvalidLoginException ex) {
				assertTrue(true);
			}			
		});
	}
	@Test
	void Login_PasswordExpired() throws Exception {
		TestDatabase.runTest(connection -> {
			try {
				User admin = UserFactory.instance.loadByUserName(connection, "administrator");
				Date expired = Date.valueOf("2000-1-1");
				admin.setPasswordDate(expired);
				
				connection.save(admin.getTable());
				
				Session session = SessionFactory.instance.create();
				session.login(connection, "administrator", "Welcome123");
				fail("No exception thrown for expired password!");
			}
			catch(PasswordExpiredException ex) {
				assertTrue(true);
			}			
		});
	}
	
	@Test
	void IsAllowed_AdministratorUser() throws Exception {
		TestDatabase.runTest(connection -> {
			Securable securable = TableFactory.instanciate(SecurableFactory.instance).createRow();
			securable.setDisabled(false);
			securable.setDisplayName("Test Securable");
			connection.save(securable.getTable());
			securable = SecurableFactory.instance.loadByDisplayName(connection, "Test Securable");
			
			Session session = SessionFactory.instance.create();
			session.login(connection, "administrator", "Welcome123");
			assertTrue(session.isAllowed(securable.getGuid(), Action.READ_GUID));
		});
	}
	@Test
	void IsAllowed_AdministratorGroup() throws Exception {
		TestDatabase.runTest(connection -> {
			try {
				User user = TableFactory.instanciate(UserFactory.instance).createRow();
				user.setDisplayName("Test User");
				user.setDisabled(false);
				user.setEmailAddress("test@localhost");
				((User) user).setUserName("test");
				connection.save(user.getTable());
				
				Session session = SessionFactory.instance.create();
				session.login(connection, "administrator", "Welcome123");

				user = UserFactory.instance.loadByUserName(connection, "test");
				user.resetPassword(session, "Welcome123", "Welcome123");
				
				Securable securable = TableFactory.instanciate(SecurableFactory.instance).createRow();
				securable.setDisabled(false);
				securable.setDisplayName("Test Securable");
				connection.save(securable.getTable());
				securable = SecurableFactory.instance.loadByDisplayName(connection, "Test Securable");

				session = SessionFactory.instance.create();
				session.login(connection, "test", "Welcome123");
				
				assertTrue(false);
				//assertTrue(session.isAllowed(securable.getGuid(), Action.READ_GUID));
			}
			catch(PasswordExpiredException ex) {
				assertTrue(false);
			}			
		});
	}
	@Test
	void IsAllowed_EveryoneAllowed() {
		assertTrue(false);
	}
	@Test 
	void IsAllowed_GroupAllowed() {
		assertTrue(false);
	}
	@Test 
	void IsAllowed_False() {
		assertTrue(false);		
	}
}
