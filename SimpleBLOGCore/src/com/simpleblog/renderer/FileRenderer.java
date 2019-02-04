package com.simpleblog.renderer;

import com.simpleblog.entries.Entry;

public interface FileRenderer {
	
	RenderableData render(Entry entry);

}
