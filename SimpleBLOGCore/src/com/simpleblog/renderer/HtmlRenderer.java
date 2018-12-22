package com.simpleblog.renderer;

import java.io.IOException;
import java.nio.file.Files;

import com.simpleblog.entries.Entry;

public class HtmlRenderer implements FileRenderer {

	@Override
	public RenderedData render(Entry entry) {
		String mimeType = "text/html";
		byte [] content = new byte [] {};
		try {
			content = Files.readAllBytes(entry.getFile().toPath());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new RenderedData(content, mimeType);
	}

}
