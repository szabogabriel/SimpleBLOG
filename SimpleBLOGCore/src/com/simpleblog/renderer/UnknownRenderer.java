package com.simpleblog.renderer;

import com.simpleblog.entries.Entry;

public class UnknownRenderer implements FileRenderer {

	@Override
	public RenderedData render(Entry entry) {
		return new RenderedData(entry.getContent().getBytes(), "application/octet-stream");
	}

}
