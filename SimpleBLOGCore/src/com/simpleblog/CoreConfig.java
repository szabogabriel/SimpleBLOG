package com.simpleblog;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

public enum CoreConfig {
	
	COMMAND_PERL("command.perl", "perl"),
	BUFFER_SIZE("size.buffer", "8096"),
	DIR_ENTRIES("dir.entries", "/entries"),
	DIR_TOOLS("dir.tools", "/tools"),
	DIR_LOGS("dir.logs", "/logs"),
	DIR_IMAGES("dir.images", "/images"),
	DIR_USERS("dir.users", "/users"),
	DIR_STRINGS("dir.strings", "/strings"),
	USER_MANAGER("manager.user", "com.simpleblog.users.BasicUserManager"),
	LOCALE_DEFAULT("locale.default", "en"),
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
			PROPS.put(DIR_LOGS.KEY, wd + DIR_LOGS.DEFAULT);
			PROPS.put(DIR_USERS.KEY, wd + DIR_USERS.DEFAULT);
			PROPS.put(DIR_IMAGES.KEY, wd + DIR_IMAGES.DEFAULT);
			PROPS.put(DIR_STRINGS.KEY, wd + DIR_STRINGS.DEFAULT);
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
