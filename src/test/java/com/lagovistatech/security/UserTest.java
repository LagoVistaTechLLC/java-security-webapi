package com.lagovistatech.security;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.lagovistatech.database.Connection;
import com.lagovistatech.database.Table;
import com.lagovistatech.database.TableFactory;
import com.lagovistatech.security.webapi.entities.AccessDeniedException;
import com.lagovistatech.security.webapi.entities.Group;
import com.lagovistatech.security.webapi.entities.InvalidLoginException;
import com.lagovistatech.security.webapi.entities.Membership;
import com.lagovistatech.security.webapi.entities.MembershipFactory;
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
		TestDatabase.runTest(connection -> {
			Table<User> table = TableFactory.instanciate(UserFactory.instance);
			
			User adminUser = table.createRow();
			adminUser.setDisabled(false);
			adminUser.setDisplayName("Admin User");
			adminUser.setEmailAddress("admin@localhost");
			adminUser.setUserName("admin");

			User user = table.createRow();
			user.setDisabled(false);
			user.setDisplayName("Test User");
			user.setEmailAddress("test@localhost");
			user.setUserName("test");

			connection.save(table);
			
			Membership membership = TableFactory.instanciate(MembershipFactory.instance).createRow();
			membership.setGroupsGuid(Group.ADMINISTRATORS_GUID);
			membership.setUsersGuid(adminUser.getGuid());
			
			connection.save(membership.getTable());
			
			Session session = SessionFactory.instance.create();
			session.login(connection, "administrator", "Welcome123");
			
			adminUser = UserFactory.instance.loadByUserName(connection, "admin");
			adminUser.resetPassword(session, "Welcome123", "Welcome123");
			
			session = SessionFactory.instance.create();
			session.login(connection, "admin", "Welcome123");
			
			user = UserFactory.instance.loadByUserName(connection, "admin");
			user.resetPassword(session, "Welcome123", "Welcome123");
			
			assertTrue(true);
		});
	}
	@Test
	void ResetPassword_NotAdministrator() throws Exception {
		TestDatabase.runTest(connection -> {
			Table<User> table = TableFactory.instanciate(UserFactory.instance);
			
			User user1 = table.createRow();
			user1.setDisabled(false);
			user1.setDisplayName("Test User 1");
			user1.setEmailAddress("test1@localhost");
			user1.setUserName("test1");

			User user2 = table.createRow();
			user2.setDisabled(false);
			user2.setDisplayName("Test User 2");
			user2.setEmailAddress("test2@localhost");
			user2.setUserName("test2");

			connection.save(table);
			
			Session session = SessionFactory.instance.create();
			session.login(connection, "administrator", "Welcome123");
			
			user1 = UserFactory.instance.loadByUserName(connection, "test1");
			user1.resetPassword(session, "Welcome123", "Welcome123");
			
			session = SessionFactory.instance.create();
			session.login(connection, "test1", "Welcome123");
			
			user2 = UserFactory.instance.loadByUserName(connection, "test2");
			try { user2.resetPassword(session, "Welcome123", "Welcome123"); }
			catch(AccessDeniedException ex) { 
				assertTrue(true);
				return;
			}
			
			fail("An AccessDeniedException should have been thrown!");
		});
	}

	private User createTestUser(Connection connection) throws Exception {
		User user = TableFactory.instanciate(UserFactory.instance).createRow();
		user.setDisabled(false);
		user.setDisplayName("Test User");
		user.setEmailAddress("test@localhost");
		user.setUserName("test");
		
		connection.save(user.getTable());
		
		Session session = SessionFactory.instance.create();
		session.login(connection, "administrator", "Welcome123");
		
		user = UserFactory.instance.loadByUserName(connection, "test");
		user.resetPassword(session, "Welcome123", "Welcome123");
		return user;
	}
	
	@Test
	void ChangePassword_Success() throws Exception {
		TestDatabase.runTest(connection -> {
			User user = createTestUser(connection);
			
			Session session = SessionFactory.instance.create();
			session.login(connection, "test", "Welcome123");
			
			user.changePassword(session, "Welcome123", "Welcome456!", "Welcome456!");
		});
	}
	@Test
	void ChangePassword_ComplexityFail() throws Exception {
		TestDatabase.runTest(connection -> {
			User user = createTestUser(connection);
			
			Session session = SessionFactory.instance.create();
			session.login(connection, "test", "Welcome123");
			
			try { user.changePassword(session, "Welcome123", "welcomeshawn", "welcomeshawn"); }
			catch(Exception ex) {
				assertTrue(ex.getMessage().contains("The password does not meet complexity requirements of at least"));
				return;
			}
			
			fail("An exception should have been thrown!");
		});
	}
	@Test
	void ChangePassword_LengthFail() throws Exception {
		TestDatabase.runTest(connection -> {
			User user = createTestUser(connection);
			
			Session session = SessionFactory.instance.create();
			session.login(connection, "test", "Welcome123");
			
			try { user.changePassword(session, "Welcome123", "w!2", "w!2"); }
			catch(Exception ex) {
				assertTrue(ex.getMessage().contains("The password does not meet the length requirements of at least"));
				return;
			}
			
			fail("An exception should have been thrown!");
		});
	}
	@Test
	void ChangePassword_Missmatch() throws Exception {
		TestDatabase.runTest(connection -> {
			User user = createTestUser(connection);
			
			Session session = SessionFactory.instance.create();
			session.login(connection, "test", "Welcome123");
			
			try { user.changePassword(session, "Welcome123", "Welcome123!", "Welcome456!"); }
			catch(Exception ex) {
				assertTrue(ex.getMessage().contains("The new password and confirm password do not match!"));
				return;
			}
			
			fail("An exception should have been thrown!");
		});
	}
	@Test
	void ChangePassword_Empty() throws Exception {
		TestDatabase.runTest(connection -> {
			User user = createTestUser(connection);
			
			Session session = SessionFactory.instance.create();
			session.login(connection, "test", "Welcome123");
			
			try { user.changePassword(session, "Welcome123", "", ""); }
			catch(Exception ex) {
				assertTrue(ex.getMessage().contains("You must provide a new password!"));
				return;
			}
			
			fail("An exception should have been thrown!");
		});
	}
	@Test
	void ChangePassword_LoginFail() throws Exception {
		TestDatabase.runTest(connection -> {
			User user = createTestUser(connection);
			
			Session session = SessionFactory.instance.create();
			session.login(connection, "test", "Welcome123");
			
			try { user.changePassword(session, "BadPassword", "Welcome456", "Welcome456"); }
			catch(InvalidLoginException ex) {
				assertTrue(true);
				return;
			}
			
			fail("Sould have thrown an InvalidLoginException!");
		});
	}
}
