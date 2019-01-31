package com.simpleblog.minihttp;

import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;

import com.simpleblog.minihttp.upload.ApacheCommonsUploadHandler;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

public class Server {
	
	private final String HANDLER_URL;
	private final File TARGET_FOLDER;
	private final HttpServer SERVER;
	private final HttpHandler HANDLER;
	
	public Server(int port, File targetDir, String prefix) throws IllegalArgumentException, IOException {
		HANDLER_URL = prefix;
		TARGET_FOLDER = targetDir;
		HANDLER = new ApacheCommonsUploadHandler(HANDLER_URL, TARGET_FOLDER);
		SERVER = HttpServer.create(new InetSocketAddress(port), 0);
		SERVER.createContext(prefix, HANDLER);
		SERVER.setExecutor(null);
	}
	
	public void run() {
		SERVER.start();
	}
	
	public static void main(String [] args) {
		int port = -1;
		File uploadDir = null;
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
			if ("-dir".equals(args[i]) && i < args.length - 1) {
				uploadDir = new File(args[++i]);
			}
			if ("-prefix".equals(args[i]) && i < args.length - 1) {
				prefix = args[++i];
			}
			if ("-help".equals(args[i])) {
				printHelp();
			}
		}
		if (port == -1) {
			System.out.println("Port not set. Using default port: 65000");
			port = 65000;
		}
		if (uploadDir == null) {
			System.out.println("Upload dir not set. Using default: ./upload");
			uploadDir = new File("./upload");
		}
		if (!uploadDir.exists()) {
			System.out.println("Upload dir doesn't exist. Creating it. " + uploadDir.getAbsolutePath());
			uploadDir.mkdirs();
		}
		if (prefix == null) {
			System.out.println("Prefix not set. Using default: /upload");
			prefix = "/upload";
		}
		try {
			new Server(port, uploadDir, prefix).run();
		} catch (IllegalArgumentException | IOException e) {
			System.out.println("Error when executing the service.");
			e.printStackTrace();
		}
	}
	
	private static void printHelp() {
		System.out.println(
				  "This is a fairly simple web application responsible for uploads."
				+ "The configuration is as follows:"
				+ "       -port [portNumber]    - sets the listen port (default 65000)"
				+ "       -prefix [URL]         - sets the URL prefix (default /upload)"
				+ "       -dir [targetDir]      - sets the target dir (defualt ./upload)"
				+ "       -help                 - print this help"
				);
		System.exit(0);	
	}

}
