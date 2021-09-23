package com.lagovistatech.security.webapi.test;

import static org.junit.jupiter.api.Assertions.*;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse.BodyHandlers;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lagovistatech.rest.AcceptHeader;
import com.lagovistatech.rest.ContentTypeHeader;
import com.lagovistatech.rest.JsonContentType;
import com.lagovistatech.rest.JsonRequest;
import com.lagovistatech.rest.JsonResponse;
import com.lagovistatech.rest.Method;
import com.lagovistatech.rest.StringRequest;
import com.lagovistatech.rest.StringResponse;
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
	void StringRequestTest_SessionLogin_200() throws Exception {
		StringRequest req = new StringRequest();
		req.setUrl("http://localhost:8080/api/v1/session/login");
		req.setHeader(new AcceptHeader(JsonContentType.instance));
		req.setHeader(new ContentTypeHeader(JsonContentType.instance));
		req.setMethod(Method.POST);
		req.setBody("{ \"userName\": \"administrator\", \"password\": \"Welcome123\" }");
		
		StringResponse resp = req.send();
		if(resp.getStatus() != 200)
			fail("Response code not 200!");
		
		String body = resp.getBody();
		assertTrue(body.contains("System Administrator"));
	}
	@Test
	void HttpRequest_SessionLogin_200() throws Exception {
		CredentialsDto creds = new CredentialsDto();
		creds.setUserName("administrator");
		creds.setPassword("Welcome123");
		
		ObjectMapper om = new ObjectMapper();
		String credsJson = om.writeValueAsString(creds);
		
		HttpClient client = HttpClient.newHttpClient();
		
		HttpRequest.Builder requestBuilder = HttpRequest.newBuilder();
		requestBuilder.uri(new URI("http://localhost:8080/api/v1/session/login"));
		requestBuilder.header("Content-Type", "application/json");
		
		HttpRequest.BodyPublisher bodyPublisher = BodyPublishers.ofString(credsJson);
		
		requestBuilder.method("POST", bodyPublisher);
		HttpRequest request = requestBuilder.build();
		
		HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
		if(response.statusCode() != 200)
			fail("Response status code not 200!");
		
		SessionDto sessionDto = om.readValue(response.body(), SessionDto.class);
		assertEquals("System Administrator", sessionDto.getUser().getDisplayName());
	}
	@Test
	void JsonRequestTest_SessionLogin_200() throws Exception {
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

}
