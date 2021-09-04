package com.lagovistatech.security.webapi.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.lagovistatech.database.TableFactory;
import com.lagovistatech.security.webapi.entities.Securable;
import com.lagovistatech.security.webapi.entities.SecurableFactory;

class SecurableFactoryTest {
	@Test
	void LoadAll() throws Exception {
		TestDatabase.runTest(connection -> {
			Securable sec = TableFactory.instanciate(SecurableFactory.instance).createRow();
			sec.setDisabled(false);
			sec.setDisplayName("Test");
			
			connection.save(sec.getTable());
			
			assertTrue(SecurableFactory.instance.loadAll(connection).size() >= 1);
		});
	}

}
