package com.lagovistatech.security.webapi;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lagovistatech.Logger;
import com.lagovistatech.Logger.LogType;

public class Config {
	private String databaseServer;
	public String getDatabaseServer() {
		return databaseServer;
	}
	public void setDatabaseServer(String databaseServer) {
		this.databaseServer = databaseServer;
	}

	private String databaseName;
	public String getDatabaseName() {
		return databaseName;
	}
	public void setDatabaseName(String databaseName) {
		this.databaseName = databaseName;
	}

	private String databaseUser;
	public String getDatabaseUser() {
		return databaseUser;
	}
	public void setDatabaseUser(String databaseUser) {
		this.databaseUser = databaseUser;
	}

	private String databasePassword;
	public String getDatabasePassword() {
		return databasePassword;
	}
	public void setDatabasePassword(String databasePassword) {
		this.databasePassword = databasePassword;
	}

	private int databasePort;
	public int getDatabasePort() {
		return databasePort;
	}
	public void setDatabasePort(int databasePort) {
		this.databasePort = databasePort;
	}

	private int databaseTimeout;
	public int getDatabaseTimeout() {
		return databaseTimeout;
	}
	public void setDatabaseTimeout(int databaseTimeout) {
		this.databaseTimeout = databaseTimeout;
	}

	private int httpPort;
	public int getHttpPort() {
		return httpPort;
	}
	public void setHttpPort(int httpPort) {
		this.httpPort = httpPort;
	}

	private static final Logger logger = new Logger("c.lvt.s.w.Config");
	private static Config cachedConfig = null;
	public static Config load() throws Exception {
		if(cachedConfig == null) {
			Path configPath = Path.of("config.json");
			logger.write(LogType.INFO, UUID.randomUUID(), "Loading config: " + configPath.toAbsolutePath().toString());
			
			String configJson = Files.readString(configPath.toAbsolutePath());
	
			ObjectMapper om = new ObjectMapper();
			cachedConfig = om.readValue(configJson, Config.class);
		}
		
		return cachedConfig;	
	}
}
