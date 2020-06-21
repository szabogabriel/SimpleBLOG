package com.simpleblog.entries;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class EntriesManager {
	
	private final File ROOT_FOLDER;
	
	private final Map<String, File> ROOT_LOCAL_FOLDER = new HashMap<>();
	
	public EntriesManager(File rootFolder) {
		this.ROOT_FOLDER = rootFolder;
	}
	
	public void createCategory(String locale, String category) {
		File targetFolder = getRootFolder(locale, category);
		if (!targetFolder.exists()) {
			targetFolder.mkdir();
		}
	}
	
	public void createEntry(String locale, String category, String entryName, byte [] entry) {
		File targetFile = getTargetFile(locale, category, entryName);
		if (!targetFile.exists()) {
			try {
				Files.write(targetFile.toPath(), entry);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void createEntry(String locale, String category, String entryName, File entry) {
		if (entry.exists() && entry.isFile()) {
			File targetFile = getTargetFile(locale, category, entryName);
			try {
				Files.move(entry.toPath(), targetFile.toPath());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private File getRootFolder(String locale) {
		if (!ROOT_LOCAL_FOLDER.containsKey(locale)) {
			ROOT_LOCAL_FOLDER.put(locale, new File(ROOT_FOLDER.getAbsoluteFile() + "/" + locale));
		}
		return ROOT_LOCAL_FOLDER.get(locale);
	}
	
	private File getRootFolder(String locale, String category) {
		return new File(getRootFolder(locale).getAbsoluteFile() + "/" + category);
	}
	
	private File getTargetFile(String locale, String category, String entryName) {
		return new File(getRootFolder(locale, category).getAbsoluteFile() + "/" + entryName);
	}
	
	public boolean isKnownCategory(String locale, String category) {
		return Arrays.asList(getEntryCategories(locale)).stream().filter(e -> e.equals(category)).count() > 0;
	}
	
	public String[] getEntryCategories(String locale) {
		return Arrays.asList(getRootFolder(locale).list()).stream().sorted((a, b) -> a.compareTo(b)).toArray(String[]::new);
	}
	
	public File[] getEntries(String locale, String category) {
		return 
			Arrays.asList(getRootFolder(locale, category).listFiles())
				.stream()
				.sorted((a, b) -> a.getName().compareTo(b.getName()))
				.toArray(File[]::new);
	}
	
}
