package com.lagovistatech.rest;

public class AcceptHeader implements Header {
	private ContentType value;

	public AcceptHeader(ContentType type) { value = type; }
	
	public String getName() { return "Accept"; }
	public String getValue() { return value.getMime(); }
}
