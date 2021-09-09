package com.lagovistatech.security.webapi;

import java.util.UUID;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import com.lagovistatech.logging.Logger;
import com.lagovistatech.logging.Logger.LogType;

@SpringBootApplication
public class Application {
	private static final Logger logger = new Logger("c.lvt.s.w.Application");
	
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	
	@Bean
	public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
		return args -> {
			UUID corelation = logger.write(LogType.INFO, UUID.randomUUID(), "Authentication WebAPI");
			logger.write(LogType.INFO, corelation, "Copyright (c) 2021 Lago Vista Technologies");
			logger.write(LogType.INFO, corelation, "GNU Affero General Public License v3");
		};
	}
}
