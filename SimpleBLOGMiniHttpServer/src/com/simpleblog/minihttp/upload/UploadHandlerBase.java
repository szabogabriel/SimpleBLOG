package com.simpleblog.minihttp.upload;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public abstract class UploadHandlerBase implements HttpHandler {
	
	private final int HTTP_OK_STATUS = 200;
	
	private final String HANDLER_URL;
	private final File TARGET_FOLDER;
	
	public UploadHandlerBase(String handlerURL, File targetFolder) {
		HANDLER_URL = handlerURL;
		TARGET_FOLDER = targetFolder;
	}
	
	protected String getHandlerUrl() {
		return HANDLER_URL;
	}
	
	protected File getTargetFolder() {
		return TARGET_FOLDER;
	}
	
	public void handle(HttpExchange arg0) throws IOException {
		String method = arg0.getRequestMethod().toUpperCase();
		if ("GET".equals(method)) {
			URI uri = arg0.getRequestURI();
			String response = 
					  "<html><head><title>Hello world</title></head>"
					+ "<body><h1>Hello, world!</h1><p>URI: " + uri.getPath() + "</p>"
					+ "<form action=\"" + HANDLER_URL + "\" method=\"POST\" enctype=\"multipart/form-data\">"
					+ "<input type=\"file\" name=\"pic\" accept=\"image/*\">"
						+ "<input type=\"submit\">"
					+ "</form>"
					+ "</body>"
					+ "</html>";

			System.out.println("Response: " + response);
			
			arg0.sendResponseHeaders(HTTP_OK_STATUS, response.getBytes().length);
	        
	        OutputStream os = arg0.getResponseBody();
	        os.write(response.getBytes());
			   os.close();
		}
		if ("POST".equals(method)) {
			handlePost(arg0);
		}
	}
	
	public abstract void handlePost(HttpExchange arg0) throws IOException;

}
