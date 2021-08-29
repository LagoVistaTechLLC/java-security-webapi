package com.lagovistatech.security.webapi.entities;

public class AccessDeniedException extends Exception {
	private static final long serialVersionUID = 1L;
	public AccessDeniedException(String message) { super(message); }
}
