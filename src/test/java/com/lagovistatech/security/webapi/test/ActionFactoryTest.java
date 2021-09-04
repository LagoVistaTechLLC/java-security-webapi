package com.lagovistatech.security.webapi.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.lagovistatech.security.webapi.entities.ActionFactory;

class ActionFactoryTest {

	@Test
	void LoadAll() throws Exception {
		TestDatabase.runTest(connection -> {
			assertTrue(ActionFactory.instance.loadAll(connection).size() >= 4);
		});
	}
}
