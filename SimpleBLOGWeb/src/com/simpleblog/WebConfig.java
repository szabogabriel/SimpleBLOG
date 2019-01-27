package com.simpleblog;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

public enum WebConfig {

	DIR_TEMPLATES("dir.templates", "/templates"),
	TEMPLATES_RELOAD("templates.reload", "true"),
	;
	
	private static final Properties PROPS = new Properties();
	
	static {
		String wd = System.getenv("SIMPLEBLOG_HOME");
		if (wd == null) {
			wd = System.getProperty("SIMPLEBLOG_HOME");
		}
		File WORKING_DIRECTORY = new File(wd);
		
		if (WORKING_DIRECTORY.exists() && WORKING_DIRECTORY.isDirectory()) {
			File configFile = new File(WORKING_DIRECTORY.getAbsolutePath() + "/settings.properties");
			try {
				PROPS.load(new FileInputStream(configFile));
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			PROPS.put(DIR_TEMPLATES.KEY, wd + DIR_TEMPLATES.DEFAULT);
		}
	}
	
	private final String KEY;
	private final String DEFAULT;
	
	private WebConfig(String key, String defaultvalue) {
		this.KEY = key;
		this.DEFAULT = defaultvalue;
	}
	
	@Override
	public String toString() { 
		return PROPS.getProperty(KEY, DEFAULT);
	}
	
}
