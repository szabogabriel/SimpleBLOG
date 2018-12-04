package com.simpleblog;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

public enum CoreConfig {
	
	COMMAND_PERL("command.perl", "perl"),
	DIR_ENTRIES("dir.entries", "/entries"),
	DIR_TOOLS("dir.tools", "/tools"),
	;
	
	private static final Properties PROPS = new Properties();
	
	static {
		String wd = System.getenv("SIMPLEBLOG_HOME");
		if (wd == null) {
			wd = System.getProperty("SIMPLEBLOG_HOME");
		}
		File WORKING_DIRECTORY = new File(wd);
		
		if (WORKING_DIRECTORY.exists() && WORKING_DIRECTORY.isDirectory()) {
			File configFile = new File(WORKING_DIRECTORY.getAbsolutePath() + "/core.properties");
			try {
				PROPS.load(new FileInputStream(configFile));
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			PROPS.put(DIR_ENTRIES.KEY, wd + DIR_ENTRIES.DEFAULT);
			PROPS.put(DIR_TOOLS.KEY, wd + DIR_TOOLS.DEFAULT);
		}
	}
	
	private final String KEY;
	private final String DEFAULT;
	
	private CoreConfig(String key, String defaultvalue) {
		this.KEY = key;
		this.DEFAULT = defaultvalue;
	}
	
	@Override
	public String toString() { 
		return PROPS.getProperty(KEY, DEFAULT);
	}

}
