package com.simpleblog.utils;

import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;

public class QueryString {
	
	private Properties properties = new Properties();
	
	public QueryString(String qs) {
		if (qs != null) {
			String stripped = qs;
			if (qs.startsWith("?")) {
				stripped = qs.substring(1).trim();
			}
			
			String [] props = stripped.split("&");
			if (props != null) {
				for (String it : props) {
					String [] keyvalue = it.split("=");
					if (keyvalue.length == 2) {
						properties.put(keyvalue[0], keyvalue[1]);
					} else if (keyvalue.length == 1 && keyvalue[0].length() > 1) {
						properties.put(keyvalue[0], "");
					}
				}
			}
		}
	}
	
	public boolean containsKey(String key) {
		return properties.containsKey(key);
	}
	
	public String getValue(String key) {
		return properties.getProperty(key);
	}
	
	public String getValue(String key, String defaultValue) {
		return properties.getProperty(key, defaultValue);
	}
	
	public Set<String> keys() { 
		return properties.keySet().stream().map(k -> k.toString()).collect(Collectors.toSet());
	}

}