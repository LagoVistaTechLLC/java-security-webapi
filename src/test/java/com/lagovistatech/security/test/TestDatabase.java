package com.lagovistatech.security.test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.apache.commons.codec.binary.Hex;

import com.lagovistatech.Helpers;
import com.lagovistatech.database.Connection;
import com.lagovistatech.database.ConnectionFactory;
import com.lagovistatech.security.webapi.entities.Session;
import com.lagovistatech.security.webapi.entities.User;
import com.lagovistatech.security.webapi.entities.UserFactory;

public class TestDatabase {	
	public interface testFunction { void run(Connection connection) throws Exception; }
	
	public static void runTest(testFunction test) throws Exception {
		Exception exception = null;

		try {
			String dbName = null;
			Connection connection = null;
			
			try {					
				connection = connect();
				dbName = createDatabase(connection);
				reconnectToDatabase(connection, dbName);
				createTables(connection);
				
				User admin = UserFactory.instance.loadByGuid(connection, User.ADMINISTRATORS_GUID);
				admin.setPasswordIterations(1);
				admin.setPasswordSalt(Hex.decodeHex("395ec87d8532b6acf30a084c5d9e7fc06792d0a21f5bef47f1f2f15b75b56049"));
				admin.setPasswordHash(Hex.decodeHex("8b1266a1c167ab37f19038c1a15bb61c1d5466d59a30db6aa81a9b73c5138420146335d8f200a1e0b3e3fcfdfb6d068eaa490ecb4eae54f3651d8cb22044195b"));
				connection.save(admin.getTable());
				
				UserFactory.setIterations(1);
				
				test.run(connection);
			}
			catch(Exception ex) {
				exception = ex;
			}
			finally {
				reconnectToDatabase(connection, "postgres");
				deleteDatabase(connection, dbName);
				connection.close();			
			}
			
			if(exception != null)
				throw exception;
		}
		catch(Exception ex) {
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			if(exception != null)
				exception.printStackTrace(pw);;
			ex.printStackTrace(pw);
			fail(sw.toString());
			pw.close();
			sw.close();
		}
	}

	private static Connection connect() throws Exception {
		Connection connection = ConnectionFactory.instanciate();
		connection.setServer("localhost");
		connection.setPort(54321);
		connection.setDatabase("postgres");
		connection.setUser("postgres");
		connection.setPassword("postgres");
		connection.open();
		return connection;
	}
	private static String createDatabase(Connection connection) throws Exception {
		String dbName = com.lagovistatech.Guid.computeUuid().toString().toLowerCase().replace("-", "");
		String sql = "CREATE DATABASE " + connection.getAdapter().quoteIdentifier(dbName);
		connection.execute(sql);
		return dbName;
	}
	private static void reconnectToDatabase(Connection connection, String dbName) throws Exception {
		connection.close();
		connection.setDatabase(dbName);
		connection.open();
	}
	private static void createTables(Connection connection) throws Exception {
		String sql = Helpers.readResourceAsString(Session.class, "/database/20210724.sql");
		connection.execute(sql);
	}
	private static void deleteDatabase(Connection connection, String dbName) throws Exception {
		String sql = "DROP DATABASE " + connection.getAdapter().quoteIdentifier(dbName);
		connection.execute(sql);
	}

}
