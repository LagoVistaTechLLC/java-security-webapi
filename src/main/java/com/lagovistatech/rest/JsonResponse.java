package com.lagovistatech.rest;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonResponse<T> implements Response<T> {
	private static final ObjectMapper om = new ObjectMapper();
	private StringResponse response;
	private Class<T> bodyType;
	
	protected JsonResponse(Class<T> bodyType, StringResponse response) {
		this.response = response;
		this.bodyType = bodyType;
	}

	public int getStatus() { return response.getStatus(); }
	public T getBody() throws Exception {
		return om.readValue(response.getBody(), bodyType);
	}
	public Map<String, List<String>> getHeaders() { return response.getHeaders(); }

}
