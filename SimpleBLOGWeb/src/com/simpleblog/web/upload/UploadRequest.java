package com.simpleblog.web.upload;

import java.io.File;

public class UploadRequest {
	
	private final String USERNAME;
	private final String PASSWORD;
	private final String CATEGORY;
	private final File FILE;
	private final String FILE_NAME;
	
	public UploadRequest(String uname, String psswd, String selectedCategory, File targetFile, String fileName) {
		this.USERNAME = uname;
		this.PASSWORD = psswd;
		this.CATEGORY = selectedCategory;
		this.FILE = targetFile;
		this.FILE_NAME = fileName;
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
	
	public String getFileName() {
		return FILE_NAME;
	}
	
}
