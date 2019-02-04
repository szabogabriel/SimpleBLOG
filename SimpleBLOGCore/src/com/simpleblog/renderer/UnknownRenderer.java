package com.simpleblog.renderer;

import com.simpleblog.entries.Entry;

public class UnknownRenderer implements FileRenderer {

	@Override
	public RenderableData render(Entry entry) {
		return new RenderableData(entry);
	}

}
