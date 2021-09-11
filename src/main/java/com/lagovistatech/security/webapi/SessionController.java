package com.lagovistatech.security.webapi;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.lagovistatech.logging.Logger;
import com.lagovistatech.logging.Logger.LogType;
import com.lagovistatech.security.dto.CredentialsDto;
import com.lagovistatech.security.dto.SessionDto;
import com.lagovistatech.security.webapi.DatabaseCaller.Callable;
import com.lagovistatech.security.webapi.entities.InvalidLoginException;
import com.lagovistatech.security.webapi.entities.Session;
import com.lagovistatech.security.webapi.entities.SessionFactory;

@RestController
public class SessionController {
	private static final Logger logger = new Logger("c.lvt.s.w.SessionController");

	@PostMapping("/api/v1/session/login")
	public ResponseEntity<SessionDto> login(@RequestBody CredentialsDto credentials) {
		UUID correlation = logger.write(LogType.INFO, UUID.randomUUID(),  "/api/v1/session/login");
		return DatabaseCaller.run(correlation, (Callable<SessionDto>) (connection) -> {
			try { 
				Session session = SessionFactory.instance.create();
				session.login(connection, credentials.getUserName(), credentials.getPassword());
				logger.write(LogType.INFO, correlation, "Login: " + credentials.getUserName());
				
				return new ResponseEntity<>(session.createDto(), null, HttpStatus.OK);
			}
			catch(InvalidLoginException ex) {
				logger.write(LogType.ERROR, correlation, ex.toString());
				return new ResponseEntity<>(null, null, HttpStatus.UNAUTHORIZED);
			}
		});
	}
}