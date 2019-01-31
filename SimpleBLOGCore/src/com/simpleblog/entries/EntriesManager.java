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
		File targetFile = getTargetFile(category, entryName);
		if (!targetFile.exists()) {
			try {
				Files.write(targetFile.toPath(), entry);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void createEntry(String category, String entryName, File entry) {
		if (entry.exists() && entry.isFile()) {
			File targetFile = getTargetFile(category, entryName);
			try {
				Files.move(entry.toPath(), targetFile.toPath());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private File getTargetFile(String category, String entryName) {
		return new File(ROOT_FOLDER.getAbsoluteFile() + "/" + category + "/" + entryName);
	}
	
	public boolean isKnownCategory(String category) {
		return Arrays.asList(getEntryCategories()).stream().filter(e -> e.equals(category)).count() > 0;
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
