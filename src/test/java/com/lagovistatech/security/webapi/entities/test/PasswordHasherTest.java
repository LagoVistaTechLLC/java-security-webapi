package com.lagovistatech.security.webapi.entities.test;

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
		
		System.out.println("------------------------------------------------------------------------");
		System.out.println("Iterations: " + iterations);
		System.out.println("Salt: " + Hex.encodeHexString(firstPh.getSalt()));
		System.out.println("Hash: " + firstHash);
		System.out.println("------------------------------------------------------------------------");

		assertTrue(firstHash.equals(repeatHash));
	}
	@Test
	void Repeatable_Hash_1() throws Exception {
		int iterations = 1;
		
		PasswordHasher firstPh = new PasswordHasher(32, iterations, 512);
		byte[] firstKey = firstPh.calculate("Welcome123");
		String firstHash = Hex.encodeHexString(firstKey);
		
		PasswordHasher repeatPh = new PasswordHasher(32, iterations, 512);
		repeatPh.setSalt(firstPh.getSalt());
		byte[] repeatedKey = repeatPh.calculate("Welcome123");
		String repeatHash = Hex.encodeHexString(repeatedKey);
		
		System.out.println("------------------------------------------------------------------------");
		System.out.println("Iterations: " + iterations);
		System.out.println("Salt: " + Hex.encodeHexString(firstPh.getSalt()));
		System.out.println("Hash: " + firstHash);
		System.out.println("------------------------------------------------------------------------");

		assertTrue(firstHash.equals(repeatHash));
	}
	
	@Test
	void Complexity_Lowers() {
		assertTrue(PasswordHasher.calculateComplexity("happy") == 1);
	}
	@Test
	void Complexity_Uppers() {
		assertTrue(PasswordHasher.calculateComplexity("HAPPY") == 1);
	}
	@Test
	void Complexity_Numbers() {
		assertTrue(PasswordHasher.calculateComplexity("123456789") == 1);
	}
	@Test
	void Complexity_Other() {
		assertTrue(PasswordHasher.calculateComplexity("!@#$%^") == 1);
	}
}
