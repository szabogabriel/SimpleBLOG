package com.simpleblog.renderer;

import java.util.HashMap;
import java.util.Map;

import com.simpleblog.entries.Entry;
import com.simpleblog.entries.EntryTypes;

public class EntryRenderer {
	
	private final Map<EntryTypes, FileRenderer> RENDERERS = new HashMap<>();
	
	public EntryRenderer() {
		RENDERERS.put(EntryTypes.HTML, new HtmlRenderer());
		RENDERERS.put(EntryTypes.IMAGE, new ImageRenderer());
		RENDERERS.put(EntryTypes.MD, new MarkdownRenderer());
		RENDERERS.put(EntryTypes.UNKNOWN, new UnknownRenderer());
	}
	
	public RenderableData render(Entry entry) {
		RenderableData ret = null;
		if (RENDERERS.containsKey(entry.getType())) {
			ret = RENDERERS.get(entry.getType()).render(entry);
		} else {
			ret = new RenderableData(entry);
		}
		return ret;
	}

}
