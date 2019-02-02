
package com.simpleblog.minihttp.handlers.upload;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map.Entry;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.RequestContext;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.simpleblog.utils.IOUtil;
import com.simpleblog.web.Response;
import com.simpleblog.web.Upload;
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
		for (Entry<String, List<String>> header : exchange.getRequestHeaders().entrySet()) {
			System.out.println(header.getKey() + ": " + header.getValue().get(0));
		}
		DiskFileItemFactory d = new DiskFileItemFactory();

		try {
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
			
			exchange.getResponseHeaders().add("Content-type", "text/plain");
			exchange.sendResponseHeaders(200, 0);
			OutputStream os = exchange.getResponseBody();
			for (FileItem fi : result) {
				os.write(fi.getName().getBytes());
				os.write("\r\n".getBytes());
				System.out.println("File-Item: " + fi.getFieldName() + " = " + fi.getName());
				File targetFile = new File(IOUtil.getTempFolder().getAbsolutePath() + "/" + fi.getName());
				try (FileOutputStream out = new FileOutputStream(targetFile)) {
					System.out.println("Creating file: " + targetFile.getAbsolutePath());
					out.write(fi.get());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			os.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		
//		UploadRequest request = new UploadRequest(uname, psswd, selectedCategory, targetFile, fileName)
		upload.doPost(null, new Response() {

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

}
