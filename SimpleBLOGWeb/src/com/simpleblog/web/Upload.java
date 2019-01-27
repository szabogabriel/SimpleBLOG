package com.simpleblog.web;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.jmtemplate.Template;
import com.simpleblog.Main;
import com.simpleblog.utils.IOUtil;
import com.simpleblog.utils.QueryString;
import com.simpleblog.utils.TemplateLoader;

public class Upload extends PageBase {
	
	private static final Template TEMPLATE = TemplateLoader.INSTANCE.load("upload.template");

	public void doGet(String queryString, Response response) throws IOException {
		Map<String, Object> data = new HashMap<>();
		QueryString qs = new QueryString(queryString);
		
		data.put("entries", getMenuEntries(qs));
		
		String toReturn = TEMPLATE.render(data);
		response.setContentType("text/html");
		response.getOutputStream().write(toReturn.getBytes());
	}
	
	public void addCategory(String category, Response response) throws IOException {
		Main.INSTANCE.getEntriesManager().createCategory(category);
		doGet("", response);
	}
	
	public void addFile(String category, File file, Response response) throws IOException {
		String fileName = file.getName();
		byte [] fileContent = IOUtil.readFileBytes(file);
		Main.INSTANCE.getEntriesManager().createEntry(category, fileName, fileContent);
		doGet("", response);
	}
	

}
