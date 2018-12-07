package com.simpleblog.web;

import java.io.OutputStream;

public interface Response {
	
	void setContentType(String type);
	OutputStream getOutputStream();

}
