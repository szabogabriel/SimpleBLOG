package com.simpleblog.servlet;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.simpleblog.web.Response;
import com.simpleblog.web.Upload;

@WebServlet("/upload")
@MultipartConfig
public class UploadPage extends HttpServlet {

	private static final long serialVersionUID = 3119479271119782795L;
	
	private Upload upload = new Upload();
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		upload.doGet(request.getQueryString(), new Response() {

			@Override
			public void setContentType(String type) {
				response.setContentType(type);
			}

			@Override
			public OutputStream getOutputStream() {
				try {
					return response.getOutputStream();
				} catch (IOException e) {
					e.printStackTrace();
				}
				return null;
			}
		});
	}

}
