package com.simpleblog.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.file.Files;

public class IOUtil {
	
	public static String readFileString(File file) {
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
	
	public static byte[] readFileBytes(File file) {
		byte [] ret = new byte [] {};
		if (file != null && file.exists()) {
			try {
				ret = Files.readAllBytes(file.toPath());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return ret;
	}

}
