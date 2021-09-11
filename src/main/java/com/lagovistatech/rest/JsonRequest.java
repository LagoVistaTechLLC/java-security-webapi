package com.lagovistatech.rest;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonRequest<T> implements Request<Object, T> {
	private StringRequest request;
	private Class<T> responseBodyType;
	private static final ObjectMapper om = new ObjectMapper();
	
	public JsonRequest(Class<T> responseBodyType) {
		this.request = new StringRequest();
		
		request.setHeader(new AcceptHeader(JsonContentType.instance));
		request.setHeader(new ContentTypeHeader(JsonContentType.instance));

		this.responseBodyType = responseBodyType;
	}
	
	public void setHeader(Header value) { request.setHeader(value); }
	public void setUrl(String value) { request.setUrl(value); }
	public void setMethod(Method value) { request.setMethod(value); }
	public void setBody(Object value) throws Exception { request.setBody(om.writeValueAsString(value)); }

	public JsonResponse<T> send() throws Exception {
		StringResponse resp = request.send();
		return new JsonResponse<T>(responseBodyType, resp);
	}

}
