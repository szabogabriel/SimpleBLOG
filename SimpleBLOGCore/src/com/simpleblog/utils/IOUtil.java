package com.simpleblog.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
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
	
	public static void append(File targetFile, String message) {
		try (FileWriter out = new FileWriter(targetFile, true)){
			out.write(message);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static File getTempFolder() {
		File ret = new File(System.getProperty("java.io.tmpdir"));
		return ret;
	}
}
