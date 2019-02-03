package com.simpleblog.minihttp.handlers;

import java.io.IOException;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class RootHandler implements HttpHandler {
	
	private final String RESPONSE;
	
	private final String RESPONSE1 = "<html><head><meta http-equiv=\"refresh\" content=\"0; url=";
	private final String RESPONSE2 = "\" /></head><body/></html>";
	
	public RootHandler(String urlPrefix) {
		RESPONSE = RESPONSE1 + urlPrefix + "/page" + RESPONSE2;
	}

	@Override
	public void handle(HttpExchange arg0) throws IOException {
		arg0.sendResponseHeaders(200, RESPONSE.length());
		arg0.getResponseHeaders().set("Content-Type", "text/html");
		arg0.getResponseBody().write(RESPONSE.getBytes());
		arg0.getResponseBody().close();
	}

}
