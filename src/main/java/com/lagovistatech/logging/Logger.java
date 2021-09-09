package com.lagovistatech.logging;

import java.util.UUID;

import org.slf4j.LoggerFactory;

public class Logger {
	public enum LogType {
		ERROR,
		TRACE,
		WARN,
		DEBUG,
		INFO
	}
	
	org.slf4j.Logger logger;
	
	public Logger(String name) { logger = LoggerFactory.getLogger(name); }
	
	public UUID write(LogType logType, UUID corelation, String message) {
		String output = "{ \"corelation\":\"" + corelation.toString() + "\", \"message\":\"" + message.replace("\"", "\\\"") + "\" }";
		
		switch(logType) {
			case WARN:
				logger.warn(output); 
				break;
			case TRACE:
				logger.trace(output);
				break;
			case ERROR:
				logger.error(output);
				break;
			case DEBUG:
				logger.debug(output);
				break;
			case INFO:
			default:
				logger.info(output);
		}
		
		return corelation;
	}
}
