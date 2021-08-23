package com.lagovistatech.security.webapi;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.lagovistatech.security.webapi.entities.Session;

@RestController
public class SessionController {
	@GetMapping("/v1/session/test")
	public String version() {
		return "Hello World!";
	}
	
	@PostMapping("/v1/session/authenticate")
	public Session authenticate(
		@RequestBody String userName,
		@RequestBody String password
	) {
		return null;
	}
}
