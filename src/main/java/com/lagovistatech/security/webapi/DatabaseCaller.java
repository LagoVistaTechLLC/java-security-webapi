package com.lagovistatech.security.webapi;

import java.util.UUID;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.lagovistatech.database.Connection;
import com.lagovistatech.database.ConnectionFactory;
import com.lagovistatech.Logger;
import com.lagovistatech.Logger.LogType;

@ConfigurationProperties(prefix = "database")
public class DatabaseCaller<T> {
	private static final Logger logger = new Logger("c.lvt.s.w.DatabaseCaller");
	
	public interface Callable<T> {
		ResponseEntity<T> run(Connection connection) throws Exception;
	}
	
	private String server = "localhost";
	public String getServer() { return server; }
	public void setServer(String server) { this.server = server; }

	private String user = "postgres";
	public String getUser() { return user; }
	public void setUser(String user) { this.user = user; }

	private String name = "postgres";
	public String getName() { return name; }
	public void setName(String name) { this.name = name; }

	private String password = "postgres";
	public String getPassword() { return password; }
	public void setPassword(String password) { this.password = password; }

	private String port = "54321";
	public String getPort() { return port; }
	public void setPort(String port) { this.port = port; }

	private String timeout = "120";
	public String getTimeout() { return timeout; }
	public void setTimeout(String timeout) { this.timeout = timeout; }

	public static <T> ResponseEntity<T> run(UUID corelation, Callable<T> callable) {
		DatabaseCaller<T> instance = new DatabaseCaller<>();
		return instance.execute(corelation, callable);
	}
	
	private ResponseEntity<T> execute(UUID corelation, Callable<T> callable) {
		Connection connection = ConnectionFactory.instance.create();
		connection.setDatabase(name);
		connection.setServer(server);
		connection.setUser(user);
		connection.setPassword(password);
		connection.setPort(Integer.parseInt(port));
		connection.setTimeOut(Integer.parseInt(timeout));
		
		try {
			connection.open();
			return callable.run(connection);
		}
		catch(Exception ex) {
			logger.write(LogType.ERROR, corelation, ex.toString());
			return new ResponseEntity<>(null, null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		finally { 
			try { connection.close(); }
			catch(Exception ex) {
				logger.write(LogType.ERROR, corelation, ex.toString());
			}
		}			
	}
}
