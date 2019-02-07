package com.simpleblog.minihttp;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;

import com.simpleblog.minihttp.handlers.ImageHandler;
import com.simpleblog.minihttp.handlers.PageHandler;
import com.simpleblog.minihttp.handlers.RootHandler;
import com.simpleblog.minihttp.handlers.UploadHandler;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

public class Server {
	
	private final String HANDLER_URL;
	private final HttpServer SERVER;
	private final HttpHandler HANDLER_ROOT;
	private final HttpHandler HANDLER_PAGE;
	private final HttpHandler HANDLER_UPLOAD;
	private final HttpHandler HANDLER_IMAGE;
	
	public Server(InetSocketAddress addr, String prefix) throws IllegalArgumentException, IOException {
		HANDLER_URL = prefix;
		HANDLER_ROOT = new RootHandler(HANDLER_URL);
		HANDLER_IMAGE = new ImageHandler(HANDLER_URL + "/image");
		HANDLER_PAGE = new PageHandler();
		HANDLER_UPLOAD = new UploadHandler();
		
		SERVER = HttpServer.create(addr, 0);
		SERVER.createContext(HANDLER_URL + "/page", HANDLER_PAGE);
		SERVER.createContext(HANDLER_URL + "/upload", HANDLER_UPLOAD);
		SERVER.createContext(HANDLER_URL + "/image", HANDLER_IMAGE);
		SERVER.createContext(HANDLER_URL, HANDLER_ROOT);
		SERVER.setExecutor(null);
	}
	
	public void run() {
		SERVER.start();
	}
	
	public static void main(String [] args) {
		String host = null;
		int port = -1;
		String prefix = null;
		for (int i = 0; i < args.length; i++) {
			if ("-port".equals(args[i]) && i < args.length - 1) {
				try {
					port = Integer.parseInt(args[i + 1]);
				} catch (Exception e) {
					e.printStackTrace();
				}
				i++;
			}
			if ("-prefix".equals(args[i]) && i < args.length - 1) {
				prefix = args[++i];
			}
			if ("-help".equals(args[i])) {
				printHelp();
			}
			if ("-host".equals(args[i]) && i < args.length - 1) {
				host = args[++i];
			}
		}
		if (port == -1) {
			System.out.println("Port not set. Using default port: 65000");
			port = 65000;
		}
		if (prefix == null) {
			System.out.println("Prefix not set. Using default: /SimpleBLOG");
			prefix = "/SimpleBLOG";
		}
		if (host == null) {
			System.out.println("Host not set. Using default: localhost");
			host = "localhost";
		}
		try {
			new Server(new InetSocketAddress(InetAddress.getByName(host), port), prefix).run();
		} catch (IllegalArgumentException | IOException e) {
			System.out.println("Error when executing the service.");
			e.printStackTrace();
		}
	}
	
	private static void printHelp() {
		System.out.println(
				  "This is a fairly simple web blog application."
				+ "The configuration is as follows:"
				+ "     -port [portNumber]    - sets the listen port (default 65000)"
				+ "     -prefix [URL]         - sets the URL prefix (default /SimpleBLOG)"
				+ "     -help                 - print this help"
				);
		System.exit(0);	
	}

}
