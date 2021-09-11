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
	
	public UUID write(LogType logType, UUID correlation, String function, String message) {
		String output = "{ \"correlation\":\"" + correlation.toString() + "\", \"function\":\"" + function.replace("\"", "\\\"") + "\", \"message\":\"" + message.replace("\"", "\\\"") + "\" }";
		return rawWrite(logType, correlation, output);
	}
	public UUID write(LogType logType, UUID correlation, String message) {
		String output = "{ \"correlation\":\"" + correlation.toString() + "\", \"message\":\"" + message.replace("\"", "\\\"") + "\" }";
		return rawWrite(logType, correlation, output);
	}
	private UUID rawWrite(LogType logType, UUID correlation, String output) {
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
		
		return correlation;
	}
}
