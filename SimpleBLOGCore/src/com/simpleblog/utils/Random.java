package com.simpleblog.utils;

public class Random {
	
	private static final char [] SALT_ALPHABET = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();
	
	public static String salt(int length) {
		StringBuilder ret = new StringBuilder();
		
		if (length > 0)
			for (int i = 0; i < length; i++)
				ret.append(SALT_ALPHABET[randomInt(0, SALT_ALPHABET.length - 1)]);
		
		return ret.toString();
	}
	
	public static int randomInt(int fromInclusive, int toInclusive) {
		double interval = (double)Math.abs(toInclusive - fromInclusive);
		int returnIntValue = ((int)(Math.random() * interval)) + fromInclusive;
		return returnIntValue;		
	}

}
