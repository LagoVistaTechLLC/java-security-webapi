package com.lagovistatech.security.webapi.test;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Date;

import org.junit.jupiter.api.Test;

import com.lagovistatech.database.Table;
import com.lagovistatech.database.TableFactory;
import com.lagovistatech.security.webapi.entities.Action;
import com.lagovistatech.security.webapi.entities.Group;
import com.lagovistatech.security.webapi.entities.InvalidLoginException;
import com.lagovistatech.security.webapi.entities.Membership;
import com.lagovistatech.security.webapi.entities.MembershipFactory;
import com.lagovistatech.security.webapi.entities.PasswordExpiredException;
import com.lagovistatech.security.webapi.entities.Permission;
import com.lagovistatech.security.webapi.entities.PermissionFactory;
import com.lagovistatech.security.webapi.entities.Securable;
import com.lagovistatech.security.webapi.entities.SecurableAction;
import com.lagovistatech.security.webapi.entities.SecurableActionFactory;
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
			} catch(InvalidLoginException ex) {
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
			} catch(InvalidLoginException ex) {
				assertTrue(true);
			}
		});
	}
	@Test
	void Login_PasswordExpired() throws Exception {
		TestDatabase.runTest(connection -> {
			User admin = UserFactory.instance.loadByUserName(connection, "administrator");
			Date expired = Date.valueOf("2000-1-1");
			admin.setPasswordDate(expired);

			connection.save(admin.getTable());

			Session session = SessionFactory.instance.create();
			session.login(connection, "administrator", "Welcome123");
			assertTrue(session.isPasswordExpired());
		});
	}
	@Test
	void Login_Disabled() throws Exception {
		TestDatabase.runTest(connection -> {
			try {
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
				user.setDisabled(true);

				connection.save(user.getTable());

				session = SessionFactory.instance.create();
				session.login(connection, "test", "Welcome123");
			} catch(InvalidLoginException ex) {
				assertTrue(true);
			}
		});
	}
	@Test
	void Login_PasswordNotSet() throws Exception {
		TestDatabase.runTest(connection -> {
			Table<User> table = TableFactory.instanciate(UserFactory.instance);
			
			User adminUser = table.createRow();
			adminUser.setDisabled(false);
			adminUser.setDisplayName("Admin User");
			adminUser.setEmailAddress("admin@localhost");
			adminUser.setUserName("admin");

			connection.save(table);
						
			Session session = SessionFactory.instance.create();
			try { session.login(connection, "admin", "Welcome123"); }
			catch(InvalidLoginException ex) {
				assertTrue(true);
				return;
			}
			
			fail("InvalidLoginException should have been thrown!");
			
		});
	}
	@Test
	void Login_NoPassword() throws Exception {
		TestDatabase.runTest(connection -> {
			try {
				Session session = SessionFactory.instance.create();
				session.login(connection, "administrator", "");
				fail("No exception thrown for invalid login.");
			} catch(InvalidLoginException ex) {
				assertTrue(true);
			}
		});
	}
	@Test	
	void Login_NoUser() throws Exception {
		TestDatabase.runTest(connection -> {
			try {
				Session session = SessionFactory.instance.create();
				session.login(connection, "", "Welcome123");
				fail("No exception thrown for invalid login.");
			} catch(InvalidLoginException ex) {
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
				user.setUserName("test");
				connection.save(user.getTable());

				Session session = SessionFactory.instance.create();
				session.login(connection, "administrator", "Welcome123");

				user = UserFactory.instance.loadByUserName(connection, "test");
				user.resetPassword(session, "Welcome123", "Welcome123");

				Membership membership = TableFactory.instanciate(MembershipFactory.instance).createRow();
				membership.setUsersGuid(user.getGuid());
				membership.setGroupsGuid(Group.ADMINISTRATORS_GUID);
				connection.save(membership.getTable());

				Securable securable = TableFactory.instanciate(SecurableFactory.instance).createRow();
				securable.setDisabled(false);
				securable.setDisplayName("Test Securable");
				connection.save(securable.getTable());
				securable = SecurableFactory.instance.loadByDisplayName(connection, "Test Securable");

				session = SessionFactory.instance.create();
				session.login(connection, "test", "Welcome123");

				assertTrue(session.isAllowed(securable.getGuid(), Action.READ_GUID));
			} catch(PasswordExpiredException ex) {
				assertTrue(false);
			}
		});
	}
	@Test
	void IsAllowed_EveryoneAllowed() throws Exception {
		TestDatabase.runTest(connection -> {
			User user = TableFactory.instanciate(UserFactory.instance).createRow();
			user.setDisplayName("Test User");
			user.setDisabled(false);
			user.setEmailAddress("test@localhost");
			user.setUserName("test");
			connection.save(user.getTable());

			Session session = SessionFactory.instance.create();
			session.login(connection, "administrator", "Welcome123");

			user = UserFactory.instance.loadByUserName(connection, "test");
			user.resetPassword(session, "Welcome123", "Welcome123");

			Membership membership = TableFactory.instanciate(MembershipFactory.instance).createRow();
			membership.setUsersGuid(user.getGuid());
			membership.setGroupsGuid(Group.EVERYONES_GUID);
			connection.save(membership.getTable());

			Securable securable = TableFactory.instanciate(SecurableFactory.instance).createRow();
			securable.setDisabled(false);
			securable.setDisplayName("Test Securable");
			connection.save(securable.getTable());
			securable = SecurableFactory.instance.loadByDisplayName(connection, "Test Securable");
			
			SecurableAction secAction = TableFactory.instanciate(SecurableActionFactory.instance).createRow();
			secAction.setSecurablesGuid(securable.getGuid());
			secAction.setActionsGuid(Action.READ_GUID);
			connection.save(secAction.getTable());
			secAction = SecurableActionFactory.instance.loadByGuid(connection, secAction.getGuid());
			
			Permission permission = TableFactory.instanciate(PermissionFactory.instance).createRow();
			permission.setGroupsGuid(Group.EVERYONES_GUID);
			permission.setSecurableActionsGuid(secAction.getGuid());
			connection.save(permission.getTable());
		
			session = SessionFactory.instance.create();
			session.login(connection, "test", "Welcome123");

			assertTrue(session.isAllowed(securable.getGuid(), Action.READ_GUID));
		});
	}
	@Test
	void IsAllowed_GroupAllowed() throws Exception {
		TestDatabase.runTest(connection -> {
			User user = TableFactory.instanciate(UserFactory.instance).createRow();
			user.setDisplayName("Test User");
			user.setDisabled(false);
			user.setEmailAddress("test@localhost");
			user.setUserName("test");
			connection.save(user.getTable());

			Session session = SessionFactory.instance.create();
			session.login(connection, "administrator", "Welcome123");

			user = UserFactory.instance.loadByUserName(connection, "test");
			user.resetPassword(session, "Welcome123", "Welcome123");

			Membership membership = TableFactory.instanciate(MembershipFactory.instance).createRow();
			membership.setUsersGuid(user.getGuid());
			membership.setGroupsGuid(Group.EVERYONES_GUID);
			connection.save(membership.getTable());

			Securable securable = TableFactory.instanciate(SecurableFactory.instance).createRow();
			securable.setDisabled(false);
			securable.setDisplayName("Test Securable");
			connection.save(securable.getTable());
			securable = SecurableFactory.instance.loadByDisplayName(connection, "Test Securable");
			
			SecurableAction secAction = TableFactory.instanciate(SecurableActionFactory.instance).createRow();
			secAction.setSecurablesGuid(securable.getGuid());
			secAction.setActionsGuid(Action.READ_GUID);
			connection.save(secAction.getTable());
			secAction = SecurableActionFactory.instance.loadByGuid(connection, secAction.getGuid());
			
			Permission permission = TableFactory.instanciate(PermissionFactory.instance).createRow();
			permission.setGroupsGuid(Group.EVERYONES_GUID);
			permission.setSecurableActionsGuid(secAction.getGuid());
			connection.save(permission.getTable());
		
			session = SessionFactory.instance.create();
			session.login(connection, "test", "Welcome123");

			assertTrue(session.isAllowed(securable.getGuid(), Action.READ_GUID));
		});
	}

	@Test
	void IsAllowed_FailNoSecurable() throws Exception {
		TestDatabase.runTest(connection -> {
			User user = TableFactory.instanciate(UserFactory.instance).createRow();
			user.setDisplayName("Test User");
			user.setDisabled(false);
			user.setEmailAddress("test@localhost");
			user.setUserName("test");
			connection.save(user.getTable());

			Session session = SessionFactory.instance.create();
			session.login(connection, "administrator", "Welcome123");

			user = UserFactory.instance.loadByUserName(connection, "test");
			user.resetPassword(session, "Welcome123", "Welcome123");

			Membership membership = TableFactory.instanciate(MembershipFactory.instance).createRow();
			membership.setUsersGuid(user.getGuid());
			membership.setGroupsGuid(Group.EVERYONES_GUID);
			connection.save(membership.getTable());

			Securable securable = TableFactory.instanciate(SecurableFactory.instance).createRow();
			securable.setDisabled(false);
			securable.setDisplayName("Test Securable");
			connection.save(securable.getTable());
			securable = SecurableFactory.instance.loadByDisplayName(connection, "Test Securable");
			
			SecurableAction secAction = TableFactory.instanciate(SecurableActionFactory.instance).createRow();
			secAction.setSecurablesGuid(securable.getGuid());
			secAction.setActionsGuid(Action.READ_GUID);
			connection.save(secAction.getTable());
			secAction = SecurableActionFactory.instance.loadByGuid(connection, secAction.getGuid());
			
//			Permission permission = TableFactory.instanciate(PermissionFactory.instance).createRow();
//			permission.setGroupsGuid(Group.EVERYONES_GUID);
//			permission.setSecurableActionsGuid(secAction.getGuid());
//			connection.save(permission.getTable());
		
			session = SessionFactory.instance.create();
			session.login(connection, "test", "Welcome123");

			assertFalse(session.isAllowed(securable.getGuid(), Action.READ_GUID));
		});
	}
	@Test
	void IsAllowed_FailNoAction() throws Exception {
		TestDatabase.runTest(connection -> {
			User user = TableFactory.instanciate(UserFactory.instance).createRow();
			user.setDisplayName("Test User");
			user.setDisabled(false);
			user.setEmailAddress("test@localhost");
			user.setUserName("test");
			connection.save(user.getTable());

			Session session = SessionFactory.instance.create();
			session.login(connection, "administrator", "Welcome123");

			user = UserFactory.instance.loadByUserName(connection, "test");
			user.resetPassword(session, "Welcome123", "Welcome123");

			Membership membership = TableFactory.instanciate(MembershipFactory.instance).createRow();
			membership.setUsersGuid(user.getGuid());
			membership.setGroupsGuid(Group.EVERYONES_GUID);
			connection.save(membership.getTable());

			Securable securable = TableFactory.instanciate(SecurableFactory.instance).createRow();
			securable.setDisabled(false);
			securable.setDisplayName("Test Securable");
			connection.save(securable.getTable());
			securable = SecurableFactory.instance.loadByDisplayName(connection, "Test Securable");
			
			SecurableAction secAction = TableFactory.instanciate(SecurableActionFactory.instance).createRow();
			secAction.setSecurablesGuid(securable.getGuid());
			secAction.setActionsGuid(Action.UPDATE_GUID);
			connection.save(secAction.getTable());
			secAction = SecurableActionFactory.instance.loadByGuid(connection, secAction.getGuid());
			
			Permission permission = TableFactory.instanciate(PermissionFactory.instance).createRow();
			permission.setGroupsGuid(Group.EVERYONES_GUID);
			permission.setSecurableActionsGuid(secAction.getGuid());
			connection.save(permission.getTable());
		
			session = SessionFactory.instance.create();
			session.login(connection, "test", "Welcome123");

			assertFalse(session.isAllowed(securable.getGuid(), Action.READ_GUID));
		});
	}
	
}
