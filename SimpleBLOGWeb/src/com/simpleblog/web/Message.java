package com.simpleblog.web;

import java.util.HashMap;
import java.util.Map;

public class Message {
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
	
	public Map<String, Object> getRenderableData() {
		Map<String, Object> ret = new HashMap<>();
		ret.put("message_title", getHeader());
		ret.put("message_body", getMessage());
		return ret;
	}
}
