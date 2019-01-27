package com.simpleblog.web;

import java.io.File;

public class UploadRequest {
	
	private final String USERNAME;
	private final String PASSWORD;
	private final String CATEGORY;
	private final File FILE;
	
	public UploadRequest(String uname, String psswd, String selectedCategory, File targetFile) {
		this.USERNAME = uname;
		this.PASSWORD = psswd;
		this.CATEGORY = selectedCategory;
		this.FILE = targetFile;
	}
	
	public String getUsername() {
		return USERNAME;
	}
	
	public String getPassword() {
		return PASSWORD;
	}
	
	public String getCategory() {
		return CATEGORY;
	}
	
	public File getFile() {
		return FILE;
	}
	
}
