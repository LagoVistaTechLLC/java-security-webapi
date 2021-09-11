package com.lagovistatech.rest;

public class TextContentType implements ContentType {
	public String getExtension() { return "txt"; }
	public String getMime() { return "text/text"; }
}
