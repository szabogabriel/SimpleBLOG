package com.simpleblog.renderer;

import java.util.HashMap;
import java.util.Map;

import com.simpleblog.entries.Entry;

public class EntryRenderer {
	
	private final Map<Entry.Types, FileRenderer> RENDERERS = new HashMap<>();
	
	public EntryRenderer() {
		RENDERERS.put(Entry.Types.UNKNOWN, new UnknownRenderer());
		RENDERERS.put(Entry.Types.MD, new MarkdownRenderer());
	}
	
	public String render(Entry entry) {
		String ret = "";
		if (RENDERERS.containsKey(entry.getType())) {
			ret = RENDERERS.get(entry.getType()).render(entry);
		} else {
			ret = entry.getContent();
		}
		return ret;
	}

}
