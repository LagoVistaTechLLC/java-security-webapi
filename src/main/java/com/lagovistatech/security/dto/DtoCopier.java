package com.lagovistatech.security.dto;

public interface DtoCopier<T> {
	void copyTo(T destination);
	void copyFrom(T source);
}
