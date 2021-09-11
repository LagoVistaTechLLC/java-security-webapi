package com.lagovistatech.rest;

import java.util.List;
import java.util.Map;

public interface Response<B> {
	int getStatus();
	B getBody() throws Exception;
	Map<String, List<String>> getHeaders();
}