package com.simpleblog.entries;

import java.io.File;

import com.simpleblog.utils.IOUtil;

public class Entry {
	
	private final File ENTRY_FILE;
	private final EntryTypes TYPE;
	
	public Entry(File file) {
		this.ENTRY_FILE = file;
		this.TYPE = EntryTypes.getType(file.getName());
	}
	
	public File getFile() {
		return ENTRY_FILE;
	}

	public String getContent() {
		return IOUtil.readFile(ENTRY_FILE);
	}
	
	public EntryTypes getType() {
		return TYPE;
	}
}
