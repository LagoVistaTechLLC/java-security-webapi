package com.lagovistatech.rest;

public class JsonContentType implements ContentType {
	public static final JsonContentType instance = new JsonContentType();
	
	public String getExtension() { return "json"; }
	public String getMime() { return "application/json"; }
}
