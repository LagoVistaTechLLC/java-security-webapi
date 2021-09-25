package com.lagovistatech.security.webapi.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.lagovistatech.rest.JsonRequest;
import com.lagovistatech.rest.JsonResponse;
import com.lagovistatech.rest.Method;
import com.lagovistatech.security.dto.CredentialsDto;
import com.lagovistatech.security.dto.SessionDto;
import com.lagovistatech.security.webapi.Application;

class EndToEndTest {
	static Thread website;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		website = new Thread() {
			public void run() {
				Application.main(new String[] {});
			}
		};
		website.start();
		Thread.sleep(5 * 1000);
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
		website.interrupt();
	}
	
	@Test
	void Login_Successful() throws Exception {
		JsonRequest<CredentialsDto, SessionDto> req = new JsonRequest<>(SessionDto.class);
		req.setUrl("http://localhost:8080/api/v1/session/login");
		req.setMethod(Method.POST);
		
		CredentialsDto cred = new CredentialsDto();
		cred.setUserName("administrator");
		cred.setPassword("Welcome123");
		req.setBody(cred);
		
		JsonResponse<SessionDto> resp = req.send();
		if(resp.getStatus() != 200)
			fail("Response code not 200!");
		
		SessionDto session = resp.getBody();
		assertEquals("System Administrator", session.getUser().getDisplayName());
	}
	@Test
	void Login_BadUser() throws Exception {
		JsonRequest<CredentialsDto, SessionDto> req = new JsonRequest<>(SessionDto.class);
		req.setUrl("http://localhost:8080/api/v1/session/login");
		req.setMethod(Method.POST);
		
		CredentialsDto cred = new CredentialsDto();
		cred.setUserName("bad");
		cred.setPassword("Welcome123");
		req.setBody(cred);
		
		JsonResponse<SessionDto> resp = req.send();
		assertEquals(401, resp.getStatus());
	}

}
