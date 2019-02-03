package com.simpleblog.minihttp.handlers.root;

import java.io.IOException;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class RootHandler implements HttpHandler {
	
	private final String URL_PREFIX;
	
	private final String RESPONSE = "<html><head><meta http-equiv=\"refresh\" content=\"0; url=###\" /></head><body/></html>";
	
	public RootHandler(String urlPrefix) {
		URL_PREFIX = urlPrefix;
	}

	@Override
	public void handle(HttpExchange arg0) throws IOException {
		String renderedResponse = RESPONSE.replaceAll("###", URL_PREFIX + "/page");
		arg0.sendResponseHeaders(200, renderedResponse.length());
		arg0.getResponseHeaders().set("Content-Type", "text/html");
		arg0.getResponseBody().write(renderedResponse.getBytes());
		arg0.getResponseBody().close();
	}

}
