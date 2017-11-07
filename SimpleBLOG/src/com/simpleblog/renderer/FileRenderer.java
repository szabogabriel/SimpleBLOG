package com.simpleblog.renderer;

import com.simpleblog.entries.Entry;

public interface FileRenderer {
	
	String render(Entry entry);

}
