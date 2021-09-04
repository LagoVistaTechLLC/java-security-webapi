package com.lagovistatech.security.webapi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {
	private static final Logger logger = LoggerFactory.getLogger("c.lvt.s.w.Application");
	
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	
	@Bean
	public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
		return args -> {
			logger.info("Authentication WebAPI");
			logger.info("Copyright (c) 2021 Lago Vista Technologies");
			logger.info("GNU Affero General Public License v3");
		};
	}
}
