package com.lagovistatech.security.webapi;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	
	@Bean
	public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
		return args -> {
			System.out.println();
			System.out.println(
				  "-------------------------------------------------------------------------------\n"
				+ "\n"
				+ "Authentication WebAPI\n"
				+ "Copyright (C) 2021 Lag Vista Technologies\n"
				+ "\n"
				+ "This program is free software: you can redistribute it and/or modify\n"
				+ "it under the terms of the GNU Affero General Public License as\n"
				+ "published by the Free Software Foundation, either version 3 of the\n"
				+ "License, or (at your option) any later version.\n"
				+ "\n"
				+ "This program is distributed in the hope that it will be useful,\n"
				+ "but WITHOUT ANY WARRANTY; without even the implied warranty of\n"
				+ "MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the\n"
				+ "GNU Affero General Public License for more details.\n"
				+ "\n"
				+ "You should have received a copy of the GNU Affero General Public License\n"
				+ "along with this program.  If not, see <https://www.gnu.org/licenses/>.\n"
				+ "\n"
				+ "-------------------------------------------------------------------------------\n"
			);
		};
	}
}
