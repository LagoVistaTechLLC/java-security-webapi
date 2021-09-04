package com.lagovistatech.security.webapi;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.lagovistatech.security.dto.CredentialsDto;
import com.lagovistatech.security.webapi.DatabaseCaller.Callable;
import com.lagovistatech.security.webapi.entities.InvalidLoginException;
import com.lagovistatech.security.webapi.entities.Session;
import com.lagovistatech.security.webapi.entities.SessionFactory;

@RestController
public class SessionController {	
	@PostMapping("/api/v1/session/login")
	public ResponseEntity<Session> login(@RequestBody CredentialsDto credentials) {
		return DatabaseCaller.run((Callable<Session>) connection -> {
			try { 
				Session session = SessionFactory.instance.create();
				session.login(connection, credentials.getUserName(), credentials.getPassword());
				return new ResponseEntity<Session>(session, null, HttpStatus.OK);
			}
			catch(InvalidLoginException ex) {
				return new ResponseEntity<Session>(null, null, HttpStatus.UNAUTHORIZED);
			}
		});
	}
}