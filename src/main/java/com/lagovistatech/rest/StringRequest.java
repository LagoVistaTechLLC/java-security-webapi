package com.lagovistatech.rest;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.HashMap;
import java.util.Map;

public class StringRequest implements Request<String, String> {
	private Map<String, Header> headers = new HashMap<String, Header>();
	private String url = null;
	private Method method = null;
	private String body = null;
	
	public void setHeader(Header value) { headers.put(value.getName(), value); }
	public void setUrl(String value) { url = value; }
	public void setMethod(Method value) { method = value; }
	public void setBody(String value) { body = value; }
	
	public StringResponse send() throws Exception {
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest.Builder requestBuilder = HttpRequest.newBuilder();
		
		for(String key : headers.keySet())
			requestBuilder.header(key, headers.get(key).getValue());
				
		requestBuilder.uri(URI.create(url));
		
		HttpRequest.BodyPublisher bodyPublisher = HttpRequest.BodyPublishers.ofString(body);
		requestBuilder.method(method.name(), bodyPublisher);
		HttpResponse<String> response = client.send(requestBuilder.build(), BodyHandlers.ofString());

		return new StringResponse(response);
	}
}
