package com.simpleblog.entries;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public enum EntryTypes {
	
	MD(new String[] {".md"}),
	OFFICE(new String[] {
			".doc", ".dot", ".docx", ".dotx", ".docm", ".dotm", 
			".xls", ".xlt", ".xla", ".xlsx", ".xltx", ".xlsm", ".xltm", ".xlam", ".xlsb", 
			".ppt", ".pot", ".pps", ".ppa",
			".pptx", ".potx", ".ppsx", ".ppam", ".pptm", ".potm", ".ppsm",
			".mdb"
			}),
	UNKNOWN(new String[] {""});
	;
	
	private final Set<String> POSTFIX = new HashSet<>(); 
	private EntryTypes(String[] postfix) {
		POSTFIX.addAll(Arrays.asList(postfix));
	}
	
	static EntryTypes getType(String filename) {
		for (EntryTypes it : values()) {
			if (it.POSTFIX.contains(extension(filename))) {
				return it;
			}
		}
		return EntryTypes.UNKNOWN;
	}
	
	private static String extension(String filename) {
		return filename.substring(filename.lastIndexOf("."));
	}

}
