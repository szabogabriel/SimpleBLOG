package com.simpleblog.renderer;

import com.simpleblog.entries.Entry;

public class UnknownRenderer implements FileRenderer {

	@Override
	public String render(Entry entry) {
		return entry.getContent();
	}

}
