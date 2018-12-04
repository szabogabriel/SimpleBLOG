package com.simpleblog.renderer;

import java.util.HashMap;
import java.util.Map;

import com.simpleblog.entries.Entry;
import com.simpleblog.entries.EntryTypes;

public class EntryRenderer {
	
	private final Map<EntryTypes, FileRenderer> RENDERERS = new HashMap<>();
	
	public EntryRenderer() {
		RENDERERS.put(EntryTypes.UNKNOWN, new UnknownRenderer());
		RENDERERS.put(EntryTypes.MD, new MarkdownRenderer());
		RENDERERS.put(EntryTypes.OFFICE, new MSOfficeRenderer());
	}
	
	public RenderedData render(Entry entry) {
		RenderedData ret = null;
		if (RENDERERS.containsKey(entry.getType())) {
			ret = RENDERERS.get(entry.getType()).render(entry);
		} else {
			ret = new RenderedData(entry.getContent().getBytes(), "application/octet-stream");
		}
		return ret;
	}

}
