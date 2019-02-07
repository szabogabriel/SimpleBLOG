package com.simpleblog.users;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import com.simpleblog.CoreConfig;
import com.simpleblog.utils.Random;

public class BasicUserManager implements UserManager {
	
	private final File USER_FILE;
	
	private final Properties PROPS = new Properties();
	
	public BasicUserManager() {
		this(new File(CoreConfig.DIR_USERS.toString()));
	}
	
	public BasicUserManager(File userFile) {
		File target = userFile;
		if (target.isDirectory()) {
			target = new File(target.getAbsolutePath() + "/users.properties");
		}
		USER_FILE = target;
		if (USER_FILE.exists() && USER_FILE.isFile()) {
			try {
				PROPS.load(new FileInputStream(USER_FILE));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		checkUsers();
	}
	
	private void checkUsers() {
		getUsers().stream().forEach(U -> {
			if (!PROPS.containsKey(U + ".salt")) {
				String salt = generateSalt();
				PROPS.put(saltKey(U), salt);
				PROPS.put(passwordKey(U), generatePasswordHash(getPassword(U), salt));
				try {
					persist();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	private Set<String> getUsers() {
		Set<String> users = new HashSet<>();
		PROPS.keySet().stream()
			.map(K -> K.toString())
			.forEach(K -> {
				users.add(K.substring(0, K.lastIndexOf(".")));
			});
		return users;
	}

	@Override
	public boolean isKnownUsername(String username) {
		return 
				isValidUsername(username) && 
				PROPS.containsKey(saltKey(username));
	}

	@Override
	public boolean isCorrectCredentials(String username, String password) {
		boolean ret = false;
		if (isValidUsername(username) && isValidPassword(password)) {
			String salt = getSalt(username);
			String generatedPasswordHash = generatePasswordHash(password, salt);
			ret = generatedPasswordHash.equals(getPassword(username));
		}
		return ret;
	}

	@Override
	public boolean createUser(String username, String password) {
		boolean ret = false;
		if (isValidUsername(username) && isValidPassword(password)) {
			try {
				if (!isKnownUsername(username)) {
					String salt = generateSalt();
					PROPS.put(saltKey(username), salt);
					PROPS.put(passwordKey(username), generatePasswordHash(password, salt));
					persist();
				}
				ret = true;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return ret;
	}

	@Override
	public boolean removeUser(String username, String password) {
		boolean ret = false;
		if (isValidUsername(username) && isValidPassword(password)) {
			try {
				if (isKnownUsername(username) && isCorrectCredentials(username, password)) {
					PROPS.remove(saltKey(username));
					PROPS.remove(passwordKey(username));
					persist();
					ret = true;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return ret;
	}
	
	@Override
	public boolean changePassword(String username, String newPassword) {
		boolean ret = false;
		if (isValidUsername(username) && isValidPassword(newPassword)) {
			try {
				if (isKnownUsername(username)) {
					String salt = PROPS.getProperty(saltKey(username));
					String hash = generatePasswordHash(newPassword, salt);
					PROPS.put(passwordKey(username), hash);
					persist();
				}
				ret = true;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return ret;
	}
	
	private String generatePasswordHash(String password, String salt) {
		return ((password + salt).hashCode() + "");
	}
	
	private String generateSalt() {
		return Random.salt(5);
	}
	
	private void persist() throws FileNotFoundException, IOException {
		PROPS.store(new FileOutputStream(USER_FILE), "");
	}
	
	private String getPassword(String user) {
		return PROPS.getProperty(passwordKey(user));
	}
	
	private String getSalt(String user) {
		return PROPS.getProperty(saltKey(user));
	}
	
	private String saltKey(String user) {
		return user + ".salt";
	}
	
	private String passwordKey(String user) {
		return user + ".password";
	}

	private boolean isValidUsername(String username) {
		return
				username != null &&
				username.length() > 5;
	}
	
	private boolean isValidPassword(String password) {
		return 
				password != null &&
				password.length() > 5;
	}
}
