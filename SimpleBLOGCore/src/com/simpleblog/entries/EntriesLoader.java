package com.simpleblog.entries;

import java.io.File;
import java.util.Arrays;

public class EntriesLoader {
	
	private final File ROOT_FOLDER;
	
	public EntriesLoader(File rootFolder) {
		this.ROOT_FOLDER = rootFolder;
	}
	
	public String[] getEntryCategories() {
		return Arrays.asList(ROOT_FOLDER.list()).stream().sorted((a, b) -> a.compareTo(b)).toArray(String[]::new);
	}
	
	public File[] getEntries(String category) {
		return 
			Arrays.asList(new File(ROOT_FOLDER.getAbsolutePath() + "/" + category).listFiles())
				.stream()
				.sorted((a, b) -> a.getName().compareTo(b.getName()))
				.toArray(File[]::new);
	}
	
}
