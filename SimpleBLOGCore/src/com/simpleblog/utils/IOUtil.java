package com.simpleblog.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class IOUtil {
	
	public static String readFile(File file) {
		StringBuilder ret = new StringBuilder();
		try (BufferedReader in = new BufferedReader(new FileReader(file))) {
			String line;
			while ((line = in.readLine()) != null) {
				ret.append(line + "\n");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret.toString();
	}

}
