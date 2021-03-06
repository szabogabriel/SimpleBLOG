package com.simpleblog;

import java.io.File;
import java.util.Arrays;

import com.simpleblog.entries.EntriesManager;
import com.simpleblog.entries.Entry;
import com.simpleblog.locale.Locales;
import com.simpleblog.renderer.EntryRenderer;
import com.simpleblog.renderer.RenderableData;
import com.simpleblog.users.UserManager;

public class Main {
	
	public static final Main INSTANCE = new Main();
	
	private UserManager userManager = UserManager.Factory.INSTANCE.getUserManager();
	private EntriesManager entriesLoader;
	private EntryRenderer entriesRenderer;
	private Locales locales;
	
	private Main() {
		entriesLoader = new EntriesManager(new File(CoreConfig.DIR_ENTRIES.toString()));
		entriesRenderer = new EntryRenderer();
		locales = new Locales(new File(CoreConfig.DIR_STRINGS.toString()));
	}
	
	public EntriesManager getEntriesManager() {
		return entriesLoader;
	}
	
	public EntryRenderer getEntriesRenderer() {
		return entriesRenderer;
	}
	
	public UserManager getUserManager() {
		return userManager;
	}
	
	public Locales getLocales() {
		return locales;
	}

	public RenderableData getRenderedEntry(String locale, String category, String name) {
		RenderableData ret = new RenderableData("", "application/octet-stream", false);
		File entry = Arrays.asList(entriesLoader.getEntries(locale, category)).stream().filter(e -> e.getName().startsWith(name)).findAny().orElse(null);
		
		if (entry != null) {
			ret = entriesRenderer.render(new Entry(entry));
		}
		
		return ret;
	}
}
