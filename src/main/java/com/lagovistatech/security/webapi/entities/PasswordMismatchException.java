package com.lagovistatech.security.webapi.entities;

public class PasswordMismatchException extends Exception {
	private static final long serialVersionUID = 1L;
	public PasswordMismatchException(String message) { super(message); }
}
