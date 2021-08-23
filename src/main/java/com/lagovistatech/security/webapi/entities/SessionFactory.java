package com.lagovistatech.security.webapi.entities;

import com.lagovistatech.Factory;

public class SessionFactory implements Factory<Session> {
	public static SessionFactory instance = new SessionFactory();
	public Session create() { return new SessionImp(); }
}
