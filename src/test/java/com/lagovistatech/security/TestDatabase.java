package com.lagovistatech.security;

import static org.junit.jupiter.api.Assertions.*;

import java.io.PrintWriter;
import java.io.StringWriter;

import com.lagovistatech.Helpers;
import com.lagovistatech.database.Connection;
import com.lagovistatech.database.ConnectionFactory;
import com.lagovistatech.security.webapi.entities.Session;

public class TestDatabase {	
	public interface testFunction { void run(Connection connection) throws Exception;
	}
	
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
