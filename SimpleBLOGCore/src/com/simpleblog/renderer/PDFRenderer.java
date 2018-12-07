package com.simpleblog.renderer;

import java.nio.file.Files;

import com.simpleblog.entries.Entry;

public class PDFRenderer implements FileRenderer {

	@Override
	public RenderedData render(Entry entry) {
		byte [] data = new byte [] {};
		try {
			data = Files.readAllBytes(entry.getFile().toPath());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new RenderedData(data, "application/pdf");
	}

}
