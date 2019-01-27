package com.simpleblog.entries;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;

public class EntriesManager {
	
	private final File ROOT_FOLDER;
	
	public EntriesManager(File rootFolder) {
		this.ROOT_FOLDER = rootFolder;
	}
	
	public void createCategory(String category) {
		File targetFolder = new File(ROOT_FOLDER.getAbsoluteFile() + "/" + category);
		if (!targetFolder.exists()) {
			targetFolder.mkdir();
		}
	}
	
	public void createEntry(String category, String entryName, byte [] entry) {
		File targetFile = new File(ROOT_FOLDER.getAbsoluteFile() + "/" + category + "/" + entryName);
		if (!targetFile.exists()) {
			try {
				Files.write(targetFile.toPath(), entry);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
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
