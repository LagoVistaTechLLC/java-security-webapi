package com.lagovistatech.rest;

public class ContentTypeHeader implements Header {
	private ContentType contentType;
	
	public ContentTypeHeader(ContentType contentType) {
		this.contentType = contentType;
	}

	public String getName() { return "Content-Type"; }
	public String getValue() { return contentType.getMime(); }
}
