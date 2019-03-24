package com.simpleblog.web;

public enum MessageType {
	NONE(""),
	SUCCESS("messages_success"),
	ERROR("messages_error"),
	;
	
	private final String KEY;
	
	private MessageType(String key) {
		this.KEY = key;
	}
	
	public String getKey() {
		return KEY;
	}
}
