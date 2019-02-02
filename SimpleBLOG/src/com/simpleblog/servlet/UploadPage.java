package com.simpleblog.servlet;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.simpleblog.web.Response;
import com.simpleblog.web.Upload;
import com.simpleblog.web.UploadRequest;

@WebServlet("/upload")
@MultipartConfig
public class UploadPage extends HttpServlet {

	private static final long serialVersionUID = 3119479271119782795L;
	
	private Upload upload = new Upload();
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		upload.doGet(new Response() {

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
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String category = getCategory(request.getParameter("categoryNew"), request.getParameter("categoryExisting"));
	    
		Part filePart = request.getPart("file");
	    String fileName = getSubmittedFileName(filePart);
	    File temporaryFile = getFile(filePart.getInputStream());
	    
	    UploadRequest uploadRequest = new UploadRequest(username, password, category, temporaryFile, fileName);
	    
		upload.doPost(uploadRequest, new Response() {
			
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
	
	private String getCategory(String newCategory, String oldCategory) {
		String ret = null;
		if (oldCategory != null && oldCategory.trim().length() > 0) {
			ret = oldCategory;
		} else {
			if (newCategory != null && newCategory.trim().length() > 0) {
				ret = newCategory;
			}
		}
		return ret;
	}
	
	private String getSubmittedFileName(Part part) {
	    for (String cd : part.getHeader("content-disposition").split(";")) {
	        if (cd.trim().startsWith("filename")) {
	            String fileName = cd.substring(cd.indexOf('=') + 1).trim().replace("\"", "");
	            return fileName.substring(fileName.lastIndexOf('/') + 1).substring(fileName.lastIndexOf('\\') + 1); // MSIE fix.
	        }
	    }
	    return null;
	}

	private File getFile(InputStream in) {
		return null;
	}
}
