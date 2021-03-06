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
	
	private String server = null;
	public String getServer() { return server; }
	public void setServer(String server) { this.server = server; }

	private String user = null;
	public String getUser() { return user; }
	public void setUser(String user) { this.user = user; }

	private String name = null;
	public String getName() { return name; }
	public void setName(String name) { this.name = name; }

	private String password = null;
	public String getPassword() { return password; }
	public void setPassword(String password) { this.password = password; }

	private String port = null;
	public String getPort() { return port; }
	public void setPort(String port) { this.port = port; }

	private String timeout = null;
	public String getTimeout() { return timeout; }
	public void setTimeout(String timeout) { this.timeout = timeout; }

	public static <T> ResponseEntity<T> run(UUID corelation, Callable<T> callable) {
		DatabaseCaller<T> instance = new DatabaseCaller<>();
		return instance.execute(corelation, callable);
	}
	
	private ResponseEntity<T> execute(UUID corelation, Callable<T> callable) {		
		Connection connection = null;
		try {
			connection = ConnectionFactory.instance.create();
			connection.setDatabase(Config.load().getDatabaseName());
			connection.setServer(Config.load().getDatabaseServer());
			connection.setUser(Config.load().getDatabaseUser());
			connection.setPassword(Config.load().getDatabasePassword());
			connection.setPort(Config.load().getDatabasePort());
			connection.setTimeOut(Config.load().getDatabaseTimeout());
			
			connection.open();
			return callable.run(connection);
		}
		catch(Exception ex) {
			logger.write(LogType.ERROR, corelation, ex.toString());
			return new ResponseEntity<>(null, null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		finally { 
			try { 
				if(connection != null)
					connection.close(); 
			}
			catch(Exception ex) {
				logger.write(LogType.ERROR, corelation, ex.toString());
			}
		}			
	}
}
