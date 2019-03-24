package com.simpleblog.utils;

import java.util.regex.Pattern;

public class InputUtils {

	private static final Pattern PATTERN_EMAIL = Pattern.compile("^[a-zA-Z0-9\\\\.\\\\-\\\\_]+@[a-zA-Z0-9\\\\.\\\\-\\\\_]+$");
	private static final Pattern PATTERN_PSSWD = Pattern.compile("^[a-zA-Z0-9\\\\.-_@#!?$%^&*\\\\(\\\\)]{8,}$");
	
	public static boolean isValidEmail(String email) {
		boolean ret = false;
		
		if (email != null) {
			//TODO - fix THIS!!!
			ret = true;
		}
		
		return ret;
	}
	
	public static boolean isValidPassword(String password) {
		boolean ret = false;
		
		if (password != null) {
			//TODO - fix THIS!!!
			ret = true;
		}
		
		return ret;
	}

}
