package com.lagovistatech.rest;

import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;

public class StringResponse implements Response<String> {
	private HttpResponse<String> response;
	
	protected StringResponse(HttpResponse<String> response) {
		this.response = response;
	}
	
	public int getStatus() { return response.statusCode(); }
	public String getBody() { return response.body(); }
	public Map<String, List<String>> getHeaders() { return this.response.headers().map(); }
}
