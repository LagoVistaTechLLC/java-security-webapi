package com.lagovistatech.security.webapi.entities;

public class PasswordExpiredException extends Exception {
	private static final long serialVersionUID = 1L;
	public PasswordExpiredException(String message) { super(message); }
}
