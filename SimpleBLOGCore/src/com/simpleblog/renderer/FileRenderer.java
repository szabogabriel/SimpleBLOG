package com.simpleblog.renderer;

import com.simpleblog.entries.Entry;

public interface FileRenderer {
	
	RenderedData render(Entry entry);

}
