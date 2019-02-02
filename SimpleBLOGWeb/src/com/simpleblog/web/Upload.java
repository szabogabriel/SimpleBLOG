package com.simpleblog.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jmtemplate.Template;
import com.simpleblog.Main;
import com.simpleblog.utils.TemplateLoader;

public class Upload extends PageBase {
	
	private static final Template TEMPLATE = TemplateLoader.INSTANCE.load("upload.template");
	
	private static class Message {
		public static final Message NULL_OBJECT = new Message("", "", MessageType.NONE);
		
		private String header;
		private String message;
		private MessageType type;
		public Message(String header, String message, MessageType type) {
			this.header = header;
			this.message = message;
			this.type = type;
		}
		public String getHeader() {
			return header;
		}
		
		public String getMessage() {
			return message;
		}
		public MessageType getType() {
			return type;
		}
	}
	private enum MessageType {
		NONE,
		SUCCESS,
		ERROR,
		;
	}
	
	public void doGet(Response response) throws IOException {
		doGet(response, Message.NULL_OBJECT);
	}
	
	public void doGet(Response response, Message message) throws IOException {
		Map<String, Object> data = new HashMap<>();
		
		data.put("existingCategories", getCategoriesData());
		
		if (message.type != MessageType.NONE) {
			data.put(getMessageTypeKey(message), getMessageData(message));
		}
		
		String toReturn = TEMPLATE.render(data);
		response.setContentType("text/html");
		response.getOutputStream().write(toReturn.getBytes());
	}
	
	public void doPost(UploadRequest request, Response response) throws IOException {
		Message message = new Message("Ooops!", "The file couldn't be uploaded.", MessageType.ERROR);
		
		if (isUserCorrect(request.getUsername(), request.getPassword())) {
			checkCategory(request.getCategory());
			Main.INSTANCE.getEntriesManager().createEntry(request.getCategory(), request.getFileName(), request.getFile());
			message = new Message("Success!", "The file " + request.getFileName() + "was uploaded successfully.", MessageType.SUCCESS);
		}
		
		doGet(response, message);
	}
	
	private void checkCategory(String category) {
		if (!Main.INSTANCE.getEntriesManager().isKnownCategory(category)) {
			Main.INSTANCE.getEntriesManager().createCategory(category);
		}
	}
	
	private List<Map<String, Object>> getCategoriesData() {
		List<Map<String, Object>> ret = getCategories(true);
		
		return ret;
	}
	
	private String getMessageTypeKey(Message message) {
		String ret;
		switch (message.getType()) {
		case ERROR: ret = "messages_error"; break;
		case NONE: ret = ""; break;
		case SUCCESS: ret = "messages_success"; break;
		default: ret = ""; break;
		}
		return ret;
	}
	
	private Map<String, Object> getMessageData(Message m) {
		Map<String, Object> ret = new HashMap<>();
		ret.put("message_title", m.getHeader());
		ret.put("message_body", m.getMessage());
		return ret;
	}
	
	private boolean isUserCorrect(String uname, String psswd) {
		boolean ret = false;
		
		return ret;
	}

}
