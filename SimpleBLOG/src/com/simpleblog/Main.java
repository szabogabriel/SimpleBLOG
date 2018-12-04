package com.simpleblog;

import java.io.File;
import java.util.Arrays;

import com.simpleblog.entries.EntriesLoader;
import com.simpleblog.entries.Entry;
import com.simpleblog.renderer.EntryRenderer;
import com.simpleblog.renderer.RenderedData;

public class Main {
	
	public static final Main INSTANCE = new Main();
	
	private EntriesLoader entriesLoader;
	private EntryRenderer entriesRenderer;
	
	private Main() {
		entriesLoader = new EntriesLoader(new File(CoreConfig.DIR_ENTRIES.toString()));
		entriesRenderer = new EntryRenderer();
	}
	
	public EntriesLoader getEntriesLoader() {
		return entriesLoader;
	}
	
	public EntryRenderer getEntriesRenderer() {
		return entriesRenderer;
	}

	public RenderedData getRenderedEntry(String category, String name) {
		RenderedData ret = new RenderedData(new byte [] {}, "application/octet-stream");
		File entry = Arrays.asList(entriesLoader.getEntries(category)).stream().filter(e -> e.getName().startsWith(name)).findAny().orElse(null);
		
		if (entry != null) {
			ret = entriesRenderer.render(new Entry(entry));
		}
		
		return ret;
	}
}
