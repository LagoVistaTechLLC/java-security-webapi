package com.lagovistatech.rest;

public interface Request<B, R> {
	void setHeader(Header value);
	void setUrl(String value);
	void setMethod(Method value);
	void setBody(B value) throws Exception;

	Response<R> send() throws Exception;
}