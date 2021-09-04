package com.lagovistatech.security.dto;

public interface Copier<T> {
	void copyTo(T destination);
	void copyFrom(T source);
}
