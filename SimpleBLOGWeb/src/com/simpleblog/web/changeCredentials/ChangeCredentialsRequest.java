package com.simpleblog.web.changeCredentials;

public class ChangeCredentialsRequest {
	
	private final String USERNAME;
	private final String OLD_PASSWORD;
	private final String NEW_PASSWORD;
	
	public ChangeCredentialsRequest(String username, String oldPassword, String newPassword) {
		this.USERNAME = username;
		this.OLD_PASSWORD = oldPassword;
		this.NEW_PASSWORD = newPassword;
	}
	
	public String getUsername() {
		return USERNAME;
	}
	
	public String getOldPassword() {
		return OLD_PASSWORD;
	}
	
	public String getNewPassword() {
		return NEW_PASSWORD;
	}

}
