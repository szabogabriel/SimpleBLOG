package com.simpleblog.locale;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

public class Locales {

	private static final String ACCEPTED_FILE_ENDING = ".properties";
	private static final String VALUES_PREFIX = "string.";

	private final String[] KEYS;
	
	private final Set<String> KEYS_SET = new HashSet<>();

	private final Map<String, Map<String, String>> PROPERTIES = new HashMap<>();

	public Locales(File rootFolder) {
		for (File f : rootFolder.listFiles()) {
			if (isFileAcceptable(f)) {
				loadFile(f);
				KEYS_SET.add(getFileNameWithoutExtension(f));
			}
		}
		
		KEYS = KEYS_SET.stream().sorted().toArray(String[]::new);
	}

	private boolean isFileAcceptable(File file) {
		 return file.isFile() && file.getName().endsWith(ACCEPTED_FILE_ENDING);
	}

	private void loadFile(File f) {
		Properties propertiesToLoad = new Properties();
		try (InputStream in = new FileInputStream(f)) {
			propertiesToLoad.load(in);
			PROPERTIES.put(getFileNameWithoutExtension(f), propertiesToMap(propertiesToLoad));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private Map<String, String> propertiesToMap(Properties props) {
		Map<String, String> ret = new HashMap<>();
		
		props.keySet().stream().forEach(key -> ret.put(VALUES_PREFIX + key.toString(), props.get(key).toString()));
		
		return ret;
	}

	private String getFileNameWithoutExtension(File file) {
		String fileName = file.getName();
		return fileName.substring(0, fileName.indexOf("."));
	}
	
	public boolean isKnownLocale(String locale) {
		return KEYS_SET.contains(locale);
	}
	
	public String[] getLocaleNames() {
		return KEYS;
	}
	
	public String getValue(String stringName, String key) {
		return getValue(stringName, key, "");
	}
	
	public String getValue(String stringName, String key, String defaultValue) {
		String ret = null;
		if (PROPERTIES.containsKey(stringName)) {
			ret = PROPERTIES.get(stringName).getOrDefault(key, defaultValue).toString();
		}
		return ret;
	}
	
	public Map<String, String> getStrings(String stringName) {
		return PROPERTIES.get(stringName);
	}

}
