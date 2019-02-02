
package com.simpleblog.minihttp.handlers.upload;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.RequestContext;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.simpleblog.utils.IOUtil;
import com.simpleblog.web.Response;
import com.simpleblog.web.Upload;
import com.simpleblog.web.UploadRequest;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class UploadHandlerBase implements HttpHandler {
	
	private Upload upload = new Upload();
	
	public void handle(HttpExchange arg0) throws IOException {
		String method = arg0.getRequestMethod().toUpperCase();
		if ("GET".equalsIgnoreCase(method)) {
			handleGet(arg0);
		}
		if ("POST".equalsIgnoreCase(method)) {
			handlePost(arg0);
		}
	}
	
	private void handleGet(HttpExchange exchange) throws IOException {
		upload.doGet(new Response() {

			@Override
			public OutputStream getOutputStream() {
				return exchange.getResponseBody();
			}

			@Override
			public void setContentType(String arg0) {
				exchange.getResponseHeaders().set("Content-Type", arg0);
			}

			@Override
			public void sendResponse(int code, long length) {
				try {
					exchange.sendResponseHeaders(code, length);
				} catch (IOException e) {
					e.printStackTrace();
				}				
			}
		});
	}
	
	public void handlePost(HttpExchange exchange) throws IOException {
		String uname = null;
		String psswd = null;
		String categoryNew = null;
		String categoryOld = null;
		String fileName = null;
		File file = null;
		
		try {
			List<FileItem> result = createPostMessageItems(exchange);
			
			for (FileItem fi : result) {
				String fieldName = fi.getFieldName();
				
				if ("username".equals(fieldName)) {
					uname = new String(fi.get());
				} else
				if ("password".equals(fieldName)) {
					psswd = new String(fi.get());
				} else
				if ("categoryNew".equals(fieldName)) {
					categoryNew = new String(fi.get());
				} else
				if ("categoryExisting".equals(fieldName)) {
					categoryOld = new String(fi.get());
				} else
				if ("file".equals(fieldName)) {
					fileName = fi.getName();
					file = new File(IOUtil.getTempFolder().getAbsolutePath() + "/" + fi.getName());
					fi.write(file);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		UploadRequest request = new UploadRequest(uname, psswd, getCategory(categoryNew, categoryOld), file, fileName);
		upload.doPost(request, new Response() {

			@Override
			public OutputStream getOutputStream() {
				return exchange.getResponseBody();
			}

			@Override
			public void setContentType(String arg0) {
				exchange.getResponseHeaders().set("Content-Type", arg0);
			}

			@Override
			public void sendResponse(int code, long length) {
				try {
					exchange.sendResponseHeaders(code, length);
				} catch (IOException e) {
					e.printStackTrace();
				}				
			}
		});
	}
	
	private String getCategory(String newCategory, String oldCategory) {
		String ret = null;
		
		if (oldCategory != null && oldCategory.length() > 0) {
			ret = oldCategory;
		} else
		if (newCategory != null && newCategory.length() > 0) {
			ret = newCategory;
		}
		
		return ret;
	}
	
	private List<FileItem> createPostMessageItems(HttpExchange exchange) throws FileUploadException {
		DiskFileItemFactory d = new DiskFileItemFactory();
		ServletFileUpload up = new ServletFileUpload(d);
		List<FileItem> result = up.parseRequest(new RequestContext() {
			@Override
			public String getCharacterEncoding() {
				return "UTF-8";
			}
			@Override
			public int getContentLength() {
				return 0; // tested to work with 0 as return
			}
			@Override
			public String getContentType() {
				return exchange.getRequestHeaders().getFirst("Content-type");
			}
			@Override
			public InputStream getInputStream() throws IOException {
				return exchange.getRequestBody();
			}
		});
		return result;
	}

}
