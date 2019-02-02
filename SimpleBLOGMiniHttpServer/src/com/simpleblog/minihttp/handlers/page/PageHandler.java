package com.simpleblog.minihttp.handlers.page;

import java.io.IOException;
import java.io.OutputStream;

import com.simpleblog.web.Page;
import com.simpleblog.web.Response;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class PageHandler implements HttpHandler {
	
	private final Page PAGE = new Page();
	
	@Override
	public void handle(HttpExchange exchange) throws IOException {
		PAGE.doGet(exchange.getRequestURI().getQuery(), new Response() {

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
