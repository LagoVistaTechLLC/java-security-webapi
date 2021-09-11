package com.lagovistatech.security.webapi.entities.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.lagovistatech.security.test.TestDatabase;
import com.lagovistatech.security.webapi.entities.SettingFactory;

class SettingFactoryTest {

	@Test
	void LoadAll() throws Exception {
		TestDatabase.runTest(connection -> {
			assertTrue(SettingFactory.instance.loadAll(connection).size() >= 3);
		});
	}

}
