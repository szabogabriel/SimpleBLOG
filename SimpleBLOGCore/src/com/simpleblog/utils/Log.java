package com.simpleblog.utils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;

import com.simpleblog.CoreConfig;

public class Log {
	
	private static final SimpleDateFormat DATE_FORMAT_FILE = new SimpleDateFormat("yyyyMMdd");
	private static final SimpleDateFormat DATE_FORMAT_LOG_PREFIX = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
	
	private static final String LOGS_DIR = CoreConfig.DIR_LOGS.toString();
	
	public static void Info(String source, String message) {
		LogMessage(Level.INFO, source, message);
	}
	
	public static void Warning(String source, String message) {
		LogMessage(Level.WARNING, source, message);
	}
	
	public static void Error(String source, String message) {
		LogMessage(Level.SEVERE, source, message);
	}
	
	public static void Trace(String source, String message) {
		LogMessage(Level.FINE, source, message);
	}
	
	private static void LogMessage(Level level, String source, String message) {
		Date date = new Date(System.currentTimeMillis());
		File targetFile = new File(LOGS_DIR + "/" + DATE_FORMAT_FILE.format(date) + ".log");
		IOUtil.append(targetFile, System.lineSeparator() + "[" + DATE_FORMAT_LOG_PREFIX.format(date) + "] [" + level + "] " + targetFile);
	}
	
}
