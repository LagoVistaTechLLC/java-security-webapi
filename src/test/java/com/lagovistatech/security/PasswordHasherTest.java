package com.lagovistatech.security;

import static org.junit.jupiter.api.Assertions.*;

import org.apache.commons.codec.binary.Hex;
import org.junit.jupiter.api.Test;

import com.lagovistatech.security.webapi.entities.PasswordHasher;

class PasswordHasherTest {
	@Test
	void Repeatable_Hash() throws Exception {
		int iterations = 10000;
		
		PasswordHasher firstPh = new PasswordHasher(32, iterations, 512);
		byte[] firstKey = firstPh.calculate("Welcome123");
		String firstHash = Hex.encodeHexString(firstKey);
		
		PasswordHasher repeatPh = new PasswordHasher(32, iterations, 512);
		repeatPh.setSalt(firstPh.getSalt());
		byte[] repeatedKey = repeatPh.calculate("Welcome123");
		String repeatHash = Hex.encodeHexString(repeatedKey);
		
		System.out.println("Iterations: " + iterations);
		System.out.println("Salt: " + Hex.encodeHexString(firstPh.getSalt()));
		System.out.println("Hash: " + firstHash);

		assertTrue(firstHash.equals(repeatHash));
	}
}
