package com.simpleblog.renderer;

import java.io.File;
import java.nio.file.Files;

import com.simpleblog.entries.Entry;

public class ImageRenderer implements FileRenderer {

	@Override
	public RenderedData render(Entry entry) {
		byte[] data;
		try {
			data = Files.readAllBytes(entry.getFile().toPath());
		} catch (Exception e) {
			e.printStackTrace();
			data = new byte [] {};
		}
		return new RenderedData(data, toMimeType(entry.getFile()));
	}
	
	private String toMimeType(File file) {
		String ret = "application/octet-stream";
		String fileName = file.getName();
		switch (fileName.substring(fileName.lastIndexOf('.'))) {
		case ".jpg" : ret = "image/jpeg"; break;
		case ".jpeg" : ret = "image/jpeg"; break;
		case ".bmp" : ret = "image/bmp"; break;
		case ".gif" : ret = "image/gif"; break;
		case ".tiff" : ret = "image/tiff"; break;
		}
		
		return ret;
	}

}
