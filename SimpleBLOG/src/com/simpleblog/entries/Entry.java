package com.simpleblog.entries;

import java.io.File;

import com.simpleblog.utils.IOUtil;

public class Entry {
	
	private final File ENTRY_FILE;
	private final Types TYPE;
	
	public static enum Types {
		MD(".md"),
		UNKNOWN("");
		;
		
		private final String POSTFIX; 
		private Types(String postfix) {
			POSTFIX = postfix;
		}
		
		static Types getType(String filename) {
			for (Types it : values()) {
				if (filename.endsWith(it.POSTFIX)) {
					return it;
				}
			}
			return Types.UNKNOWN;
		}
	}
	
	public Entry(File file) {
		this.ENTRY_FILE = file;
		this.TYPE = Types.getType(file.getName());
	}
	
	public File getFile() {
		return ENTRY_FILE;
	}

	public String getContent() {
		return IOUtil.readFile(ENTRY_FILE);
	}
	
	public Types getType() {
		return TYPE;
	}
}
