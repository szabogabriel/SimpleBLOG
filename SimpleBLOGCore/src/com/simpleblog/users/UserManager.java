package com.simpleblog.users;

import com.simpleblog.CoreConfig;

public interface UserManager {

	boolean isKnownUsername(String username);
	
	boolean isCorrectCredentials(String username, String password);
	
	boolean createUser(String username, String password);
	
	boolean removeUser(String username, String password);
	
	boolean changePassword(String username, String newPassword);
	
	public static class Factory {
		public static final Factory INSTANCE = new Factory();
		
		public UserManager getUserManager() {
			UserManager ret = null;
			
			String className = CoreConfig.USER_MANAGER.toString();
			
			try {
				ret = (UserManager)Class.forName(className).newInstance();
			} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
				e.printStackTrace();
			}
			
			return ret;
		}
	}
	
}
