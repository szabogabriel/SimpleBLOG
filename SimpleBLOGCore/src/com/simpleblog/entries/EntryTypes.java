package com.simpleblog.entries;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public enum EntryTypes {

	HTML(new String[] {".htm", ".html"}),
	IMAGE(new String[] { "jpg", "jpeg", "gif", "tiff", "bmp"}),
	MD(new String[] {".md"}),
	OFFICE(new String[] {
			".doc", ".dot", ".docx", ".dotx", ".docm", ".dotm", 
			".xls", ".xlt", ".xla", ".xlsx", ".xltx", ".xlsm", ".xltm", ".xlam", ".xlsb", 
			".ppt", ".pot", ".pps", ".ppa",
			".pptx", ".potx", ".ppsx", ".ppam", ".pptm", ".potm", ".ppsm",
			".mdb"
			}),
	PDF(new String [] {".pdf"}),
	UNKNOWN(new String[] {""});
	;
	
	private final static Map<String, EntryTypes> MAPPING = new HashMap<>();
	
	private final Set<String> POSTFIX = new HashSet<>(); 
	private EntryTypes(String[] postfix) {
		POSTFIX.addAll(Arrays.asList(postfix));
	}
	
	static EntryTypes getType(String filename) {
		String extension = extension(filename).toLowerCase();
		EntryTypes ret = EntryTypes.UNKNOWN;
		
		if (MAPPING.containsKey(extension)) {
			ret = MAPPING.get(extension);
		} else {
			for (EntryTypes it : values()) {
				if (it.POSTFIX.contains(extension)) {
					ret = it;
				}
			}
			MAPPING.put(extension, ret);
		}
		return ret;
	}
	
	private static String extension(String filename) {
		return filename.substring(filename.lastIndexOf("."));
	}

}
