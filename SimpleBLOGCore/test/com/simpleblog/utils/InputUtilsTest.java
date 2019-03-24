package com.simpleblog.utils;

import static org.junit.Assert.assertTrue;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class InputUtilsTest {

	@Test
	public void test01_checkPassword() {
		String[] passwords = new String [] {
			"$upeR6ei1", "123456", "abcdefg", "asdf.gfdh"
		};
		
		for (String it : passwords) {
			assertTrue(InputUtils.isValidPassword(it));
		}
	}
	
}
