package com.simpleblog.renderer;

import java.io.File;
import java.nio.file.Files;

import com.simpleblog.entries.Entry;

public class MSOfficeRenderer implements FileRenderer {

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
		case ".doc" : ret = "application/msword"; break;
		case ".dot" : ret = "application/msword"; break;
		case ".docx" : ret = "application/vnd.openxmlformats-officedocument.wordprocessingml.document"; break;
		case ".dotx" : ret = "application/vnd.openxmlformats-officedocument.wordprocessingml.template"; break;
		case ".docm" : ret = "application/vnd.ms-word.document.macroEnabled.12"; break;
		case ".dotm" : ret = "application/vnd.ms-word.template.macroEnabled.12"; break;
		case ".xls" : ret = "application/vnd.ms-excel"; break;
		case ".xlt" : ret = "application/vnd.ms-excel"; break;
		case ".xla" : ret = "application/vnd.ms-excel"; break;
		case ".xlsx" : ret = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"; break;
		case ".xltx" : ret = "application/vnd.openxmlformats-officedocument.spreadsheetml.template"; break;
		case ".xlsm" : ret = "application/vnd.ms-excel.sheet.macroEnabled.12"; break;
		case ".xltm" : ret = "application/vnd.ms-excel.template.macroEnabled.12"; break;
		case ".xlam" : ret = "application/vnd.ms-excel.addin.macroEnabled.12"; break;
		case ".xlsb" : ret = "application/vnd.ms-excel.sheet.binary.macroEnabled.12"; break;
		case ".ppt" : ret = "application/vnd.ms-powerpoint"; break;
		case ".pot" : ret = "application/vnd.ms-powerpoint"; break;
		case ".pps" : ret = "application/vnd.ms-powerpoint"; break;
		case ".ppa" : ret = "application/vnd.ms-powerpoint"; break;
		case ".pptx" : ret = "application/vnd.openxmlformats-officedocument.presentationml.presentation"; break;
		case ".potx" : ret = "application/vnd.openxmlformats-officedocument.presentationml.template"; break;
		case ".ppsx" : ret = "application/vnd.openxmlformats-officedocument.presentationml.slideshow"; break;
		case ".ppam" : ret = "application/vnd.ms-powerpoint.addin.macroEnabled.12"; break;
		case ".pptm" : ret = "application/vnd.ms-powerpoint.presentation.macroEnabled.12"; break;
		case ".potm" : ret = "application/vnd.ms-powerpoint.template.macroEnabled.12"; break;
		case ".ppsm" : ret = "application/vnd.ms-powerpoint.slideshow.macroEnabled.12"; break;
		case ".mdb" : ret = "application/vnd.ms-access"; break;
		}
		
		return ret;
	}

}
