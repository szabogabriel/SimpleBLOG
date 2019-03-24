package com.simpleblog.servlet;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.simpleblog.web.Response;
import com.simpleblog.web.page.Page;

@WebServlet("/page")
public class ServletPage extends HttpServlet {

	private static final long serialVersionUID = 9022332293020565015L;
	
	private Page page = new Page();

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		page.doGet(request.getQueryString(), new Response() {
			
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

			@Override
			public void sendResponse(int code, long length) {
				response.setStatus(code);
				response.setContentLength((int)length);
			}
		});
	}
}
