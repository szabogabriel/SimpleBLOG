package com.simpleblog.minihttp.upload;

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

import com.sun.net.httpserver.HttpExchange;

public class ApacheCommonsUploadHandler extends UploadHandlerBase {

	public ApacheCommonsUploadHandler(String handlerURL, File targetFolder) {
		super(handlerURL, targetFolder);
	}

	@Override
	public void handlePost(HttpExchange t) throws IOException {
		for (Entry<String, List<String>> header : t.getRequestHeaders().entrySet()) {
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
					return t.getRequestHeaders().getFirst("Content-type");
				}

				@Override
				public InputStream getInputStream() throws IOException {
					return t.getRequestBody();
				}

			});
			t.getResponseHeaders().add("Content-type", "text/plain");
			t.sendResponseHeaders(200, 0);
			OutputStream os = t.getResponseBody();
			for (FileItem fi : result) {
				os.write(fi.getName().getBytes());
				os.write("\r\n".getBytes());
				System.out.println("File-Item: " + fi.getFieldName() + " = " + fi.getName());
				File targetFile = new File(getTargetFolder().getAbsoluteFile() + "/" + fi.getName());
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
	}

}
