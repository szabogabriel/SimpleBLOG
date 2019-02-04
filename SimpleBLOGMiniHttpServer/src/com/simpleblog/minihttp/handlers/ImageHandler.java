package com.simpleblog.minihttp.handlers;

import java.io.File;
import java.io.IOException;

import com.simpleblog.CoreConfig;
import com.simpleblog.utils.IOUtil;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class ImageHandler implements HttpHandler {
	
	private final String IMAGE_FOLDER = CoreConfig.DIR_IMAGES.toString();
	private final String URL_PREFIX;
	
	public ImageHandler(String prefix) {
		URL_PREFIX = prefix;
	}

	@Override
	public void handle(HttpExchange arg0) throws IOException {
		File image = getImageFile(arg0.getRequestURI().getPath());
		if (image == null || !image.exists()) {
			sendEmptyFile(arg0);
		} else {
			sendFile(arg0, image);
		}
	}
	
	private void sendFile(HttpExchange arg0, File image) throws IOException {
		arg0.sendResponseHeaders(200, image.length());
		arg0.getResponseHeaders().set("Content-Type", IOUtil.getMimeType(image));
		IOUtil.sendFileToStream(image, arg0.getResponseBody());
		arg0.getResponseBody().close();
	}
	
	private void sendEmptyFile(HttpExchange arg0) throws IOException {
		arg0.sendResponseHeaders(200, 0);
		arg0.getResponseHeaders().set("Content-Type", "application/octet-stream");
		arg0.getResponseBody().close();
	}
	
	private File getImageFile(String url) {
		File ret = null;
		if (url.length() > URL_PREFIX.length() + 1 && !url.contains("..")) {
			ret = new File(IMAGE_FOLDER + url.substring(URL_PREFIX.length()));
		}
		return ret;
	}
	
}
