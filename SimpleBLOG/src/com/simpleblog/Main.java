package com.simpleblog;

import java.io.File;
import java.util.Arrays;

import com.simpleblog.entries.EntriesLoader;
import com.simpleblog.entries.Entry;
import com.simpleblog.renderer.EntryRenderer;

public class Main {
	
	public static final Main INSTANCE = new Main();
	
	private EntriesLoader entriesLoader;
	private EntryRenderer entriesRenderer;
	
	private Main() {
		entriesLoader = new EntriesLoader(new File(Config.DIR_ENTRIES.toString()));
		entriesRenderer = new EntryRenderer();
	}
	
	public EntriesLoader getEntriesLoader() {
		return entriesLoader;
	}
	
	public EntryRenderer getEntriesRenderer() {
		return entriesRenderer;
	}

	public String getRenderedEntry(String category, String name) {
		String ret = "";
		File entry = Arrays.asList(entriesLoader.getEntries(category)).stream().filter(e -> e.getName().startsWith(name)).findAny().orElse(null);
		
		if (entry != null) {
			ret = entriesRenderer.render(new Entry(entry));
		}
		
		return ret;
	}
}
